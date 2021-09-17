package com.simplesystem.todotask.vm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@ApiModel(value="Todo Model",description = "View model for transfering data between the business object")
@JsonIgnoreProperties(value={ "id" }, allowGetters=true)
public class TodoVM {

  Long id;
  String description;
  LocalDateTime dueDate;
  TodoStatus status;
  LocalDateTime creationDate;
  LocalDateTime doneDate;

}
