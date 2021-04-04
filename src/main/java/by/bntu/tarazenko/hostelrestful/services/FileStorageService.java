package by.bntu.tarazenko.hostelrestful.services;

import by.bntu.tarazenko.hostelrestful.models.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileStorageService {
    public File store(MultipartFile file) throws IOException;
    public File getFile(Long id);
    public Stream<File> getAllFiles();
}
