package by.bntu.tarazenko.hostelrestful.converters;

import by.bntu.tarazenko.hostelrestful.models.Document;
import by.bntu.tarazenko.hostelrestful.models.dtos.DocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentConverter {

    @Autowired
    FileConverter fileConverter;

    public DocumentDTO toDocumentDTO(Document document){
        return DocumentDTO.builder()
                .name(document.getName())
                .category(document.getCategory())
                .file(fileConverter.toFileDTO(document.getFile()))
                .build();
    }
}
