package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.models.News;
import by.bntu.tarazenko.hostelrestful.repository.FileRepository;
import by.bntu.tarazenko.hostelrestful.repository.NewsRepository;
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
public class NewsServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImpl newsService;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private File file;
    private News news;

    @Before
    public void init(){
        news = new News();
        file = new File(1L, "image","png", new byte[0]);
        news.setId(1L);
        news.setFile(file);
        news.setText("Test");
        news.setTitle("Test title");
        news.setPreview("Test preview");
    }

    @Test
    public void getById() {
        doReturn(Optional.of(news)).when(newsRepository).findById(1L);
        News actualNews = newsService.getById(1L);
        assertEquals(news, actualNews);
    }

    @Test
    public void createNews() {
        doReturn(news).when(newsRepository).save(news);
        doReturn(Optional.of(file)).when(fileRepository).findById(1l);
        News createdNews = newsService.create(news);
        assertEquals(news, createdNews);
    }

    @Test
    public void getByIdExpectNotFoundException() {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("News with id 2 not found");
        doReturn(Optional.empty()).when(newsRepository).findById(2L);
        News actualNews = newsService.getById(2L);
        verify(newsRepository, times(0)).findById(2L);
    }

    @Test
    public void deleteByIdExpectDeletion() {
        doReturn(Optional.of(news)).when(newsRepository).findById(1L);
        newsService.deleteById(1L);
        verify(newsRepository).deleteById(1L);
    }

    @Test
    public void deleteByIdExpectNotfoundException() {
        exceptionRule.expect(EntityNotFoundException.class);
        exceptionRule.expectMessage("News with id 2 not found");
        doReturn(Optional.empty()).when(newsRepository).findById(2L);
        newsService.deleteById(2L);
        verify(newsRepository, times(0)).deleteById(1L);
    }

}
