package com.tmac2236.spring.core.dto;

import java.util.ArrayList;
import java.util.HashMap;

public class AjaxDto {
    public static final String ACTION_LOGOUT = "logout";
    private static final int SUCCESS = 1;
    private static final int FAIL = 0;
    private int status = 0;
    private String message = "";
    private String action = "";
    private Object data;

    public int getStatus() {
        return this.status;
    }

    public void setStatusOK() {
        this.status = SUCCESS;
    }

    public void setStatusFail() {
        this.status = FAIL;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
        this.setStatusOK();
    }

    public void setDataButNullFail(Object data, String nullMessage) {
        this.data = data;
        if (data == null) {
            this.setStatusFail();
            this.setMessage(nullMessage);
        } else {
            this.setStatusOK();
        }

    }

    public void setDataFixListNull(Object data) {
        if (data == null) {
            data = new ArrayList();
        }

        this.data = data;
        this.setStatusOK();
    }

    public void setDataFixObjNull(Object data) {
        if (data == null) {
            data = new HashMap();
        }

        this.data = data;
        this.setStatusOK();
    }
}