package testgenerator.utils.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class EmailValidatorImpl implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value.isBlank()) {
            context.buildConstraintViolationWithTemplate("Email must not be blank.")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        Pattern pattern = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

        if(!pattern.matcher(value).matches()) {
            context.buildConstraintViolationWithTemplate("Invalid mail format.")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }

        return true;
    }

}
