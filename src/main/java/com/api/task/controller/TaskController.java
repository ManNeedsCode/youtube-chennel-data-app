package com.api.task.controller;

import com.api.task.model.VideoDetailsBE;
import com.api.task.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/api")
public class TaskController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private TaskService taskService;

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    @PostMapping("/tasks/{youtubeChannelId}")
    public CompletableFuture<ResponseEntity> createTask(@PathVariable String youtubeChannelId) throws ExecutionException, InterruptedException, IOException {
        return taskService.createTask(youtubeChannelId).<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleFailure);

    }

    @GetMapping("/tasks/{taskId}")
    public List<VideoDetailsBE> getVideosLinkedToChannel(@PathVariable Long taskId) {
        return taskService.getVideosLinkedToChannel(taskId);
    }

    private static Function<Throwable, ResponseEntity<? extends Long>> handleFailure = throwable -> {
        LOGGER.error("Failed to create task: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };
}

