package com.hms.controller;

import com.hms.payloads.LoginDto;
import com.hms.payloads.TokenDto;
import com.hms.payloads.UserDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.JWTService;
import com.hms.service.OTPService;
import com.hms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;
    private JWTService jwtService;
    private OTPService otpService;

    public UserController(UserService userService,
                          AppUserRepository appUserRepository, JWTService jwtService, OTPService otpService) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.otpService = otpService;
    }

    //http://localhost:8080/api/v1/users/signup-user
    @PostMapping("/signup-user")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDto userDto
    ){

        try {
            userDto.setRole("ROLE_USER");
            UserDto createdUser = userService.createUser(userDto);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(
                    "An error occurred while creating the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //http://localhost:8080/api/v1/users/signup-propertyOwner
    @PostMapping("/signup-propertyOwner")
    public ResponseEntity<?> createPropertyOwnerUser(
           @Valid @RequestBody UserDto userDto
    ) {
        try {
            userDto.setRole("ROLE_OWNER");
            UserDto createdUser = userService.createUser(userDto);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(
                    "An error occurred while creating the user.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //http://localhost:8080/api/v1/users/login
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

//    @PostMapping("/login-otp")
//    public String login(@RequestParam String mobileNumber){
//        //Generate OTP and send via SMS
//        return otpService.generateAndSendOTP(mobileNumber);
//    }
}
