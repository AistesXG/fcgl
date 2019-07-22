package com.fcgl.common.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

/**
 * 接口传递的搜索对象
 *
 * @author furg@senthink.com
 * @date 2017/11/24
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SearchRequest {

    private String search;

    private String searchFields;

    public <T> Predicate generatePredicate(Root<T> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new LinkedList<>();
        if (StringUtils.isAnyBlank(searchFields, search)) {
            return cb.conjunction();
        }
        String[] keyArray = searchFields.trim().split(",");
        for (int i = 0; i < keyArray.length; i++) {
            if (StringUtils.isNotBlank(keyArray[i])) {
                predicates.add(cb.gt(cb.locate(root.get(keyArray[i]), search), 0));
            }
        }
        return cb.or(predicates.toArray(new Predicate[predicates.size()]));
    }
}
