package com.simplesystem.todotask.controller;

import com.simplesystem.todotask.service.TodoService;
import com.simplesystem.todotask.vm.ApiResponseVM;
import com.simplesystem.todotask.vm.CreateTodoVM;
import com.simplesystem.todotask.vm.ModifyTodoVM;
import com.simplesystem.todotask.vm.TodoVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @Operation(summary = "creates a todo item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Todo item created successfully", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = CreateTodoVM.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ApiResponseVM<CreateTodoVM> create(@RequestBody @Valid CreateTodoVM todo) {

    return new ApiResponseVM<CreateTodoVM>().withBody(todoService.create(todo));

  }

  @Operation(summary = "modify a todo item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Todo item modified successfully", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = ModifyTodoVM.class))}),
      @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)})
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ApiResponseVM<TodoVM> modify(@PathVariable Long id, @RequestBody @Valid ModifyTodoVM todoVM) {

    return new ApiResponseVM<TodoVM>().withBody(todoService.modify(id, todoVM));

  }

  @Operation(summary = "retrieves pagination of todo items")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retrieves todo items", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = TodoVM.class))})})
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  ApiResponseVM<Page<TodoVM>> findAll(@NotNull TodoVM todo, @PageableDefault(page = 0, size = 100)
  @SortDefault.SortDefaults({
      @SortDefault(sort = "creationDate", direction = Sort.Direction.DESC)}) Pageable pageable) {

    return new ApiResponseVM<Page<TodoVM>>().withBody(todoService.findAll(todo, pageable));

  }


  @Operation(summary = "retrieves a todo item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retrieves a todo item", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = TodoVM.class))})})
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  ApiResponseVM<TodoVM> findOne(@PathVariable Long id) {

    return new ApiResponseVM<TodoVM>().withBody(todoService.findOne(id));

  }

  @Operation(summary = "removes a todo item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Removes a todo item", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = TodoVM.class)),
      }),
      @ApiResponse(responseCode = "404", description = "Todo item not found", content = @Content)})
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  ApiResponseVM<?> deleteOne(@PathVariable Long id) {
    todoService.deleteOne(id);
    return new ApiResponseVM<>();

  }


}
