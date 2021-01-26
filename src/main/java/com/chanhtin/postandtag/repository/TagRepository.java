package com.chanhtin.postandtag.repository;

import com.chanhtin.postandtag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findAllByNameContaining(String name);
}
