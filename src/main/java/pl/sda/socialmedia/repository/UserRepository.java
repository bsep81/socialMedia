package pl.sda.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.socialmedia.db.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
