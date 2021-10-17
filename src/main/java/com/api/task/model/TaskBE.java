package com.api.task.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name="t_task")
public class TaskBE implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long id;

    @Column(name = "channel_id", unique = true)
    private String channelId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskBE")
    @JsonManagedReference
    private List<VideoDetailsBE> videoDetailsBEList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public List<VideoDetailsBE> getVideoDetailsBEList() {
        return videoDetailsBEList;
    }

    public void setVideoDetailsBEList(List<VideoDetailsBE> videoDetailsBEList) {
        this.videoDetailsBEList = videoDetailsBEList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskBE)) return false;
        TaskBE task = (TaskBE) o;
        return Objects.equals(getChannelId(), task.getChannelId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getChannelId());
    }
}