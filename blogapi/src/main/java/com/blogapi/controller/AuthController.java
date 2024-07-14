package com.blogapi.controller;

import com.blogapi.entity.Role;
import com.blogapi.entity.User;
import com.blogapi.payload.LogInDto;
import com.blogapi.payload.SignUpDto;
import com.blogapi.repository.RoleRepository;
import com.blogapi.repository.UserRepository;
import com.blogapi.security.JwtAuthResponse;
import com.blogapi.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private TokenProvider tokenProvider;
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LogInDto logInDto) {


            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInDto.getUsernameOrEmail(), logInDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);
            return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);


    }

    @PostMapping("/signout")
    public ResponseEntity<String> signOutUser(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof UserDetails){

           String username =  ((UserDetails) principal).getUsername();

           User user = userRepo.findByUsername(username).get();

           if(user!=null){

              // database related logic like removing  session related data ;
           }
        }

        SecurityContextHolder.clearContext();

        return new ResponseEntity<>("user successfully sign-out",HttpStatus.OK);
    }




    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(@RequestBody SignUpDto signUpDto){

        if(userRepo.existsByEmail(signUpDto.getEmail())){

            return new ResponseEntity<>("email is already taken", HttpStatus.BAD_REQUEST);
        }

        if(userRepo.existsByUsername(signUpDto.getUsername())){

            return new ResponseEntity<>("username is already taken",HttpStatus.BAD_REQUEST);

        }

        User user = new User();

        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepo.findByName("ROLE_USER").get();

        user.setRoles(Collections.singleton(roles));

        userRepo.save(user);

        return new ResponseEntity<>("user registered successfully",HttpStatus.CREATED);
    }




}
