package com.example.originalapp.validation.constraints;



import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


//入力チェックのクラスはjavax.validation.ConstraintValidatorインターフェイスを実装
public class PasswordEqualsValidator implements ConstraintValidator<PasswordEquals, Object> { //型パラメータはチェックするアノテーション、チェック対象の値の型を指定

    private String field1;
    private String field2;
    private String message;

    @Override
    public void initialize(PasswordEquals annotation) { //入力チェッククラスが初期化されたときに実行する処理を記述
        field1 = "password";
        field2 = "passwordConfirmation";
        message = annotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) { //チェックを行う処理を記述
        BeanWrapper beanWrapper = new BeanWrapperImpl(value); //Setter,Getterを使わずに、プロパティの値を設定したり取得したりするAPI
        String field1Value = (String) beanWrapper.getPropertyValue(field1); //プロパティの値を取得
        String field2Value = (String) beanWrapper.getPropertyValue(field2);
        if ((field1Value.isEmpty() || field2Value.isEmpty()) || Objects.equals(field1Value, field2Value)) {
            return true;
        } else {
        	//動的にエラーメッセージを変えられる
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(field2).addConstraintViolation();
            return false;
        }
    }

}