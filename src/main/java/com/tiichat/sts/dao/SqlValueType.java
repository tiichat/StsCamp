package com.tiichat.sts.dao;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.web.multipart.MultipartFile;

/**
 * SQL文に埋め込む、値の種類。
 *
 * @author tiich
 *
 */
public enum SqlValueType {

    /**
     * 特になにもしない。
     */
    EQUAL {
        @Override
        Object format(Object value) {
            return value;
        }
    },

    /**
     * 部分一致させるために、% を前後に付与している。当然、ＳＱＬ文側は、LIKE前提。
     */
    LIKE {
        @Override
        Object format(Object value) {
            return "%" + value.toString() + "%";
        }
    },

    /**
     * 未実装。配列を IN句に埋め込むときに、何らかの成型が必要なら、ここに記述する。
     */
    IN {
        @Override
        Object format(Object value) {
            return value;
        }
    },

    /**
     * BLOB型は、SqlLobValue にしてあげる必要がある。
     */
    BLOB {
        @Override
        Object format(Object value) {
            final MultipartFile blob = (MultipartFile) value;
            InputStream is;
            try {
                is = blob.getInputStream();
            } catch (final IOException e) {
                is = null;
            }
            return new SqlLobValue(is, (int) blob.getSize(), new DefaultLobHandler());
        }
    };

    /**
     * 値の種類によって、SQL文に埋め込めるように成型する。
     *
     * @param value
     * @return
     */
    abstract Object format(Object value);
}
