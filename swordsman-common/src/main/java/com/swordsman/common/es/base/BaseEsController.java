package com.swordsman.common.es.base;

import com.beluga.api.es.core.CurrencyEsSearch;
import com.beluga.api.web.Code;
import com.beluga.api.web.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-09-10 11:43
 *
 */
@NoRepositoryBean
public abstract class BaseEsController<T extends BaseEsEntity,M extends BaseEsService>{

    @Autowired
    protected M baseService;

    @ApiOperation("根据 Id 查找")
    @GetMapping("findById/{id}")
    public ResultVo findById(@PathVariable String id){
        return new ResultVo(baseService.findById(id), Code.OK);
    }

    @ApiOperation("根据 Id 修改")
    @PutMapping("{id}")
    public ResultVo updateById(@PathVariable String id,@RequestBody T t){
        return new ResultVo(baseService.update(id,t),Code.OK);
    }

    @ApiOperation("保存实体")
    @PostMapping
    public ResultVo save(@RequestBody T t){
        return new ResultVo(baseService.save(t), Code.OK);
    }

    @ApiOperation("根据 Id 删除")
    @DeleteMapping("{id}")
    public ResultVo deleteById(@PathVariable String id){
        baseService.delete(id);
        return new ResultVo(Code.OK);
    }

    @ApiOperation("批量保存实体")
    @PostMapping("saveEntities")
    public ResultVo saveEntities(@RequestBody List<T> t){
        return new ResultVo(baseService.saveEntities(t), Code.OK);
    }

    @ApiOperation("统计总数")
    @GetMapping("count")
    public ResultVo count(){
        return new ResultVo(baseService.count(),Code.OK);
    }

    @ApiOperation("多功能条件统计")
    @PostMapping("currencyEsCount")
    public ResultVo currencyEsCount(@RequestBody CurrencyEsSearch search){
        return new ResultVo(baseService.currencyEsCount(search),Code.OK);
    }

    @ApiOperation("多功能条件查询")
    @PostMapping("currencyEsSearch")
    public ResultVo currencyEsSearch(@RequestBody CurrencyEsSearch search){
        return new ResultVo(baseService.currencyEsSearch(search),Code.OK);
    }

}
