package com.tiichat.sts.service;

/**
 * CSVダウンロード用のサービスインタフェース
 *
 * @author tiich
 *
 */
public interface CsvService {

    /**
     * CSVデータを取得する。
     *
     * @param csvName
     *            取得したいCSVデータの名前を指定する。
     * @return CSVデータ
     */
    public String get(String csvName);
}
