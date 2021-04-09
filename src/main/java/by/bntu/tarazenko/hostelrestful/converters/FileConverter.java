package by.bntu.tarazenko.hostelrestful.converters;

import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.models.dtos.FileDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
public class FileConverter {
    public static final String FILES_URL = "/files/";

    public FileDTO toFileDTO(File file) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(FILES_URL)
                .path(file.getId().toString())
                .toUriString();

        return FileDTO.builder()
                .id(file.getId())
                .name(file.getName())
                .url(fileDownloadUri)
                .type(file.getType())
                .size(file.getData().length)
                .build();

    }

}
