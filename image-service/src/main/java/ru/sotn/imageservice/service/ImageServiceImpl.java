package ru.sotn.imageservice.service;

import lombok.extern.slf4j.Slf4j;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.LocalSourceFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sotn.imageservice.feign.CatalogRestFeign;
import ru.sotn.imageservice.sftp.SftpConnect;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



@Service
@Slf4j

public class ImageServiceImpl implements ImageService{

    private final String localDir;
    private final String remoteDir;
    private final SftpConnect sftpConnect;
    private final CatalogRestFeign catalogRestFeign;

    public ImageServiceImpl(@Value("${upload.path}") String localDir, @Value("${sftp.remoteDir}") String remoteDir, SftpConnect sftpConnect, CatalogRestFeign catalogRestFeign) {
        this.localDir = localDir;
        this.remoteDir = remoteDir;
        this.sftpConnect = sftpConnect;
        this.catalogRestFeign = catalogRestFeign;
    }

    @Override
    public String uploadFile(MultipartFile file, Long id) throws IOException {

        String path = new ClassPathResource(localDir).getPath();
        SSHClient sshClient = sftpConnect.connect();
        SFTPClient sftpClient = sshClient.newSFTPClient();
        File convFile = convert(file);
        LocalSourceFile localSourceFile = new FileSystemFile(convFile);
        sftpClient.put(localSourceFile, remoteDir + convFile.getName());
        catalogRestFeign.uploadImageForClothe(id, file.getOriginalFilename()).getBody();
        sftpClient.close();
        sshClient.disconnect();



        return file.getOriginalFilename();
    }

    @Override
    public byte[] downloadFile(String fileName, Long id) throws IOException {
        SSHClient sshClient = sftpConnect.connect();
        SFTPClient sftpClient = sshClient.newSFTPClient();
        String file = catalogRestFeign.getImageForClothe(id, fileName);
        if(file == null){
            return null;
        }
        sftpClient.get(remoteDir + file, localDir + file);
        String pathFile = new ClassPathResource(localDir + File.separator + fileName).getPath();
        Path path = Paths.get(pathFile);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        sftpClient.close();
        sshClient.disconnect();
        return resource.getByteArray();

    }

    @Override
    public String deleteFile(String fileName, Long id) throws IOException {
        SSHClient sshClient = sftpConnect.connect();
        SFTPClient sftpClient = sshClient.newSFTPClient();
        String fileDelete = catalogRestFeign.deleteImageForClothe(id, fileName);
        if(fileDelete == null){
            return "file not found";
        }

        sftpClient.rm(remoteDir+fileName);

        return fileName;
    }
    private File convert(MultipartFile file) {
        File convFile = new File(localDir+ File.separator+ file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close(); //IOUtils.closeQuietly(fos);
        } catch (IOException e) {
            convFile = null;
        }

        return convFile;
    }
    private void deleteLocalFile(File file){

        file.delete();
    }
}
