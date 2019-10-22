package com.swordsman.common.jpa.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.swordsman.common.base.*;
import com.swordsman.common.exception.CustomException;
import com.swordsman.common.jpa.entity.CurrencyJpaGroup;
import com.swordsman.common.jpa.entity.CurrencyJpaSearch;
import com.swordsman.common.jpa.entity.SearchConstant;
import com.swordsman.common.redis.RedisConstant;
import com.swordsman.common.redis.RedisUtil;
import com.swordsman.common.util.BeanUtil;
import com.swordsman.common.web.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Order;
import javax.validation.Valid;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:55
 * Jpa Base Service
 */
@NoRepositoryBean
public class BaseJpaService<T extends BaseJpaEntity, M extends BaseJpaRepository<T>> {

    @Autowired
    protected M baseDao;

    @Autowired
    protected RedisUtil redisUtil;

    @Autowired
    protected Snowflake snowflake;

    @PersistenceContext
    protected EntityManager baseMg;

    // 获取泛型class类
    ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
    Class<T> tClass = (Class) type.getActualTypeArguments()[0];

    /**
     * 分页查询
     */
    public PageResult page(PageRequest page) {
        return PageResult.process(this.baseDao.findAll(page.pageable()));
    }

    /**
     * 通过 Id 查询对象
     */
    public T findById(String id) {
        Optional<T> byId = baseDao.findById(id);
        if (!byId.isPresent())
            throw new CustomException("【Id没有对应数据】\t" + id);
        redisUtil.setEx(RedisConstant.MYSQL + id,byId.get());
        return byId.get();
    }

    /**
     * 保存实体
     */
    public T save(T entity) {
        entity.setId(snowflake.nextIdStr());
        entity.setCreateTime(new Date());
        return baseDao.saveAndFlush(entity);
    }

    /**
     * 通过Id 修改实体类
     */
    public T update(String id, @Valid T entity) {
        T t = findById(id);
        BeanUtils.copyProperties(entity, t, BeanUtil.getNullPropertyNames(entity));
        t.setUpdateTime(new Date());
        redisUtil.delete(RedisConstant.MYSQL + id);
        return baseDao.saveAndFlush(t);
    }

    /**
     * 批量保存
     */
    public List<T> save(Iterable<T> entities) {
        Iterator iterator = entities.iterator();
        while (iterator.hasNext()) {
            T entity = (T) iterator.next();
            entity.setCreateTime(new Date());
            entity.setId(snowflake.nextIdStr());
        }
        return baseDao.saveAll(entities);
    }

    /**
     * 查询所有数据
     */
    public List<T> findAll() {
        return baseDao.findAll();
    }

    /**
     * 根据 Id 删除对象
     */
    @Async
    public void delete(String id) {
        redisUtil.delete(RedisConstant.MYSQL + id);
        baseDao.deleteById(id);
    }

    /**
     * 批量删除
     */
    @Async
    public void deleteInBatch(Iterable<T> entities) {
        baseDao.deleteInBatch(entities);
    }

    /**
     * 全局统计数据
     */
    public Long count() {
        return baseDao.count();
    }

    /**
     * 多功能条件查询
     */
    public PageResult currencyJpaPageSearch(CurrencyJpaSearch currencyJpaSearch) {
        Specification<T> specification = basecurrencyJpaSearch(currencyJpaSearch);
        Page<T> all;
        if (currencyJpaSearch == null || currencyJpaSearch.getPageOrder() == null) {
            PageRequest pageOrder = new PageRequest();
            all = baseDao.findAll(specification, pageOrder.pageable());
        } else {
            all = baseDao.findAll(specification, currencyJpaSearch.getPageOrder().pageable());
        }

        return PageResult.process(all);
    }

    /**
     * 多功能统计查询
     */
    public Long currencyJpaCountSearch(CurrencyJpaSearch currencyJpaSearch) {
        Long count;

        if (currencyJpaSearch == null)
            count = baseDao.count();
        else
            count = baseDao.count(basecurrencyJpaSearch(currencyJpaSearch));

        return count;
    }

    /**
     * 多功能聚合查询
     */
    public List currencyGroup(CurrencyJpaGroup currencyJpaGroup) {

        CriteriaBuilder builder = baseMg.getCriteriaBuilder();
        CriteriaQuery query = builder.createQuery();
        // 获取实体类
        Root<T> root = query.from(tClass);

        // 构建查询条件
        if (currencyJpaGroup.getCurrencyJpaSearch() != null) {
            List<Predicate> predicateList = getPredicateList(root, query, builder, currencyJpaGroup.getCurrencyJpaSearch());
            query.where(predicateList.toArray(new Predicate[predicateList.size()]));
        }

        // 构建聚合条件
        if (CollUtil.isNotEmpty(currencyJpaGroup.getOrders())) {
            List<Order> orders = new CopyOnWriteArrayList<>();
            currencyJpaGroup.getOrders().forEach(v -> {
                if (StrUtil.isBlank(v.getFiled()))
                    throw new CustomException("【currencyJpaGroup】【Order】【Field】不能为空");
                if (StrUtil.isBlank(v.getType()))
                    throw new CustomException("【currencyJpaGroup】【Order】【Type】不能为空");
                if (StrUtil.isBlank(v.getType()))
                    throw new CustomException("【currencyJpaGroup】【Order】【Direction】不能为空");

                if (SearchConstant.ORDER_DESC.equals(v.getDirection())) {
                    orders.add(builder.desc(getExpression(root, v.getType(), v.getFiled(), builder)));
                }
                if (SearchConstant.ORDER_ASC.equals(v.getDirection())) {
                    orders.add(builder.asc(getExpression(root, v.getType(), v.getFiled(), builder)));
                }
            });
            query.orderBy(orders);
        }

        // 构建 Source 条件
        if (CollUtil.isNotEmpty(currencyJpaGroup.getSource())) {
            // 创建需要查询的对象的集合
            List<Selection<?>> selections = new CopyOnWriteArrayList<>();
            // 循环遍历select
            currencyJpaGroup.getSource().forEach(v -> {
                if (StrUtil.isBlank(v.getField()))
                    throw new CustomException("【currencyJpaGroup】【Source】【Field】不能为空");
                Selection<?> selection = getExpression(root, v.getType(), v.getField(), builder);
                selections.add(selection);
            });
            query.multiselect(selections);
        }


        // 构建分组条件
        if (currencyJpaGroup.getGroupBy() != null) {
            List<Expression<?>> list = new CopyOnWriteArrayList<>();
            currencyJpaGroup.getGroupBy().forEach(v -> list.add(root.get(v.getField())));
            query.groupBy(list);
        }

        Query search = baseMg.createQuery(query);

        // 构建分页条件
        if (currencyJpaGroup.getCurrencyJpaSearch() != null) {
            if (currencyJpaGroup.getCurrencyJpaSearch().getPageOrder() != null) {
                PageRequest pageOrder = currencyJpaGroup.getCurrencyJpaSearch().getPageOrder();
                if (pageOrder.getPage() != null && pageOrder.getLimit() != null && pageOrder.getPage() > 0 && pageOrder.getLimit() > 0) {
                    search.setFirstResult((pageOrder.getPage() - 1) * pageOrder.getLimit());
                    search.setMaxResults(pageOrder.getLimit());
                }
            }
        }

        return search.getResultList();
    }

    /**
     * Jpa Specification 构造
     */
    private Specification<T> basecurrencyJpaSearch(CurrencyJpaSearch currencyJpaSearch) {
        Specification<T> specification = ((root, query, builder) -> {
            List<Predicate> predicateList = getPredicateList(root, query, builder, currencyJpaSearch);
            return builder.and(predicateList.toArray(new Predicate[predicateList.size()]));
        });
        return specification;
    }

    /**
     * Specification 条件构造
     */
    private List<Predicate> getPredicateList(Root<T> root, CriteriaQuery query, CriteriaBuilder builder, CurrencyJpaSearch currencyJpaSearch) {
        List<Predicate> predicateList = new CopyOnWriteArrayList<>();

        if (currencyJpaSearch == null)
            return predicateList;

        // 判断查询 equal 条件
        if (CollUtil.isNotEmpty(currencyJpaSearch.getQuery())) {
            List<Condition> querys = currencyJpaSearch.getQuery();
            // 循环遍历需要查询的条件，并且写入query查询
            querys.forEach(v -> {
                if (StrUtil.isBlank(v.getField()))
                    throw new CustomException("【currencyJpaSearch】【Query】【Field】不能为空");
                if (v.getValue() == null)
                    throw new CustomException("【currencyJpaSearch】【Query】【Value】不能为空");
                predicateList.add(builder.equal(root.get(v.getField()), v.getValue()));
            });
        }

        // 判断查询 In 条件
        if (CollUtil.isNotEmpty(currencyJpaSearch.getIn())) {
            List<InCondition> ins = currencyJpaSearch.getIn();
            // 循环遍历需要查询的条件，并且写入query查询
            ins.forEach(v -> {
                if (StrUtil.isBlank(v.getField()))
                    throw new CustomException("【currencyJpaSearch】【In】【Field】不能为空");
                if (CollUtil.isEmpty(v.getValue()))
                    throw new CustomException("【currencyJpaSearch】【In】【Value】不能为空");
                CriteriaBuilder.In<Object> in = builder.in(root.get(v.getField()));
                v.getValue().forEach(z -> in.value(z));
                predicateList.add(builder.and(in));
            });
        }

        // 判断查询 Like 条件
        if (CollUtil.isNotEmpty(currencyJpaSearch.getLike())) {
            List<Condition> likes = currencyJpaSearch.getLike();
            // 循环遍历需要查询的条件，并且写入query查询
            likes.forEach(v -> {
                if (StrUtil.isBlank(v.getField()))
                    throw new CustomException("【currencyJpaSearch】【Like】【Field】不能为空");
                if (v.getValue() == null)
                    throw new CustomException("【currencyJpaSearch】【Like】【Value】不能为空");
                predicateList.add(builder.and(builder.like(root.get(v.getField()), "%" + v.getValue().toString() + "%")));
            });
        }

        // 判断查询 Not 条件
        if (CollUtil.isNotEmpty(currencyJpaSearch.getNot())) {
            List<Condition> nots = currencyJpaSearch.getNot();
            //循环遍历需要查询的条件，并且写入query查询
            nots.forEach(v -> {
                if (StrUtil.isBlank(v.getField()))
                    throw new CustomException("【currencyJpaSearch】【Not】【Field】不能为空");
                if (v.getValue() == null)
                    throw new CustomException("【currencyJpaSearch】【Not】【Value】不能为空");
                predicateList.add(builder.notEqual(root.get(v.getField()), v.getValue()));
            });
        }

        // 判断查询 Between 条件
        if (CollUtil.isNotEmpty(currencyJpaSearch.getBetween())) {
            List<Between> betweens = currencyJpaSearch.getBetween();
            //循环遍历需要查询的条件，并且写入query查询
            betweens.forEach(v -> {
                if (StrUtil.isBlank(v.getField()))
                    throw new CustomException("【currencyJpaSearch】【Between】【Field】不能为空");
                if (v.getStart() == null)
                    throw new CustomException("【currencyJpaSearch】【Between】【Start】不能为空");
                if (v.getEnd() == null)
                    throw new CustomException("【currencyJpaSearch】【Between】【End】不能为空");
                predicateList.add(builder.ge(root.get(v.getField()), (Number) v.getStart()));
                predicateList.add(builder.le(root.get(v.getField()), (Number) v.getEnd()));
            });
        }

        // 判断查询 DataParam 条件
        if (currencyJpaSearch.getDateParam() != null) {
            DateParam dateParam = currencyJpaSearch.getDateParam();
            if (StrUtil.isEmpty(dateParam.getStartDate()))
                throw new CustomException("【currencyJpaSearch】【DataParam】【StartDate】不能为空");
            if (StrUtil.isEmpty(dateParam.getEndDate()))
                throw new CustomException("【currencyJpaSearch】【DataParam】【EndData】不能为空");
            predicateList.add(builder.between(root.get(dateParam.getField()), dateParam.start(), dateParam.end()));
        }

        return predicateList;
    }

    /**
     * 聚合统计
     */
    public Expression<?> getExpression(Root<?> root, String type, String filed, CriteriaBuilder builder) {
        if (StrUtil.isBlank(filed))
            return null;
        if (StrUtil.isBlank(type))
            return root.get(filed);
        else if (SearchConstant.GROUP_COUNT.equals(type))
            //查询数量              count
            return builder.count(root.get(filed));
        else if (SearchConstant.GROUP_SUM.equals(type))
            //查询求和              sum
            return builder.sum(root.get(filed));
        else if (SearchConstant.GROUP_AVG.equals(type))
            //查询平均数            avg
            return builder.avg(root.get(filed));
        else if (SearchConstant.GROUP_MAX.equals(type))
            //查询最大数            max
            return builder.max(root.get(filed));
        else if (SearchConstant.GROUP_MIN.equals(type))
            //查询最小数            min
            return builder.min(root.get(filed));
        return null;
    }

}
