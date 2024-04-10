package ru.netology.cloudstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.dto.AuthRequest;
import ru.netology.cloudstorage.dto.AuthResponse;
import ru.netology.cloudstorage.entities.UserEntity;
import ru.netology.cloudstorage.exceptions.AuthorizationException;
import ru.netology.cloudstorage.exceptions.BadCredentialsException;
import ru.netology.cloudstorage.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Slf4j
@Service
public class AuthorizationService {

    private final UserRepository userRepository;
    private final Random random = new Random();
    private final List<String> listTokens = new ArrayList<>();

    public AuthorizationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<AuthResponse> loginUser(AuthRequest authRequest) {
        final String login = authRequest.getLogin();
        final UserEntity user = userRepository.findById(login).orElseThrow(() ->
                new BadCredentialsException("User with login " + login + " not found"));

        if (!user.getPassword().equals(authRequest.getPassword())) {
            throw new BadCredentialsException("Incorrect password for user " + login);
        }
        final String authToken = String.valueOf(random.nextLong());
        listTokens.add(authToken);
        log.info("User " + login + " entered with token " + authToken);
        return new ResponseEntity<>(new AuthResponse(authToken), HttpStatus.OK);
    }


    public void logoutUser(String authToken) {
        listTokens.remove(authToken);
        log.info("user's token " + authToken + " has been deleted");
    }

    public void checkToken(String authToken) {
        if (!listTokens.contains(authToken)) {
            throw new AuthorizationException();
        } log.info("token " + authToken + " has been successfully verified");
    }

}