package ru.sotn.catalogservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sotn.catalogservice.domain.Size;

import java.io.Serial;
import java.io.Serializable;

/**
 * A DTO for the {@link Size} entity
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class SizeDto implements Serializable {


    private  String eurSize;
    private  String ruSize;
    private  String worldSize;




}