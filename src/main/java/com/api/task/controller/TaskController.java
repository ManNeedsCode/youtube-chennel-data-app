package com.api.task.controller;

import com.api.task.model.VideoDetailsBE;
import com.api.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/")
    public String home() {
        return "Hello Docker World";
    }

    @PostMapping("/tasks/{youtubeChannelId}")
    public Long createTask(@PathVariable String youtubeChannelId) throws ExecutionException, InterruptedException, IOException {
        return taskService.createTask(youtubeChannelId);
    }

    @GetMapping("/tasks/{taskId}")
    public List<VideoDetailsBE> getVideosLinkedToChannel(@PathVariable Long taskId) {
        return taskService.getVideosLinkedToChannel(taskId);
    }
}

