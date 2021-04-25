package by.bntu.tarazenko.hostelrestful.repository;

import by.bntu.tarazenko.hostelrestful.models.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByIdDesc();
}
