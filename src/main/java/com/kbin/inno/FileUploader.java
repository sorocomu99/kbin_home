package com.kbin.inno;

import com.kbin.inno.Startup.DTO.FileDTO;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class FileUploader {
    // 파일 저장
    public FileDTO insertFile(MultipartFile file) {

        // 오리지널 파일 이름
        String originalFileName = file.getOriginalFilename();

        String encodedFileName = Base64.encode(originalFileName.getBytes(StandardCharsets.UTF_8));

        System.out.println("Base64 Encoded File Name: " + encodedFileName);


        // 파일 확장자 설정
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

        // 파일 이름 설정
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // 경로 설정
        /*
        Path path = Paths.get("D:\\fsfile\\dev_kbinnovation\\").toAbsolutePath().normalize();
        //String uploadPath = "D:/fsfile/dev_kbinnovation/";
        //Path path = Paths.get("/fsfile").toAbsolutePath().normalize();
        //String savePath = path + "\\dev_kbinnovation\\";
        //String savePath = path + "/dev_kbinnovation/";
        */
//        Path path = Paths.get("D:\\fsfile").toAbsolutePath().normalize();
//        String savePath = path + "\\dev_kbinnovation\\";

//        Path path = Paths.get("/fsfile").toAbsolutePath().normalize();
//        String savePath = path + "/dev_kbinnovation/";

        String savePath = "/Users/johuiyang/Documents/web/uploads/kbinno/";

        File directory = new File(savePath);

        System.out.println("savePath====================="+savePath);

        // 디렉토리 없으면 생성
        //File directory = new File(savePath + fileName);
        //File directory = new File(savePath);
        //File directory = new File(savePath + fileName);

        System.out.println("directory====================="+directory);

        if(!directory.exists()) {
            directory.mkdirs();
        }

        File targetFile = new File(savePath + fileName);

        // 파일 저장
        /*
        try {
            file.transferTo(new File(savePath, fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/
        try (InputStream fileStream = file.getInputStream()) {
            // 파일 복사
            Files.copy(fileStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 파일 사이즈 구하기
        int bytes = (int) file.getSize();

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(fileName);
        fileDTO.setOriginalFilename(encodedFileName);
        fileDTO.setFilePath(savePath);
        return fileDTO;
    }
}
