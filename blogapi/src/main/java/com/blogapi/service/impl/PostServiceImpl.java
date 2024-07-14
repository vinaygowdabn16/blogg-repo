package com.blogapi.service.impl;


import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.PostDto;


import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepo;

    private  ModelMapper modelMapper;

   public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper){

       this.postRepo=postRepo;
       this.modelMapper=modelMapper;
   }

    @Override
    public PostDto createPost(PostDto postDto) {

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());


        Post post = mapToEntity(postDto);

        Post savedPost = postRepo.save(post);

//        PostDto savedDto=new PostDto();
//        savedDto.setId(savedPost.getId());
//        savedDto.setTitle(savedPost.getTitle());
//        savedDto.setDescription(savedPost.getDescription());
//        savedDto.setContent(savedPost.getContent());

        PostDto savedDto = mapToDto(savedPost);


        return savedDto;
    }

    @Override
    public PostDto getPostById(long id) {

        Post post =postRepo.findById(id).orElseThrow(

                ()->new ResourceNotFoundException(id)
        );

//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());

        PostDto postDto = mapToDto(post);

        return postDto;


    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

      Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> pageContent = postRepo.findAll(pageable);
        List<Post> posts = pageContent.getContent();


        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());


        return postDtos;
    }

    @Override
    public void deletePostById(long id) {

        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
       );
        postRepo.deleteById(id);

    }

    @Override
    public PostDto updatePostById(long id, PostDto postDto) {

       Post post = postRepo.findById(id).orElseThrow(
               ()->new ResourceNotFoundException(id)
       );

       post.setTitle(postDto.getTitle());
       post.setContent(postDto.getContent());
       post.setDescription(postDto.getDescription());

       Post updatedPost = postRepo.save(post);

       PostDto updatedDto = mapToDto(updatedPost);
        return updatedDto;
    }

    public PostDto mapToDto(Post post){

       PostDto postDto = modelMapper.map(post,PostDto.class);
//       PostDto postDto = new PostDto();
//       postDto.setId(post.getId());
//       postDto.setTitle(post.getTitle());
//       postDto.setDescription(post.getDescription());
//       postDto.setContent(post.getContent());

       return postDto;
    }

    public Post mapToEntity(PostDto postDto){

       Post post = modelMapper.map(postDto,Post.class);
//       Post post = new Post();
//       post.setTitle(postDto.getTitle());
//       post.setDescription(postDto.getDescription());
//       post.setContent(postDto.getContent());



       return post;
    }
}
