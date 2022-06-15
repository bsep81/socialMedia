package pl.sda.socialmedia.mappers;

import org.springframework.stereotype.Component;
import pl.sda.socialmedia.db.FriendshipEntity;
import pl.sda.socialmedia.model.Friendship;

import java.util.Optional;

@Component
public class FriendshipMapper {

    private final UserMapper userMapper;

    public FriendshipMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Optional<Friendship> mapEntityToFriendship(FriendshipEntity entity){

        if(entity == null){
            return Optional.empty();
        }

        Friendship friendship = Friendship.builder()
                .id(entity.getId())
                .friends(userMapper.mapEntitiesToUsers(entity.getFriends()))
                .status(entity.getStatus())
                .build();

        return Optional.of(friendship);
    }

    public FriendshipEntity mapFriendshipToEntity(Friendship friendship){

        return FriendshipEntity.builder()
                .id(friendship.getId())
                .friends(userMapper.mapUsersToEntities(friendship.getFriends()))
                .status(friendship.getStatus())
                .build();
    }


}
