package com.simplesystem.todotask.bo;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseBo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

}
