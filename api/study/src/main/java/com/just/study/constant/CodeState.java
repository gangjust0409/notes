package com.just.study.constant;

import lombok.Data;

public class CodeState {

    public enum ResponseState{
        SUCCESS(200, "操作成功！"),ERROR(500, "操作失败！");
        private Integer  code;
        private String msg;

        ResponseState(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Integer getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

}
