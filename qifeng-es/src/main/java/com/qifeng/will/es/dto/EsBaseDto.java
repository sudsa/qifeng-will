package com.qifeng.will.es.dto;

public class EsBaseDto<T> {

    private String id;

    private T data;

    public EsBaseDto() {

    }

    public EsBaseDto(String id, T data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
