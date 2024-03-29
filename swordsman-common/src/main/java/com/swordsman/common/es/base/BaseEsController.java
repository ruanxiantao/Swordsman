package com.swordsman.common.es.base;

import com.swordsman.common.base.PageRequest;
import com.swordsman.common.es.core.CurrencyEsSearch;
import com.swordsman.common.valid.ValidList;
import com.swordsman.common.web.ApiResult;
import com.swordsman.common.web.Status;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 14:17
 * Base ES Controller
 */
@NoRepositoryBean
public abstract class BaseEsController<T extends BaseEsEntity,M extends BaseEsService>{

    @Autowired
    protected M baseService;

    @ApiOperation("保存实体")
    @PostMapping
    public ApiResult save(@RequestBody T t){
        return new ApiResult(baseService.save(t));
    }

    @ApiOperation("根据 Id 删除")
    @DeleteMapping("{id}")
    public ApiResult deleteById(@PathVariable String id){
        baseService.delete(id);
        return new ApiResult(Status.SUCCESS);
    }

    @ApiOperation("根据 Id 修改")
    @PutMapping("{id}")
    public ApiResult updateById(@PathVariable String id,@RequestBody T t){
        return new ApiResult(baseService.update(id,t));
    }

    @ApiOperation("根据 Id 查找")
    @GetMapping("findById/{id}")
    public ApiResult findById(@PathVariable String id){
        return new ApiResult(baseService.findById(id));
    }

    @ApiOperation("批量保存")
    @PostMapping("saveEntities")
    public ApiResult saveEntities(@RequestBody ValidList<T> t){
        return new ApiResult(baseService.saveEntities(t));
    }

    @ApiOperation("批量删除")
    @DeleteMapping("deleteEntities")
    public ApiResult deleteEntities(@RequestBody List<T> t){
        baseService.deleteEntities(t);
        return new ApiResult(Status.SUCCESS);
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public ApiResult page(@ApiParam("分页参数") @RequestBody PageRequest pageRequest) {
        return new ApiResult(baseService.page(pageRequest));
    }

    @ApiOperation("统计总数")
    @GetMapping("count")
    public ApiResult count(){
        return new ApiResult(baseService.count());
    }

    @ApiOperation("多功能条件统计")
    @PostMapping("currencyEsCount")
    public ApiResult currencyEsCount(@RequestBody CurrencyEsSearch search){
        return new ApiResult(baseService.currencyEsCount(search));
    }

    @ApiOperation("多功能条件查询")
    @PostMapping("currencyEsSearch")
    public ApiResult currencyEsSearch(@RequestBody CurrencyEsSearch search){
        return new ApiResult(baseService.currencyEsSearch(search));
    }

}
