package com.api.task.repository;

import com.api.task.model.TaskBE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskBE, Long> {

    TaskBE findByChannelId(String channelId);
}