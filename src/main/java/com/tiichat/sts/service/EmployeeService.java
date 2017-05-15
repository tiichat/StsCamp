package com.tiichat.sts.service;

import java.util.List;

import com.tiichat.sts.form.EmployeeQueryForm;
import com.tiichat.sts.model.Employee;

/**
 * 社員情報を扱うサービスインタフェース。
 *
 * @author tiich
 *
 */
public interface EmployeeService {

    /**
     * 社員一覧を取得する。
     *
     * @param form
     *            画面からの入力情報 = 検索キー
     * @return
     */
    public List<Employee> getList(EmployeeQueryForm form);

}
