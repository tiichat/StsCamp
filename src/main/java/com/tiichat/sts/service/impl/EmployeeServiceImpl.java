package com.tiichat.sts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiichat.sts.dao.SqlDao;
import com.tiichat.sts.dao.SqlValueType;
import com.tiichat.sts.form.EmployeeQueryForm;
import com.tiichat.sts.model.Employee;
import com.tiichat.sts.service.EmployeeService;

/**
 * 社員サービスのインプリメンテーション。
 *
 * @author tiich
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public List<Employee> getList(EmployeeQueryForm form) {
        return dao.get(Employee.class, "employeeList")
                .addValue("deptId", form.getDeptId())
                .addValue("name", form.getName(), SqlValueType.LIKE)
                .query();
    }

    // 教育用に作成した、SqlDaoをDIしている。
    @Autowired
    private SqlDao dao;

}
