package ru.sotn.catalogservice.repository;

import ru.sotn.catalogservice.domain.Description;
import ru.sotn.catalogservice.domain.Size;

import java.util.Optional;

public interface DescriptionRepositoryCustom {
    Optional<Description> getByTextileOrCreate(Description description);
}
