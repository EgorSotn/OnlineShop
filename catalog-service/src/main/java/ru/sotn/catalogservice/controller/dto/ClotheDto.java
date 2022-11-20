package ru.sotn.catalogservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sotn.catalogservice.domain.Clothe;
import ru.sotn.catalogservice.domain.Gender;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link Clothe} entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class ClotheDto implements Serializable {



    private  String nameCloth;
    private  String colorCloth;
    private  Long quantity;
    private  String price;
    private  Set<SizeDto> sizes;
    private  List<ImageDto> images;
    private  DescriptionDto description;
    private  Gender gender;



}