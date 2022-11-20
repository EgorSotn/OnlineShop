package ru.sotn.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import ru.sotn.catalogservice.domain.Clothe;

import java.util.List;
import java.util.Optional;


public interface ClotheRepository extends JpaRepository<Clothe, Long> {


    List<Clothe> findByNameCloth(String nameCloth);


    Optional<Clothe> findClotheByNameClothAndColorCloth( String nameCloth,  String colorCloth);
}
