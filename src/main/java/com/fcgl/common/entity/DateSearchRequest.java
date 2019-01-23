package com.fcgl.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fcgl.common.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接口传递的按照日期(yyyy-MM-dd)搜索的对象
 *
 * @author furg@senthink.com
 * @date 2017/12
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateSearchRequest {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public void plusOneDay() {
        this.endTime = TimeUtils.nDaysAfter(endTime, 1);
    }
}
