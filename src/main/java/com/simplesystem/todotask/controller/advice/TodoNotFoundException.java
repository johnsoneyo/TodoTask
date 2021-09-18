package com.simplesystem.todotask.controller.advice;

public class TodoNotFoundException  extends RuntimeException{

  public TodoNotFoundException(String message) {
    super(message);
  }
}
