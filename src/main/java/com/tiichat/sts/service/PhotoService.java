package com.tiichat.sts.service;

import com.tiichat.sts.form.FileUploadForm;
import com.tiichat.sts.model.Photo;

/**
 * フォト情報を扱うサービスインタフェース。
 *
 * @author tiich
 *
 */
public interface PhotoService {

    /**
     * フォト画像を保存する。
     *
     * @param userId
     *            画像が紐づくユーザＩＤ
     * @param form
     *            ファイルアップロードフォーム情報。
     * @return
     */
    public int save(Integer userId, FileUploadForm form);

    /**
     * フォト画像を取得する。
     *
     * @param userId
     *            画像が紐づくユーザＩＤ
     * @return
     */
    public Photo load(Integer userId);
}
