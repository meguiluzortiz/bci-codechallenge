package com.example.bci.web.request;

import net.datafaker.Faker;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

import static javax.validation.Validation.buildDefaultValidatorFactory;
import static org.assertj.core.api.Assertions.assertThat;

public class SignupRequestTest {

    Faker faker = new Faker();
    final Validator validator = buildDefaultValidatorFactory().getValidator();

    @Nested
    class WhenInvalid {

        String[] errors = new String[] {
                "name: must not be empty",
                "email: must not be empty",
                "password: must not be empty",
                "phones: must not be empty"
        };
        
        @Test
        void shouldHasExpectedViolation() {
            SignupRequest dataClass = new SignupRequest();

            Set<ConstraintViolation<Object>> violations = validator.validate(dataClass);
            assertThat(violations).hasSize(errors.length);
        }

        @Test
        void shouldHasExpectedMessage() {
            SignupRequest dataClass = new SignupRequest();

            Set<ConstraintViolation<Object>> violations = validator.validate(dataClass);
            String[] messages = violations.stream()
                    .map(e -> String.format("%s: %s", e.getPropertyPath(), e.getMessage())).toArray(String[]::new);
            assertThat(messages).containsExactlyInAnyOrder(errors);
        }
    }

    @Nested
    class WhenValid {
        @Test
        void shouldNotHaveViolations() {
            SignupRequest dataClass = new SignupRequest();
            dataClass.setName(faker.name().firstName());
            dataClass.setEmail(faker.internet().emailAddress());
            dataClass.setPassword("1aA!4567");
            dataClass.setPhones(List.of(
                    Phone.builder().number("1234567890").cityCode("01").countryCode("51").build(),
                    Phone.builder().number("1234567891").cityCode("01").countryCode("51").build()
            ));

            Set<ConstraintViolation<Object>> violations = validator.validate(dataClass);

            assertThat(violations).isEmpty();
        }
    }

}
