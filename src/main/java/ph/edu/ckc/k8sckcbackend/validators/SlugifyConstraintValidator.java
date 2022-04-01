package ph.edu.ckc.k8sckcbackend.validators;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/*@SupportedValidationTarget({ValidationTarget.PARAMETERS, ValidationTarget.ANNOTATED_ELEMENT})*/
public class SlugifyConstraintValidator implements ConstraintValidator<Slugify, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null) return true;
        return !s.contains(" ");
    }

}
