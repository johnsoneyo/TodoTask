package com.simplesystem.todotask.service;

import com.simplesystem.todotask.vm.CreateTodoVM;

public interface TodoService {

  /**
   *  Creates a new todo item
   * @param todo
   * @return
   */
  CreateTodoVM create(CreateTodoVM todo);
}
