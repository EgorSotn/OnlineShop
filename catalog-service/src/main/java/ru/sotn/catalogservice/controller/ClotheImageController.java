package ru.sotn.catalogservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sotn.catalogservice.domain.Clothe;
import ru.sotn.catalogservice.service.ClotheImageService;

@RestController
@RequiredArgsConstructor
public class ClotheImageController {
    private final ClotheImageService clotheImageService;

    @PostMapping("/api/clothe/image/add/{id}")
    public ResponseEntity<Clothe> uploadImageClothe(@PathVariable(value = "id") Long id,
                                                    @RequestParam(value = "fileName") String fileName){
        return new ResponseEntity<>(clotheImageService.addToClotheImage(id, fileName), HttpStatus.OK);
    }
    @GetMapping("/api/clothe/image/get/{id}")
    public ResponseEntity<String> getImageForClothe(@PathVariable(value = "id") Long id,
                                                    @RequestParam(value = "fileName") String fileName){
        return new ResponseEntity<>(clotheImageService.getImageNameByIdClothe(id, fileName), HttpStatus.OK);
    }
    @PutMapping("/api/clothe/image/delete/{id}")
    public ResponseEntity<String> deleteImageForClothe(@PathVariable(value = "id") Long id,
                                                    @RequestParam(value = "fileName") String fileName){
        return new ResponseEntity<>(clotheImageService.deleteImageNameByClothe(id, fileName), HttpStatus.OK);
    }
}
