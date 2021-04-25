package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Request;
import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.repository.RequestRepository;
import by.bntu.tarazenko.hostelrestful.repository.UserRepository;
import by.bntu.tarazenko.hostelrestful.services.UserService;
import by.bntu.tarazenko.hostelrestful.services.exceptions.BadRequestException;
import by.bntu.tarazenko.hostelrestful.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            throw new BadRequestException(String
                    .format("No user with id - %s", user.get().getId()));
        }
        return user.get();
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            log.debug("Delete user - {}", user);
            userRepository.deleteById(id);
        }
    }

    @Override
    public User update(User user) {
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


    @Override
    public List<Request> getRequests(Long userId) {
        List<Request> requests = requestRepository.getByUserIdOrderByIdDesc(userId);
        return requests;
    }
}
