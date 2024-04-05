package com.shop.alcoshopspring.controllers;

import com.shop.alcoshopspring.wrappers.ImageWrapper;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ImageController {
    @GetMapping(value = "/images/{name}")
    public ImageWrapper getImage(@PathVariable String name) {
        File file = new File("./images/" + name);

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fileInputStream.read(bytes);
            String encodedImage = Base64.getEncoder().encodeToString(bytes);
            return new ImageWrapper(encodedImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
