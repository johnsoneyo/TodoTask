package com.simplesystem.todotask.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.simplesystem.todotask.vm.TodoStatus;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoStatusControllerTest {

  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .build();
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  @DisplayName("")
  @SneakyThrows
  void findAll() {

    mockMvc.perform(get("/statuses"))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body", not(empty())))
        .andExpect(jsonPath("$.body",contains("NOT_DONE","DONE")))
        .andExpect(jsonPath("$.body",hasSize(2)))
        .andExpect(status().isOk());


  }
}