package pl.sda.socialmedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.sda.socialmedia.db.FriendshipEntity;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.exceptions.FriendshipException;
import pl.sda.socialmedia.exceptions.UserException;
import pl.sda.socialmedia.mappers.FriendshipMapper;
import pl.sda.socialmedia.mappers.UserMapper;
import pl.sda.socialmedia.model.Friendship;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.FriendshipRepository;
import pl.sda.socialmedia.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private static final Logger LOG = LoggerFactory.getLogger(FriendshipService.class);
    private static final String FRIENDSHIP_NOT_FOUND = "Friendship not found.";

    public FriendshipService(FriendshipRepository friendshipRepository, FriendshipMapper friendshipMapper, UserMapper userMapper, UserRepository userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.friendshipMapper = friendshipMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Friendship addFriendship(List<User> users){

        if(friendshipRepository.findFriendshipEntityByFriendsContainingAndFriendsContaining(userMapper.mapUserToEntity(users.get(0)), userMapper.mapUserToEntity(users.get(1))).isPresent()){
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

    public Friendship changeStatus(Long friendshipId, String status){

        Optional<FriendshipEntity> friendshipEntityOptional = friendshipRepository.findById(friendshipId);
        if(friendshipEntityOptional.isEmpty()){
            throw new FriendshipException(FRIENDSHIP_NOT_FOUND);
        }

        FriendshipEntity friendshipEntity = friendshipEntityOptional.get();
        friendshipEntity.setStatus(status);
        FriendshipEntity changed = friendshipRepository.save(friendshipEntity);
        LOG.info("Status of friendship {} changed to {}.", changed.getId(), status);

        return friendshipMapper.mapEntityToFriendship(changed).get();
    }

    public Friendship findFriendship(String username1, String username2){

        Optional<UserEntity> user1Optional = userRepository.findById(username1);
        Optional<UserEntity> user2Optional = userRepository.findById(username2);

        if(user1Optional.isEmpty() || user2Optional.isEmpty()){
            throw new UserException("User not found.");
        }

        Optional<FriendshipEntity> friendshipEntityOptional = friendshipRepository
                .findFriendshipEntityByFriendsContainingAndFriendsContaining(user1Optional.get(), user2Optional.get());

        if(friendshipEntityOptional.isEmpty()){
            throw new FriendshipException(FRIENDSHIP_NOT_FOUND);
        }

        LOG.info("Friendship between {} and {} found.", username1, username2);
        return friendshipMapper.mapEntityToFriendship(friendshipEntityOptional.get()).get();
    }

    public List<Friendship> getAllFriendships(String username){
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new UserException("User not found.");
        }

        List<FriendshipEntity> friendshipEntities = friendshipRepository.findFriendshipEntitiesByFriendsContaining(userEntityOptional.get());
        return friendshipEntities.stream().map(entity -> friendshipMapper.mapEntityToFriendship(entity).get()).collect(Collectors.toList());
    }
}
