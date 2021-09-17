package com.simplesystem.todotask.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.simplesystem.todotask.bo.TodoBo;
import com.simplesystem.todotask.controller.advice.TodoException;
import com.simplesystem.todotask.repository.TodoRepository;
import com.simplesystem.todotask.service.TodoService;
import com.simplesystem.todotask.vm.CreateTodoVM;
import com.simplesystem.todotask.vm.ModifyTodoVM;
import com.simplesystem.todotask.vm.TodoStatus;
import com.simplesystem.todotask.vm.TodoVM;
import java.time.LocalDateTime;
import java.util.Objects;
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
  public TodoVM modify(Long id, ModifyTodoVM source) {

    return todoRepository.findById(id).map(destination -> {
      mapper.map(source, destination);

      if(Objects.equals(source.getStatus(),TodoStatus.DONE)){
        destination.setDoneDate(LocalDateTime.now());
      }

      if(Objects.equals(source.getStatus(),TodoStatus.PAST_DUE)){
        throw new TodoException(String.format("Todo status with id %d cannot be modified as past due",id));
      }

      return destination;
    }).map(modifiedTodo -> mapper.map(modifiedTodo, TodoVM.class))
        .orElseThrow(() -> new TodoException(String.format("Todo with id %d not found ",id)));
  }

  @SneakyThrows
  private TodoBo patchToObject(JsonPatch patch, TodoBo todo) {
    JsonNode patched = patch.apply(objectMapper.convertValue(todo, JsonNode.class));
    String status = patched.path("status").asText();
    return objectMapper.treeToValue(patched, TodoBo.class);
  }
}
