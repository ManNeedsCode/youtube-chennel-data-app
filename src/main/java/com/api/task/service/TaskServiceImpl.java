package com.api.task.service;

import com.api.task.model.TaskBE;
import com.api.task.model.VideoDetailsBE;
import com.api.task.repository.TaskRepository;
import com.google.api.services.youtube.model.PlaylistItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.api.task.model.VideoDetailsBEBuilder.aVideoDetailsBE;
import static com.api.task.model.TaskBEBuilder.aTaskBE;

@Service
public class TaskServiceImpl implements TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    private TaskRepository taskRepository;

    @Override
    @Transactional
    @Async
    public CompletableFuture<Long> createTask(String youtubeChannelId) throws ExecutionException, InterruptedException, IOException {
        final long start = System.currentTimeMillis();
        List<VideoDetailsBE> videoDetailsBEList = new ArrayList<>();

        Optional<TaskBE> optionalTask = taskRepository.findByChannelId(youtubeChannelId);
        TaskBE task = optionalTask.isPresent() ? optionalTask.get() : aTaskBE().withChannelId(youtubeChannelId).build();

        List<VideoDetailsBE> oldVideoDetails = task.getVideoDetailsBEList();
        LOGGER.info("Fetching details of videos of channel {}", youtubeChannelId);
        List<PlaylistItem> playlistItemList = YoutubeAccessUtil.GetDateFromYoutubeChennel(youtubeChannelId);
        LOGGER.info("Number of videos in youtube chennel {} are " + playlistItemList.size(), youtubeChannelId);
        for (PlaylistItem playlistItem : playlistItemList) {
            String videoId = playlistItem.getContentDetails().getVideoId();
            VideoDetailsBE videoDetailsBE = aVideoDetailsBE()
                    .withVideoId(playlistItem.getContentDetails().getVideoId())
                    .withDescription(playlistItem.getSnippet().getDescription())
                    .withVideoLink("https://www.youtube.com/watch?v=" + videoId)
                    .withTask(task)
                    .build();
            if (oldVideoDetails == null
                    || !oldVideoDetails.stream().filter(videoDetails -> videoDetails.equals(videoDetailsBE)).findFirst().isPresent()) {
                videoDetailsBEList.add(videoDetailsBE);
            }
        }
        task.setVideoDetailsBEList(videoDetailsBEList);
        LOGGER.info("Saving task with details of videos");
        task = taskRepository.save(task);
        LOGGER.info("Elapsed time: {}", (System.currentTimeMillis() - start));
        return CompletableFuture.completedFuture(task.getId());
    }

    public List<VideoDetailsBE> getVideosLinkedToChannel(Long taskId) {
        Optional<TaskBE> task = taskRepository.findById(taskId);
        return task.isPresent() ? taskRepository.getById(taskId).getVideoDetailsBEList() : null;
    }
}
