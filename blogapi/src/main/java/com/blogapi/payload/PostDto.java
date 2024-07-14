package com.blogapi.payload;


import lombok.*;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;

    @NotEmpty(message="title is empty")
    @Size(min=2,message="title should be more than 2 letter")
    private String title;

    @NotEmpty(message="description is empty")
    @Size(min=2,message="description should be more than 2 letter")
    private String description;

    @NotEmpty(message="content is empty")
    @Size(min=2,message="content should be more than 2 letter")
    private String content;
}
