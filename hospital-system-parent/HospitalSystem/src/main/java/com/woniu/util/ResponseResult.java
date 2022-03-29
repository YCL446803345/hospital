package com.woniu.util;

import lombok.Data;

/**
 * 响应状态码工具类
 * @param <T>
 */
@Data
public class ResponseResult<T> {

    public ResponseResult(){}
    private int status;
    private String msg;

    private T data;
    public ResponseResult(int status, String msg){
        this.status = status;
        this.msg = msg;
    }
    public ResponseResult(T data, String msg, int status){
        this(status,msg);
        this.data = data;
        this.msg = msg;
    }

    public static ResponseResult ok(){
        ResponseResult result = new ResponseResult();
        result.setStatus(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static ResponseResult error(ResultCode resultCode){
        ResponseResult result = new ResponseResult();
        result.setStatus(resultCode.getCode());
        result.setMsg(resultCode.getMessage());
        return result;
    }

    public static ResponseResult<Void> SUCCESS = new ResponseResult<>(200,"成功");
    public static ResponseResult<Void> MoneyERROR = new ResponseResult<>(304,"余额不足");
    public static ResponseResult<Void> INTEVER_ERROR = new ResponseResult<>(500,"服务器错误");
    public static ResponseResult<Void> NOT_FOUND = new ResponseResult<>(404,"未找到");
    public static ResponseResult<Void> CardExe = new ResponseResult<>(303,"身份证号重复");
    public static ResponseResult<Void> AccountExe = new ResponseResult<>(306,"账号重复");
    public static ResponseResult<Void> ERROLD = new ResponseResult<>(456,"系统繁忙");
    public static ResponseResult<Void> NO_PERMISSION = new ResponseResult<>(4001,"没有权限");

}
