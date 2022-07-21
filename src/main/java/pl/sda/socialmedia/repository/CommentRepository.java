package pl.sda.socialmedia.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.socialmedia.db.CommentEntity;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, Long> {
    List<CommentEntity> findCommentEntitiesByCommentedMessage_Id(Long id);
}
