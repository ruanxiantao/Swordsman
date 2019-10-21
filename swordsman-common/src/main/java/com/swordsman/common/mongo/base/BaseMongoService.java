package com.swordsman.common.mongo.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.swordsman.common.base.*;
import com.swordsman.common.exception.CustomException;
import com.swordsman.common.mongo.constant.SearchMethod;
import com.swordsman.common.mongo.core.CurrencyMongoSearch;
import com.swordsman.common.redis.RedisConstant;
import com.swordsman.common.redis.RedisUtil;
import com.swordsman.common.util.BeanUtil;
import com.swordsman.common.web.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:19
 * Mongo Base Service
 */
public class BaseMongoService<T extends BaseMongoEntity, M extends BaseMongoDao<T>>{

    @Autowired
    protected M baseDao;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    protected MongoTemplate mongoTemplate;

    // 获取泛型实体类
    ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
    Class<T> tClass = (Class) type.getActualTypeArguments()[0];

    /**
     * 分页查询
     */
    public PageResult page(PageRequest pageRequest) {
        Page<T> all = baseDao.findAll(pageRequest.pageable());
        return PageResult.process(all);
    }

    /**
     * 根据 Id 修改对象
     */
    public T update(String id, T entity) {
        T t = findById(id);
        BeanUtils.copyProperties(entity,t, BeanUtil.getNullPropertyNames(entity));
        t.setUpdateTime(new Date());
        redisUtil.delete(RedisConstant.MONGO + id);
        return this.baseDao.save(t);
    }

    /**
     * 保存对象
     */
    public T save(T entity) {
        entity.setId(snowflake.nextIdStr());
        entity.setCreateTime(new Date());
        return baseDao.save(entity);
    }

    /**
     * 批量保存
     */
    public List<T> saveEntities(List<T> entities) {
        Iterator var2 = entities.iterator();
        while (var2.hasNext()) {
            T entity = (T) var2.next();
            entity.setId(snowflake.nextIdStr());
            entity.setCreateTime(new Date());
        }
        return baseDao.insert(entities);
    }

    /**
     * 根据 Id 删除
     */
    @Async
    public void deleteEntityById(String id) {
        redisUtil.delete(RedisConstant.MONGO + id);
        baseDao.deleteById(id);
    }

    /**
     * 根据 ID 查找
     */
    public T findById(String id){
        if (redisUtil.get(RedisConstant.MONGO + id) != null)
            return (T) redisUtil.get(RedisConstant.MONGO + id);
        Optional<T> byId = baseDao.findById(id);
        if (!byId.isPresent())
            throw new CustomException("【Id 没有对应数据】\t" + id);
        redisUtil.setEx(RedisConstant.MONGO + id,byId.get());
        return byId.get();
    }

    /**
     * 批量删除
     */
    @Async
    public void deleteEntities(List<T> entities) {
        baseDao.deleteAll(entities);
    }

    /**
     * 清空集合
     */
    public void dropCollection() {
        mongoTemplate.dropCollection(tClass);
    }

    /**
     * 查询所有
     * @return
     */
    public List<T> findAll() {
        return baseDao.findAll();
    }

    /**
     * 总数
     */
    public Long count() {
        return baseDao.count();
    }

    /**
     * 多功能统计
     */
    public Long currencyCount(CurrencyMongoSearch currencyMongoSearch) {
        Query query = basecurrencyMongoSearch(currencyMongoSearch);
        return mongoTemplate.count(query, tClass);
    }

    /**
     * 多功能查询,指定字段，返回Map
     */
    public PageResult currencyMongoSearchMap(CurrencyMongoSearch currencyMongoSearch) {
        Query query = basecurrencyMongoSearch(currencyMongoSearch);

        // 通过class获取注解，并拿到值
        Document annotation = tClass.getAnnotation(Document.class);
        String collection = annotation.collection();

        List<HashMap> collect = mongoTemplate.find(query, HashMap.class, collection);

        Long count = mongoTemplate.count(query, collection);

        PageImpl page = new PageImpl(collect,currencyMongoSearch.getPageOrder().pageable(),count);
        return PageResult.process(page);
    }

    /**
     * 多功能查询
     */
    public PageResult currencyMongoSearch(CurrencyMongoSearch currencyMongoSearch) {
        Query query = basecurrencyMongoSearch(currencyMongoSearch);
        List<? extends BaseMongoEntity> list  = mongoTemplate.find(query,tClass);
        Long count = mongoTemplate.count(query, tClass);
        PageImpl page = new PageImpl(list,currencyMongoSearch.getPageOrder().pageable(),count);
        return PageResult.process(page);
    }

    /**
     * 多功能条件构造器
     */
    private Query basecurrencyMongoSearch(CurrencyMongoSearch currencyMongoSearch) {

        // 初始化查询对象
        Query query;

        // 初始化查询构建器
        QueryBuilder queryBuilder;

        // 如果连条件都为空则初始化
        if (currencyMongoSearch == null) {
            query = new Query();
            PageRequest pageOrder = new PageRequest();
            query.with(pageOrder.pageable());
            currencyMongoSearch.setPageOrder(pageOrder);
            return query;
        } else {
            // 是否查询所有字段，为空默认查询所有
            if (CollUtil.isEmpty(currencyMongoSearch.getSource()))
                query = new Query();
            else {
                // 不为空进行赋值条件
                queryBuilder = new QueryBuilder();
                BasicDBObject basicDBObject = new BasicDBObject();
                currencyMongoSearch.getSource().forEach(v -> basicDBObject.put(v, 1));
                // 初始化query对象
                query = new BasicQuery(queryBuilder.get().toString(), basicDBObject.toJson());
            }

            // 判断查询 equal 条件
            if (CollUtil.isNotEmpty(currencyMongoSearch.getQuery())) {
                List<Condition> querys = currencyMongoSearch.getQuery();
                //循环遍历需要查询的条件，并且写入query查询
                querys.forEach(v -> {
                    if (StrUtil.isBlank(v.getField()))
                        throw new CustomException("【CurrencyMongoSearch】【Query】【Field】不能为空");
                    if (v.getValue() == null)
                        throw new CustomException("【CurrencyMongoSearch】【Query】【Value】不能为空");
                    query.addCriteria(Criteria.where(v.getField()).is(v.getValue()));
                });
            }

            // 判断查询 In 条件
            if (CollUtil.isNotEmpty(currencyMongoSearch.getIn())) {
                List<InCondition> ins = currencyMongoSearch.getIn();
                //循环遍历需要查询的条件，并且写入query查询
                ins.forEach(v -> {
                    if (StrUtil.isBlank(v.getField()))
                        throw new CustomException("【CurrencyMongoSearch】【In】【Field】不能为空");
                    if (CollUtil.isEmpty(v.getValue()))
                        throw new CustomException("【CurrencyMongoSearch】【In】【Value】不能为空");
                    query.addCriteria(Criteria.where(v.getField()).in(v.getValue()));
                });
            }

            // 判断查询 NotIn 条件
            if (CollUtil.isNotEmpty(currencyMongoSearch.getNotIn())) {
                List<InCondition> notIns = currencyMongoSearch.getNotIn();
                //循环遍历需要查询的条件，并且写入query查询
                notIns.forEach(v -> {
                    if (StrUtil.isBlank(v.getField()))
                        throw new CustomException("【CurrencyMongoSearch】【Not In】【Field】不能为空");
                    if (CollUtil.isEmpty(v.getValue()))
                        throw new CustomException("【CurrencyMongoSearch】【Not In】【Value】不能为空");
                    query.addCriteria(Criteria.where(v.getField()).nin(v.getValue()));
                });
            }

            // 判断查询 Like 条件
            if (CollUtil.isNotEmpty(currencyMongoSearch.getLike())) {
                List<Condition> likes = currencyMongoSearch.getLike();
                //循环遍历需要查询的条件，并且写入query查询
                likes.forEach(v -> {
                    if (StrUtil.isBlank(v.getField()))
                        throw new CustomException("【CurrencyMongoSearch】【Like】【Field】不能为空");
                    if (v.getValue() == null)
                        throw new CustomException("【CurrencyMongoSearch】【Like】【Value】不能为空");
                    query.addCriteria(Criteria.where(v.getField()).regex(SearchMethod.pattern(v.getValue().toString())));
                });
            }

            // 判断查询 Not 条件
            if (CollUtil.isNotEmpty(currencyMongoSearch.getNot())) {
                List<Condition> nots = currencyMongoSearch.getNot();
                //循环遍历需要查询的条件，并且写入query查询
                nots.forEach(v -> {
                    if (StrUtil.isBlank(v.getField()))
                        throw new CustomException("【CurrencyMongoSearch】【Not】【Field】不能为空");
                    if (v.getValue() == null)
                        throw new CustomException("【CurrencyMongoSearch】【Not】【Value】不能为空");
                    query.addCriteria(Criteria.where(v.getField()).ne(v.getValue()));
                });
            }

            // 判断查询 Between 条件
            if (CollUtil.isNotEmpty(currencyMongoSearch.getBetween())) {
                List<Between> betweens = currencyMongoSearch.getBetween();
                //循环遍历需要查询的条件，并且写入query查询
                betweens.forEach(v -> {
                    if (StrUtil.isBlank(v.getField()))
                        throw new CustomException("【CurrencyMongoSearch】【Between】【Field】不能为空");
                    if (v.getStart() == null)
                        throw new CustomException("【CurrencyMongoSearch】【Between】【Start】不能为空");
                    if (v.getEnd() == null)
                        throw new CustomException("【CurrencyMongoSearch】【Between】【End】不能为空");
                    query.addCriteria(Criteria.where(v.getField()).gte(v.getStart()));
                    query.addCriteria(Criteria.where(v.getField()).lte(v.getEnd()));
                });
            }

            // 判断查询 DataParam 条件
            if (currencyMongoSearch.getDateParam() != null) {
                DateParam dateParam = currencyMongoSearch.getDateParam();
                Criteria where = Criteria.where(dateParam.getField());
                if (StrUtil.isEmpty(dateParam.getStartDate()))
                    throw new CustomException("【CurrencyMongoSearch】【DataParam】【StartDate】不能为空");
                if (StrUtil.isEmpty(dateParam.getEndDate()))
                    throw new CustomException("【CurrencyMongoSearch】【DataParam】【EndData】不能为空");
                query.addCriteria(where.gte(dateParam.start()).lte(dateParam.end()));
            }

            PageRequest pageOrder;

            // 判断查询 PageAble 条件
            if (currencyMongoSearch.getPageOrder() != null) {
                pageOrder = currencyMongoSearch.getPageOrder();
                if (pageOrder.getLimit() == null)
                    throw new CustomException("【CurrencyMongoSearch】【Pageable】【Limit】不能为空");
                if (pageOrder.getPage() == null)
                    throw new CustomException("【CurrencyMongoSearch】【Pageable】【Page】不能为空");
            } else {
                pageOrder = new PageRequest();
                currencyMongoSearch.setPageOrder(pageOrder);
            }

            query.with(pageOrder.pageable());
            return query;
        }

    }



}
