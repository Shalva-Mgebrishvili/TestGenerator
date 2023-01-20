package testgenerator.utils.annotation;

import javax.validation.ConstraintValidatorContext;

public class ValidationError {

    static boolean throwError(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }

}

