package com.project.Healthcare.controller;


import com.project.Healthcare.response.ResultResponse;
import com.project.Healthcare.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/s3")
@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/uploadFile")
    public ResultResponse upload(@RequestParam("file") MultipartFile file) {
        return s3Service.upload(file);
    }
}
