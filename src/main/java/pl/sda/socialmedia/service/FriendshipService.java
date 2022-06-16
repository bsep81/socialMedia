package pl.sda.socialmedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.sda.socialmedia.db.FriendshipEntity;
import pl.sda.socialmedia.exceptions.FriendshipException;
import pl.sda.socialmedia.mappers.FriendshipMapper;
import pl.sda.socialmedia.mappers.UserMapper;
import pl.sda.socialmedia.model.Friendship;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.FriendshipRepository;

import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;
    private final UserMapper userMapper;
    private static final Logger LOG = LoggerFactory.getLogger(FriendshipService.class);
    private static final String FRIENDSHIP_NOT_FOUND = "Friendship not found.";

    public FriendshipService(FriendshipRepository friendshipRepository, FriendshipMapper friendshipMapper, UserMapper userMapper) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipMapper = friendshipMapper;
        this.userMapper = userMapper;
    }

    public Friendship addFriendship(List<User> users){
        if(friendshipRepository.findFriendshipEntityByFriends(userMapper.mapUsersToEntities(users)).isPresent()){
            throw new FriendshipException("Friendship already exists.");
        }

        Friendship friendship = Friendship.builder()
                .friends(users)
                .status("requested")
                .build();

        FriendshipEntity created = friendshipRepository.save(friendshipMapper.mapFriendshipToEntity(friendship));
        LOG.info("Friendship between {} and {} saved to database", users.get(0).getUsername(), users.get(1).getUsername());
        return friendshipMapper.mapEntityToFriendship(created).get();

    }
}
