package com.swordsman.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author DuChao
 * @Date 2019-10-23 14:18
 * 分配请求实体
 */
@Data
@ToString
public class AssignRequest {

    @NotBlank(message = "被分配Id 不能为空")
    @ApiModelProperty(value = "被分配Id<例: 给用户分配角色时，此处填用户Id>")
    private String assignedId;

    @ApiModelProperty(value = "分配Id 集合<例: 给用户分配角色时，此处填角色Id集合>")
    private List<String> assignIds;

}
