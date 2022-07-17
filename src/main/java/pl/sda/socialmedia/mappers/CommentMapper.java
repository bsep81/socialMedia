package pl.sda.socialmedia.mappers;

import org.springframework.stereotype.Component;
import pl.sda.socialmedia.dao.CommentDAO;
import pl.sda.socialmedia.db.CommentEntity;
import pl.sda.socialmedia.db.MessageEntity;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.exceptions.MessageException;
import pl.sda.socialmedia.exceptions.UserException;
import pl.sda.socialmedia.model.Comment;
import pl.sda.socialmedia.model.Message;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.MessageRepository;
import pl.sda.socialmedia.repository.UserRepository;

import java.util.Optional;

@Component
public class CommentMapper {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    public CommentMapper(UserMapper userMapper, UserRepository userRepository, MessageMapper messageMapper, MessageRepository messageRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
    }

    public Comment mapDAOToComment(CommentDAO commentDAO){

        Optional<UserEntity> userEntityOptional = userRepository.findById(commentDAO.getUsername());
        if(userEntityOptional.isEmpty()){
            throw new UserException("User not found.");
        }

        Optional<MessageEntity> messageEntityOptional = messageRepository.findById(commentDAO.getCommentedMessageId());
        if(messageEntityOptional.isEmpty()){
            throw new MessageException("Message not found");
        }

        User user = userMapper.mapEntityToUser(userEntityOptional.get()).get();
        Message message = messageMapper.mapEntityToMessage(messageEntityOptional.get()).get();

        return Comment.builder()
                .id(commentDAO.getId())
                .content(commentDAO.getContent())
                .publishedOn(commentDAO.getPublishedOn())
                .author(user)
                .commentedMessage(message)
                .build();
    }

    public CommentEntity mapCommentToEntity(Comment comment){
        return CommentEntity.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .publishedOn(comment.getPublishedOn())
                .author(userMapper.mapUserToEntity(comment.getAuthor()))
                .commentedMessage(messageMapper.mapMessageToEntity(comment.getCommentedMessage()))
                .build();
    }


    public CommentEntity mapDAOToEntity(CommentDAO commentDAO) {
        return mapCommentToEntity(mapDAOToComment(commentDAO));
    }

    public CommentDAO mapEntityToCommentDAO(CommentEntity entity) {
        return mapCommentToDAO(mapEntityToComment(entity).get());
    }

    private CommentDAO mapCommentToDAO(Comment comment) {
        return CommentDAO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .publishedOn(comment.getPublishedOn())
                .username(comment.getAuthor().getUsername())
                .commentedMessageId(comment.getCommentedMessage().getId())
                .build();
    }

    private Optional<Comment> mapEntityToComment(CommentEntity entity) {
        if(entity == null){
            return Optional.empty();
        }

        Comment comment = Comment.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .publishedOn(entity.getPublishedOn())
                .author(userMapper.mapEntityToUser(entity.getAuthor()).get())
                .commentedMessage(messageMapper.mapEntityToMessage(entity.getCommentedMessage()).get())
                .build();
        return Optional.of(comment);
    }
}
