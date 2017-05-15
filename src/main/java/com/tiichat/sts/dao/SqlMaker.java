package com.tiichat.sts.dao;

import java.io.IOException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * ORマッパーを使わずに、JDBCでSQL文を投げて処理を行うための、SQL文字列合成を行うクラス。
 *
 * @author tiich
 *
 * @param <T>
 *            結果セットのレコード情報を格納するＤＴＯクラス。
 */
public class SqlMaker<T> {

    /**
     * コンストラクタ
     *
     * @param sqlParts
     *            SQL部分文字列
     * @param jdbc
     *            JDBCアクセスオブジェクト
     * @param dtoClass
     *            dtoクラス
     */
    SqlMaker(Map<String, String> sqlParts,
            NamedParameterJdbcTemplate jdbc, Class<T> dtoClass) {
        this.sqlParts = sqlParts;
        this.ps = new MapSqlParameterSource();
        this.sql = this.sqlParts.get(base);
        this.jdbc = jdbc;
        this.dtoClass = dtoClass;
    }

    /**
     * SQLのSELECT文で、WHERE句を指定する際に使用する。 抽出条件を、１つずつ追加していく。
     *
     * @param key
     *            抽出条件のキー
     * @param value
     *            抽出条件の値
     * @return
     * @throws IOException
     */
    public SqlMaker<T> addValue(String key, Object value) {
        return this.addValue(key, value, SqlValueType.EQUAL);
    }

    /**
     * SQLのSELECT文で、WHERE句を指定する際に使用する。 抽出条件を、１つずつ追加していく。
     *
     * @param key
     *            抽出条件のキー
     * @param value
     *            抽出条件の値
     * @param valueType
     *            値の種別
     * @return
     * @throws IOException
     */
    public SqlMaker<T> addValue(String key, Object value, SqlValueType valueType) {
        if (!isNull(value)) {
            if (valueType == SqlValueType.BLOB) {
                this.ps.addValue(key, valueType.format(value), Types.BLOB);
            } else {
                this.ps.addValue(key, valueType.format(value));
            }
            concatSql(key);
        }
        return this;
    }

    /**
     * 検索条件に一致するリストを取得する。
     *
     * @return DTOインスタンスのリストを返す。
     */
    public List<T> query() {
        return jdbc.query(this.sql, this.ps, new BeanPropertyRowMapper<T>(dtoClass));
    }

    /**
     * レコードを保存する。
     *
     * @return
     */
    public int save() {
        if (0 == jdbc.queryForObject(this.sqlParts.get("count"), this.ps,
                Integer.class)) {
            return jdbc.update(this.sqlParts.get("insert"), this.ps);
        } else {
            return jdbc.update(this.sqlParts.get("update"), this.ps);
        }
    }

    /**
     * レコードを取得する。
     *
     * @return
     */
    public T load() {
        return jdbc.queryForObject(this.sql, this.ps,
                new BeanPropertyRowMapper<T>(dtoClass));
    }

    /*
     * Nullチェック。
     */
    private boolean isNull(Object value) {
        return value == null || (value instanceof String && ((String) value).isEmpty());
    }

    /*
     * SQL文字列を合成する。教育用だけれども、サンプルなので + で連結してしまっている。
     */
    private void concatSql(String key) {
        final String part = this.sqlParts.get(key);
        if (part != null) {
            sql += " " + part;
        }
    }

    private final NamedParameterJdbcTemplate jdbc;

    private final Class<T> dtoClass;

    private final MapSqlParameterSource ps;

    private String sql;

    private final Map<String, String> sqlParts;

    // application.yaml のプロパティ階層構造に特定のルールを設けていて、 SqlMakerは、それを前提としている。
    private static final String base = "base";

}
