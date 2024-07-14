package com.blogapi.payload;


import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {


    private long id;
    private String name;
    private String email;
    private String username;
    private String password;
}
