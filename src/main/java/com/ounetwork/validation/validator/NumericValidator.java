/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ounetwork.validation.validator;

/**
 *
 * @author Admin
 */
import com.ounetwork.validation.annotation.Numberic;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numberic, String> {
    @Override
    public void initialize(Numberic constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // If null or empty, this is validated by @NotBlank
        }
        return value.matches("\\d+"); // Check if the value contains only digits
    }
}
