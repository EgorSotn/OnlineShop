package ru.sotn.imageservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;
import ru.sotn.imageservice.service.ImageService;

import java.io.IOException;


@RestController
@RequestMapping("/file")
@AllArgsConstructor

public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload/toclothe/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String uploadFile(@RequestParam(value = "file") MultipartFile file,
                                   @PathVariable Long id) throws IOException {

        return imageService.uploadFile(file, id);
    }

    @GetMapping("/download/{id}/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("fileName") String fileName,
                                                          @PathVariable Long id) throws IOException {
        byte[] data = imageService.downloadFile(fileName, id );
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{id}/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileName") String fileName,
                                             @PathVariable Long id) throws IOException {
        return new ResponseEntity<>(imageService.deleteFile(fileName, id), HttpStatus.OK);
    }
}
