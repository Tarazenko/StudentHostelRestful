package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.models.Request;
import by.bntu.tarazenko.hostelrestful.repository.RequestRepository;
import by.bntu.tarazenko.hostelrestful.services.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("api/requests")
@Slf4j
public class RequestContorller {

    @Autowired
    RequestService requestService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<Request>> getRequests() {
        List<Request> requests = requestService.getAll();
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
    public ResponseEntity<?> updateCategory(@RequestBody Request request) {
        log.debug("Update request - {}", request);
        Request updatedRequest = requestService.update(request);
        log.debug("Updated request - {}", updatedRequest);
        return ResponseEntity.ok(updatedRequest);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        log.debug("Request - {}", request);
        Request createdRequest = requestService.create(request);
        log.debug("Created request - {}", createdRequest);
        return ResponseEntity.ok(createdRequest);
    }
}
