package pl.sda.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.socialmedia.db.CommentEntity;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
}
