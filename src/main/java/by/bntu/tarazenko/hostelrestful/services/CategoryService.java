package by.bntu.tarazenko.hostelrestful.services;

import by.bntu.tarazenko.hostelrestful.models.Category;

import java.util.List;

public interface CategoryService {
    public List<Category> getAll();
    public Category save(Category category);
    public Category update(Category category);
    public void delete(Long id);
    public Category getCategory(Long id);
}
