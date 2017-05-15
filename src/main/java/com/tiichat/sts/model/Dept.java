package com.tiichat.sts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 部署一覧のためのDTOクラス。 Springでは、ゲッタ・セッタは必須。
 *
 * @author tiich
 *
 */
@Getter
@Setter
public class Dept {
    private Integer id;
    private String name;
}
