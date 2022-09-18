package com.fukwang.dto;

import lombok.Data;

import java.util.List;

/**
 * 通用DTO 可以直接使用是否成功的参数或者List以及分页或者语句
 * @param <T>
 */

@Data
public class DataDto <T>{
    //成功是0，失败是1
    private Integer id;
    //携带的通知信息
    private String message;
    //携带的数据,在创建时需要传入信息类型
    private List<T> list;
    private T obj;
    private Long count;
    //成功时的无参返回
    public DataDto<T> success(){
        this.id = 0;
        this.message = "";
        this.list = null;
        return this;
    }
    //成功时的有参返回
    public DataDto<T> success(String message, List<T> list) {
        this.id = 0;
        this.message = message;
        this.list = list;
        return this;
    }
    //失败时的无参返回
    public DataDto<T> err() {
        this.id = 1;
        this.message = "";
        this.list = null;
        return this;
    }
    //失败时的有参返回
    public DataDto<T> err(String message, List<T> list) {
        this.id = 1;
        this.message = message;
        this.list = list;
        return this;
    }
    public DataDto<T> err(String message) {
        this.id = 1;
        this.message = message;
        this.list = null;
        return this;
    }
    //成功时的有参返回
    public DataDto<T> success(List<T> list) {
        this.id = 0;
        this.message = "";
        this.list = list;
        return this;
    }
    //成功时的有参返回
    public DataDto<T> success(List<T> list, Long count) {
        this.id = 0;
        this.message = "";
        this.list = list;
        this.count = count;
        return this;
    }
    public DataDto<T> success(String message) {
        this.id = 0;
        this.message = message;
        this.list = null;
        return this;
    }
    public DataDto<T> success(String message,T obj){
        this.id = 0;
        this.message = message;
        this.obj = obj;
        return this;
    }
    public DataDto<T> success(T obj){
        this.id = 0;
        this.message = "";
        this.obj = obj;
        return this;
    }
}
