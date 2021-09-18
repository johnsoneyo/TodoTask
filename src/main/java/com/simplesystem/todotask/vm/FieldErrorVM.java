package com.simplesystem.todotask.vm;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With

@ApiModel(value="Field Error Model",description = "View model for bean validation error")
public class FieldErrorVM {

  String objectName;
  String field;
  String message;

}
