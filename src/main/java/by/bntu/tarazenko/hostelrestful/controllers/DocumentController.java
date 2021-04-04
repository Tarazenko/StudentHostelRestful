package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("api/documents")
@Slf4j
public class DocumentController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = categoryService.getAll();
        log.debug("Categories count - {}", categories.size());
        return  ResponseEntity.ok(categories);
    }

    @DeleteMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId){
        log.debug("Delete category with id - {}", categoryId);
        categoryService.delete(categoryId);
        return  ResponseEntity.ok().build();
    }

    @PutMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        log.debug("Update category - {}", category);
        categoryService.update(category);
        return  ResponseEntity.ok(category);
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        log.debug("Saved category - {}", category);
        return  ResponseEntity.ok(category);
    }

}
