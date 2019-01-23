package com.fcgl.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author furg@senthink.com
 * @date 2018/11/7
 */
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity<ID> {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "native")
    private ID id;

    @Override
    public ID getId() {
        return this.id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }
}
