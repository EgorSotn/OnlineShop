package ru.sotn.catalogservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.sotn.catalogservice.domain.Clothe;
import ru.sotn.catalogservice.domain.Image;
import ru.sotn.catalogservice.exception.NotFoundException;
import ru.sotn.catalogservice.repository.ClotheRepository;
import ru.sotn.catalogservice.repository.ImageRepository;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClotheImageServiceImpl implements ClotheImageService {
    private final ClotheRepository clotheRepository;
    private final ImageRepository imageRepository;
    @Override
    @Transactional
    public Clothe addToClotheImage(Long id, String fileName) {
        Clothe clothe =
                clotheRepository.findById(id).orElseThrow(()-> new NotFoundException(id.toString()));
        Image image = new Image(fileName);
        image.setClothe(clothe);
        imageRepository.save(image);
        clothe.getImages().add(image);

        log.info("File {} save", fileName);
        return clotheRepository.save(clothe);
    }

    @Transactional(readOnly = true)
    @Override
    public String getImageNameByIdClothe(Long id, String fileName) {
        Clothe clothe =
                clotheRepository.findById(id).orElseThrow(()-> new NotFoundException(id.toString()));
        for (Image tempImage: clothe.getImages()) {
            if(tempImage.getUrl().equals(fileName)){
                return tempImage.getUrl();
            }
        }
        return null;
    }

    @Override
    public String deleteImageNameByClothe(Long id, String fileName) {
        Clothe clothe =
                clotheRepository.findById(id).orElseThrow(()-> new NotFoundException(id.toString()));
        boolean keyDelete = false;
        Iterator<Image> imageIterator = clothe.getImages().iterator();
        while (imageIterator.hasNext()){
            Image imageNext = imageIterator.next();
            if(imageNext.getUrl().equals(fileName)){
                imageRepository.delete(imageNext);
                keyDelete = true;
            }
        }
        if(keyDelete) {
            clotheRepository.save(clothe);
            return fileName;
        }else return null;

    }
}
