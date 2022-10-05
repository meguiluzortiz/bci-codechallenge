package com.example.bci.web.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

@Slf4j
public class PropertyPatternValidator implements ConstraintValidator<PropertyPattern, CharSequence> {

    private final Environment environment;
    private Map<String, Pattern> patterns = new HashMap<>();
    private Pattern pattern;

    public PropertyPatternValidator(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void initialize(PropertyPattern propertyPattern) {
        String propertyKey = propertyPattern.property();

        pattern = patterns.computeIfAbsent(propertyKey, key -> {
            String propertyValue = environment.getProperty(key);
            return Pattern.compile(propertyValue);
        });
    }

    @Override
    public boolean isValid(CharSequence inputToValidate, ConstraintValidatorContext ctx) {
        CharSequence input = inputToValidate != null ? inputToValidate : "";

        if (input.toString().isEmpty()) return true;

        Matcher matcher = pattern.matcher(input);

        log.debug("Password value must accomplish the pattern {}", matcher.pattern().toString());
        return matcher.matches();
    }

}
