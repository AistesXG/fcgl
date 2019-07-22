package com.fcgl.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yuanpeng@senthink.com
 * @data 2018-03-02 13:39
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class TimeSearchRequest {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public Predicate generatePredicate(Path<Date> path, CriteriaBuilder cb) {
        if (!ObjectUtils.anyNotNull(startTime, endTime)) {
            return cb.conjunction();
        }
        if (!ObjectUtils.allNotNull(startTime, endTime)) {
            throw new IllegalArgumentException();
        }
        if (startTime.compareTo(endTime) > 0) {
            throw new IllegalArgumentException();
        }
        return cb.between(path.as(String.class), FORMATTER.format(startTime), FORMATTER.format(endTime));
    }

}
