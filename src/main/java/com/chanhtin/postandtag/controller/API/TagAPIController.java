package com.chanhtin.postandtag.controller.API;

import com.chanhtin.postandtag.model.Post;
import com.chanhtin.postandtag.model.Tag;
import com.chanhtin.postandtag.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/")
public class TagAPIController {

    final static Logger logger = Logger.getLogger("post and tag");

    @Autowired
    private TagService tagService;

    @GetMapping(value = "/tags/")
    public ResponseEntity<List<Tag>> listTags() {
        try {
            List<Tag> tags = tagService.findAll();
            return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not find All Tag", ex);
            return new ResponseEntity<List<Tag>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/tag/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        try {
            boolean tag = tagService.deleteById(id);
            return new ResponseEntity<Boolean>(tag, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not delete a Tag", ex);
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/tag/{id}")
    public ResponseEntity<Tag> listTag(@PathVariable("id") Long id) {
        try {
            Tag tag = tagService.findById(id);
            if (tag == null) {
                return new ResponseEntity<Tag>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Tag>(tag, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not find a Tag", ex);
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/tag/", method = RequestMethod.PUT)
    public ResponseEntity<Tag> update(@RequestBody Tag tag) {
        try {
            tagService.save(tag);
            return new ResponseEntity<Tag>(tag, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not update a Tag", ex);
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/tag/")
    public ResponseEntity<Tag> addTag(@RequestBody Tag object) {
        try {
            Tag tag = tagService.save(object);
            return new ResponseEntity<Tag>(tag, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not add a Tag", ex);
            return new ResponseEntity<Tag>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
