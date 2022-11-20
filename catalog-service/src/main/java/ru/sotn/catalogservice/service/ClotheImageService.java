package ru.sotn.catalogservice.service;

import ru.sotn.catalogservice.domain.Clothe;

public interface ClotheImageService {
    Clothe addToClotheImage(Long id, String fileName);
    String getImageNameByIdClothe(Long id, String fileName);
    String deleteImageNameByClothe(Long id, String fileName);

}
