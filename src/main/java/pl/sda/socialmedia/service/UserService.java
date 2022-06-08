package pl.sda.socialmedia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.socialmedia.db.UserEntity;
import pl.sda.socialmedia.exceptions.UserException;
import pl.sda.socialmedia.mappers.UserMapper;
import pl.sda.socialmedia.model.User;
import pl.sda.socialmedia.repository.UserRepository;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final String USER_NOT_FOUND = "User not found.";

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User addUser(User user){
        if(userRepository.findById(user.getUsername()).isPresent()){
            throw new UserException("User already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity created = userRepository.save(userMapper.mapUserToEntity(user));
        LOG.info("User {} saved to database", user.getUsername());
        return userMapper.mapEntityToUser(created).get();
    }

    public User getUserById(String username){
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        if(userEntityOptional.isEmpty()){
            throw new UserException(USER_NOT_FOUND);
        }

        LOG.info("User {} found.", username);
        return userMapper.mapEntityToUser(userEntityOptional.get()).orElseGet(User::new);
    }

    public void deleteUser(String username) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(username);
        userEntityOptional.ifPresentOrElse(userEntity -> {
            userRepository.deleteById(username);
            LOG.info("User {} deleted from database.", username);
        }, () -> LOG.info("User {} not found", username));
    }

    public User changePassword(User user){
        if(userRepository.findById(user.getUsername()).isEmpty()){
            throw new UserException(USER_NOT_FOUND);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity updated = userRepository.save(userMapper.mapUserToEntity(user));
        LOG.info("Password for {} has been changed.", user.getUsername());
        return userMapper.mapEntityToUser(updated).get();
    }

    public User changeEmail(User user){
        if(userRepository.findById(user.getUsername()).isEmpty()){
            throw new UserException(USER_NOT_FOUND);
        }
        UserEntity updated = userRepository.save(userMapper.mapUserToEntity(user));
        LOG.info("Email for {} has been changed.", user.getUsername());
        return userMapper.mapEntityToUser(updated).get();
    }
}
