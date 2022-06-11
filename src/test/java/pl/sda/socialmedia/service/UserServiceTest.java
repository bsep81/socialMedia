package pl.sda.socialmedia.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.exceptions.UserException;
import pl.sda.socialmedia.mappers.UserMapper;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    void shouldThrowUserExceptionWhenUserAlreadyExists(){
        User user = new User();
        Optional<UserEntity> userOptional = Optional.of(new UserEntity());
        when(userRepository.findById(user.getUsername())).thenReturn(userOptional);

        UserException exception = assertThrows(UserException.class, () -> userService.addUser(user));
        assertEquals("User already exists.", exception.getMessage());
    }

    @Test
    void shouldReturnCreatedUser(){
        User user = User.builder()
                .username("testuser")
                .password("testpass")
                .role("ADMIN")
                .email("test@email")
                .build();

        UserEntity created = UserEntity.builder()
                .username("testuser")
                .password("$2a$10$kVjeRsmt/tW/PNMhji2eVOhXAhRtvlqp2CoYnJqLuD1wrzPIjwnsy")
                .role("ADMIN")
                .email("test@email")
                .build();
        when(passwordEncoder.encode(user.getPassword())).thenReturn("$2a$10$kVjeRsmt/tW/PNMhji2eVOhXAhRtvlqp2CoYnJqLuD1wrzPIjwnsy");
        when(userRepository.save(userMapper.mapUserToEntity(user))).thenReturn(created);
        when(userMapper.mapEntityToUser(created)).thenCallRealMethod();

        User result = userService.addUser(user);


        user.setPassword("$2a$10$kVjeRsmt/tW/PNMhji2eVOhXAhRtvlqp2CoYnJqLuD1wrzPIjwnsy");

        assertEquals(user, result);
    }

    @Test
    void shouldThrowUserExceptionWhenUserNotFound(){
        String username = "testuser";
        when(userRepository.findById(username)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> userService.getUserById(username));
        assertEquals("User not found.", exception.getMessage());
    }

}