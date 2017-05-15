package com.tiichat.sts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 社員一覧のためのDTOクラス。
 *
 * フォーム、SQL文のSELECT句、プロパティyamlのSQL部分文字列のキー名称、HTMLのname属性、
 * そしてDTOで、フィールド名を一致させるルール前提でロジックを組んでいる。
 *
 * @author tiich
 *
 */
@Getter
@Setter
public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private Integer sex;
    private Integer photoId;
    private String address;
    private Integer deptId;
    private String deptName;
}
