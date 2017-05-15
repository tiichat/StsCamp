package com.tiichat.sts.service;

import java.util.List;

import com.tiichat.sts.model.Dept;

/**
 * 部署情報を扱うサービスインタフェース
 *
 * @author tiich
 *
 */
public interface DeptService {

    /**
     * 部署一覧を取得する。
     * 
     * @return
     */
    public List<Dept> getList();
}
