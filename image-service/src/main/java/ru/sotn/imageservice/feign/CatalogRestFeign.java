package ru.sotn.imageservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("catalog-service")
public interface CatalogRestFeign {
    @PostMapping("/api/clothe/image/add/{id}")
    ResponseEntity<String> uploadImageForClothe(@PathVariable(value = "id") Long id,
                                                @RequestParam(value = "fileName") String fileName);

    @GetMapping(value = "/api/clothe/image/get/{id}")
    String getImageForClothe(@PathVariable(value = "id") Long id,
                             @RequestParam(value = "fileName") String fileName);

    @PutMapping(value = "/api/clothe/image/delete/{id}")
    String deleteImageForClothe(@PathVariable(value = "id") Long id,
                                @RequestParam(value = "fileName") String fileName);
}
