package com.tiichat.sts.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.tiichat.sts.dao.SqlDao;
import com.tiichat.sts.dao.SqlValueType;
import com.tiichat.sts.form.FileUploadForm;
import com.tiichat.sts.model.Photo;
import com.tiichat.sts.service.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService {

    /**
     * 画像をDBに保存する。
     * 更新処理のため、@Transactional アノテーションを付与する。
     * これにより、このメソッドで RuntimeException が発生したとき、
     * 自動的にロールバックされる様になる。
     *
     * @Transactional は、AOPで実装されている。
     */
    @Transactional
    @Override
    public int save(Integer userId, FileUploadForm form) {
        return dao.get(Photo.class, "savePhoto")
                .addValue("id", userId)
                .addValue("image", form.getFileData(), SqlValueType.BLOB)
                .save();
    }

    /**
     * 画像をDBから読み出す。
     */
    @Override
    public Photo load(Integer userId) {
        try {
            return dao.get(Photo.class, "loadPhoto")
                    .addValue("id", userId)
                    .load();
        } catch (final EmptyResultDataAccessException e) {
            // 本当だったら、No Images とかの画像を返したり・・。
            return new Photo();
        }
    }

    // 教育用に作成した、SqlDaoをDIしている。
    @Autowired
    private SqlDao dao;

}
