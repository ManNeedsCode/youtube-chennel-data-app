package com.api.task.controller;

import com.api.task.BaseIT;
import com.api.task.service.YoutubeAccessUtil;
import com.google.api.services.youtube.model.*;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@TestMethodOrder(OrderAnnotation.class)
public class TaskControllerIT extends BaseIT {
    private static final String VIDEO_ID = "Video_1";
    private static final String DESCRIPTION = "Description_1";
    private static final long TASK_ID = 1L;

    @MockBean
    YoutubeAccessUtil youtubeAccessUtil;

    @Test
    @Order(1)
    public void shouldCreateTask() throws IOException {
        //given
        ChannelListResponse channelListResponse = new ChannelListResponse();
        Channel channel = new Channel();
        ChannelContentDetails channelContentDetails = new ChannelContentDetails();
        ChannelContentDetails.RelatedPlaylists relatedPlaylists = new ChannelContentDetails.RelatedPlaylists();
        relatedPlaylists.setUploads("Upload_Id");
        channelContentDetails.setRelatedPlaylists(relatedPlaylists);
        channel.setContentDetails(channelContentDetails);
        channelListResponse.setItems(Arrays.asList(channel));

        PlaylistItem playlistItem = new PlaylistItem();
        PlaylistItemContentDetails playlistItemContentDetails = new PlaylistItemContentDetails();
        playlistItemContentDetails.setVideoId(VIDEO_ID);
        PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
        playlistItemSnippet.setDescription(DESCRIPTION);

        playlistItem.setContentDetails(playlistItemContentDetails);
        playlistItem.setSnippet(playlistItemSnippet);

        Mockito.when(youtubeAccessUtil.getDataFromYoutubeChennel("my_test_youtube_channel_id")).thenReturn(Arrays.asList(playlistItem));

        //when
        ResponseEntity<Long> response = testRestTemplate.postForEntity( "/api/tasks/my_test_youtube_channel_id", null, Long.class);
        long taskId = response.getBody();

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(taskId);
        assertEquals(TASK_ID, taskId);
    }

    @Test
    @Order(2)
    public void shouldGetVideosDetail() {
        //when
        ResponseEntity<List> response =  testRestTemplate.getForEntity("/api/tasks/1", List.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        assertThat(response.getBody().size()).isEqualTo(1);
        LinkedHashMap videoDetails = (LinkedHashMap) response.getBody().get(0);
        assertThat(videoDetails.get("videoId")).isEqualTo(VIDEO_ID);
        assertThat(videoDetails.get("description")).isEqualTo(DESCRIPTION);
        assertThat(videoDetails.get("videoLink")).isEqualTo("https://www.youtube.com/watch?v=" + VIDEO_ID);
    }
}
