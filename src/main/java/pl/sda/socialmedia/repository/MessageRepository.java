package pl.sda.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.socialmedia.db.MessageEntity;

public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
}
