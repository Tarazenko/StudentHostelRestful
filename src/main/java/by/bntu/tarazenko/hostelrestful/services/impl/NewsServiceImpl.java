package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.models.News;
import by.bntu.tarazenko.hostelrestful.repository.FileRepository;
import by.bntu.tarazenko.hostelrestful.repository.NewsRepository;
import by.bntu.tarazenko.hostelrestful.services.NewsService;
import by.bntu.tarazenko.hostelrestful.services.exceptions.BadRequestException;
import by.bntu.tarazenko.hostelrestful.services.exceptions.EntityNotFoundException;
import by.bntu.tarazenko.hostelrestful.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    FileRepository fileRepository;

    @Override
    public News getById(Long id) {
        return checkExistById(id);
    }

    @Override
    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @Override
    public News create(News news) {
        Optional<File> file = fileRepository.findById(news.getFile().getId());
        if(!file.isPresent()){
            throw new BadRequestException(String
                    .format("No file with id - %s", news.getFile().getId()));
        }
        news.setFile(file.get());
        return newsRepository.save(news);
    }

    @Override
    public News update(News news) {
        News dbNews = checkExistById(news.getId());
        UpdateUtil.copyProperties(news, dbNews);
        log.debug("News - {}; DBNews - {}", news, dbNews);
        return newsRepository.saveAndFlush(dbNews);
    }

    @Override
    public void deleteById(Long id) {
        checkExistById(id);
        newsRepository.deleteById(id);
    }
    private News checkExistById(Long id){
        Optional<News> news = newsRepository.findById(id);
        if (!news.isPresent()) {
            throw new EntityNotFoundException(String.format("News with id %s not found.",
                    id));
        }
        return news.get();
    }
}
