package ru.sotn.catalogservice.controller;


import lombok.AllArgsConstructor;

import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sotn.catalogservice.controller.dto.ClotheDto;
import ru.sotn.catalogservice.domain.Clothe;
import ru.sotn.catalogservice.service.ClotheService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
//@Api(description = "clothes")
public class ClotheController {
    private final ClotheService clotheService;


    @GetMapping("/clothe")
//    @ApiOperation("clothes")
    public ResponseEntity<List<Clothe>> getAllClothe(){
        return new ResponseEntity<>(clotheService.getAllClothe(), HttpStatus.OK);
    }
    @GetMapping("/clothe/{id}")
    public ResponseEntity<Clothe> getClotheById(@PathVariable("id") Long id){
        return new ResponseEntity<>(clotheService.getClotheById(id), HttpStatus.OK);
    }
    @DeleteMapping("/api/clothe/delete")
    public ResponseEntity<?> deleteClotheById(@RequestParam("id") Long id){
        clotheService.deleteClothe(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/clothe/create")
    public ResponseEntity<Clothe> createClothe(@RequestBody ClotheDto clotheDto){
        return new ResponseEntity<>(clotheService.createClothe(clotheDto), HttpStatus.CREATED);
    }

    @PutMapping("/clothe/update")
    public ResponseEntity<Clothe> updateClothe(@RequestBody ClotheDto clotheDto){
        return new ResponseEntity<>(clotheService.updateClothe(clotheDto), HttpStatus.OK);
    }

}
