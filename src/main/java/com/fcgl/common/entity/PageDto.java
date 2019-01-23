package com.fcgl.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 接口传递的分页参数对象
 *
 * @author furg@senthink.com
 * @date 2017/11/23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PageDto {

    /**
     * 页数
     */
    @Min(value = 0, message = "{common.page.size}")
    @NotNull(message = "{common.page.notBlank}")
    private int page;

    /**
     * 每页大小
     */
    @Min(value = 1, message = "{common.pageSize.size}")
    @NotNull(message = "{common.pageSize.notBlank}")
    private int pageSize;

    public PageRequest convertToPageRequest() {
        return new PageRequest(page, pageSize);
    }

    public PageRequest convertToPageRequest(Sort sort) {
        return new PageRequest(page, pageSize, sort);
    }

    public PageRequest convertToPageRequestOrderByTime() {
        return new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "createTime"));
    }

    public Pageable convertToPageableOrderByTime() {
        return new PageRequest(page, pageSize, new Sort(Sort.Direction.DESC, "lastModifiedTime"));
    }
}
