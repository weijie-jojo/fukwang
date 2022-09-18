package com.fukwang.service;

import com.fukwang.pojo.TDepartment;

import java.util.List;


public interface TDepartmentService {
    List<TDepartment> selectById(int companyId);
}
