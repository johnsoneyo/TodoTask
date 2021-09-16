package com.simplesystem.todotask.service.impl;

import com.simplesystem.todotask.bo.TodoBo;
import com.simplesystem.todotask.repository.TodoRepository;
import com.simplesystem.todotask.service.TodoService;
import com.simplesystem.todotask.vm.TodoStatus;
import com.simplesystem.todotask.vm.CreateTodoVM;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;
  private final ModelMapper mapper;

  @Override
  @Transactional
  public CreateTodoVM create(CreateTodoVM todo) {
    TodoBo created = todoRepository.save(mapper.map(todo, TodoBo.class)
        .withCreationDate(LocalDateTime.now()).withStatus(TodoStatus.NOT_DONE));
    return mapper.map(created, CreateTodoVM.class);
  }
}
