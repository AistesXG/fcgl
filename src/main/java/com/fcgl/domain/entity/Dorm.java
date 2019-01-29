package com.fcgl.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fcgl.common.entity.BornableEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 宿舍
 *
 * @author furg@senthink.com
 * @date 2019/1/25
 */
@Entity
@Table(name = "dorm", indexes = {
        @Index(name = "idx_dorm_did", columnList = "did", unique = true),
        @Index(name = "idx_dorm_dn", columnList = "dn", unique = true)
})
@Getter
@Setter
public class Dorm extends BornableEntity<Long> {

    /**
     * 宿舍唯一id
     */
    @Column(name = "did", nullable = false)
    private String did;

    /**
     * 宿舍编号
     */
    @Column(name = "dn", nullable = false)
    private String dn;

    /**
     * 宿舍的位置
     */
    @Column(name = "location")
    private String location;

    /**
     * 使用情况
     */
    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "dorm")
    @JsonIgnore
    private List<User> users;
}
