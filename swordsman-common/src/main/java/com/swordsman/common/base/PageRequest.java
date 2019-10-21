package com.swordsman.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 11:33
 * 分页排序条件实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "分页排序 条件，例: 第一页、每页十条数据、以创建时间倒序")
public class PageRequest {

    @ApiModelProperty(value = "当前第几页<默认第一页>")
    Integer page = 1;

    @ApiModelProperty(value = "每页多少条数据<默认十条>")
    Integer limit = 10;

    @ApiModelProperty(value = "排序条件，数组形式<默认以createTime 倒序排列>")
    List<Order> mySorts = new ArrayList();


    public Pageable pageable() {
        Pageable pageable = new org.springframework.data.domain.PageRequest(this.page - 1, this.limit, this.sorter());
        return pageable;
    }

    /**
     * 排序
     */
    public Sort sorter() {
        // 传入 mySorts 条件为 null，默认添加按创建时间倒序
        if (this.mySorts.size() == 0)
            this.mySorts.add(new Order(Sort.Direction.DESC, "createTime"));

        // 迭代排序条件
        List<Sort.Order> sorters = new ArrayList();
        Iterator iterator = this.mySorts.iterator();

        while(iterator.hasNext()) {
            Order order = (Order)iterator.next();
            sorters.add(new Sort.Order(order.getDirection(), order.getOrderBy()));
        }

        return new Sort(sorters);
    }

}
