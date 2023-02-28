package ru.yandex.practicum.filmorate.validation;

import org.junit.jupiter.api.BeforeAll;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.util.Set;

public abstract class AbstractValidationTest {

    protected static Validator validator;

    @BeforeAll
    static void beforeAll() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    protected <T> Class<? extends Annotation> getAnnotationType(ConstraintViolation<T> violation) {
        return violation.getConstraintDescriptor().getAnnotation().annotationType();
    }

    /**
     * Проверяет, есть ли нарушение аннотации annotationType, примененной к свойству property.
     * @param model объект модели, содержащей свойство
     * @param property аннотированное свойство
     * @param annotationType тип аннотации, примененной к свойству
     * @param validator
     * @return true - нарушение аннотации есть
     * <p>
     * false - нарушения нет
     */
    protected <T> boolean hasAnnotationViolation(T model, String property,
                                                     Class<? extends Annotation> annotationType, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        for (ConstraintViolation<T> violation : violations) {
            String currProperty = violation.getPropertyPath().toString();
            Class<? extends Annotation> currAnnotationType = getAnnotationType(violation);
            if (currProperty.equals(property) && currAnnotationType.equals(annotationType))
                return true;
        }
        return false;
    }

    /**
     * Проверяет, есть ли нарушение хотя бы одной аннотации, примененной к свойству property.
     * @param model объект модели, содержащей свойство
     * @param property аннотированное свойство
     * @param validator
     * @return
     * true - хотя бы одна аннотация, примеренная к свойству нарушена
     * <p>
     * false - нет нарушенных аннотаций
     */
    protected <T> boolean hasAnnotationViolation(T model, String property, Validator validator) {
        Set<ConstraintViolation<T>> violations = validator.validate(model);
        for (ConstraintViolation<T> violation : violations) {
            String currProperty = violation.getPropertyPath().toString();
            if (currProperty.equals(property))
                return true;
        }
        return false;
    }
}
