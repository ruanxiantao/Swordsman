package com.swordsman.common.base;

import cn.hutool.core.util.StrUtil;
import com.swordsman.common.util.DateUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:31
 * 时间区间条件实体
 */
@Data
@ApiModel(description = "时间范围 条件，例: createTime 在1号到2号")
public class DateParam implements Serializable {

    @ApiModelProperty(value = "需要查询的字段,默认为 creatTime")
    private String field;

    @ApiModelProperty(value = "字段起始时间，格式: yyyy-MM-dd HH:mm:ss")
    private String startDate;

    @ApiModelProperty(value = "字段结束时间，格式: yyyy-MM-dd HH:mm:ss")
    private String endDate;

    public Date start() {
        return DateUtil.StringToDate(this.startDate);
    }

    public Date end() {
        return DateUtil.StringToDate(this.endDate);
    }

    public String getField(){
        if(StrUtil.isEmpty(this.field))
            return "createTime";
        return this.field;
    }
}
