package com.chanhtin.postandtag.controller.API;

import com.chanhtin.postandtag.model.Post;
import com.chanhtin.postandtag.service.PostService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class PostAPIController {

    final static Logger logger = Logger.getLogger("post and tag");

    @Autowired
    private PostService postService;

    @GetMapping(value = "/posts/")
    public ResponseEntity<List<Post>> listPost() {
        try {
            List<Post> posts = postService.findAll();
            if(posts.size()==0)
                return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
            return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not find All Post", ex);
            return new ResponseEntity<List<Post>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        try {
            boolean post = postService.deleteById(id);
                return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not delete a Post", ex);
            return new ResponseEntity<Boolean>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<Post> listPost(@PathVariable("id") Long id) {
        try {
            Post post = postService.findById(id);
            if (post == null) {
                return new ResponseEntity<Post>(post, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not find a Post", ex);
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/post/", method = RequestMethod.PUT)
    public ResponseEntity<Post> update(@RequestBody Post post) {
        try{
            postService.save(post);
            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not update a Post", ex);
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/post/")
    public ResponseEntity<Post> savePost(@RequestBody Post post) {
        try{
            postService.save(post);
            return new ResponseEntity<Post>(post, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Can not add a Post", ex);
            return new ResponseEntity<Post>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
