package ru.sotn.catalogservice.service;

import org.springframework.stereotype.Service;
import ru.sotn.catalogservice.controller.dto.ClotheDto;
import ru.sotn.catalogservice.controller.dto.DescriptionDto;
import ru.sotn.catalogservice.controller.dto.ImageDto;
import ru.sotn.catalogservice.controller.dto.SizeDto;
import ru.sotn.catalogservice.domain.Clothe;
import ru.sotn.catalogservice.domain.Description;
import ru.sotn.catalogservice.domain.Image;
import ru.sotn.catalogservice.domain.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MapClotheToDtoImpl implements MapClotheToDto{
    @Override
    public ClotheDto mapToClotheDto(Clothe clothe) {
        Set<SizeDto> sizeDtos = new HashSet<>();
        clothe.getSizes().stream().forEach(s->{
            SizeDto sizeDto = new SizeDto(s.getEurSize(),s.getRuSize(),s.getWorldSize());
            sizeDtos.add(sizeDto);
        });
        List<ImageDto> imageDtoList = new ArrayList<>();
        clothe.getImages().stream().forEach(i->{
            ImageDto imageDto = new ImageDto(i.getUrl());
            imageDtoList.add(imageDto);
        });
        return ClotheDto.builder()
                .id(clothe.getId())
                .colorCloth(clothe.getColorCloth())
                .nameCloth(clothe.getNameCloth())
                .description(DescriptionDto.builder()
                        .aboutCloth(clothe.getDescription().getAboutCloth())
                        .textile(clothe.getDescription().getTextile()).build())
                .sizes(sizeDtos)
                .images(imageDtoList).build();
    }

    @Override
    public Clothe mapDtoToClothe(ClotheDto clotheDto) {
        Set<Size> sizes = new HashSet<>();
        clotheDto.getSizes().stream().forEach(s->{
            Size size = new Size(s.getEurSize(),s.getRuSize(),s.getWorldSize());
            sizes.add(size);
        });
        List<Image> imageList = new ArrayList<>();
        if(clotheDto.getImages()!=null){
            clotheDto.getImages().stream().forEach(i->{
                Image image = new Image(i.getUrl());
                imageList.add(image);
            });
        }

        Clothe clothe = new Clothe();
        clothe.setNameCloth(clotheDto.getNameCloth());
        clothe.setColorCloth(clotheDto.getColorCloth());
        clothe.setQuantity(clotheDto.getQuantity());
        if(clotheDto.getDescription() != null){
            clothe.setDescription(new Description(clotheDto.getDescription().getTextile(),
                    clotheDto.getDescription().getAboutCloth()));
        }

        clothe.setSizes(sizes);
        clothe.setImages(imageList);
        return clothe;
    }
}
