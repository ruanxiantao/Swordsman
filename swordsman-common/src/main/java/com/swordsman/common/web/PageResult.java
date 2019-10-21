package com.swordsman.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-21 10:52
 * 通用分页查询结果实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {

    /**
     * 分页查询数据
     */
    private List content;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 总元素数
     */
    private Long totalElements;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 当前页元素数
     */
    private int pageSize;

    /**
     * 转换 SpringData 分页查询结果 Page 接口数据
     */
    public static PageResult process(Page page){
       return new PageResult(page.getContent(),page.getTotalPages(),page.getTotalElements(),page.getPageable().getPageNumber()+1,page.getPageable().getPageSize());
    }

}
