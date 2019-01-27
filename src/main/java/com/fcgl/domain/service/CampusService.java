package com.fcgl.domain.service;

import com.fcgl.common.entity.ParamRequest;
import com.fcgl.common.exception.BusinessException;
import com.fcgl.common.exception.DataAccessException;
import com.fcgl.common.request.BatchDeleteRequest;
import com.fcgl.common.util.RandomUtils;
import com.fcgl.domain.entity.Campus;
import com.fcgl.domain.repository.CampusRepository;
import com.fcgl.domain.repository.UserRepository;
import com.fcgl.domain.request.CampusRequest;
import com.fcgl.domain.response.CampusResponse;
import com.fcgl.messages.CodeMsg;
import com.fcgl.response.ApiResponse;
import com.fcgl.response.CodeMsgDataResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
    private UserRepository userRepository;
    @Autowired
    private CampusRepository campusRepository;

    /**
     * 添加校区
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {DataAccessException.class, BusinessException.class})
    public ApiResponse addCampus(CampusRequest request) {
        if (campusRepository.countAllByName(request.getName()) > 0) {
            throw new BusinessException(codeMsg.recordAlreadyExistCode(), codeMsg.recordAlreadyExistMsg());
        }
        Campus campus = new Campus();
        BeanUtils.copyProperties(request, campus);
        campus.setCid(RandomUtils.randomString(30));
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), campusRepository.save(campus));
    }

    /**
     * 批量删除校区
     *
     * @param request
     * @return
     */
    @Transactional
    public ApiResponse batchDelete(BatchDeleteRequest request) {
        long count = campusRepository.deleteAllByCidIn(request.getIds());
        if (count > 0) {
            return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), count);
        }
        throw new BusinessException(codeMsg.recordNotFoundCode(), codeMsg.recordNotFoundMsg());
    }

    /**
     * 查找校区
     *
     * @param cid
     * @return
     */
    public ApiResponse findOne(String cid) {
        Campus campus = campusRepository.findTopByCid(cid).orElseThrow(() ->
                new BusinessException(codeMsg.recordNotFoundCode(), codeMsg.recordNotFoundMsg()));
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), campus);
    }

    /**
     * 校区分页列表
     *
     * @param request
     * @return
     */
    public ApiResponse findAll(ParamRequest request) {
        Page<Campus> page = getPage(request);
        List<CampusResponse> responses = new LinkedList<>();
        for (Campus campus : page.getContent()) {
            CampusResponse response = new CampusResponse();
            BeanUtils.copyProperties(campus, response);
            responses.add(response);
        }
        Page<CampusResponse> list = new PageImpl<>(responses, request.getPageDto().convertToPageRequest(), page.getTotalElements());
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), list);
    }

    /**
     * 查找校区的cid列表
     *
     * @return
     */
    public ApiResponse findAllCids() {
        List<String> cids = campusRepository.findAll().stream()
                .map(Campus::getCid)
                .collect(Collectors.toList());
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), cids);
    }

    /**
     * 编辑校区
     *
     * @param cid
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse update(String cid, CampusRequest request) {
        Campus campus = campusRepository.findTopByCid(cid).orElseThrow(() ->
                new BusinessException(codeMsg.recordNotFoundCode(), codeMsg.recordNotFoundMsg()));
        if (!request.getName().equalsIgnoreCase(campus.getName())) {
            if (campusRepository.countAllByName(request.getName()) > 0) {
                throw new BusinessException(codeMsg.recordAlreadyExistCode(), codeMsg.recordAlreadyExistMsg());
            }
        }
        BeanUtils.copyProperties(request, campus);
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), campusRepository.save(campus));
    }

    /**
     * 获取page
     *
     * @param request
     * @return
     */
    private Page<Campus> getPage(ParamRequest request) {
        Page<Campus> page;
        try {
            page = campusRepository.findAll((root, query, cb) -> {
                List<Predicate> predicates = new LinkedList<>();
                if (request.getSearchRequest() != null) {
                    predicates.add(request.getSearchRequest().generatePredicate(root, cb));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }, request.getPageDto().convertToPageRequestOrderByTime());
        } catch (Exception e) {
            throw new DataAccessException();
        }
        return page;
    }
}
