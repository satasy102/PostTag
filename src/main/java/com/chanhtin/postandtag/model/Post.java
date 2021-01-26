package com.chanhtin.postandtag.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Where(clause = "deleted=false")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @NotNull
    @Size(max = 100)
    private String title;

    @NotNull
    @Size(max = 250)
    private String description;

    @NotNull
    private String content;

    @NotNull
    @Column(name = "posted_at")
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private ZonedDateTime postedAt= ZonedDateTime.now();

    @NotNull
    @Column(name = "last_updated_at")
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private ZonedDateTime lastUpdatedAt=postedAt;

    @ManyToMany
    @JoinTable(name = "Post_Tag",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private Set<Tag> tags = new HashSet<>();

    private boolean deleted=false;

    public Post(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public Post(String title, String description, String content, Set<Tag> tags) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags = tags;
    }

    public Post(Long post_id, String title, String description, String content, Set<Tag> tags) {
        this.post_id = post_id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags = tags;
    }

    public Post() {
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(ZonedDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public ZonedDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(ZonedDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}