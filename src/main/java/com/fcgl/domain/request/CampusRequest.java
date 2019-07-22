package com.fcgl.domain.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 校区的request
 *
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CampusRequest {

    /**
     * 校区名称
     */
    @NotBlank
    private String name;

    /**
     * 校区的面积
     */
    @NotNull
    private Double area;

    /**
     * 校区的坐落
     */
    @NotBlank
    private String location;
}
