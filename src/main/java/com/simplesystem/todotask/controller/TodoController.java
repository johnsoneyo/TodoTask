package com.simplesystem.todotask.controller;

import com.simplesystem.todotask.service.TodoService;
import com.simplesystem.todotask.vm.ApiResponseVM;
import com.simplesystem.todotask.vm.CreateTodoVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Collections;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "todos")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  @Operation(summary = "create a todo item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Todo item creation successfull", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CreateTodoVM.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ApiResponseVM<CreateTodoVM> create(@RequestBody @Valid CreateTodoVM todo) {
    return new ApiResponseVM<CreateTodoVM>().withBody(todoService.create(todo))
        .withErrors(Collections.emptySet());
  }

}
