package com.blogapi.service;

import com.blogapi.payload.PostDto;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    public PostDto getPostById(long id);

    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    public void deletePostById(long id);

    public PostDto updatePostById(long id, PostDto postDto);
}
