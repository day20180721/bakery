package com.littlejenny.bakery.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileUtil {
    public static String galleryFolder = "./gallery/";
    @Value("${file.stored-path}")
    public void setGalleryFolder(String storedPath){
        FileUtil.galleryFolder = storedPath;
    }
    public static String saveUploadedFile(MultipartFile file) throws IOException,RuntimeException {
        if (file.isEmpty()) {
            throw new RuntimeException("嘗試儲存空檔案");
        }
        byte[] bytes = file.getBytes();
        // 新建圖片的名稱
        String uploadedFileName = file.getOriginalFilename();
        String extension = getExtension(uploadedFileName);
        String imageName = FileNameUtil.getRandomImageName(extension);
        //
        Path uploadedFilePathInServer = Paths.get(galleryFolder + imageName);
        Files.write(uploadedFilePathInServer, bytes);
        return imageName;
    }

    public static String getExtension(String uploadedFileName) {
        if (!StringUtils.hasLength(uploadedFileName)) throw new RuntimeException("檔案名稱為空");
        int lastIndexOfDot = uploadedFileName.lastIndexOf(".");
        int fileNameLength = uploadedFileName.length();
        return uploadedFileName.substring(lastIndexOfDot, fileNameLength);
    }

    public static byte[] getImageBytesByImageName(String requestImageName) throws IOException {
        if (!isImageNameExist(requestImageName)) {
            throw new FileNotFoundException("請求無效檔案");
        } else {
            Path path = getImagePath(requestImageName);
            return getImageBytes(path);
        }
    }

    private static boolean isImageNameExist(String imageName) {
        boolean result = StringUtils.hasLength(imageName) && !imageName.equals("null");
        if (imageName.equals("null")) {
            System.out.println("請求 null");
        }
        return result;
    }

    private static byte[] getImageBytes(Path path) throws IOException {
        InputStream imageStream = getInputStreamByPath(path);
        return imageStream.readAllBytes();
    }

    private static InputStream getInputStreamByPath(Path path) throws IOException {
        InputStream is = Files.newInputStream(path);
        return new BufferedInputStream(is);
    }

    private static Path getImagePath(String imageName) {
        return Paths.get(galleryFolder + imageName);
    }
}
