package com.fcgl.domain.response;

import lombok.Data;

/**
 * @author furg@senthink.com
 * @date 2019/1/27
 */
@Data
public class CampusResponse {

    /**
     * 校区的唯一id
     */
    private String cid;

    /**
     * 校区名称
     */
    private String name;

    /**
     * 校区的面积
     */
    private Double area;

    /**
     * 校区的坐落
     */
    private String location;
}
