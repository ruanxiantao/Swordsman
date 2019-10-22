package com.swordsman.common.jpa.base;

import com.swordsman.common.jpa.entity.CurrencyJpaSearch;
import com.swordsman.common.base.PageRequest;
import com.swordsman.common.jpa.entity.CurrencyJpaGroup;
import com.swordsman.common.valid.ValidList;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 12:08
 * Jpa Base Controller
 */
@NoRepositoryBean
public abstract class BaseJpaController<T extends BaseJpaEntity, M extends BaseJpaService> {

    @Autowired
    protected M baseService;

    @ApiOperation("保存实体")
    @PostMapping("/")
    public ApiResult save(@Valid @RequestBody T entity) {
        return new ApiResult(baseService.save(entity));
    }

    @ApiOperation("根据ID删除实体")
    @DeleteMapping("/{id}")
    public ApiResult deleteById(@PathVariable String id) {
        baseService.delete(id);
        return new ApiResult(Status.SUCCESS);
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

    @ApiOperation("批量保存")
    @PostMapping("/entities")
    public ApiResult saveEntities(@Valid @RequestBody ValidList<T> entities) {
        return new ApiResult(baseService.save(entities));
    }

    @ApiOperation("批量删除")
    @DeleteMapping("/deleteBatch")
    public ApiResult deleteEntities(@RequestBody List<T> entities) {
        baseService.deleteInBatch(entities);
        return new ApiResult(Status.SUCCESS);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public ApiResult page(@ApiParam("分页参数") @RequestBody PageRequest pageRequest) {
        return new ApiResult(baseService.page(pageRequest));
    }

    @ApiOperation("统计数据总数")
    @GetMapping("/count")
    public ApiResult count() {
        return new ApiResult(baseService.count());
    }

    @ApiOperation("多功能通用统计")
    @PostMapping("/currencyCount")
    public ApiResult currencyCount(@RequestBody CurrencyJpaSearch currencyJpaSearch) {
        return new ApiResult(baseService.currencyJpaCountSearch(currencyJpaSearch));
    }

    @ApiOperation("多功能通用查询")
    @PostMapping("/currencySearch")
    public ApiResult currencyJpaSearch(@RequestBody CurrencyJpaSearch currencyJpaSearch) {
       return new ApiResult(baseService.currencyJpaPageSearch(currencyJpaSearch));
    }

    @ApiOperation("多功能通用聚合")
    @PostMapping("/currencyGroup")
    public ApiResult currencyGroup(@RequestBody CurrencyJpaGroup currencyJpaGroup) {
        return new ApiResult(baseService.currencyGroup(currencyJpaGroup));
    }

}
