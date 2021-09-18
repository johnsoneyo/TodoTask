package com.simplesystem.todotask.vm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.simplesystem.todotask.common.ValidDueDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
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
@ApiModel(value="Create Todo Model",description = "View model for transfering data between the business object")
@JsonIgnoreProperties(value={ "id" }, allowGetters=true)
public class CreateTodoVM {

  Long id;
  @NotEmpty(message = "title is required")
  @ApiModelProperty(position = 1, notes = "Title of the todo")
  String description;
  @NotNull
  @ApiModelProperty(position = 2, notes = "Due date of the todo")
  @ValidDueDate(message = "due date must be in the future")
  LocalDateTime dueDate;

}
