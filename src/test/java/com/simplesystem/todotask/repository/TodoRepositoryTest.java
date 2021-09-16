package com.simplesystem.todotask.repository;

import com.simplesystem.todotask.bo.TodoBo;
import com.simplesystem.todotask.vm.TodoStatus;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TodoRepositoryTest {

  @Autowired
  private TodoRepository repository;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }

  @DisplayName("when new Todo object is saved, it is successfull")
  @Test
  @Transactional
  void test_newTodo_saves_successfully() {

    Assertions.assertThat( repository.save(new TodoBo().withStatus(TodoStatus.NOT_DONE)
    .withCreationDate(LocalDateTime.now())
    .withDoneDate(null).withDescription("test todo").withDueDate(LocalDateTime.now().plusMinutes(5))))
       .isNotNull()
       .hasFieldOrPropertyWithValue("status",TodoStatus.NOT_DONE)
       .hasFieldOrPropertyWithValue("id",1L);

  }


}