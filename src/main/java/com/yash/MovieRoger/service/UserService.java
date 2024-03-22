package com.yash.MovieRoger.service;

import com.yash.MovieRoger.dto.UserDTO;
import com.yash.MovieRoger.model.User;
import com.yash.MovieRoger.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public UserDTO addUser(UserDTO userDTO) {
        if (userRepository.existsByMobile(userDTO.getMobile())) {
            return userDTO;
        }

        User user = User.toEntity(userDTO);
        user.setPassword(encoder.encode(user.getPassword()));
        user = userRepository.save(user);

        log.info("Added New User: {}", user.toString());
        return User.toResource(user);
    }

    public UserDTO getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            log.error("User Not Fount with ID: {}", id);
            throw new EntityNotFoundException("User Not Fount with ID: " + id);
        }

        return User.toResource(user.get());
    }
}
