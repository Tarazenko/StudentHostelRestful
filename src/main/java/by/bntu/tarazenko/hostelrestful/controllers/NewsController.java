package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.converters.NewsConverter;
import by.bntu.tarazenko.hostelrestful.models.News;
import by.bntu.tarazenko.hostelrestful.models.dtos.NewsDTO;
import by.bntu.tarazenko.hostelrestful.services.NewsService;
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
@RequestMapping("api/news")
@Slf4j
public class NewsController {

    @Autowired
    NewsService newsService;
    
    @Autowired
    NewsConverter newsConverter;

    @GetMapping()
    public ResponseEntity<List<NewsDTO>> getNews() {
        List<NewsDTO> news = newsService.getAll().stream().map(item ->
               newsConverter.toNewsDTO(item)
        ).collect(Collectors.toList());
        log.info("News count - {}", news.size());
        return ResponseEntity.ok(news);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<NewsDTO> createNews(@RequestBody News requestNews) {
        log.trace("News request - {}", requestNews);
        News news = newsService.create(requestNews);
        log.trace("News - {}", news);
        return ResponseEntity.ok(newsConverter.toNewsDTO(news));
    }

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDTO> getNews(@PathVariable("newsId") Long newsId) {
        log.info("Getting news by id - {}", newsId);
        News news = newsService.getById(newsId);
        log.trace("Get news - {}", news);
        return ResponseEntity.ok(newsConverter.toNewsDTO(news));
    }

    @DeleteMapping("/{newsId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> deleteNews(@PathVariable("newsId") Long newsId) {
        log.info("Deleting news by id - {}", newsId);
        newsService.deleteById(newsId);
        log.info("Delete successful.");
        return ResponseEntity.ok().build();
    }


    @PutMapping("/{newsId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<NewsDTO> updateNews(@RequestBody News requestNews) {
        log.info("Update news with id - {}", requestNews.getId());
        News news = newsService.update(requestNews);
        log.info("News with id {} updated", news);
        return ResponseEntity.ok(newsConverter.toNewsDTO(news));
    }
}
