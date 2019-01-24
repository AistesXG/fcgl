package com.fcgl.domain.entity;

import com.fcgl.common.entity.BornableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 校区
 *
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@Entity
@Table(name = "campus", indexes = {
        @Index(name = "idx_campus_cid", columnList = "cid", unique = true),
        @Index(name = "idx_campus_name", columnList = "name", unique = true)}
)
@Getter
@Setter
public class Campus extends BornableEntity<Long> {

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
