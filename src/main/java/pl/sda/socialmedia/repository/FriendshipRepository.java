package pl.sda.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.socialmedia.db.FriendshipEntity;
import pl.sda.socialmedia.db.UserEntity;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends CrudRepository<FriendshipEntity, Long> {

    Optional<FriendshipEntity> findFriendshipEntityByFriends(List<UserEntity> friends);
}
