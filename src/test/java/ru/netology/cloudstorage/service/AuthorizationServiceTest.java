package ru.netology.cloudstorage.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.cloudstorage.dto.AuthRequest;
import ru.netology.cloudstorage.entities.UserEntity;
import ru.netology.cloudstorage.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;

class AuthorizationServiceTest {
    public static final String EXISTING_USER = "ivanov";
    public static final String NOT_EXISTING_USER = "sidorov";
    public static final String CORRECT_PASSWORD = "password1";

    private final UserRepository userRepository = userRepositoryMock();

    private UserRepository userRepositoryMock() {
        final UserRepository userRepository = Mockito.mock(UserRepository.class);
        when(userRepository.findById(EXISTING_USER)).thenReturn(Optional.of(new UserEntity(EXISTING_USER, CORRECT_PASSWORD)));
        when(userRepository.findById(NOT_EXISTING_USER)).thenReturn(Optional.empty());
        return userRepository;
    }

    @Test
    void test_loginUser() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository);
        Assertions.assertDoesNotThrow(() -> authorizationService.loginUser(new AuthRequest(EXISTING_USER, CORRECT_PASSWORD)));
    }

    @Test
    void test_loginNotExistingUser() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository);
        Assertions.assertThrows(RuntimeException.class, () -> authorizationService.
                loginUser(new AuthRequest(NOT_EXISTING_USER, CORRECT_PASSWORD)));
    }

    @Test
    void test_loginUserWithIncorrectPassword() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository);
        Assertions.assertThrows(RuntimeException.class, () -> authorizationService.
                loginUser(new AuthRequest(EXISTING_USER, "12345")));
    }

    @Test
    void test_checkIncorrectToken() {
        final AuthorizationService authorizationService = new AuthorizationService(userRepository);
        Assertions.assertThrows(RuntimeException.class, () -> authorizationService.checkToken("123"));
    }
}
