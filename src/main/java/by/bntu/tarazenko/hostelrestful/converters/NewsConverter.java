package by.bntu.tarazenko.hostelrestful.converters;

import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.models.News;
import by.bntu.tarazenko.hostelrestful.models.dtos.DocumentDTO;
import by.bntu.tarazenko.hostelrestful.models.dtos.NewsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsConverter {
    @Autowired
    FileConverter fileConverter;

    public NewsDTO toNewsDTO(News news){
        return NewsDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .preview(news.getPreview())
                .text(news.getText())
                .file(fileConverter.toFileDTO(news.getFile()))
                .build();
    }
}
