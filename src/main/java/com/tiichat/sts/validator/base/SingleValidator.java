package com.tiichat.sts.validator.base;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 単項目チェック用の、基底クラス。
 *
 * @author tiich
 *
 * @param <A>
 *            アノテーションを指定する。
 * @param <T>
 *            チェック対象の値の型を指定する。
 */
public abstract class SingleValidator<A extends Annotation, T>
        implements ConstraintValidator<A, T> {

    /**
     * このメソッドをオーバーライドして、チェックロジックを記述する。
     *
     * @param value
     *            チェック対象となる値
     * @return
     */
    public abstract boolean isValid(T value);

    /**
     * 単項目チェックの場合、Nullは必ずＯＫとするため、そのロジックだけ入れている。
     * もし、項目が必須項目なのであれば、@NotNull アノテーションを別途付与すれば良い。
     */
    @Override
    public final boolean isValid(T value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return isValid(value);
    }

}
