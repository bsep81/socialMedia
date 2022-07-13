package pl.sda.socialmedia.mappers;

import org.springframework.stereotype.Component;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserMapper {




    public Optional<User> mapEntityToUser(UserEntity entity){

        if(entity == null){
            return Optional.empty();
        }

        User user = User.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .role(entity.getRole())
                .email(entity.getEmail())
                .build();

        return Optional.of(user);
    }

    public UserEntity mapUserToEntity(User user){

        return UserEntity.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .email(user.getEmail())
                .build();
    }

    public List<User> mapEntitiesToUsers(List<UserEntity> entities){
        List<User> users = new ArrayList<>();
        if(!entities.isEmpty()) {
            entities.forEach(entity -> users.add(mapEntityToUser(entity).orElse(new User())));
        }
        return users;
    }

    public List<UserEntity> mapUsersToEntities(List<User> users){
        List<UserEntity> entities = new ArrayList<>();

        if(!users.isEmpty()){
            users.forEach(user -> entities.add(mapUserToEntity(user)));
        }
        return entities;
    }
}
