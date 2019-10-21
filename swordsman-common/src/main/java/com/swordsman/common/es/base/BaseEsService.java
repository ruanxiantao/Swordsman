package com.swordsman.common.es.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.swordsman.common.base.*;
import com.swordsman.common.es.core.CurrencyEsSearch;
import com.swordsman.common.es.core.Highlight;
import com.swordsman.common.exception.CustomException;
import com.swordsman.common.redis.RedisConstant;
import com.swordsman.common.redis.RedisUtil;
import com.swordsman.common.util.BeanUtil;
import com.swordsman.common.web.PageResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.scheduling.annotation.Async;

import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:48
 * Base ES Service
 */
public class BaseEsService<T extends BaseEsEntity, M extends BaseEsDao<T>> {

    /**
     * 基础 Repository
     */
    @Autowired
    protected M baseDao;

    @Autowired
    protected RedisUtil redisUtil;

    @Autowired
    protected Snowflake snowflake;

    /**
     * ES 客户端模板
     */
    @Autowired
    protected ElasticsearchTemplate template;

    /**
     * 获取泛型 Class 类
     */
    ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
    Class<T> tClass = (Class<T>) type.getActualTypeArguments()[0];

    /**
     * 通过 Id 查询实体
     */
    public T findById(String id) {
        if (redisUtil.get(RedisConstant.ES + id) != null)
            return (T) redisUtil.get(RedisConstant.ES + id);
        Optional<T> byId = baseDao.findById(id);
        if (!byId.isPresent())
            throw new CustomException("【Id 没有对应数据 \t】" + id);
        redisUtil.setEx(RedisConstant.ES + id,byId.get());
        return byId.get();
    }

    /**
     * 保存实体
     */
    public T save(T entity) {
        entity.setId(snowflake.nextIdStr());
        entity.setCreateTime(new Date());
        return baseDao.save(entity);
    }

    /**
     * 根据 Id 修改实体
     */
    public T update(String id, T entity) {
        T byId = findById(id);
        BeanUtils.copyProperties(entity, byId, BeanUtil.getNullPropertyNames(entity));
        byId.setUpdateTime(new Date());
        redisUtil.delete(RedisConstant.ES + id);
        return baseDao.save(byId);
    }

    public Iterable<T> findAll() {
        return baseDao.findAll();
    }

    /**
     * 批量保存
     */
    public Iterable<T> saveEntities(List<T> entities) {
        Iterator iterator = entities.iterator();
        while (iterator.hasNext()) {
            T entity = (T) iterator.next();
            entity.setCreateTime(new Date());
            entity.setId(snowflake.nextIdStr());
        }
        return baseDao.saveAll(entities);
    }

    /**
     * 根据 Id 删除
     */
    @Async
    public void delete(String id) {
        redisUtil.delete(RedisConstant.ES + id);
        baseDao.deleteById(id);
    }

    /**
     * 统计数量
     */
    public Long count() {
        return baseDao.count();
    }


    /**
     * 多功能条件统计
     */
    public Long currencyEsCount(CurrencyEsSearch search) {
        // 条件拼接
        SearchQuery searchQuery = baseCurrencyEsSearch(search);
        return template.count(searchQuery, tClass);
    }

    /**
     * 多功能条件查询
     */
    public PageResult currencyEsSearch(CurrencyEsSearch search) {

        // 条件拼接
        SearchQuery searchQuery = baseCurrencyEsSearch(search);

        AggregatedPage<T> result;

        if (search != null && CollUtil.isNotEmpty(search.getHighlight())) {
            // 高亮结果处理
            result = template.queryForPage(searchQuery, tClass, new SearchResultMapper() {
                @Override
                public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {

                    List<T> results = new ArrayList<>();
                    Arrays.stream(response.getHits().getHits()).forEach(q -> {

                        Map<String, Object> map = q.getSourceAsMap();
                        search.getHighlight().forEach(v -> {
                            if (StrUtil.isNotEmpty(v.getHighlightBy())) {
                                HighlightField hig = q.getHighlightFields().get(v.getHighlightBy());
                                if(hig != null){
                                    String result = hig.fragments()[0].toString();
                                    map.put(v.getHighlightBy(), result);
                                }
                            }
                        });
                        results.add((T) BeanUtil.map2Object(map, clazz));
                    });
                    return new AggregatedPageImpl<>(results, pageable, response.getHits().totalHits);
                }
            });
        } else {
            result = template.queryForPage(searchQuery, tClass);
        }

        return PageResult.process(result);
    }

    /**
     * ES 条件封装
     */
    public SearchQuery baseCurrencyEsSearch(CurrencyEsSearch search) {

        // 构建 ES 查询对象
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();

        // 构建 条件 查询对象
        BoolQueryBuilder boolQuery = new BoolQueryBuilder();

        // 判断参数是否为空
        if (search == null) {
            searchQuery.withPageable(new PageRequest().pageable());
        } else {
            // 判断查询字段条件
            String[] source = search.getSource();
            if (ArrayUtil.isNotEmpty(source))
                searchQuery.withSourceFilter(new FetchSourceFilter(source, null));

            // 判断分页排序条件
            PageRequest pageOrder = search.getPageOrder();
            if (pageOrder != null)
                searchQuery.withPageable(pageOrder.pageable());
            else
                searchQuery.withPageable(new PageRequest().pageable());

            // 判断 Query 条件
            List<Condition> query = search.getQuery();
            if (CollUtil.isNotEmpty(query))
                query.forEach(v -> {
                    if (StrUtil.isNotBlank(v.getField()) && v.getValue() != null)
                        boolQuery.must(new TermQueryBuilder(v.getField(), v.getValue()));
                });

            // 判断 In 条件
            List<InCondition> in = search.getIn();
            if (CollUtil.isNotEmpty(in))
                in.forEach(v -> {
                    if (StrUtil.isNotBlank(v.getField()) && v.getValue() != null)
                        boolQuery.must(new TermsQueryBuilder(v.getField(), v.getValue()));
                });

            // 判断 KeyWord<分词> 条件
            List<Condition> keyword = search.getKeyword();
            if (CollUtil.isNotEmpty(keyword)){
                keyword.forEach(v -> {
                    if (StrUtil.isNotBlank(v.getField()) && v.getValue() != null)
                        boolQuery.should(new MatchQueryBuilder(v.getField(), v.getValue()));
                });
                boolQuery.minimumShouldMatch(1);
            }

            // 判断 Like 条件
            List<Condition> like = search.getLike();
            if (CollUtil.isNotEmpty(like))
                like.forEach(v -> {
                    if (StrUtil.isNotBlank(v.getField()) && v.getValue() != null)
                        boolQuery.must(new WildcardQueryBuilder(v.getField(), v.getValue().toString()));
                });

            // 判断 Not 查询条件
            List<Condition> not = search.getNot();
            if (CollUtil.isNotEmpty(not))
                not.forEach(v -> {
                    if (StrUtil.isNotBlank(v.getField()) && v.getValue() != null)
                        boolQuery.mustNot(new TermQueryBuilder(v.getField(), v.getValue()));
                });

            // 判断 Between 条件
            List<Between> between = search.getBetween();
            if (CollUtil.isNotEmpty(between))
                between.forEach(v -> {
                    if (StrUtil.isNotBlank(v.getField()) && v.getStart() != null && v.getEnd() != null)
                        boolQuery.must(new RangeQueryBuilder(v.getField()).gte(v.getStart()).lte(v.getEnd()));
                });

            // 判断 DataParam 条件
            DateParam dateParam = search.getDateParam();
            if (dateParam != null && StrUtil.isNotBlank(dateParam.getStartDate()) && StrUtil.isNotBlank(dateParam.getEndDate()))
                boolQuery.must(new RangeQueryBuilder(dateParam.getField()).gte(dateParam.start().getTime()).lte(dateParam.end().getTime()));

            // 判断 HighLight 条件
            List<Highlight> highlight = search.getHighlight();
            if (CollUtil.isNotEmpty(highlight)) {
                List<HighlightBuilder.Field> list = new ArrayList<>();
                highlight.forEach(v ->
                        list.add(new HighlightBuilder
                                .Field(v.getHighlightBy())
                        )
                );
                searchQuery.withHighlightFields(list.toArray(new HighlightBuilder.Field[list.size()]));
            }

            // 最后整合到一个对象
            searchQuery.withQuery(boolQuery);
        }

        return searchQuery.build();
    }

}
