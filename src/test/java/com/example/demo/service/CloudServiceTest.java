package com.example.demo.service;

import com.cloudinary.utils.ObjectUtils;
import com.example.demo.service.cloud.CloudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CloudServiceTest {
    @Autowired
    private CloudService cloudService;
    private MultipartFile file;

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths.get("/home/rose/Pictures/images.jpeg");
        file = new MockMultipartFile("Software",
                Files.readAllBytes(path));
    }

    @Test
    @DisplayName("cloudinary upload test")
    void uploadTest(){
        try {
            String response = cloudService.upload(file.getBytes(),
                    ObjectUtils.emptyMap());
            assertThat(response).isNotNull();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
