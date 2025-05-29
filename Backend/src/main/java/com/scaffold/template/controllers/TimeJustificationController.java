package com.scaffold.template.controllers;

import com.scaffold.template.dtos.TimeJustificationDto;
import com.scaffold.template.models.TimeJustification;
import com.scaffold.template.services.TimeJustificationService;
import io.minio.errors.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/justification")
public class TimeJustificationController {
    @Autowired
    private TimeJustificationService justificationService;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper mapper;

    @PostMapping("")
    public ResponseEntity<TimeJustification> createTimeJustification(@RequestPart("justification") TimeJustificationDto justificationDto,
                                                                     @RequestPart("file") MultipartFile file)
            throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
            NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException
    {
        TimeJustification createdJustification = justificationService.createTimeJustification(mapper.map(justificationDto, TimeJustification.class), file);
        return ResponseEntity.ok(createdJustification);
    }


}
