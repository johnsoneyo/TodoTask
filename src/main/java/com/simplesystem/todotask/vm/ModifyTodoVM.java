package com.simplesystem.todotask.vm;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ApiModel(value="Modify Todo Model",description = "View model for modifying data between the business object")
public class ModifyTodoVM {

  String description;
  TodoStatus status;

}
