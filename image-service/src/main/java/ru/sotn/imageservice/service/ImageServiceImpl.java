package ru.sotn.imageservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sotn.imageservice.feign.CatalogRestFeign;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
    @Value("${upload.path}")
    private String uploadPath;
    private final CatalogRestFeign catalogRestFeign;
    @Override
    public String uploadFile(MultipartFile file, Long id){

        String path = new ClassPathResource(uploadPath).getPath();

        try {
            Files.copy(file.getInputStream(),Paths.get(path.toString()+ File.separator+file.getOriginalFilename()));
            catalogRestFeign.uploadImageForClothe(id, file.getOriginalFilename()).getBody();
            log.info("File {} upload", file.getOriginalFilename());
        } catch (IOException e) {
            log.info("Error!!");
            throw new RuntimeException(e);
        }

        return file.getOriginalFilename();
    }

    @Override
    public byte[] downloadFile(String fileName, Long id) throws IOException {
        String file = catalogRestFeign.getImageForClothe(id, fileName);
        if(file == null){
            return null;
        }
        String pathFile = new ClassPathResource(uploadPath + File.separator + file).getPath();
        Path path = Paths.get(pathFile);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return resource.getByteArray();

    }

    @Override
    public String deleteFile(String fileName, Long id) throws IOException {
        String fileDelete = catalogRestFeign.deleteImageForClothe(id, fileName);
        if(fileDelete == null){
            return "file not found";
        }
        String pathFile = new ClassPathResource(uploadPath + File.separator + fileDelete).getPath();
        File file = Paths.get(pathFile).toFile();
        if( file.delete()){
            return "success delete: " + fileName;

        }
        else {
            return "error delete file";
        }
//        FileUtils.forceDelete(file);
    }
}
