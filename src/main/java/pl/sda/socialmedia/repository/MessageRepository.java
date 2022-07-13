package pl.sda.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.socialmedia.db.MessageEntity;

import java.util.List;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {

    List<MessageEntity> findMessageEntitiesByAuthor_UsernameContaining(String username);
}
