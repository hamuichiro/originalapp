package com.example.originalapp.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented //そのアノテーションは Javadoc API ドキュメントの出力にも表示されるようになる
@Constraint(validatedBy = PasswordEqualsValidator.class) //入力チェックを行うクラスを指定
@Target({ ElementType.TYPE }) //アノテーションがどの要素（クラスやメソッドなど）に対して適用することができるかを定義, Class、Interface、Enum可
@Retention(RetentionPolicy.RUNTIME) //アノテーションで付加された情報がどの段階まで保持されるかを定義,
@ReportAsSingleViolation //アノテーションに設定したメッセージが使われるようになる
public @interface PasswordEquals {
	
	//独自のアノテーションのプロパティにはmessage()、groups()、payload()の指定が必要
    String message() default "{com.example.originalapp.validation.constraints.PasswordEquals.message}"; //デフォルトのエラーメッセージを指定
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; //groups()、payload()荷は必ず空の配列を指定

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List { //アノテーションの定義
        PasswordEquals[] value(); //アノテーションのメンバとして配列を持たせる
    }
}