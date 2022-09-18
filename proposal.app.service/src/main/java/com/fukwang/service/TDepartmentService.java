package com.fukwang.service;

import com.fukwang.exception.LoginException;
import com.fukwang.pojo.AdminInfo;
import com.fukwang.pojo.TCompany;
import com.fukwang.pojo.TDepartment;
import com.fukwang.qo.LoginQo;

import java.util.List;


public interface TDepartmentService {
    List<TDepartment> selectById(int companyId);
}
