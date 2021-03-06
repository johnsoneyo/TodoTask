package com.simplesystem.todotask.service;

import com.simplesystem.todotask.vm.CreateTodoVM;
import com.simplesystem.todotask.vm.ModifyTodoVM;
import com.simplesystem.todotask.vm.TodoVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoService {

  /**
   *  Creates a new todo item
   * @param todo
   * @return
   */
  CreateTodoVM create(CreateTodoVM todo);

  /**
   * Receives id of the todo item and json patch value
   * @param id
   * @param todoVM
   * @return
   */
  TodoVM modify(Long id, ModifyTodoVM todoVM);


  /**
   *
   * Retrieves all todo items, filters/ sort data
   * @param todo
   * @param pageable
   * @return
   */
  Page<TodoVM> findAll(TodoVM todo,Pageable pageable);

  /**
   * Retrieves one todo item by ID
   * @param id
   * @return
   */
  TodoVM findOne(Long id);

  /**
   * Removes one todo item by their ID
   * @param id
   */
  void deleteOne(Long id);
}
