package ru.sotn.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.sotn.catalogservice.domain.Image;

import java.util.Optional;



public interface ImageRepository extends JpaRepository<Image, Long> , ImageRepositoryCustom{

    @Query("SELECT i FROM Image i WHERE i.clothe.id =: id")
    Optional<Image> findByClotheIdInImage(@Param("id") Long aLong);
}
