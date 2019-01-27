package com.fcgl.domain.response;

import com.fcgl.domain.entity.User;
import lombok.Data;
import java.util.Set;

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

    /**
     * user
     */
    private Set<User> users;
}
