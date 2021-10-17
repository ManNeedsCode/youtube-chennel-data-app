package com.api.task.model;

import java.util.List;

public final class TaskBEBuilder {
    private Long id;
    private String channelId;
    private List<VideoDetailsBE> videoDetailsBEList;

    private TaskBEBuilder() {
    }

    public static TaskBEBuilder aTaskBE() {
        return new TaskBEBuilder();
    }

    public TaskBEBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public TaskBEBuilder withChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public TaskBEBuilder withVideoDetailsBEList(List<VideoDetailsBE> videoDetailsBEList) {
        this.videoDetailsBEList = videoDetailsBEList;
        return this;
    }

    public TaskBE build() {
        TaskBE taskBE = new TaskBE();
        taskBE.setId(id);
        taskBE.setChannelId(channelId);
        taskBE.setVideoDetailsBEList(videoDetailsBEList);
        return taskBE;
    }
}
