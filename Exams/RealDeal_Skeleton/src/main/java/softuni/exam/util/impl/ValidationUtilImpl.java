package softuni.exam.util.impl;

import org.springframework.stereotype.Component;
import softuni.exam.util.ValidationUtil;

import javax.validation.Validator;

@Component
public class ValidationUtilImpl implements ValidationUtil {
    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E e) {
        return this.validator.validate(e).isEmpty();
    }
}
