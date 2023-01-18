package testgenerator.utils.annotation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordValidatorImpl implements ConstraintValidator<Password, CharSequence> {

    @Override
    public void initialize(Password constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CharSequence charSequence, ConstraintValidatorContext context) {

        if (charSequence.toString().isBlank()) {
            context.buildConstraintViolationWithTemplate("Password must not be blank.")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

            return false;
        }

        PasswordValidator validator = validator();
        RuleResult result = validator.validate(new PasswordData(charSequence.toString()));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = String.join("\n", messages);

        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }

    private PasswordValidator validator() {
        return new PasswordValidator(Arrays.asList(
                new LengthRule(8, 100),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        ));
    }

}
