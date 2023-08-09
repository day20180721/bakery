package com.littlejenny.bakery.controller;

import com.littlejenny.bakery.pojo.ErrorField;
import com.littlejenny.bakery.pojo.ErrorMap;
import com.littlejenny.bakery.pojo.R;
import com.littlejenny.bakery.utils.FileUtil;
import com.littlejenny.bakery.utils.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("${file.request-path}")
public class FileController {
    @ResponseBody
    @PostMapping("/upload/single")
    public R uploadFile(@RequestParam("file") MultipartFile uploadfile) {
        try {
            String result = FileUtil.saveUploadedFile(uploadfile);
            return R.ok(result);
        } catch (RuntimeException | IOException e) {
            return R.error(getErrorMap(e));
        }
    }

    private HashMap<String, String> getErrorMap(Exception e) {
        ArrayList<ErrorField> fields = new ArrayList<>();
        fields.add(ErrorField.get("file", e));
        return ErrorMap.getErrorMap(fields);
    }

    @ResponseBody
    @PostMapping("/upload/multi")
    public R uploadMultiFile(@RequestParam("files") MultipartFile[] uploadfiles) {
        for (MultipartFile file : uploadfiles) {
            return uploadFile(file);
        }
        return R.ok;
    }

    @ResponseBody
    @GetMapping("/{imageName}")
    public byte[] getImageOrNull(@PathVariable(value = "imageName") String uploadedImageName, HttpServletRequest request) {
        try {
            return FileUtil.getImageBytesByImageName(uploadedImageName);
        } catch (Exception e) {
            log.error(RequestUtil.getUserOrNull(request).getUsername() + e.getMessage());
            return null;
        }
    }
}
