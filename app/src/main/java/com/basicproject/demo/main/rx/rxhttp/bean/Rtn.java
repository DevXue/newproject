package com.basicproject.demo.main.rx.rxhttp.bean;

/**
 * 返回数据基类
 */

public class Rtn<T> {
    private T data;
    private Meta meta;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public class Meta{

        private int code;
        private String message;
        private boolean  success;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    @Override
    public String toString() {
        return "Rtn{" +
                "data=" + data +
                ", meta=" + meta +
                '}';
    }
}
