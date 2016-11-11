package com.alanmarksp.marvelapiclient.models;


public class DataWrapper<T> {

    private int code;
    private String status;
    private String attributionText;
    private String etag;
    private T data;

    public DataWrapper(int code, String status, String attributionText, String etag, T data) {
        this.code = code;
        this.status = status;
        this.attributionText = attributionText;
        this.etag = etag;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttributionText() {
        return attributionText;
    }

    public void setAttributionText(String attributionText) {
        this.attributionText = attributionText;
    }

    public String getETag() {
        return etag;
    }

    public void setETag(String etag) {
        this.etag = etag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
