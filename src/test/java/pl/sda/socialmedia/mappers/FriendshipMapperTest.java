package pl.sda.socialmedia.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.socialmedia.db.FriendshipEntity;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.model.Friendship;
import pl.sda.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipMapperTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private FriendshipMapper friendshipMapper;

    private User user;
    private UserEntity userEntity;
    private Friendship friendship;
    private FriendshipEntity friendshipEntity;
    private List<User> users;
    private List<UserEntity> userEntities;


    @BeforeEach
    void setup(){
        userEntity = UserEntity.builder()
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

        users = List.of(user, user);
        userEntities = List.of(userEntity, userEntity);

        friendshipEntity = FriendshipEntity.builder()
                .id(10L)
                .friends(userEntities)
                .status("requested")
                .build();

        friendship = Friendship.builder()
                .id(10L)
                .friends(users)
                .status("requested")
                .build();


    }

    @Test
    void shouldReturnEmptyOptionalWhenFriendshipEntityIsNull(){
        Optional<Friendship> result = friendshipMapper.mapEntityToFriendship(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapEntityToFriendship(){
        when(userMapper.mapEntitiesToUsers(friendshipEntity.getFriends())).thenReturn(users);

        Friendship result = friendshipMapper.mapEntityToFriendship(friendshipEntity).get();
        assertEquals(friendship, result);

    }

    @Test
    void shouldMapFriendshipToEntity(){
        when(userMapper.mapUsersToEntities(friendship.getFriends())).thenReturn(userEntities);

        FriendshipEntity result = friendshipMapper.mapFriendshipToEntity(friendship);
        assertEquals(friendshipEntity, result);
    }



}