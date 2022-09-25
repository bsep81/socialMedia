package pl.sda.socialmedia.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.socialmedia.dao.MessageDAO;
import pl.sda.socialmedia.db.MessageEntity;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.model.Message;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;

    private UserEntity userEntity;
    private User user;
    private MessageEntity messageEntity;
    private Message message;
    private MessageDAO messageDAO;

    @InjectMocks
    private MessageMapper messageMapper;

    @BeforeEach
    void setup() {
        userEntity = UserEntity.builder()
                .username("TestUser")
                .password("Test password")
                .email("tesr@email")
                .role("ADMIN")
                .build();

        user = User.builder()
                .username("TestUser")
                .password("Test password")
                .email("test@email")
                .role("ADMIN")
                .build();

        messageEntity = MessageEntity.builder()
                .id(1L)
                .author(userEntity)
                .content("Test message")
                .publishedOn(LocalDateTime.of(2022, 8, 20, 20, 20))
                .build();

        message = Message.builder()
                .id(1L)
                .author(user)
                .content("Test message")
                .publishedOn(LocalDateTime.of(2022, 8, 20, 20, 20))
                .build();

        messageDAO = MessageDAO.builder()
                .id(1L)
                .username("TestUser")
                .content("Test message")
                .publishedOn(LocalDateTime.of(2022, 8, 20, 20, 20))
                .build();
    }

    @Test
    void shouldMapEntityToMessage() {
        when(userMapper.mapEntityToUser(messageEntity.getAuthor())).thenReturn(Optional.of(user));

        Message result = messageMapper.mapEntityToMessage(messageEntity).get();

        assertEquals(message, result);
    }

    @Test
    void shouldReturnEmtyOptionalWhenNullEntityIsMappedToMessage() {
        Optional<Message> result = messageMapper.mapEntityToMessage(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMApMessageToEntity() {
        when(userMapper.mapUserToEntity(message.getAuthor())).thenReturn(userEntity);

        MessageEntity result = messageMapper.mapMessageToEntity(message);

        assertEquals(messageEntity, result);
    }

    @Test
    void shouldMapEntityToMessageDAO() {
        MessageDAO result = messageMapper.mapEntityToMessageDAO(messageEntity).get();

        assertEquals(messageDAO, result);
    }

    @Test
    void shouldReturnEmptyOptionalWhenNullEntityIsMappedToDAO() {
        Optional<MessageDAO> result = messageMapper.mapEntityToMessageDAO(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldMapMessageToDAO() {
        MessageDAO result = messageMapper.mapMessageToDAO(message);

        assertEquals(messageDAO, result);
    }

    @Test
    void shouldMapDAOToMessage() {
        when(userRepository.findById(messageDAO.getUsername())).thenReturn(Optional.of(userEntity));
        Optional<UserEntity> userEntityOptional = Optional.of(userEntity);
        when(userMapper.mapEntityToUser(userEntityOptional.get())).thenReturn(Optional.of(user));

        Message result = messageMapper.mapDAOToMessage(messageDAO);

        assertEquals(message, result);
    }


}