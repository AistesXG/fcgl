package com.fcgl.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fcgl.common.entity.BornableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    /**
     * 对应的user
     */
    @ManyToMany(mappedBy = "campus", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, targetEntity = User.class)
    @JsonIgnore
    private Set<User> users;

    /**
     * 宿舍
     */
    @OneToMany(mappedBy = "campus", targetEntity = Dorm.class)
    @JsonIgnore
    private List<Dorm> dorms;
}
