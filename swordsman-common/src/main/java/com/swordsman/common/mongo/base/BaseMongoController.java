package com.swordsman.common.mongo.base;

import com.swordsman.common.base.PageRequest;
import com.swordsman.common.mongo.core.CurrencyMongoSearch;
import com.swordsman.common.valid.ValidList;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:43
 * Mongo Base Controller
 */
public abstract class BaseMongoController<T extends BaseMongoEntity,S extends BaseMongoService> {

    @Autowired
    protected S baseService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public ApiResult page(@ApiParam("分页参数") @RequestBody PageRequest pageRequest) {
        return new ApiResult(baseService.page(pageRequest));
    }

    @ApiOperation("根据id更新实体")
    @PutMapping("/{id}")
    public ApiResult update(@PathVariable String id, @RequestBody T entity) {
        return new ApiResult(baseService.update(id,entity));
    }

    @ApiOperation("根据id查询实体")
    @GetMapping("/{id}")
    public ApiResult findById(@PathVariable String id) {
        return new ApiResult(baseService.findById(id));
    }

    @ApiOperation("保存实体")
    @PostMapping("/")
    public ApiResult save(@Valid @RequestBody T entity) {
        return new ApiResult(baseService.save(entity));
    }

    @ApiOperation("批量保存")
    @PostMapping("/entities")
    public ApiResult saveEntities(@Valid @RequestBody ValidList<T> entities) {
        return new ApiResult(baseService.saveEntities(entities));
    }

    @ApiOperation("根据ID删除实体")
    @DeleteMapping("/{id}")
    public ApiResult deleteEntityById(@PathVariable String id) {
        baseService.deleteEntityById(id);
        return new ApiResult(Status.SUCCESS);
    }

    @ApiOperation("删除多个实体")
    @DeleteMapping("/deleteBatch")
    public ApiResult deleteEntities(@RequestBody List<T> entities) {
        this.baseService.deleteEntities(entities);
        return new ApiResult(Status.SUCCESS);
    }

    @ApiOperation("统计数据总数")
    @GetMapping("/count")
    public ApiResult count() {
        return new ApiResult(this.baseService.count());
    }

    @ApiOperation("多功能通用查询")
    @PostMapping("/currencySearch")
    public ApiResult currencyMongoSearch(@RequestBody CurrencyMongoSearch currencyMongoSearch) {
        return new ApiResult(baseService.currencyMongoSearch(currencyMongoSearch));
    }

    @ApiOperation("多功能通用统计")
    @PostMapping("/currencyCount")
    public ApiResult currencyCount(@RequestBody CurrencyMongoSearch currencyMongoSearch) {
        return new ApiResult(baseService.currencyCount(currencyMongoSearch));
    }

    @ApiOperation("多功能通用查询，指定字段返回Map")
    @PostMapping("/currencySearchMap")
    public ApiResult currencyMongoSearchMap(@RequestBody CurrencyMongoSearch currencyMongoSearch) {
       return new ApiResult(baseService.currencyMongoSearchMap(currencyMongoSearch));
    }


}
