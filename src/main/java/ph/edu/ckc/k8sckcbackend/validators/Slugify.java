package ph.edu.ckc.k8sckcbackend.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = SlugifyConstraintValidator.class
)
public @interface Slugify {

    String message() default "{ph.edu.ckc.k8sckcbackend.validators.Slugify.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
