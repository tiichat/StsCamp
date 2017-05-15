/*
 * 実装クラスのパッケージをどう分けるかは、考えどころ。
 */
package com.tiichat.sts.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.tiichat.sts.service.CsvService;

/**
 * サービスの実装クラス アノテーション @Service で、このクラスがサービスであることを宣言する。
 *
 * このサービスは、@Scope で、セッションスコープのサービスとして定義している。
 * サービスが DIされるコントローラのスコープがシングルトンだとエラーとなるため、
 * proxyMode = ScopedProxyMode.TARGET_CLASS を設定している。
 * これにより、CGLIBを利用してDI時にProxyを作成する動きをする。
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CsvServiceImpl implements CsvService {

    /**
     * CSVデータを返す。
     *
     * 教育用に、インスタンス変数を使うサービスの例を示す。
     */
    @Override
    public String get(String csvName) {
        counter++;
        if (csvName.equals("hoge")) {
            return "1,abc,21," + counter;
        } else {
            return "xyz,xyz," + counter;
        }
    }

    // セッションスコープを実験するためのカウンタ。
    private int counter = 0;
}
