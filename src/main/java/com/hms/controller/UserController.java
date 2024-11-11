package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payloads.LoginDto;
import com.hms.payloads.TokenDto;
import com.hms.payloads.UserDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.JWTService;
import com.hms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    private  AppUserRepository appUserRepository;
    private JWTService jwtService;

    public UserController(UserService userService,
                          AppUserRepository appUserRepository, JWTService jwtService) {
        this.userService = userService;
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup-user")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDto userDto
    ){

        String encryptedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(encryptedPassword);
        userDto.setRole("ROLE_USER");
        ResponseEntity<?> user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }


    @PostMapping("/signup-property-owner")
    public ResponseEntity<?> createPropertyOwnerUser(
           @Valid @RequestBody UserDto userDto
    ){

        String encryptedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(encryptedPassword);
        userDto.setRole("ROLE_OWNER");
        ResponseEntity<?> user = userService.createUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);

    }



    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto dto
    ){
        String token = userService.verifyLogin(dto);
        if (token!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid Username/Password!!", HttpStatus.FORBIDDEN);
        }
    }
}
