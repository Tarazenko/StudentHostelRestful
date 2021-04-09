package by.bntu.tarazenko.hostelrestful.services.impl;

import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.repository.FileRepository;
import by.bntu.tarazenko.hostelrestful.services.FileStorageService;
import by.bntu.tarazenko.hostelrestful.services.exceptions.BadRequestException;
import by.bntu.tarazenko.hostelrestful.services.exceptions.CategoryAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

  //todo exceptions check

  @Autowired
  private FileRepository fileRepository;

  public File store(MultipartFile file) {
    try {
      String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      File FileDB = new File(fileName, file.getContentType(), file.getBytes());
      return fileRepository.save(FileDB);
    } catch (Exception e){
      throw new BadRequestException(String.format("Can' upload file %s",
              file.getOriginalFilename()));
    }
  }

  public File getFile(Long id) {
    return fileRepository.findById(id).get();
  }
  
  public Stream<File> getAllFiles() {
    return fileRepository.findAll().stream();
  }
}
