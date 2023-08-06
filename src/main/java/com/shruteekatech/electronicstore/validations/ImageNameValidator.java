package com.shruteekatech.electronicstore.validations;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Slf4j
public class ImageNameValidator implements ConstraintValidator<ImageNameCheck,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("Message from isValid");
        return !value.isBlank();
    }
}
