package by.bntu.tarazenko.hostelrestful.converters;

import by.bntu.tarazenko.hostelrestful.models.News;
import by.bntu.tarazenko.hostelrestful.models.Request;
import by.bntu.tarazenko.hostelrestful.models.dtos.NewsDTO;
import by.bntu.tarazenko.hostelrestful.models.dtos.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestConverter {
    @Autowired
    UserConverter userConverter;

    public RequestDTO toRequestDTO(Request request){
        return RequestDTO.builder()
                .id(request.getId())
                .room(request.getRoom())
                .content(request.getContent())
                .comment(request.getComment())
                .status(request.getStatus())
                .user(userConverter.toUserDTO(request.getUser()))
                .build();
    }
}
