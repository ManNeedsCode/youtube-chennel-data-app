package com.api.task.model;

public final class VideoDetailsBEBuilder {
    private Long id;
    private String videoId;
    private String description;
    private String videoLink;
    private TaskBE task;

    private VideoDetailsBEBuilder() {
    }

    public static VideoDetailsBEBuilder aVideoDetailsBE() {
        return new VideoDetailsBEBuilder();
    }

    public VideoDetailsBEBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public VideoDetailsBEBuilder withVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public VideoDetailsBEBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public VideoDetailsBEBuilder withVideoLink(String videoLink) {
        this.videoLink = videoLink;
        return this;
    }

    public VideoDetailsBEBuilder withTask(TaskBE task) {
        this.task = task;
        return this;
    }

    public VideoDetailsBE build() {
        VideoDetailsBE videoDetailsBE = new VideoDetailsBE();
        videoDetailsBE.setId(id);
        videoDetailsBE.setVideoId(videoId);
        videoDetailsBE.setDescription(description);
        videoDetailsBE.setVideoLink(videoLink);
        videoDetailsBE.setTaskBE(task);
        return videoDetailsBE;
    }
}
