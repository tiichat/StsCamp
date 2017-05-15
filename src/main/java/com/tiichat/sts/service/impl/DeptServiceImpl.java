package com.tiichat.sts.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiichat.sts.dao.SqlDao;
import com.tiichat.sts.model.Dept;
import com.tiichat.sts.service.DeptService;

/**
 * 部署サービスのインプリメンテーション。
 *
 * @author tiich
 *
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Override
    public List<Dept> getList() {
        return dao.get(Dept.class, "deptList").query();
    }

    // 教育用に作成した、SqlDaoをDIしている。
    @Autowired
    private SqlDao dao;

}
