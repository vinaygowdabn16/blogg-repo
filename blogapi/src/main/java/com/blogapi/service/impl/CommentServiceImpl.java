package com.blogapi.service.impl;

import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.CommentDto;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private PostRepository postRepo;

    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo,CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

       Post post =  postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException(postId)
        );

       Comment comment = new Comment();
       comment.setName(commentDto.getName());
       comment.setEmail(commentDto.getEmail());
       comment.setBody(commentDto.getBody());
       comment.setPost(post);

        Comment savedComment = commentRepo.save(comment);
        CommentDto savedCommentDto = mapToDto(savedComment);
        return savedCommentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException(postId)
        );

        List<Comment> comments = commentRepo.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id)
        );

        commentRepo.deleteById(id);
        return;
    }

    @Override
    public CommentDto getCommentByCommentId(long postId, long id) {

       Post post =  postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException(postId)
        );

       Comment comment  = commentRepo.findById(id).orElseThrow(
               ()->new ResourceNotFoundException(id)
       );

        CommentDto commentDto = this.mapToDto(comment);

        return commentDto;
    }

    @Override
    public CommentDto updateCommentById(long postId, long id, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException(postId)
        );

        Comment comment = commentRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepo.save(comment);

        CommentDto updatedCommentDto = this.mapToDto(updatedComment);

        return updatedCommentDto;
    }

    public CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;

    }
}
