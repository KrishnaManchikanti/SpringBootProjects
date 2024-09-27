package com.project.Healthcare.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import com.project.Healthcare.response.MetadataResponse;
import com.project.Healthcare.response.ResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;


@Service
public class S3Service {

    private final AmazonS3 s3Client;


    @Value("${aws.bucket}")
    private String bucketName;

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public ResultResponse upload(MultipartFile file) {

        MetadataResponse metadataResponse = new MetadataResponse();
        ResultResponse response = new ResultResponse();
        try {

            File fileSave = convertMultiPartToFile(file);
            String fileName = file.getOriginalFilename();
            s3Client.putObject(bucketName, fileName, fileSave);

            metadataResponse.setCode("200");
            metadataResponse.setMessage("file upload successfully in aws s3 bucket");
            metadataResponse.setNoOfRecords("1");
            response.setMetadataResponse(metadataResponse);
            response.setResult(fileName);

            return response;
        } catch (Exception e) {
            metadataResponse.setCode("400");
            metadataResponse.setMessage("failed to upload file in s3 bucket");
            metadataResponse.setNoOfRecords("0");
            response.setMetadataResponse(metadataResponse);
            response.setResult(null);
            return response;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
