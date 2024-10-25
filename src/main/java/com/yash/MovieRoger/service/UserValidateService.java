package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.UserLoginDTO;
import com.yash.MovieRoger.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserValidateService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<String> validateUser(UserLoginDTO userLoginDTO) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(userLoginDTO.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        }
        catch (Exception e) {
            log.error("Exception occurred while creating authentication token " + e);
            return new ResponseEntity<>("Incorrect username or password",  HttpStatus.BAD_REQUEST);
        }
    }
}
