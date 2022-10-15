package com.example.bci.web.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
public class PropertyPatternValidator implements ConstraintValidator<PropertyPattern, CharSequence>, EnvironmentAware {

    @Value("${pattern.signup-request.password}")
    String property;

    private Environment environment;
    private Map<String, Pattern> patterns = new HashMap<>();
    private Pattern pattern;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void initialize(PropertyPattern propertyPattern) {
        String propertyKey = propertyPattern.property();

        if(environment == null) {
            return;
        }

        pattern = patterns.computeIfAbsent(propertyKey, key -> {
            String propertyValue = environment.getProperty(key);
            return Pattern.compile(propertyValue);
        });
    }

    @Override
    public boolean isValid(CharSequence inputToValidate, ConstraintValidatorContext ctx) {
        CharSequence input = inputToValidate != null ? inputToValidate : "";

        if (input.toString().isEmpty()) {
            log.info("Input is not set or empty. Validation is not being applied.");
            return true;
        }

        if (environment == null) {
            log.info("Environment is not set. Validation is not being applied.");
            return true;
        }

        Matcher matcher = pattern.matcher(input);

        log.debug("Password value must accomplish the pattern {}", matcher.pattern().toString());
        return matcher.matches();
    }

}
