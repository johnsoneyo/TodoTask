package com.simplesystem.todotask.validator;

import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DueDateValidator implements ConstraintValidator<ValidDueDate, LocalDateTime> {

  @Override
  public boolean isValid(LocalDateTime dueDate, ConstraintValidatorContext context) {
    return dueDate.isAfter(LocalDateTime.now());
  }

}