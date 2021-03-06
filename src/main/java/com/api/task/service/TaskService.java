package com.api.task.service;

import com.api.task.model.VideoDetailsBE;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface TaskService {
    /**
     *
     * @param youtubeChannelId
     * @return CompletableFuture object containing taskId - id of created task
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws IOException
     */
    CompletableFuture<Long> createTask(String youtubeChannelId) throws ExecutionException, InterruptedException, IOException;

    /**
     *
     * @param taskId - id of task passed to get detail of Youtube videos in a specific channel
     * @return List of VideoDetailsBE
     */
    List<VideoDetailsBE> getVideosLinkedToChannel(Long taskId);
}
