package com.ruso.zapicito.validator;

import com.ruso.zapicito.dto.EmployeeDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        EmployeeDto user = (EmployeeDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
