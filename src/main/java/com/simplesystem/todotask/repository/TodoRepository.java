package com.simplesystem.todotask.repository;

import com.simplesystem.todotask.bo.TodoBo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoBo,Long> {

}
