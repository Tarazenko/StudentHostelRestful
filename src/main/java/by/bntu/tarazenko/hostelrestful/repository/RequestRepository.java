package by.bntu.tarazenko.hostelrestful.repository;

import by.bntu.tarazenko.hostelrestful.models.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> getByUserIdOrderByIdDesc(Long id);
    List<Request> findAllByOrderByIdDesc();
}
