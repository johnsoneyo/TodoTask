package com.simplesystem.todotask.config;

import com.simplesystem.todotask.repository.TodoRepository;
import com.simplesystem.todotask.service.TodoService;
import com.simplesystem.todotask.vm.TodoStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoStatusUpdateJob extends QuartzJobBean {
  
  private final TodoRepository repository;

  @Override
  @Transactional
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

    JobDataMap jobDataMap = context.getMergedJobDataMap();
    Long id = jobDataMap.getLong("id");
    String description = jobDataMap.getString("description");
    log.info("Running status update Job for todo with id {} and description {} ",id,description);
    repository.findById(id)
        .ifPresent(todoBo -> {
          todoBo.setStatus(TodoStatus.PAST_DUE);
          repository.saveAndFlush(todoBo);
        });
  }
}
