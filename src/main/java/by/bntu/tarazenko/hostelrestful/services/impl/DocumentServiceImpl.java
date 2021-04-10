package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.repository.CategoryRepository;
import by.bntu.tarazenko.hostelrestful.repository.DocumentRepository;
import by.bntu.tarazenko.hostelrestful.repository.FileRepository;
import by.bntu.tarazenko.hostelrestful.services.DocumentService;
import by.bntu.tarazenko.hostelrestful.services.exceptions.BadRequestException;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Document getById(Long id) {
        return checkExistById(id);
    }

    @Override
    public List<Document> getAll() {
        return documentRepository.findAll();
    }

    @Override
    public Document create(Document document) {
        Optional<File> file = fileRepository.findById(document.getFile().getId());
        Optional<Category> category = categoryRepository.findByName(document.getCategory().getName());
        if(!file.isPresent()){
            throw new BadRequestException(String
                    .format("No file with id - %s", document.getFile().getId()));
        }
        if(!category.isPresent()){
            throw new BadRequestException(String
                    .format("No category with name - %s",document.getCategory().getName()));
        };
        document.setCategory(category.get());
        document.setFile(file.get());
        return documentRepository.save(document);
    }

    @Override
    public Document update(Document document) {
       return create(document);
    }

    @Override
    public void deleteById(Long id) {
        checkExistById(id);
        documentRepository.deleteById(id);
    }

    private Document checkExistById(Long id){
        Optional<Document> document = documentRepository.findById(id);
        if (!document.isPresent()) {
            throw new EntityNotFoundException(String.format("Document with id %s not found.",
                    id));
        }
        return document.get();
    }
}
