package pl.sda.socialmedia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.socialmedia.exceptions.UserException;
import pl.sda.socialmedia.model.Error;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public User getById(@PathVariable("username") String username){
        LOG.info("Getting user details for {}.", username);
        return userService.getUserById(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User addUser(@Valid @RequestBody User user){
        LOG.info("Attempting to add user {} to database.", user.getUsername());
        return userService.addUser(user);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    public Error handleException(UserException exception) {
        LOG.error("exception message {}", exception.getMessage());
        return new Error(400, exception.getMessage());
    }


}
