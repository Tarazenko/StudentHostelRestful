package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.repository.UserRepository;
import by.bntu.tarazenko.hostelrestful.services.UserService;
import by.bntu.tarazenko.hostelrestful.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.debug("Delete user - {}", user);
            userRepository.deleteById(id);
        }
    }

    @Override
    public User updateUser(User user) {
        Optional<User> optUser = userRepository.findById(user.getId());
        if (optUser.isPresent()) {
            User dbUser = optUser.get();
            UpdateUtil.copyProperties(user, dbUser);
            log.debug("User - {}; DBUser - {}", user, dbUser);
            return userRepository.saveAndFlush(dbUser);
        } else {
            return null;
        }
    }

}
