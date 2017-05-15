package com.tiichat.sts.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tiichat.sts.validator.base.SingleValidator;

/**
 * 年齢を単項目チェックするための、アノテーションクラス
 *
 * 最大・最小チェックは既存のアノテーションで行うことができるが、
 * 単項目チェックのサンプルとして作成している。
 *
 * @author tiich
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { AgeValidator.class })
public @interface Age {

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public static @interface List {
        Age[] value();
    }

    String message() default "{com.tiichat.sts.validator.Age.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default 120;
}

/**
 * 年齢チェックのバリデータ本体
 *
 * @author tiich
 *
 */
class AgeValidator extends SingleValidator<Age, Integer> {

    @Override
    public void initialize(Age annotation) {
        min = annotation.min();
        max = annotation.max();
    }

    @Override
    public boolean isValid(Integer value) {
        return value >= min && value <= max;
    }

    int min;
    int max;

}
