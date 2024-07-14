package com.blogapi.service;

import com.blogapi.payload.CommentDto;

import java.util.List;

public interface CommentService {

    public CommentDto createComment(long postId, CommentDto commentDto);

    public List<CommentDto> getAllCommentsByPostId(long postId);


    public void deleteCommentById(long postId, long id);

    public CommentDto getCommentByCommentId(long postId, long id);

    public CommentDto updateCommentById(long postId, long id, CommentDto commentDto);
}
