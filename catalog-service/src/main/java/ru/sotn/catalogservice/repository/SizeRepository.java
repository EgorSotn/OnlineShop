package ru.sotn.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sotn.catalogservice.domain.Size;

public interface SizeRepository extends JpaRepository<Size, Long>, SizeRepositoryCustom {

}
