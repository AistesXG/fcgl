package com.fcgl.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author furg@senthink.com
 * @date 2017/11/19
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CodeMsgErrorResponse<T> extends ApiResponse {

    /**
     * code
     */
    private int code;

    /**
     * msg
     */
    private String msg;

    /**
     * error
     */
    private T error;

    public CodeMsgErrorResponse() {
    }

    public CodeMsgErrorResponse(int code, String msg, T error) {
        this.code = code;
        this.msg = msg;
        this.error = error;
    }
}
