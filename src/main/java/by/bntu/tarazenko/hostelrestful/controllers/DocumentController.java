package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.converters.DocumentConverter;
import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.models.dtos.DocumentDTO;
import by.bntu.tarazenko.hostelrestful.repository.DocumentRepository;
import by.bntu.tarazenko.hostelrestful.repository.FileRepository;
import by.bntu.tarazenko.hostelrestful.services.CategoryService;
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
@RequestMapping("api/documents")
@Slf4j
public class DocumentController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    DocumentConverter documentConverter;

    @GetMapping("/categories")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAll();
        log.debug("Categories count - {}", categories.size());
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        log.debug("Delete category with id - {}", categoryId);
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        log.debug("Update category - {}", category);
        categoryService.update(category);
        return ResponseEntity.ok(category);
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.save(category);
        log.debug("Saved category - {}", category);
        return ResponseEntity.ok(category);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<List<DocumentDTO>> getDocuments() {
        List<DocumentDTO> documents = documentRepository.findAll().stream().map(document -> {
                    return documentConverter.toDocumentDTO(document);
                }
        ).collect(Collectors.toList());
        log.debug("Documents - {}", documents);
        return ResponseEntity.ok(documents);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Document> getDocuments(@RequestBody Document requestDocument) {
        File file = fileRepository.findById(requestDocument.getFile().getId()).get();
        requestDocument.setFile(file);
        log.debug("Document request - {}", requestDocument);
        Document document = documentRepository.save(requestDocument);
        log.debug("Document - {}", document);
        return ResponseEntity.ok(document);
    }

}
