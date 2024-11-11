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

import java.util.List;
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


    public ResponseEntity<?> createUser(UserDto userDto) {
        Optional<AppUser> opUser = appUserRepository.findByUsername(userDto.getUsername());
        if (opUser.isPresent()) {
            return new ResponseEntity<>("Username is already exits!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Optional<AppUser> opEmail = appUserRepository.findByEmail(userDto.getEmail());
        if (opEmail.isPresent()) {
            return new ResponseEntity<>("Email_id is already exits!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        AppUser appUser = mapToEntity(userDto);
        AppUser saved = appUserRepository.save(appUser);

        UserDto dto = mapToDto(saved);
        return new ResponseEntity<>(dto, HttpStatus.OK);


    }

    AppUser mapToEntity(UserDto userDto) {
        AppUser mapAppUser = modelMapper.map(userDto, AppUser.class);
        return mapAppUser;
    }

    UserDto mapToDto(AppUser appUser) {
        UserDto mapUserDto = modelMapper.map(appUser, UserDto.class);
        return mapUserDto;
    }

    public String verifyLogin(LoginDto dto){
        Optional<AppUser> opUser = appUserRepository.findByUsername(dto.getUsername());
        if (opUser.isPresent()){
            AppUser appUser = opUser.get();
           if (BCrypt.checkpw(dto.getPassword(), appUser.getPassword())){
               // Generate token
               String token = jwtService.generateToken(appUser.getUsername());
               return token;
           }
        }else {
            return null;
        }
        return null;
    }
}
