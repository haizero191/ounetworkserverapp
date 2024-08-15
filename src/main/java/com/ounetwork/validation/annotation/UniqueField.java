/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.validation.annotation;
import com.ounetwork.validation.validator.UniqueFieldValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
/**
 *
 * @author Admin
 */

@Constraint(validatedBy = UniqueFieldValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueField {
    String message() default "Field value must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String table(); // tên bảng trong database

    String field(); // tên cột trong bảng database

}
