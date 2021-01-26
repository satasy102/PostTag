package com.chanhtin.postandtag.service;

import com.chanhtin.postandtag.model.Tag;

import java.util.List;

public interface TagService extends BaseService<Tag>{
    List<Tag> findAllByNameContaining(String name);
}
