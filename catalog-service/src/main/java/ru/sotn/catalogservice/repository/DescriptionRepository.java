package ru.sotn.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.sotn.catalogservice.domain.Description;


public interface DescriptionRepository extends JpaRepository<Description, Long>, DescriptionRepositoryCustom {

}
