package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Request;
import by.bntu.tarazenko.hostelrestful.models.Status;
import by.bntu.tarazenko.hostelrestful.models.User;
import by.bntu.tarazenko.hostelrestful.repository.RequestRepository;
import by.bntu.tarazenko.hostelrestful.repository.UserRepository;
import by.bntu.tarazenko.hostelrestful.services.RequestService;
import by.bntu.tarazenko.hostelrestful.services.exceptions.BadRequestException;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import by.bntu.tarazenko.hostelrestful.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RequestServiceImpl implements RequestService {

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Request getById(Long id) {
        return checkExistById(id);
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Request create(Request request) {
        Optional<User> user = userRepository.findById(request.getUser().getId());
        if (!user.isPresent()) {
            throw new BadRequestException(String
                    .format("No user with id - %s", request.getUser().getId()));
        }
        request.setUser(user.get());
        request.setStatus(Status.WAITING);
        return requestRepository.save(request);
    }

    @Override
    public Request update(Request request) {
        Request dbRequest = checkExistById(request.getId());
        UpdateUtil.copyProperties(request, dbRequest);
        return requestRepository.saveAndFlush(dbRequest);

    }

    @Override
    public void deleteById(Long id) {
        checkExistById(id);
        requestRepository.deleteById(id);
    }

    private Request checkExistById(Long id) {
        Optional<Request> request = requestRepository.findById(id);
        if (!request.isPresent()) {
            throw new EntityNotFoundException(String.format("Request with id %s not found.",
                    id));
        }
        return request.get();
    }
}
