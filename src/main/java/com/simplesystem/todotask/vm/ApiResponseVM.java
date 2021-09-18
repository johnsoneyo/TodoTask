package com.simplesystem.todotask.vm;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.simplesystem.todotask.common.NullCollectionSerializer;
import io.swagger.annotations.ApiModel;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@ApiModel(value="Api Response View Model",description = "View model for transfering data between the business object")
public class ApiResponseVM<T> {

  T body;
  @JsonSerialize(nullsUsing = NullCollectionSerializer.class)
  Set<FieldErrorVM> errors;


}
