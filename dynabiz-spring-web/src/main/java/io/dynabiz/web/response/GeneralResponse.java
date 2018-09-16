package io.dynabiz.web.response;


import io.dynabiz.BusinessException;

public class GeneralResponse {

    private int code;
    private int subCode;
    private String msg;
    private Object data;

    public GeneralResponse() {

    }

    public GeneralResponse(BusinessException ex){
        this.code = ex.getCode();
        this.msg = ex.toString();
        this.subCode = ex.getSubCode();
        this.data = null;
    }

    public GeneralResponse(Exception ex){
        this.code = 1000;
        this.msg = ex.toString();
        this.data = null;
    }

    public GeneralResponse(BusinessException ex, Object data){
        this.code = ex.getCode();
        this.msg = ex.toString();
        this.data = data;
    }

    public GeneralResponse(Object data){
        this.code = 0;
        this.msg = "All correct";
        this.data = data;
    }

    //region Getters and Setters

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getSubCode() {
        return subCode;
    }

    public void setSubCode(int subCode) {
        this.subCode = subCode;
    }

    //endregion


    @Override
    public String toString() {
        return "GeneralResponse{" +
                "code=" + code +
                ", subCode=" + subCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
