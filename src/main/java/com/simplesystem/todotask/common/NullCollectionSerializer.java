package com.simplesystem.todotask.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullCollectionSerializer extends JsonSerializer<Collections> {
  @Override
  public void serialize(
      final Collections list,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider
  ) throws IOException {
    jsonGenerator.writeStartArray();
    jsonGenerator.writeEndArray();
  }

  public static class DueDateValidator implements ConstraintValidator<ValidDueDate, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime dueDate, ConstraintValidatorContext context) {
      return dueDate.isAfter(LocalDateTime.now());
    }

  }
}