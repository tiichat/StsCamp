package com.tiichat.sts.dao;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "sqldef")
@Getter
@Setter
public class SqlSource {

    /**
     * プロパティファイル（application.yaml）から、SQL部分文字列を取得する。
     * とりあえず、application.yaml 本体に、SQL文字列を定義している。
     *
     * @param key
     *            SQL部分文字列を取得するためのキー。
     * @return
     */
    public Map<String, String> get(String key) {
        return this.query.get(key);
    }

    private Map<String, Map<String, String>> query;
}
