package com.example.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibrarySystemConfig {
    public final String CLOUDINARY_NAME = System.getenv("CLOUDINARY_API_NAME");
    public final String CLOUDINARY_KEY = System.getenv("CLOUDINARY_API_KEY");
    public final String CLOUDINARY_SECRET = System.getenv("CLOUDINARY_API_SECRET");

    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true);
        return mapper;
    }

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
                ObjectUtils.asMap(
                        CLOUDINARY_NAME,
                        CLOUDINARY_KEY,
                        CLOUDINARY_SECRET, true
                )
        );
    }
}
