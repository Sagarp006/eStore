package com.shruteekatech.electronicstore.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameCheck {
    //default error message
    String message() default "Invalid Image Name!!!";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
