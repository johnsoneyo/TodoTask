package com.simplesystem.todotask.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Collections;

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
}