package com.fukwang.service;


import com.fukwang.exception.InsertException;
import com.fukwang.pojo.AdminLog;


public interface AdminLogService {
    Integer insertLog(AdminLog adminLog)  throws InsertException;

}
