package pl.sda.socialmedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.sda.socialmedia.dao.MessageDAO;
import pl.sda.socialmedia.db.MessageEntity;
import pl.sda.socialmedia.exceptions.MessageException;
import pl.sda.socialmedia.mappers.MessageMapper;
import pl.sda.socialmedia.repository.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public MessageDAO addMessage(MessageDAO messageDAO){

        MessageEntity created = messageRepository.save(messageMapper.mapDAOToEntity(messageDAO));
        LOG.info("{} posted a new message.", messageDAO.getUsername());
        return messageMapper.mapEntityToMessageDAO(created).get();
    }

    public MessageDAO editMessage(MessageDAO messageDAO){

        if(messageDAO.getId() == null || messageRepository.findById(messageDAO.getId()).isEmpty()){
            throw new MessageException("No message id or no message with given id present in database.");
        }

        MessageEntity edited = messageRepository.save((messageMapper.mapDAOToEntity(messageDAO)));
        LOG.info("{} edited a message", messageDAO.getUsername());
        return messageMapper.mapEntityToMessageDAO(edited).get();
    }

    public void deleteMessage(Long id){
        LOG.info("message with id = {} deleted from database.", id);
        messageRepository.deleteById(id);
    }

    public MessageDAO getMessage(Long id){
        Optional<MessageEntity> entityOptional = messageRepository.findById(id);
        if(entityOptional.isEmpty()){
            throw new MessageException("Message not found.");
        }

        LOG.info("Message with id = {} obtained from database.", id);
        return messageMapper.mapEntityToMessageDAO(entityOptional.get()).get();
    }

    public List<MessageDAO> getMessages(String username){
        List<MessageEntity> messageEntities = messageRepository.findMessageEntitiesByAuthor_UsernameContaining(username);
        LOG.info("Messages of {} obtained from database.", username);
        return messageEntities.stream().map(messageMapper::mapEntityToMessageDAO).map(Optional::get).collect(Collectors.toList());
    }
}
