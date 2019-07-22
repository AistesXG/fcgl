package com.fcgl.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.Date;

/**
 * 具有createTime的实体
 *
 * @param <ID>
 */
@MappedSuperclass
public abstract class BornableEntity<ID extends Serializable> extends BaseEntity<ID> {

    /**
     * 创建时间
     */
    @Getter
    @Setter
    @JsonIgnore
    @Column(name = "create_time")
    protected Date createTime;

    @PrePersist
    protected void onCreate() {
        createTime = new Date();
    }
}
