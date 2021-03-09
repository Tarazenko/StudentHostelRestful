package by.bntu.tarazenko.hostelrestful.services;

import by.bntu.tarazenko.hostelrestful.models.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();
    public User getUser(long id);
}
