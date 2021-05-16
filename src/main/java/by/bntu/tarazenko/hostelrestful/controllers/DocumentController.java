package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.converters.DocumentConverter;
import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.models.dtos.DocumentDTO;
import by.bntu.tarazenko.hostelrestful.services.CategoryService;
import by.bntu.tarazenko.hostelrestful.services.DocumentService;
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
    DocumentService documentService;

    @Autowired
    DocumentConverter documentConverter;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAll();
        log.debug("Categories count - {}", categories.size());
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        log.debug("Delete category with id - {} and all rely documents", categoryId);
        categoryService.deleteById(categoryId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/categories/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        log.debug("Update category - {}", category);
        Category updatedCategory = categoryService.update(category);
        log.debug("Updated category - {}", updatedCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.create(category);
        log.debug("Saved category - {}", savedCategory);
        return ResponseEntity.ok(category);
    }

    @GetMapping()
    public ResponseEntity<List<DocumentDTO>> getDocuments() {
        List<DocumentDTO> documents = documentService.getAll().stream().map(document ->
                documentConverter.toDocumentDTO(document)
        ).collect(Collectors.toList());
        log.debug("Documents - {}", documents);
        return ResponseEntity.ok(documents);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Document> createDocument(@RequestBody Document requestDocument) {
        log.debug("Document request - {}", requestDocument);
        Document document = documentService.create(requestDocument);
        log.debug("Document - {}", document);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/{documentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable("documentId") Long documentId) {
        log.info("Getting document by id - {}", documentId);
        Document document = documentService.getById(documentId);
        log.info("Get document - {}", document);
        return ResponseEntity.ok(documentConverter.toDocumentDTO(document));
    }

    @DeleteMapping("/{documentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteDocument(@PathVariable("documentId") Long documentId) {
        log.info("Deleting document by id - {}", documentId);
        documentService.deleteById(documentId);
        log.info("Delete successful.");
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{documentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Document> updateDocument(@RequestBody Document requestDocument) {
        log.debug("Document request - {}", requestDocument);
        Document document = documentService.update(requestDocument);
        log.debug("Document - {}", document);
        return ResponseEntity.ok(document);
    }

}
