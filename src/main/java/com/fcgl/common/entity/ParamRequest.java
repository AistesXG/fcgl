package com.fcgl.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ParamRequest {

    /**
     * 分页对象
     */
    private PageDto pageDto;

    /**
     * 模糊搜索对象
     */
    private SearchRequest searchRequest;
}
