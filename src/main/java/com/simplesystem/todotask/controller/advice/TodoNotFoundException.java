package com.simplesystem.todotask.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason="id not found for Todo ",value = HttpStatus.NOT_FOUND)
public class TodoNotFoundException extends RuntimeException {

}
