package pl.sda.socialmedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.sda.socialmedia.dao.CommentDAO;
import pl.sda.socialmedia.db.CommentEntity;
import pl.sda.socialmedia.exceptions.CommentException;
import pl.sda.socialmedia.mappers.CommentMapper;
import pl.sda.socialmedia.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    public CommentService(CommentMapper commentMapper, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }


    public CommentDAO addComment(CommentDAO commentDAO){

        CommentEntity created = commentRepository.save(commentMapper.mapDAOToEntity(commentDAO));
        LOG.info("{} commented on message with id = {}.", commentDAO.getUsername(), commentDAO.getCommentedMessageId());
        return commentMapper.mapEntityToCommentDAO(created);
    }

    public CommentDAO editComment(CommentDAO commentDAO){
        if(commentDAO.getId() == null || commentRepository.findById(commentDAO.getId()).isEmpty()){
            throw new CommentException("No comment id or no comment with given id present in database.");
        }

        CommentEntity edited = commentRepository.save(commentMapper.mapDAOToEntity(commentDAO));
        LOG.info("{} edited a comment.", commentDAO.getUsername());
        return commentMapper.mapEntityToCommentDAO(edited);
    }

    public void deleteComment(Long id){
        LOG.info("Comment with id = {} deleted from database.", id);
        commentRepository.deleteById(id);
    }

    public CommentDAO getComment(Long id){
        Optional<CommentEntity> entityOptional = commentRepository.findById(id);
        if(entityOptional.isEmpty()){
            throw new CommentException("Comment not found");
        }

        LOG.info("Comment with id = {} obtained from database.", id);
        return commentMapper.mapEntityToCommentDAO(entityOptional.get());
    }

    public List<CommentDAO> getComments(Long messageId){
        List<CommentEntity> commentEntities = commentRepository.findCommentEntitiesByCommentedMessage_Id(messageId);
        LOG.info("Comments of message with id = {} obtained from database.", messageId);
        return commentEntities.stream().map(commentMapper::mapEntityToCommentDAO).collect(Collectors.toList());
    }
}
