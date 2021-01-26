package com.chanhtin.postandtag.service.impl;

import com.chanhtin.postandtag.model.Tag;
import com.chanhtin.postandtag.repository.TagRepository;
import com.chanhtin.postandtag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepository tagRepository;

    @Override
    public List<Tag> findAllByNameContaining(String name) {
        return tagRepository.findAllByNameContaining(name);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag save(Tag object) {
        return tagRepository.save(object);
    }

    @Override
    public boolean deleteById(Long id) {
        Tag tag = findById(id);
        if(tag != null){
            tag.setDeleted(true);
            save(tag);
            return true;
        }
        return false;
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }
}
