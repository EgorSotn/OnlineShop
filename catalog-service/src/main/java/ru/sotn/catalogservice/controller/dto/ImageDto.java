package ru.sotn.catalogservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sotn.catalogservice.domain.Image;

import java.io.Serial;
import java.io.Serializable;

/**
 * A DTO for the {@link Image} entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class ImageDto implements Serializable {


    private  String url;


}