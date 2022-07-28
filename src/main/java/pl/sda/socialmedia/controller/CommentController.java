package pl.sda.socialmedia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.socialmedia.dao.CommentDAO;
import pl.sda.socialmedia.exceptions.CommentException;
import pl.sda.socialmedia.model.Error;
import pl.sda.socialmedia.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;
    private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CommentDAO addComment(@RequestBody CommentDAO commentDAO){
        LOG.info("User {} attempts to comment on a message with id = {}.", commentDAO.getUsername(), commentDAO.getCommentedMessageId());
        return commentService.addComment(commentDAO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public CommentDAO editComment(@RequestBody CommentDAO commentDAO){
        LOG.info("User {} attempts to edit a comment with id = {}.", commentDAO.getUsername(), commentDAO.getId());
        return commentService.editComment(commentDAO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);
    }

    @GetMapping("/{id}")
    public CommentDAO getComment(@PathVariable Long id){
        return commentService.getComment(id);
    }

    @GetMapping("/message/{messageId}")
    public List<CommentDAO> getComments(@PathVariable Long messageId){
        return commentService.getComments(messageId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommentException.class)
    public Error handleException(CommentException exception) {
        LOG.error("exception message {}", exception.getMessage());
        return new Error(400, exception.getMessage());
    }


}
