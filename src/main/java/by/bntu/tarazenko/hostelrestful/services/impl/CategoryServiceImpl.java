package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.repository.CategoryRepository;
import by.bntu.tarazenko.hostelrestful.repository.DocumentRepository;
import by.bntu.tarazenko.hostelrestful.services.CategoryService;
import by.bntu.tarazenko.hostelrestful.services.exceptions.CategoryAlreadyExistException;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(Category category) {
        checkExistByName(category);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        checkExistById(category.getId());
        checkExistByName(category);
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = checkExistById(id);
        List<Document> documents = documentRepository.findByCategory(category);
        documents.forEach(document -> {
            documentRepository.deleteById(document.getId());
        });
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(Long id) {
        checkExistById(id);
        return categoryRepository.getOne(id);
    }

    private Category checkExistById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new EntityNotFoundException(String.format("Category with id %s not found.",
                    id));
        }
        return category.get();
    }

    private void checkExistByName(Category category){
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException(String.format("Category with name %s exist",
                    category.getName()));
        }
    }

}
