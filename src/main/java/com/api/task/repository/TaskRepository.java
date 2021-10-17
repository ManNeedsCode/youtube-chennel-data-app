package com.api.task.repository;

import com.api.task.model.TaskBE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskBE, Long> {

    Optional<TaskBE> findByChannelId(String channelId);
}