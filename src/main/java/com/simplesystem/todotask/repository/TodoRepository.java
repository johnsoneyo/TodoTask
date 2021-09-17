package com.simplesystem.todotask.repository;

import com.simplesystem.todotask.bo.TodoBo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodoRepository extends JpaRepository<TodoBo,Long>, JpaSpecificationExecutor<TodoBo> {

}
