package com.fcgl.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Persistable;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * 持久化实体的抽象类
 *
 * @author furg@senthink.com
 * @date 2017/11/14
 */
@MappedSuperclass
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {

    /**
     * 设置Entity的id
     *
     * @param id id
     */
    public abstract void setId(final ID id);

    @JsonIgnore
    @Override
    public boolean isNew() {
        Serializable id = getId();
        return id == null || StringUtils.isBlank(String.valueOf(id));
    }
}
