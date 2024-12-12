package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.payloads.LoginDto;
import com.hms.payloads.UserDto;
import com.hms.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private AppUserRepository appUserRepository;
    private ModelMapper modelMapper;
    private JWTService jwtService;

    public UserService(AppUserRepository appUserRepository, ModelMapper modelMapper, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }


    public UserDto createUser(UserDto userDto) {
        Optional<AppUser> opUser = appUserRepository.findByUsername(userDto.getUsername());
        if (opUser.isPresent()) {
            throw new IllegalArgumentException("Username is already in use!");
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(userDto.getEmail());
        if (opEmail.isPresent()) {
            throw new IllegalArgumentException("Email is already in use!");
        }
        String encryptedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(encryptedPassword);
        AppUser appUser = mapToEntity(userDto);
        AppUser saved = appUserRepository.save(appUser);

        UserDto dto = mapToDto(saved);
        return dto;


    }

    public String verifyLogin(LoginDto dto){
        Optional<AppUser> opUser = appUserRepository.findByUsername(dto.getUsername());
        if (opUser.isPresent()){
            AppUser appUser = opUser.get();
           if (BCrypt.checkpw(dto.getPassword(), appUser.getPassword())){
               //(plain text encrypted password, database encrypted password)
               //checkpw is a method that comes from spring security BCrypt Class.
               // Generate token
               String token = jwtService.generateToken(appUser.getUsername());
               return token;
           }else {
               return null;
           }
        }
        return null;
    }

    AppUser mapToEntity(UserDto userDto) {
        AppUser mapAppUser = modelMapper.map(userDto, AppUser.class);
        return mapAppUser;
    }

    UserDto mapToDto(AppUser appUser) {
        UserDto mapUserDto = modelMapper.map(appUser, UserDto.class);
        return mapUserDto;
    }
}
