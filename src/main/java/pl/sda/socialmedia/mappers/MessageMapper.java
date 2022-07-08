package pl.sda.socialmedia.mappers;

import org.springframework.stereotype.Component;
import pl.sda.socialmedia.db.MessageEntity;
import pl.sda.socialmedia.model.Message;

import java.util.Optional;

@Component
public class MessageMapper {

    private final UserMapper userMapper;

    public MessageMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
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
}
