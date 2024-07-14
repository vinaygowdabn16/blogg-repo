package com.blogapi.repository;

import com.blogapi.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(long PostId);

    List<Comment> findByEmail(String email);

    List<Comment> findByName(String name);
    List<Comment> findByNameOrEmail(String name,String email);
    List<Comment> findByEmailAndName(String email,String name);

    @Override
    boolean existsById(Long id);


    boolean existsByEmail(String email);

    boolean existsByNameOrEmail(String name,String email);

    boolean existsByPostId(long postId);
}
