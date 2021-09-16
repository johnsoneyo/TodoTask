package com.simplesystem.todotask.controller.advice;

import com.simplesystem.todotask.vm.ApiResponseVM;
import com.simplesystem.todotask.vm.FieldErrorVM;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ApiResponseVM> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
    Set<FieldErrorVM> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(f -> new FieldErrorVM(f.getObjectName(), f.getField(), f.getDefaultMessage()))
        .collect(Collectors.toSet());
    return new ResponseEntity<>(new ApiResponseVM<>().withErrors(errors), HttpStatus.BAD_REQUEST);
  }

}
