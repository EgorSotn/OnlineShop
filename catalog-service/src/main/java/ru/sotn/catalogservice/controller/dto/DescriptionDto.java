package ru.sotn.catalogservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sotn.catalogservice.domain.Description;

import java.io.Serial;
import java.io.Serializable;

/**
 * A DTO for the {@link Description} entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class DescriptionDto implements Serializable {



    private  String textile;
    private  String aboutCloth;


}