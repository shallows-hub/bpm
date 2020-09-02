package com.dstz.base.api.response.impl;

import java.util.HashMap;

public class OdooResultMsg {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int ERROR = -1;
    public static final int TIMEOUT = 2;

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Object data;
    public Boolean isSuccess(){
        return this.code == SUCCESS;
    }

}
