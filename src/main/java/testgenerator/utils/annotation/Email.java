package testgenerator.utils.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidatorImpl.class)
public @interface Email {

    String message() default "Invalid email format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
