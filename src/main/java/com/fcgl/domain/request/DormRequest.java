package com.fcgl.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author furg@senthink.com
 * @date 2019/1/25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DormRequest {

    /**
     * 宿舍编号
     */
    @NotBlank
    private String dn;

    /**
     * 宿舍的位置
     */
    @NotBlank
    private String location;
}
