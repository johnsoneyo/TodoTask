package com.simplesystem.todotask.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.simplesystem.todotask.bo.TodoBo;
import com.simplesystem.todotask.controller.TodoControllerTest.ObjectMapperConfig;
import com.simplesystem.todotask.repository.TodoRepository;
import com.simplesystem.todotask.vm.CreateTodoVM;
import com.simplesystem.todotask.vm.TodoStatus;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(ObjectMapperConfig.class)
class TodoControllerTest {

  MockMvc mockMvc;

  @TestConfiguration
  static class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
      return new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

  }


  @Autowired
  WebApplicationContext context;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  TodoRepository repository;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(context)
        .build();
  }


  @Test
  @SneakyThrows
  @DisplayName("when todo list is created with invalid params it is unsuccessfull")
  void test__create_with_nodescription_and_invalid_due_date() {

    byte[] data = Files.readAllBytes(Paths.get("src/test/resources/requests/invalid-todo.json"));

    mockMvc.perform(post("/todos").contentType("application/json;charset=UTF-8").content(data))
        .andDo(print())
        .andExpect(jsonPath("$.errors", not(empty())))
        .andExpect(jsonPath("$.errors", hasSize(2)))
        .andExpect(jsonPath("$.errors[0].message", is("title is required")))
        .andExpect(jsonPath("$.errors[1].message", is("due date must be in the future")))
        .andExpect(status().isBadRequest());

  }


  @Test
  @SneakyThrows
  @DisplayName("when todo list is created with valid params it is successfull")
  void test__create_with_description_and_valid_due_date() {

    byte[] data = objectMapper
        .writeValueAsString(new CreateTodoVM().withDescription("test description").withDueDate(
            LocalDateTime.now().plusMinutes(5))).getBytes();

    mockMvc.perform(post("/todos").contentType("application/json;charset=UTF-8").content(data))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body.id", notNullValue()))
        .andExpect(status().isCreated());

  }

  @Test
  @SneakyThrows
  @DisplayName("when todo description is patched value is updated")
  @Transactional
  void test__modify_with_description() {

    TodoBo createdTodo = repository.saveAndFlush(new TodoBo().withStatus(TodoStatus.NOT_DONE)
        .withCreationDate(LocalDateTime.now())
        .withDoneDate(null).withDescription("test todo")
        .withDueDate(LocalDateTime.now().plusMinutes(5)));

    byte[] data = Files
        .readAllBytes(Paths.get("src/test/resources/requests/patch-description-todo.json"));

    mockMvc.perform(
        patch("/todos/" + createdTodo.getId()).contentType("application/json;charset=UTF-8")
            .content(data))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body.description", is("modified new description")))
        .andExpect(jsonPath("$.body.status", is("NOT_DONE")))
        .andExpect(status().isOk());

  }

  @Test
  @SneakyThrows
  @DisplayName("when todo status is marked as done , doneDate is updated")
  @Transactional
  void test__modify_status_done() {

    TodoBo createdTodo = repository.saveAndFlush(new TodoBo().withStatus(TodoStatus.NOT_DONE)
        .withCreationDate(LocalDateTime.now())
        .withDoneDate(null).withDescription("test todo")
        .withDueDate(LocalDateTime.now().plusMinutes(5)));

    byte[] data = Files
        .readAllBytes(Paths.get("src/test/resources/requests/patch-update-status-done.json"));

    mockMvc.perform(
        patch("/todos/" + createdTodo.getId()).contentType("application/json;charset=UTF-8")
            .content(data))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body.doneDate", notNullValue()))
        .andExpect(jsonPath("$.body.status", is("DONE")))
        .andExpect(status().isOk());

  }

  @Test
  @SneakyThrows
  @DisplayName("when todo status is marked as past due , error is generated")
  @Transactional
  void test__modify_status_pastdue_has_errors() {

    TodoBo createdTodo = repository.saveAndFlush(new TodoBo().withStatus(TodoStatus.NOT_DONE)
        .withCreationDate(LocalDateTime.now())
        .withDoneDate(null).withDescription("test todo")
        .withDueDate(LocalDateTime.now().plusMinutes(5)));

    String  errorMessage = String.format("Todo status with id %d cannot be modified as past due",createdTodo.getId());

    byte[] data = Files
        .readAllBytes(Paths.get("src/test/resources/requests/patch-update-status-pastdue.json"));

    mockMvc.perform(
        patch("/todos/" + createdTodo.getId()).contentType("application/json;charset=UTF-8")
            .content(data))
        .andDo(print())

        .andExpect(jsonPath("$.errors", not(empty())))
        .andExpect(jsonPath("$.errors[0].message", is(errorMessage)))
        .andExpect(status().isBadRequest());

  }

  @Test
  @SneakyThrows
  @DisplayName("when todo status modified with a non existing todo item , error is generated")
  @Transactional
  void test__modify_notfound_todo__returns_error() {


    String  errorMessage = String.format("Todo with id %d not found",-1);

    byte[] data = Files
        .readAllBytes(Paths.get("src/test/resources/requests/patch-update-status-pastdue.json"));

    mockMvc.perform(
        patch("/todos/-1").contentType("application/json;charset=UTF-8")
            .content(data))
        .andDo(print())

        .andExpect(jsonPath("$.errors", not(empty())))
        .andExpect(jsonPath("$.errors[0].message", is(errorMessage)))
        .andExpect(status().isNotFound());

  }



  @Test
  @DisplayName("when todo is fetched by NOT_DONE status returns 3 items")
  @SneakyThrows
  void test_todo_items_returns_3_itemsfor_notdone_status() {

    mockMvc.perform(get("/todos?status=NOT_DONE"))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body.content", hasSize(2)))
        .andExpect(status().isOk());

  }

  @Test
  @DisplayName("when todo is fetched returns total 5 items")
  @SneakyThrows
  void test_todo_items_returns_5_items_foralltodos() {

    mockMvc.perform(get("/todos"))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body.content", hasSize(5)))
        .andExpect(status().isOk());

  }

  @Test
  @DisplayName("when todo is fetched with id 10 returns total 1 item")
  @SneakyThrows
  void test_gettodo_item_returns_anitem_() {

    mockMvc.perform(get("/todos/10"))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(jsonPath("$.body", is(notNullValue())))
        .andExpect(jsonPath("$.body.id", is(10)))
        .andExpect(status().isOk());

  }


  @Test
  @DisplayName("when todo is deleted response returns a no content")
  @SneakyThrows
  void test_deletetodo_item_returns_no_content() {

    TodoBo createdTodo = repository.saveAndFlush(new TodoBo().withStatus(TodoStatus.NOT_DONE)
        .withCreationDate(LocalDateTime.now())
        .withDoneDate(null).withDescription("test todo")
        .withDueDate(LocalDateTime.now().plusMinutes(5)));

    mockMvc.perform(
        delete("/todos/" + createdTodo.getId()))
        .andDo(print())
        .andExpect(jsonPath("$.errors", is(empty())))
        .andExpect(status().isNoContent());

    Assertions.assertThat(repository.findById(createdTodo.getId()))
        .isEqualTo(Optional.empty());

  }


  @Test
  @SneakyThrows
  @DisplayName("when todo status deleted with a non existing todo item , error is generated")
  @Transactional
  void test_deleting_notfound_todo__returns_error() {

    String  errorMessage = String.format("Todo with id %d not found",-1);

    mockMvc.perform(
        delete("/todos/-1"))
        .andDo(print())
        .andExpect(jsonPath("$.errors", not(empty())))
        .andExpect(jsonPath("$.errors[0].message", is(errorMessage)))
        .andExpect(status().isNotFound());

  }



}