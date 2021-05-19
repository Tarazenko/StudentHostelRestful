package by.bntu.tarazenko.hostelrestful.controllers;

import by.bntu.tarazenko.hostelrestful.converters.FileConverter;
import by.bntu.tarazenko.hostelrestful.models.File;
import by.bntu.tarazenko.hostelrestful.models.dtos.FileDTO;
import by.bntu.tarazenko.hostelrestful.services.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Slf4j
@RequestMapping("files")
public class FileController {

    @Autowired
    private FileStorageService storageService;
    @Autowired
    private FileConverter fileConverter;

    @PostMapping("/upload")
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        File savedFile = storageService.store(file);
        log.info("Upload {} successful", file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.OK).body(fileConverter.toFileDTO(savedFile));
    }

    @GetMapping()
    public ResponseEntity<List<FileDTO>> getListFiles() {
        List<FileDTO> files = storageService.getAllFiles().map(dbFile -> {
            return fileConverter.toFileDTO(dbFile);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        File file = storageService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }
}
