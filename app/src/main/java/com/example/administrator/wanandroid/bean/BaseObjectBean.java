package com.example.administrator.wanandroid.bean;


import java.util.List;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：对象
 */
public class BaseObjectBean<T> {

    private int errorCode;
    private String errorMsg;

    /*private List<T> data;*/
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /*public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }*/

}
