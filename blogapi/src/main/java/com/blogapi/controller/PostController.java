package com.blogapi.controller;

import com.blogapi.payload.PostDto;
import com.blogapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    public PostController(PostService postService){
        this.postService=postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){

        if(result.hasErrors()){

            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto savedDto = postService.createPost(postDto);

        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){

        PostDto postDto = postService.getPostById(id);

        return new  ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPost(@RequestParam(value="pageNo",defaultValue="0",required = false) int pageNo,
                                                    @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
                                                    @RequestParam(value="sortBy",defaultValue = "id",required = false) String sortBy,
                                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir) {

        List<PostDto> postDtos = postService.getAllPost(pageNo,pageSize,sortBy,sortDir);

        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id){

        postService.deletePostById(id);

        return new ResponseEntity<>("post is deleted",HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePostById(@PathVariable("id") long id,@Valid @RequestBody PostDto postDto,BindingResult result){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto updatedDto = postService.updatePostById(id,postDto);
        return new ResponseEntity<>(updatedDto,HttpStatus.OK);

    }

}
