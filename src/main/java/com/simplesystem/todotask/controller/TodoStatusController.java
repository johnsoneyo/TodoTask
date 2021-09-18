package com.simplesystem.todotask.controller;

import com.simplesystem.todotask.vm.ApiResponseVM;
import com.simplesystem.todotask.vm.TodoStatus;
import com.simplesystem.todotask.vm.TodoVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "statuses")
@RequiredArgsConstructor
public class TodoStatusController {

  @Operation(summary = "retrieves todo status types")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Retrieves all valid todo status types", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = TodoVM.class))})})
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  ApiResponseVM<List<TodoStatus>> findAll() {

    return new ApiResponseVM<List<TodoStatus>>().withBody(Arrays.stream(TodoStatus.values())
        .filter(todoStatus -> !Objects.equals(todoStatus, TodoStatus.PAST_DUE))
        .collect(Collectors.toList()));

  }

}
