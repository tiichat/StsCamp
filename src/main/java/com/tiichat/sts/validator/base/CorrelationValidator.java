package com.tiichat.sts.validator.base;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 相関チェック用の、基底クラス。
 *
 * @author tiich
 *
 * @param <A>
 *            アノテーションを指定する。
 */
public abstract class CorrelationValidator<A extends Annotation>
        implements ConstraintValidator<A, Object> {

    /**
     * このメソッドをオーバーライドして、チェックロジックを記述する。
     *
     * @param items
     *            相関チェック対象の値リスト。
     * @return
     */
    public abstract boolean isValid(List<Object> items);

    /**
     * リクエストされてきた入力値を、BeanWrapper でラップし値を取り出す。
     */
    @Override
    public final boolean isValid(Object value, ConstraintValidatorContext context) {
        final BeanWrapper bean = new BeanWrapperImpl(value);
        final List<Object> items = new ArrayList<>();
        for (final String fld : fields) {
            items.add(bean.getPropertyValue(fld));
        }
        if (!isValid(items)) {
            rewriteMessage(context);
            return false;
        }
        return true;
    }

    /**
     * 標準のメッセージを置き換える。
     *
     * @param context
     */
    private void rewriteMessage(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(fields.get(0))
                .addConstraintViolation();
    }

    /**
     * 相関チェック対象となる項目の、項目名リスト。
     */
    protected final List<String> fields = new ArrayList<>();

    /**
     * 置換するメッセージ。基本は、メッセージを格納しているプロパティファイルのキー名。
     */
    protected String message;

}
