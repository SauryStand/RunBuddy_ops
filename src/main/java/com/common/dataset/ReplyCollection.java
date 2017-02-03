package com.common.dataset;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @description:
 * @Author: Johnny Chou
 * @Create: 2017-01-29-12:27
 **/

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplyCollection {
    private String code;

    private String message;

    private Object result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
