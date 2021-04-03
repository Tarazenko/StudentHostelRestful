package by.bntu.tarazenko.hostelrestful.services;

import by.bntu.tarazenko.hostelrestful.models.User;

import java.util.List;

public interface UserService {
    //todo DTOs ???

    public List<User> getAll();
    public User getUser(long id);
    public void deleteUser(long id);
    public User updateUser(User user);
}
