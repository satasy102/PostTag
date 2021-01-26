package com.chanhtin.postandtag.service;

import com.chanhtin.postandtag.model.Post;

import java.util.List;

public interface PostService extends BaseService<Post>{
    List<Post> findAllByTitleContaining(String title);
    List<Post> findAllByTag(Long id);
}
