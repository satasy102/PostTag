package com.chanhtin.postandtag.service.impl;

import com.chanhtin.postandtag.model.Post;
import com.chanhtin.postandtag.repository.PostRepository;
import com.chanhtin.postandtag.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;
    @Override
    public List<Post> findAllByTitleContaining(String title) {
        return postRepository.findAllByTitleContaining(title);
    }

    @Override
    public List<Post> findAllByTag(Long id) {
        return postRepository.findAllByTag(id);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post object) {
        if(object.getPost_id()==null) {
            ZonedDateTime today = ZonedDateTime.now();
            object.setPostedAt(today);
        } else {
            Post post = findById(object.getPost_id());
            ZonedDateTime posted = post.getPostedAt();
            object.setPostedAt(posted);
            ZonedDateTime today = ZonedDateTime.now();
            object.setLastUpdatedAt(today);
        }
        return postRepository.save(object);
    }

    @Override
    public boolean deleteById(Long id) {
        Post post=findById(id);
        if(post!=null) {
            post.setDeleted(true);
            save(post);
            return true;
        }
        return false;
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }
}
