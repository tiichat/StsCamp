package com.tiichat.sts.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;
import java.util.List;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.tiichat.sts.validator.base.CorrelationValidator;

/**
 * 期間を相関チェックするための、アノテーションクラス
 *
 * @author tiich
 *
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { PeriodValidator.class })
public @interface Period {

    @Target({ TYPE, ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public static @interface List {
        Period[] value();
    }

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "{com.tiichat.sts.validator.Period.message}";

    String fieldFrom() default "from";

    String fieldTo() default "to";
}

/**
 * 相関チェックの本体。
 *
 * @author tiich
 *
 */
class PeriodValidator extends CorrelationValidator<Period> {

    /**
     * 相関チェック対象となる、ｎ個の項目名を登録する。 それとは別に、メッセージも登録する。
     */
    @Override
    public final void initialize(Period annotation) {
        fields.add(annotation.fieldFrom());
        fields.add(annotation.fieldTo());
        this.message = annotation.message();
    }

    /**
     * itemsのインデックスは、initializeで項目名をセットした順。
     * 項目名自体が何になるかは、アノテーションの記述による。
     *
     * このサンプルでは、日付From・To が逆転していないかどうかをチェックしている。
     */
    @Override
    public boolean isValid(List<Object> items) {
        final Date from = (Date) items.get(0);
        final Date to = (Date) items.get(1);

        return !(from != null && to != null && from.compareTo(to) > 0);
    }

}