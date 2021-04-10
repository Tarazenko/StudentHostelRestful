package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.repository.CategoryRepository;
import by.bntu.tarazenko.hostelrestful.services.CategoryService;
import by.bntu.tarazenko.hostelrestful.services.exceptions.CategoryAlreadyExistException;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException(String.format("Category with name %s exist",
                    category.getName()));
        }
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
        checkExistById(id);
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getById(Long id) {
        checkExistById(id);
        return categoryRepository.getOne(id);
    }

    private void checkExistById(Long id){
        if (categoryRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException(String.format("Category with id %s not found.",
                    id));
        }
    }

    private void checkExistByName(Category category){
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryAlreadyExistException(String.format("Category with name %s exist",
                    category.getName()));
        }
    }

}
