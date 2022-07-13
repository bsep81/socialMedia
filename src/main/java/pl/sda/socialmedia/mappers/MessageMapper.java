package pl.sda.socialmedia.mappers;

import org.springframework.stereotype.Component;
import pl.sda.socialmedia.dao.MessageDAO;
import pl.sda.socialmedia.db.MessageEntity;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.exceptions.UserException;
import pl.sda.socialmedia.model.Message;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.UserRepository;

import java.util.Optional;

@Component
public class MessageMapper {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public MessageMapper(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Optional<Message> mapEntityToMessage(MessageEntity entity){

        if(entity == null){
            return Optional.empty();
        }
        Message message = Message.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .author(userMapper.mapEntityToUser(entity.getAuthor()).get())
                .publishedOn(entity.getPublishedOn())
                .build();

        return Optional.of(message);
    }

    public MessageEntity mapMessageToEntity(Message message){

        return MessageEntity.builder()
                .id(message.getId())
                .content(message.getContent())
                .author(userMapper.mapUserToEntity(message.getAuthor()))
                .publishedOn(message.getPublishedOn())
                .build();
    }

    public Optional<MessageDAO> mapEntityToMessageDAO(MessageEntity entity){

        if(entity == null){
            return Optional.empty();
        }
        MessageDAO messageDao = MessageDAO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .username(entity.getAuthor().getUsername())
                .publishedOn(entity.getPublishedOn())
                .build();

        return Optional.of(messageDao);
    }

    public MessageEntity mapDAOToEntity(MessageDAO messageDAO){
        return mapMessageToEntity(mapDAOToMessage(messageDAO));
    }

    public MessageDAO mapMessageToDAO(Message message){
        return MessageDAO.builder()
                .id(message.getId())
                .content(message.getContent())
                .publishedOn(message.getPublishedOn())
                .username(message.getAuthor().getUsername())
                .build();
    }

    public Message mapDAOToMessage(MessageDAO messageDAO){

        Optional<UserEntity> userEntityOptional = userRepository.findById(messageDAO.getUsername());
        if(userEntityOptional.isEmpty()){
            throw new UserException("User not found.");
        }

        User user = userMapper.mapEntityToUser(userEntityOptional.get()).get();

        return Message.builder()
                .id(messageDAO.getId())
                .content(messageDAO.getContent())
                .publishedOn(messageDAO.getPublishedOn())
                .author(user)
                .build();
    }
}
