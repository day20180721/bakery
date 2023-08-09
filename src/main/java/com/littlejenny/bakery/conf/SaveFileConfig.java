package com.littlejenny.bakery.conf;

import com.littlejenny.bakery.utils.FileUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class SaveFileConfig {
    @PostConstruct
    public void init(){
        Path path = Paths.get(FileUtil.galleryFolder);
        path.toFile().mkdirs();
    }
}
