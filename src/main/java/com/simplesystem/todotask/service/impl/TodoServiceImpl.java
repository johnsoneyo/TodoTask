package com.simplesystem.todotask.service.impl;

import com.simplesystem.todotask.bo.TodoBo;
import com.simplesystem.todotask.controller.advice.TodoException;
import com.simplesystem.todotask.controller.advice.TodoNotFoundException;
import com.simplesystem.todotask.repository.TodoRepository;
import com.simplesystem.todotask.service.TodoJobService;
import com.simplesystem.todotask.service.TodoService;
import com.simplesystem.todotask.vm.CreateTodoVM;
import com.simplesystem.todotask.vm.ModifyTodoVM;
import com.simplesystem.todotask.vm.TodoStatus;
import com.simplesystem.todotask.vm.TodoVM;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;
  private final ModelMapper mapper;
  private final TodoJobService jobService;

  @Override
  @Transactional
  public CreateTodoVM create(CreateTodoVM todo) {
    TodoBo created = todoRepository.save(mapper.map(todo, TodoBo.class).withCreationDate(LocalDateTime.now()).withStatus(TodoStatus.NOT_DONE));
    jobService.scheduleTodo(created);
    return mapper.map(created, CreateTodoVM.class);
  }

  @Override
  @Transactional
  public TodoVM modify(Long id, ModifyTodoVM source) {

    return todoRepository.findById(id).map(destination -> {
      mapper.map(source, destination);

      if (Objects.equals(source.getStatus(), TodoStatus.DONE)) {
        destination.setDoneDate(LocalDateTime.now());
      }

      if (Objects.equals(source.getStatus(), TodoStatus.PAST_DUE)) {
        throw new TodoException(String.format("Todo status with id %d cannot be modified as past due", id));
      }

      return destination;
    }).map(modifiedTodo -> mapper.map(modifiedTodo, TodoVM.class))
        .orElseThrow(
            () -> new TodoNotFoundException(String.format("Todo with id %d not found", id)));
  }

  @Override
  public Page<TodoVM> findAll(TodoVM todo, Pageable pageable) {

    Specification<TodoBo> specification = Specification
        .where((root, query, builder) -> builder.isNotNull(root.get("id")));

    if (Objects.nonNull(todo.getStatus())) {
      specification = specification
          .and((root, query, builder) -> builder.equal(root.get("status"), todo.getStatus()));
    }
    List<TodoVM> todos = todoRepository.findAll(specification, pageable)
        .get().map(t -> mapper.map(t, TodoVM.class)).collect(Collectors.toList());
    return new PageImpl<TodoVM>(todos, pageable, todos.size());
  }

  @Override
  public TodoVM findOne(Long id) {
    return todoRepository.findById(id)
        .map(todo -> mapper.map(todo, TodoVM.class))
        .orElseThrow(() -> new TodoNotFoundException(String.format("Todo with id %d not found", id)));
  }


}
