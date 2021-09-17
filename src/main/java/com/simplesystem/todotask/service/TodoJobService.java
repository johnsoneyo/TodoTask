package com.simplesystem.todotask.service;

import com.simplesystem.todotask.bo.TodoBo;
import com.simplesystem.todotask.config.TodoStatusUpdateJob;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodoJobService {

  private final Scheduler scheduler;

  @SneakyThrows
  @Async
  public void scheduleTodo(TodoBo todo) {

    log.info("Registering Todo description [{}] with id [{}] in scheduler",todo.getDescription(),todo.getId());

    JobDataMap jobDataMap = new JobDataMap();

    jobDataMap.put("description", todo.getDescription());
    jobDataMap.put("id", todo.getId());
    jobDataMap.put("dueDate", todo.getDueDate());
    JobDetail jobDetail = JobBuilder.newJob(TodoStatusUpdateJob.class)
        .withIdentity(UUID.randomUUID().toString(), "todo-jobs")
        .withDescription("Todo Job")
        .usingJobData(jobDataMap)
        .storeDurably()
        .build();

    ZonedDateTime startAt = ZonedDateTime.of(todo.getDueDate(), ZoneId.systemDefault());

    Trigger trigger = TriggerBuilder.newTrigger()
        .forJob(jobDetail)
        .withIdentity(jobDetail.getKey().getName(), "todo-triggers")
        .withDescription("Todo Trigger")
        .startAt(Date.from(startAt.toInstant()))
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
        .build();

    scheduler.scheduleJob(jobDetail,trigger);


  }

}
