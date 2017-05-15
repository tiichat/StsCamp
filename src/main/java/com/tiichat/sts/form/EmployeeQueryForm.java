package com.tiichat.sts.form;

import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.tiichat.sts.validator.Age;
import com.tiichat.sts.validator.Period;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 社員検索用のフォームクラス。ただし、教育のため日付型など検索キーと無関係なものも定義している。
 *
 * Springでは、フォームクラスはゲッタ・セッタが必須で必要。
 * AOPの教育用に、フォーム情報を出力しているため、@ToString も指定している。
 *
 * 相関チェック用のアノテーション @Period を教育用に作成している。
 * 相関チェックは、クラスに対してアノテーションを設定する。
 *
 * @author tiich
 *
 */
@Getter
@Setter
@ToString
@Period(fieldFrom = "from", fieldTo = "to")
public class EmployeeQueryForm extends Form {
    private Integer deptId;

    @Pattern(regexp = "[0-9]*")
    private String id;

    /*
     * message を明示的に指定し、デフォルトメッセージから変更している。
     * 基本、src/main/resources
     * 直下の、ValidationMessage.properties を参照しに行く。
     * メッセージを日本語にしたい場合は、_ja 付きのプロパティファイルを作成する。
     */
    @Size(max = 5, message = "{Size.name.message}")
    private String name;

    /*
     * 単項目チェック用のアノテーション @Age を、教育用に作成している。
     * マスタ関連チェック用アノテーションは作成していないが、作りは同じ。
     */
    @Age(min = 3, max = 125)
    private Integer age;

    /*
     * HTML5で、<input type="date" />
     * としたとき、文字列は、ISO.DATEでフォーマットされる。（Chrome では・・・）
     */
    @DateTimeFormat(iso = ISO.DATE)
    private Date from;

    @DateTimeFormat(iso = ISO.DATE)
    private Date to;
}
