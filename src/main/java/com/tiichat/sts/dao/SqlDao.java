package com.tiichat.sts.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * DAOクラス。SqlMakerのインスタンスを生成するためのクラス。
 *
 * SqlMakerは内部でSQL文を構築するため、スレッドセーフではない作りになっている。
 * そのため、リクエスト毎にインスタンスを生成する必要があり、DIさせることは出来ない。
 * コントローラの様なシングルトンの中で、寿命の短いオブジェクトをＤＩさせられない。
 *
 * しかし、SqlSource, NamedParameterJdbcTemplate は、シングルトンとしてDIさせたい。
 * ところが前述の通り、SqlMakerは @Autowired させられないので、
 * その内部で @Autowired することはできない。ワイヤーが途切れてしまい、途切れた先はDIされない。
 *
 * そこで、SqlDaoで SqlSource などＤＢアクセスに必要なオブジェクトをＤＩし、
 * SqlMaker生成時に、引数として渡す様にする。
 *
 * @author tiich
 *
 */
@Component
public class SqlDao {

    /**
     * SqlMakerのインスタンスを生成する。
     *
     * @param dtoClass 結果セットのレコード情報をセットするＤＴＯクラスオブジェクト。
     * @param sqlParamKey SQL部分文字列の中から、必要なパーツを特定するためのキー。
     * @return
     */
    public <T> SqlMaker<T> get(Class<T> dtoClass, String sqlParamKey) {
        return new SqlMaker<T>(sqlSource.get(sqlParamKey), jdbc, dtoClass);
    }

    /*
     * プロパティファイルから、SQL文を取得するクラスを DI。
     */
    @Autowired
    private SqlSource sqlSource;

    /*
     * jdbcTemplate は、JDBCアクセスを色々楽チンにしてくれる。
     * NamedParameterJdbcTemplate は、名前付きのパラメタを扱える。
     * DBへの接続情報などは、application.properties に記述する。
     *
     * @Autowired で DI したオブジェクトは、デフォルトでシングルトンになることを忘れないこと。
     * jdbcTemplate は、スレッドセーフな作りのため、シングルトンで問題なし。
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

}
