package com.fcgl.domain.service;

import com.fcgl.common.exception.BusinessException;
import com.fcgl.common.exception.DataAccessException;
import com.fcgl.common.util.RandomUtils;
import com.fcgl.domain.entity.Campus;
import com.fcgl.domain.repository.CampusRepository;
import com.fcgl.domain.request.CampusRequest;
import com.fcgl.messages.CodeMsg;
import com.fcgl.response.ApiResponse;
import com.fcgl.response.CodeMsgDataResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 校区的Service
 *
 * @author furg@senthink.com
 * @date 2019/1/24
 */
@Service
public class CampusService {

    @Autowired
    private CodeMsg codeMsg;
    @Autowired
    private CampusRepository campusRepository;

    @Transactional(rollbackFor = {DataAccessException.class, BusinessException.class})
    public ApiResponse addCampus(CampusRequest request) {
        Campus campus = new Campus();
        BeanUtils.copyProperties(request, campus);
        campus.setCid(RandomUtils.randomString(30));
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), campusRepository.save(campus));
    }
}
