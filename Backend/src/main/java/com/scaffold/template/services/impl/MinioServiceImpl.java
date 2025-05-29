package com.scaffold.template.services.impl;

import com.scaffold.template.services.MinioService;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    private String bucketName = "worktimecheck";

    @Autowired
    public MinioServiceImpl(MinioClient minioClientParam) {
        this.minioClient = minioClientParam;
    }

    @Override
    public GetObjectResponse getFile(String fileName) throws ServerException,
            InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();

        return minioClient.getObject(getObjectArgs);
    }

    @Override
    public String saveFile(String fileName, String filePrefix, MultipartFile file) throws IOException,
            ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        String objectName  = filePrefix + "/" + fileName;

        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filePrefix + "/" + fileName)
                .contentType(file.getContentType())
                .stream(file.getInputStream(), file.getSize(), -1)
                .build();

        minioClient.putObject(putObjectArgs);

        return objectName;
    }

    @Override
    public void deleteFile(String fileName) throws ServerException,
            InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException,
            InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();

        minioClient.removeObject(removeObjectArgs);
    }

    @PostConstruct
    public void testConnection() {
        try {
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket("worktimecheck").build());
            System.out.println("MinIO is up. Bucket exists: " + exists);
        } catch (Exception e) {
            System.err.println("MinIO connection failed: " + e.getMessage());
        }
    }

}
