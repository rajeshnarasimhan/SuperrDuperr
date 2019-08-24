package com.deltaa.superrduperr.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * custom validator interface for validating whole number with min value of 1.
 * @author Rajesh
 */
@Documented
@NotBlank(message = "Value cannot be blank")
@Min(value=1, message = "Value should be greater than or equal to 0" )
@Digits(fraction = 0, integer = 10, message ="Value should be a whole number")
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
public @interface NumberFormatValidator {

    String message() default "Invalid number format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}