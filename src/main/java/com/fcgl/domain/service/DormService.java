package com.fcgl.domain.service;

import com.fcgl.common.entity.ParamRequest;
import com.fcgl.common.exception.BusinessException;
import com.fcgl.common.exception.DataAccessException;
import com.fcgl.common.request.BatchDeleteRequest;
import com.fcgl.common.util.RandomUtils;
import com.fcgl.domain.entity.Dorm;
import com.fcgl.domain.entity.User;
import com.fcgl.domain.repository.DormRepository;
import com.fcgl.domain.request.DormRequest;
import com.fcgl.domain.response.DormResponse;
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
 * Dorm的Service
 *
 * @author furg@senthink.com
 * @date 2019/1/25
 */
@Service
public class DormService {

    private final CodeMsg codeMsg;
    private final DormRepository dormRepository;

    @Autowired
    public DormService(CodeMsg codeMsg, DormRepository dormRepository) {
        this.codeMsg = codeMsg;
        this.dormRepository = dormRepository;
    }

    /**
     * 添加宿舍楼
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = {DataAccessException.class, BusinessException.class})
    public ApiResponse add(DormRequest request) {
        if (dormRepository.countAllByDn(request.getDn()) > 0) {
            throw new BusinessException(codeMsg.recordAlreadyExistCode(), codeMsg.recordAlreadyExistMsg());
        }
        Dorm dorm = new Dorm();
        BeanUtils.copyProperties(request, dorm);
        dorm.setDid(RandomUtils.randomString(30));
        dorm.setStatus(false);
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), dormRepository.save(dorm));
    }


    /**
     * 批量删除宿舍楼
     *
     * @param request
     * @return
     */
    @Transactional
    public ApiResponse batchDelete(BatchDeleteRequest request) {
        //删除的时候在用的不删除
        List<Dorm> dorms = dormRepository.findAllByDidIn(request.getIds());
        List<String> dids = dorms.stream()
                .filter(t -> !t.getStatus())
                .map(Dorm::getDid)
                .collect(Collectors.toList());
//要想删除再用的，去掉下面的注释
//        for (Dorm dorm : dorms) {
//            if (!dorm.getStatus()) {
//                List<User> users = dorm.getUsers();
//                //解除user中的级联关系
//                for (User user : users) {
//                    user.setDorm(null);
//                    userRepository.save(user);
//                }
//            }
//        }
        dormRepository.deleteAllByDidIn(dids);
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg());
    }

    /**
     * 编辑宿舍楼信息
     *
     * @param did
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse update(String did, DormRequest request) {
        Dorm dorm = dormRepository.findTopByDid(did).orElseThrow(() ->
                new BusinessException(codeMsg.recordNotFoundCode(), codeMsg.recordNotFoundMsg()));
        if (!dorm.getDn().equalsIgnoreCase(request.getDn())) {
            if (dormRepository.countAllByDn(request.getDn()) > 0) {
                throw new BusinessException(codeMsg.recordAlreadyExistCode(), codeMsg.recordAlreadyExistMsg());
            }
        }
        BeanUtils.copyProperties(request, dorm);
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), dormRepository.save(dorm));
    }


    /**
     * 查找宿舍详情
     *
     * @param did
     * @return
     */
    public ApiResponse findOne(String did) {
        Dorm dorm = dormRepository.findTopByDid(did).orElseThrow(() ->
                new BusinessException(codeMsg.recordNotFoundCode(), codeMsg.recordNotFoundMsg()));
        DormResponse response = new DormResponse();
        BeanUtils.copyProperties(dorm, response);
        response = initUserNames(dorm, response);
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), response);
    }

    /**
     * 获得宿舍所使用的userName
     *
     * @param dorm
     * @param response
     */
    private DormResponse initUserNames(Dorm dorm, DormResponse response) {
        if (dorm.getUsers().size() > 0) {
            response.setUserNames(dorm.getUsers().stream()
                    .map(User::getName)
                    .collect(Collectors.toList()));
        }
        return response;
    }

    /**
     * 获取宿舍楼的分页列表
     *
     * @param request
     * @return
     */
    public ApiResponse findAll(ParamRequest request) {
        Page<Dorm> page = getPage(request);
        List<DormResponse> responses = new LinkedList<>();
        for (Dorm dorm : page.getContent()) {
            DormResponse response = new DormResponse();
            BeanUtils.copyProperties(dorm, response);
            response = initUserNames(dorm, response);
            responses.add(response);
        }
        Page<DormResponse> list = new PageImpl<>(responses, request.getPageDto().convertToPageRequest(), page.getTotalElements());
        return new CodeMsgDataResponse<>(codeMsg.successCode(), codeMsg.successMsg(), list);
    }

    /**
     * 获取page
     *
     * @param request
     * @return
     */
    private Page<Dorm> getPage(ParamRequest request) {
        Page<Dorm> page;
        try {
            List<Predicate> predicates = new LinkedList<>();
            page = dormRepository.findAll((root, query, cb) -> {
                if (request.getSearchRequest() != null) {
                    predicates.add(request.getSearchRequest().generatePredicate(root, cb));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }, request.getPageDto().convertToPageRequest());

        } catch (Exception e) {
            throw new DataAccessException();
        }
        return page;
    }
}
