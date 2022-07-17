package pl.sda.socialmedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.sda.socialmedia.dao.CommentDAO;
import pl.sda.socialmedia.db.CommentEntity;
import pl.sda.socialmedia.mappers.CommentMapper;
import pl.sda.socialmedia.repository.CommentRepository;

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
}
