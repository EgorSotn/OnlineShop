package ru.sotn.catalogservice.repository;

import ru.sotn.catalogservice.domain.Size;

import java.util.Optional;
import java.util.Set;

public interface SizeRepositoryCustom {
    Optional<Size> getBySizeOrCreate(Size size);
}
