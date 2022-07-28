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
import pl.sda.socialmedia.dao.MessageDAO;
import pl.sda.socialmedia.exceptions.MessageException;
import pl.sda.socialmedia.model.Error;
import pl.sda.socialmedia.service.MessageService;

import java.util.List;


@RestController
@RequestMapping("api/messages")
public class MessageController {

    private final MessageService messageService;
    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MessageDAO addMessage(@RequestBody MessageDAO messageDAO){
        LOG.info("User {} attempts to add a new message.", messageDAO.getUsername());
        return messageService.addMessage(messageDAO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public MessageDAO editMessage(@RequestBody MessageDAO messageDAO){
        LOG.info("User {} attempts to edit a message with id = {}.", messageDAO.getUsername(), messageDAO.getId());
        return messageService.editMessage(messageDAO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id){
        messageService.deleteMessage(id);
    }

    @GetMapping("/{id}")
    public MessageDAO getMessage(@PathVariable Long id){
        return messageService.getMessage(id);
    }

    @GetMapping("/user/{username}")
    public List<MessageDAO> getMessages(@PathVariable String username){
        return messageService.getMessages(username);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MessageException.class)
    public Error handleException(MessageException exception) {
        LOG.error("exception message {}", exception.getMessage());
        return new Error(400, exception.getMessage());
    }


}
