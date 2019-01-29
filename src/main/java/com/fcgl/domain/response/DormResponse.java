package com.fcgl.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author furg@senthink.com
 * @date 2019/1/29
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DormResponse {

    /**
     * 宿舍唯一id
     */
    private String did;

    /**
     * 宿舍编号
     */
    private String dn;

    /**
     * 宿舍的位置
     */
    private String location;

    /**
     * 使用情况
     */
    private boolean status;

    /**
     * 用户
     */
    private List<String> userNames;
}
