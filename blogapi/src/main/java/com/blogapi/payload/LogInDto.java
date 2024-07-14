package com.blogapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInDto {

    private String usernameOrEmail;
    private String password;
}
