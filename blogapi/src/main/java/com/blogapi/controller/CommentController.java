package com.blogapi.controller;

import com.blogapi.payload.CommentDto;
import com.blogapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createCommentByPostId(@PathVariable(value="postId") long postId, @RequestBody CommentDto commentDto){


        CommentDto savedCommentDto = commentService.createComment(postId,commentDto);

        return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable(value="postId") long postId){

        List<CommentDto> commentDtos = commentService.getAllCommentsByPostId(postId);
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable (value="postId") long postId,@PathVariable(value="id") long id){

        commentService.deleteCommentById(postId,id);
        return new ResponseEntity<>("comment is deleted",HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable(value="postId") long postId,@PathVariable(value="id") long id){

        CommentDto commentDto = commentService.getCommentByCommentId(postId,id);

        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") long postId,@PathVariable("id") long id, @RequestBody CommentDto commentDto){
        CommentDto updatedCommentDto = commentService.updateCommentById(postId,id,commentDto);

        return new ResponseEntity<>(updatedCommentDto,HttpStatus.OK);
    }


}
