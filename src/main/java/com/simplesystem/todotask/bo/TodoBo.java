package com.simplesystem.todotask.bo;

import com.simplesystem.todotask.vm.TodoStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "todo")
@Data
@With
public class TodoBo extends BaseBo {

  @Column
  String description;
  @Column
  @Enumerated(value = EnumType.STRING)
  TodoStatus status;
  @Column(name = "creation_date")
  LocalDateTime creationDate;
  @Column(name = "due_date")
  LocalDateTime dueDate;
  @Column(name = "done_date")
  LocalDateTime doneDate;

}
