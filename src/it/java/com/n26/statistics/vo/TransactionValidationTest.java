package com.n26.statistics.vo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static junit.framework.TestCase.*;


public class TransactionValidationTest {
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @Before
    public void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void shouldHaveNoViolations() {
        //given:
        Transaction transaction = new Transaction(BigDecimal.ONE, LocalDateTime.now());

        //when:
        Set<ConstraintViolation<Transaction>> violations
                = validator.validate(transaction);

        //then:
        assertTrue(violations.isEmpty());
    }

    @Test
    public void shouldDetectInvalidAmount() {
        //given no amount:
        Transaction transaction = new Transaction(null, LocalDateTime.now());

        //when:
        Set<ConstraintViolation<Transaction>> violations
                = validator.validate(transaction);

        //then:
        assertEquals(violations.size(), 1);

        ConstraintViolation<Transaction> violation
                = violations.iterator().next();
        assertEquals("must not be null",
                violation.getMessage());
    }


    @Test
    public void shouldDetectInvalidTimeStampFuture() {
        //given timestampInFuture:
        Transaction transaction = new Transaction(BigDecimal.TEN, LocalDateTime.of(2100, 1, 1, 1, 1));

        //when:
        Set<ConstraintViolation<Transaction>> violations
                = validator.validate(transaction);

        //then:
        assertEquals(violations.size(), 1);

        ConstraintViolation<Transaction> violation
                = violations.iterator().next();
        assertEquals("must be a date in the past or in the present",
                violation.getMessage());
    }



    @After
    public void close() {
        validatorFactory.close();
    }

}