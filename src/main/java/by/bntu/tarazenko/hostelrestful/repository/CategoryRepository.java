package by.bntu.tarazenko.hostelrestful.repository;


import by.bntu.tarazenko.hostelrestful.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}
