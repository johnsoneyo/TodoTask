package com.simplesystem.todotask.vm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplesystem.todotask.validator.ValidDueDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
