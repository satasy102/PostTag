package com.chanhtin.postandtag.repository;

import com.chanhtin.postandtag.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitleContaining(String title);
    @Query(value = "select post_tags.post_id, posts.posted_at  from post_tags\n" +
            "inner join posts on posts.post_id=post_tags.post_id where tag_id=?1 order by posts.posted_at desc",nativeQuery = true)
    List<Post> findAllByTag(Long tag_id);


}
