package com.simplesystem.todotask.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.simplesystem.todotask.bo.TodoBo;
import com.simplesystem.todotask.controller.advice.TodoNotFoundException;
import com.simplesystem.todotask.repository.TodoRepository;
import com.simplesystem.todotask.service.TodoService;
import com.simplesystem.todotask.vm.CreateTodoVM;
import com.simplesystem.todotask.vm.TodoStatus;
import com.simplesystem.todotask.vm.TodoVM;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
  private final ObjectMapper objectMapper;

  @Override
  @Transactional
  public CreateTodoVM create(CreateTodoVM todo) {
    TodoBo created = todoRepository.save(mapper.map(todo, TodoBo.class)
        .withCreationDate(LocalDateTime.now()).withStatus(TodoStatus.NOT_DONE));
    return mapper.map(created, CreateTodoVM.class);
  }

  @Override
  @Transactional
  public TodoVM modify(Long id, TodoVM source) {

    return todoRepository.findById(id).map(destination -> {
      mapper.map(source, destination);
      return destination;
    }).map(modifiedTodo -> mapper.map(modifiedTodo, TodoVM.class))
        .orElseThrow(TodoNotFoundException::new);
  }

  @SneakyThrows
  private TodoBo patchToObject(JsonPatch patch, TodoBo todo) {
    JsonNode patched = patch.apply(objectMapper.convertValue(todo, JsonNode.class));
    String status = patched.path("status").asText();
    return objectMapper.treeToValue(patched, TodoBo.class);
  }
}
