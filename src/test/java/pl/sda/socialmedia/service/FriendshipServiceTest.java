package pl.sda.socialmedia.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.socialmedia.db.FriendshipEntity;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.exceptions.FriendshipException;
import pl.sda.socialmedia.mappers.FriendshipMapper;
import pl.sda.socialmedia.mappers.UserMapper;
import pl.sda.socialmedia.model.Friendship;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.FriendshipRepository;
import pl.sda.socialmedia.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceTest {

    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private FriendshipMapper friendshipMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private FriendshipService friendshipService;

    @Test
    void shouldThrowFriendshipExceptionWhenFriendshipAlreadyExists(){

        List<User> users = List.of(new User(), new User());
        Optional<FriendshipEntity> friendshipOptional = Optional.of(new FriendshipEntity());

        when(friendshipRepository.findFriendshipEntityByFriendsContainingAndFriendsContaining(userMapper.mapUserToEntity(users.get(0)), userMapper.mapUserToEntity(users.get(1))))
                .thenReturn(friendshipOptional);

        FriendshipException exception = assertThrows(FriendshipException.class, () -> friendshipService.addFriendship(users));
        assertEquals("Friendship already exists.", exception.getMessage());
    }

    @Test
    void shouldReturnCreatedFriendship(){

        User user1 = User.builder()
                .username("testuser1")
                .password("testpass1")
                .role("ADMIN")
                .email("test@email1")
                .build();
        User user2 = User.builder()
                .username("testuser2")
                .password("testpass2")
                .role("ADMIN")
                .email("test@email2")
                .build();

        UserEntity userEntity1 = UserEntity.builder()
                .username("testuser1")
                .password("testpass1")
                .role("ADMIN")
                .email("test@email1")
                .build();
        UserEntity userEntity2 = UserEntity.builder()
                .username("testuser2")
                .password("testpass2")
                .role("ADMIN")
                .email("test@email2")
                .build();

        List<User> users = List.of(user1, user2);
        List<UserEntity> userEntities = List.of(userEntity1, userEntity2);

        Friendship friendship = Friendship.builder()
                .friends(users)
                .status("requested")
                .build();

        FriendshipEntity created = FriendshipEntity.builder()
                .id(1L)
                .friends(userEntities)
                .status("requested")
                .build();

        when(friendshipRepository.save(friendshipMapper.mapFriendshipToEntity(friendship)))
                .thenReturn(created);
        when(friendshipMapper.mapEntityToFriendship(created)).thenReturn(Optional.of(friendship));

        Friendship result = friendshipService.addFriendship(users);

        assertEquals(friendship, result);
    }

    @Test
    void shouldThrowFriendshipExceptionWhenFriendshipNotFoundInChangeStatusMethod(){
        Long friendshipId = 1L;
        when(friendshipRepository.findById(friendshipId)).thenReturn(Optional.empty());

        FriendshipException exception = assertThrows(FriendshipException.class, () -> friendshipService.changeStatus(friendshipId, "accepted"));
        assertEquals("Friendship not found.", exception.getMessage());
    }

    @Test
    void shouldChangeStatusOfFreiendship(){
        Long friendshipId = 1L;

        FriendshipEntity friendshipEntity = FriendshipEntity.builder()
                .id(friendshipId)
                .friends(List.of(new UserEntity(), new UserEntity()))
                .status("requested")
                .build();

        FriendshipEntity changed = friendshipEntity;
        changed.setStatus("accepted");

        Friendship friendship = Friendship.builder()
                .status("accepted")
                .build();

        when(friendshipRepository.findById(friendshipId)).thenReturn(Optional.of(friendshipEntity));
        when(friendshipRepository.save(friendshipEntity)).thenReturn(changed);
        when(friendshipMapper.mapEntityToFriendship(changed)).thenReturn(Optional.of(friendship));

        Friendship result = friendshipService.changeStatus(1L, "accepted");

        assertEquals(changed.getStatus(), result.getStatus());
    }

}