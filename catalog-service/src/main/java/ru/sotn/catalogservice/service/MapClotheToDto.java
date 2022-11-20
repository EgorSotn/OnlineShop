package ru.sotn.catalogservice.service;

import ru.sotn.catalogservice.controller.dto.ClotheDto;
import ru.sotn.catalogservice.domain.Clothe;

public interface MapClotheToDto {
    ClotheDto mapToClotheDto(Clothe clothe);
    Clothe mapDtoToClothe(ClotheDto clotheDto);
}
