package com.tiichat.sts.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring入門のための、最初の Helloクラス。
 * RestControllerアノテーションは、WebAPI用のコントローラを作成するときに設定する。
 * このサンプルでは、単純に文字列 "Hello" を返すだけなので、RestControllerとしている。
 *
 * @author tiich
 *
 */
@RestController
public class FirstController {

    /**
     * このWebサイトのインデックス用。 http://hoge-domain/
     * にリクエストしたとき、このメソッドに処理が渡ってくる。 ドメインに続く、パスを
     * RequestMapping で指定する。
     *
     * @return
     */
    @RequestMapping(value = "/")
    public String index() {
        return "Hello";
    }
}
