package ru.sotn.catalogservice.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sotn.catalogservice.controller.dto.ClotheDto;
import ru.sotn.catalogservice.domain.Clothe;

import java.util.List;

public interface ClotheService {
    Clothe createClothe(ClotheDto clothe);
    Clothe updateClothe(ClotheDto clothe);
    List<Clothe> getAllClothe();
    Clothe getClotheById(Long id);
    void deleteClothe(Long id);


}
