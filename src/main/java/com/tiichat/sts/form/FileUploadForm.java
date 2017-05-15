package com.tiichat.sts.form;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

/**
 * ファイルアップロード用のフォームクラス。
 * 
 * @author tiich
 *
 */
@Getter
@Setter
public class FileUploadForm {
    private MultipartFile fileData;
}
