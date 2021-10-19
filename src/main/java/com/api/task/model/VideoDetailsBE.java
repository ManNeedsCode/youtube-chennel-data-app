package com.api.task.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name="t_video_details")
public class VideoDetailsBE implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String videoId;

    @Column(name = "description", length = 1024)
    private String description;

    private String videoLink;

    @ManyToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "task_id", nullable = false)
    @JsonBackReference
    private TaskBE taskBE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public TaskBE getTaskBE() {
        return taskBE;
    }

    public void setTaskBE(TaskBE taskBE) {
        this.taskBE = taskBE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoDetailsBE)) return false;
        VideoDetailsBE videoDetails = (VideoDetailsBE) o;
        return Objects.equals(getVideoId(), videoDetails.getVideoId())
                && Objects.equals(getDescription(), videoDetails.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideoId());
    }
}
