package com.simplesystem.todotask.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesystem.todotask.controller.TodoControllerTest.ObjectMapperConfig;
import com.simplesystem.todotask.vm.CreateTodoVM;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ObjectMapperConfig.class)
class TodoControllerTest {

  MockMvc mockMvc;

  @TestConfiguration
  static class ObjectMapperConfig{

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
      ObjectMapper  objectMapper =   new ObjectMapper();
       objectMapper.findAndRegisterModules();
       return  objectMapper;
    }

  }


  @Autowired
  WebApplicationContext context;
  @Autowired
  ObjectMapper objectMapper;

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
  @SneakyThrows
  @DisplayName("when todo list is created with invalid params it is unsuccessfull")
  void test__create_with_nodescription_and_invalid_due_date() {

    byte[] data = Files.readAllBytes(Paths.get("src/test/resources/requests/invalid-todo.json"));

    mockMvc.perform(post("/todos").contentType("application/json;charset=UTF-8").content(data))
        .andDo(print())
        .andExpect(jsonPath("$.errors", not(empty())))
        .andExpect(jsonPath("$.errors",hasSize(2)))
        .andExpect(jsonPath("$.errors[0].message",is("title is required")))
        .andExpect(jsonPath("$.errors[1].message",is("due date must be in the future")))
        .andExpect(status().isBadRequest());

  }


  @Test
  @SneakyThrows
  @DisplayName("when todo list is created with valid params it is successfull")
  void test__create_with_description_and_valid_due_date() {


   byte[] data= objectMapper.writeValueAsString(new CreateTodoVM().withDescription("test description").withDueDate(
        LocalDateTime.now().plusMinutes(5))).getBytes();

    mockMvc.perform(post("/todos").contentType("application/json;charset=UTF-8").content(data))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body.id",notNullValue()))
        .andExpect(status().isCreated());

  }


}