package ru.sotn.catalogservice.repository;


import ru.sotn.catalogservice.domain.Image;

import java.util.Optional;

public interface ImageRepositoryCustom {
    Optional<Image> getByUrlOrCreate(Image image);
}
