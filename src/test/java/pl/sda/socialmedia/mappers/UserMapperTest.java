package pl.sda.socialmedia.mappers;

import org.junit.jupiter.api.Test;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.model.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();


    @Test
    void shouldReturnEmptyOptionalWhenUserEntityIsNull(){

        Optional<User> result = userMapper.mapEntityToUser(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapEntityToUser(){
        UserEntity entity = UserEntity.builder()
                .username("test name")
                .password("test password")
                .role("test role")
                .email("test email")
                .build();

        User user = User.builder()
                .username("test name")
                .password("test password")
                .role("test role")
                .email("test email")
                .build();

        User result = userMapper.mapEntityToUser(entity).get();

        assertEquals(user, result);
    }

    @Test
    void shouldMapUserToEntity(){
        User user = User.builder()
                .username("test name")
                .password("test password")
                .role("test role")
                .email("test email")
                .build();

        UserEntity entity = UserEntity.builder()
                .username("test name")
                .password("test password")
                .role("test role")
                .email("test email")
                .build();

        UserEntity result = userMapper.mapUserToEntity(user);

        assertEquals(entity, result);
    }
}