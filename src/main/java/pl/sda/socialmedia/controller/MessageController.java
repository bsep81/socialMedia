package pl.sda.socialmedia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.socialmedia.dao.MessageDAO;
import pl.sda.socialmedia.service.MessageService;


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
}
