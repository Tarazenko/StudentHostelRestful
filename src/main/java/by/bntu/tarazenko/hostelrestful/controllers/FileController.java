package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.models.ResponseMessage;
import by.bntu.tarazenko.hostelrestful.models.dtos.FileDTO;
import by.bntu.tarazenko.hostelrestful.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class FileController {

  @Autowired
  private FileStorageService storageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file);
      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

  @GetMapping("/files")
  public ResponseEntity<List<FileDTO>> getListFiles() {
    List<FileDTO> files = storageService.getAllFiles().map(dbFile -> {
      String fileDownloadUri = ServletUriComponentsBuilder
          .fromCurrentContextPath()
          .path("/files/")
          .path(dbFile.getId())
          .toUriString();

      return new FileDTO(
          dbFile.getName(),
          fileDownloadUri,
          dbFile.getType(),
          dbFile.getData().length);
    }).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(files);
  }

  @GetMapping("/files/{id}")
  public ResponseEntity<byte[]> getFile(@PathVariable String id) {
    File file = storageService.getFile(id);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
        .body(file.getData());
  }
}
