package pl.sda.socialmedia.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.socialmedia.model.Friendship;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.service.FriendshipService;

import java.util.List;

@RestController
@RequestMapping("api/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;
    private static final Logger LOG = LoggerFactory.getLogger(FriendshipController.class);

    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Friendship addFriendship(@RequestBody List<User> friends){
        LOG.info("Attempting to add friendship between {} and {} to database.", friends.get(0).getUsername(), friends.get(1).getUsername());
        return friendshipService.addFriendship(friends);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping
    public Friendship changeStatus(@RequestParam("friendshipId") Long friendshipId, @RequestParam("status") String status){
        LOG.info("Attempting to change status of friendship {} to {}.", friendshipId, status);
        return friendshipService.changeStatus(friendshipId, status);
    }

    @GetMapping
    public Friendship findFriendship(@RequestParam("username1") String username1, @RequestParam("username2") String username2){
        LOG.info("Attempting to find friendship between {} and {}.", username1, username2);
        return friendshipService.findFriendship(username1, username2);
    }

    @GetMapping("/{username}")
    public List<Friendship> getFriendships(@PathVariable("username") String username){
        LOG.info("Attempting to get List of friendships of {}", username);
        return friendshipService.getAllFriendships(username);
    }
}
