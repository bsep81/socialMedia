package pl.sda.socialmedia.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    private UserEntity entity;
    private User user;

    @BeforeEach
    void setup(){
        entity = UserEntity.builder()
                .username("test name")
                .password("test password")
                .role("test role")
                .email("test email")
                .build();

        user = User.builder()
                .username("test name")
                .password("test password")
                .role("test role")
                .email("test email")
                .build();
    }


    @Test
    void shouldReturnEmptyOptionalWhenUserEntityIsNull(){
        Optional<User> result = userMapper.mapEntityToUser(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapEntityToUser(){
        User result = userMapper.mapEntityToUser(entity).get();
        assertEquals(user, result);
    }

    @Test
    void shouldMapUserToEntity(){
        UserEntity result = userMapper.mapUserToEntity(user);
        assertEquals(entity, result);
    }

    @Test
    void shouldReturnEmptyListWhenListOfEntitiesIsEmpty(){
        List<User> result = userMapper.mapEntitiesToUsers(new ArrayList<>());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnEmptyListWhenListOfUsersIsEmpty(){
        List<UserEntity> result = userMapper.mapUsersToEntities(new ArrayList<>());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapListOfEntitiesToListOfUsers(){
        List<UserEntity> entities = List.of(entity, entity);
        List<User> users = List.of(user, user);

        List<User> result = userMapper.mapEntitiesToUsers(entities);

        assertEquals(users, result);
    }

    @Test
    void shouldMapListOfUsersToListOfEntities(){
        List<User> users = List.of(user, user);
        List<UserEntity> entities = List.of(entity, entity);

        List<UserEntity> result = userMapper.mapUsersToEntities(users);

        assertEquals(entities, result);
    }

}