package com.fukwang.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 分页用dto 换页面时必须用这个，如果可以访问用这个类返回id0
 */
@Data
public class RouteDto implements Serializable {
    private int id;
    private String message;
    private String location;
    private HashMap<String, Object> data;
    private Object obj;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
