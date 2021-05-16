package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Category;
import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.repository.CategoryRepository;
import by.bntu.tarazenko.hostelrestful.repository.DocumentRepository;
import by.bntu.tarazenko.hostelrestful.repository.FileRepository;
import by.bntu.tarazenko.hostelrestful.services.exceptions.BadRequestException;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private Document document;
    private Category category;
    private File file;

    @Before
    public void init(){
        document = new Document();
        category = new Category(1L, "Тестовая категория");
        file = new File(1L, "документ","txt", new byte[0]);
        document.setId(1L);
        document.setCategory(category);
        document.setFile(file);
    }

    @Test
    public void getByIdExpectDocument() {
        doReturn(Optional.of(document)).when(documentRepository).findById(1L);
        Document actualDocument = documentService.getById(1L);
        assertEquals(document, actualDocument);
    }

    @Test
    public void getByIdExpectNotFoundException() {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("Document with id 2 not found");
        doReturn(Optional.empty()).when(documentRepository).findById(2L);
        Document actualDocument = documentService.getById(2L);
    }

    @Test
    public void createDocumentReturnedCreatedDocument() {
        doReturn(Optional.of(file)).when(fileRepository).findById(1L);
        doReturn(Optional.of(category)).when(categoryRepository).findByName("Тестовая категория");
        doReturn(document).when(documentRepository).save(document);
        Document createdDocument = documentService.create(document);
        assertEquals(document, createdDocument);
    }

    @Test
    public void createDocumentFileNotFoundException() {
        exceptionRule.expect(BadRequestException.class);
        exceptionRule.expectMessage("No file with id - 1");
        doReturn(Optional.empty()).when(fileRepository).findById(1L);
        doReturn(Optional.of(category)).when(categoryRepository).findByName("Тестовая категория");
        doReturn(document).when(documentRepository).save(document);
        Document createdDocument = documentService.create(document);
        verify(documentRepository, times(0)).save(document);
    }

    @Test
    public void createDocumentCategoryNotFoundException() {
        exceptionRule.expect(BadRequestException.class);
        exceptionRule.expectMessage("No category with name - Тестовая категория");
        doReturn(Optional.of(file)).when(fileRepository).findById(1L);
        doReturn(Optional.empty()).when(categoryRepository).findByName("Тестовая категория");
        doReturn(document).when(documentRepository).save(document);
        Document createdDocument = documentService.create(document);
        verify(documentRepository, times(0)).save(document);
    }

    @Test
    public void deleteByIdExpectDeletion() {
        doReturn(Optional.of(document)).when(documentRepository).findById(1L);
        documentService.deleteById(1L);
        verify(documentRepository).deleteById(1L);
    }
}
