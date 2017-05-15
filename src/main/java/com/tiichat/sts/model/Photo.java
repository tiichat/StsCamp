package com.tiichat.sts.model;

import lombok.Getter;
import lombok.Setter;

/**
 * フォト情報のDTOクラス。
 * 
 * @author tiich
 *
 */
@Getter
@Setter
public class Photo {
    private Integer id;
    private byte[] image;
}
