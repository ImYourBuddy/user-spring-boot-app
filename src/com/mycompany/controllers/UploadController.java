package com.mycompany.controllers;

import com.mycompany.services.DataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class UploadController {
    @Autowired
    private DataHandler handler;
    private final String UPLOAD_DIR = "./uploads/";

    @GetMapping("/upload-file")
    public String uploadForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/upload-file";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try(BufferedInputStream is = new BufferedInputStream(file.getInputStream())) {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
            handler.mergeFiles(UPLOAD_DIR + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/upload-file";
    }
}
