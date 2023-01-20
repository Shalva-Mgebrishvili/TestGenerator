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
            return ValidationError.throwError(context, "Email must not be blank.");
        }

        Pattern pattern = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");

        if(!pattern.matcher(value).matches()) {
            return ValidationError.throwError(context, "Invalid email format.");
        }

        return true;
    }

}
