package com.api.task.service;

import com.api.task.model.TaskBE;
import com.api.task.model.VideoDetailsBE;
import com.api.task.repository.TaskRepository;
import com.google.api.services.youtube.model.PlaylistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.api.task.model.VideoDetailsBEBuilder.aVideoDetailsBE;
import static com.api.task.model.TaskBEBuilder.aTaskBE;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Long createTask(String youtubeChannelId) throws ExecutionException, InterruptedException, IOException {
        List<VideoDetailsBE> videoDetailsBEList = new ArrayList<>();

        TaskBE task = taskRepository.findByChannelId(youtubeChannelId);
        task = task != null ? task : aTaskBE().withChannelId(youtubeChannelId).build();

        List<VideoDetailsBE> oldVideoDetails = task.getVideoDetailsBEList();

        List<PlaylistItem> playlistItemList = YoutubeAccessUtil.GetDateFromYoutubeChennel(youtubeChannelId);
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
        task = taskRepository.save(task);
        return task.getId();
    }

    public List<VideoDetailsBE> getVideosLinkedToChannel(Long taskId) {
        TaskBE taskBE = taskRepository.getById(taskId);
        return taskBE != null ? taskRepository.getById(taskId).getVideoDetailsBEList() : null;
    }
}
