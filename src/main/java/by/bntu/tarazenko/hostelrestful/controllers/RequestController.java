package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.converters.RequestConverter;
import by.bntu.tarazenko.hostelrestful.models.Request;
import by.bntu.tarazenko.hostelrestful.models.dtos.RequestDTO;
import by.bntu.tarazenko.hostelrestful.services.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("api/requests")
@Slf4j
public class RequestController {

    @Autowired
    RequestService requestService;

    @Autowired
    RequestConverter requestConverter;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<RequestDTO>> getRequests() {
        List<RequestDTO> requests = requestService.getAll().stream().map(item ->
                requestConverter.toRequestDTO(item)
        ).collect(Collectors.toList());
        log.debug("Categories count - {}", requests.size());
        return ResponseEntity.ok(requests);
    }

    @DeleteMapping("{requestId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteCategory(@PathVariable("requestId") Long requestId) {
        log.debug("Delete request with id - {}", requestId);
        requestService.deleteById(requestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{requestId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<RequestDTO> updateCategory(@RequestBody Request request) {
        log.debug("Update request - {}", request);
        Request updatedRequest = requestService.update(request);
        log.debug("Updated request - {}", updatedRequest);
        return ResponseEntity.ok(requestConverter.toRequestDTO(updatedRequest));
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody Request request) {
        log.debug("Request - {}", request);
        Request createdRequest = requestService.create(request);
        log.debug("Created request - {}", createdRequest);
        return ResponseEntity.ok(requestConverter.toRequestDTO(createdRequest));
    }
}
