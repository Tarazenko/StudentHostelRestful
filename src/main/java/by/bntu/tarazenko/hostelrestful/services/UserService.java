package by.bntu.tarazenko.hostelrestful.services;

import by.bntu.tarazenko.hostelrestful.models.Request;
import by.bntu.tarazenko.hostelrestful.models.User;

import java.util.List;

public interface UserService extends Service<User>{
    List<Request> getRequests(Long userId);
}
