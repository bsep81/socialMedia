package pl.sda.socialmedia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.socialmedia.dao.CommentDAO;
import pl.sda.socialmedia.service.CommentService;

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

}
