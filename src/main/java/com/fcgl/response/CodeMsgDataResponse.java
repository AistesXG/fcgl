package com.fcgl.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author furg@senthink.com
 * @date 2017/11/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CodeMsgDataResponse<T> extends ApiResponse {
    /**
     * 返回code
     */
    private int code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    public CodeMsgDataResponse() {

    }

    public CodeMsgDataResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsgDataResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
