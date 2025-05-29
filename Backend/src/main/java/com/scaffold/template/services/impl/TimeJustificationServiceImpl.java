package com.scaffold.template.services.impl;

import com.scaffold.template.dtos.EmployeeJustificationDto;
import com.scaffold.template.models.TimeJustification;
import com.scaffold.template.dtos.TimeJustificationDto;
import com.scaffold.template.entities.TimeJustificationEntity;
import com.scaffold.template.models.EmployeeTime;
import com.scaffold.template.repositories.TimeJustificationRepository;
import com.scaffold.template.services.EmployeeTimeService;
import com.scaffold.template.services.MinioService;
import com.scaffold.template.services.TimeJustificationService;
import io.minio.errors.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TimeJustificationServiceImpl implements TimeJustificationService {
     @Autowired
     private TimeJustificationRepository timeJustificationRepository;

     @Autowired
     private ModelMapper modelMapper;

     @Autowired
     private EmployeeTimeService timeService;

     @Autowired
     private MinioService minioService;


    @Override
    public TimeJustification createTimeJustification(TimeJustification justification, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException,
                    NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        EmployeeTime time = timeService.getEmployeeTimeById(justification.getTimeId());
        if (time == null){
            throw new IllegalArgumentException("The time for the justificaiton does not exist");
        }
        String objectPath = minioService.saveFile(file.getOriginalFilename(),"justification",file);
        justification.setJustificationUrl(objectPath);
        justification.setTimeState(1L);
        TimeJustificationEntity entity = modelMapper.map(justification, TimeJustificationEntity.class);
        entity.setTimeAudUser(1L);
        entity = timeJustificationRepository.save(entity);
        return modelMapper.map(entity, TimeJustification.class);
    }

    @Override
    public TimeJustification updateTimeJustification(TimeJustification justification, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Optional<TimeJustificationEntity> justificationEntity = timeJustificationRepository.findById(justification.getJustificationId());
        if (justificationEntity.isPresent()){
            minioService.deleteFile(justificationEntity.get().getJustificationUrl());
            String objectPath = minioService.saveFile(file.getOriginalFilename(),"justification",file);
            justificationEntity.get().setJustificationUrl(objectPath);
            justificationEntity.get().setJustificationObservation(justification.getJustificationObservation());
            TimeJustificationEntity savedEntity = timeJustificationRepository.save(justificationEntity.get());
            return modelMapper.map(savedEntity, TimeJustification.class);
        }
        return null;
    }

    @Override
    public boolean deleteTimeJustification(TimeJustification justification) {
        Optional<TimeJustificationEntity> entity = timeJustificationRepository.findById(justification.getJustificationId());
        if (entity.isPresent()){
            entity.get().setTimeState(0L);
            timeJustificationRepository.save(entity.get());
            return true;
        }
        return false;
    }

    @Override
    public TimeJustificationDto getTimeJustification(Long justificationId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        Optional<TimeJustificationEntity> entity = timeJustificationRepository.findById(justificationId);
        if (entity.isPresent()){
            TimeJustificationDto dto = modelMapper.map(entity, TimeJustificationDto.class);
            dto.setFile(minioService.getFile(entity.get().getJustificationUrl()));
            return dto;
        }
        return null;
    }

    @Override
    public Page<EmployeeJustificationDto> getJustificationsPaged(int page, int size, Long employeeId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TimeJustificationEntity> paged = timeJustificationRepository.findByEmployeeId(employeeId, pageable);
        List<EmployeeJustificationDto> justificationDtoList = new ArrayList<>();
        for (TimeJustificationEntity e : paged.getContent()) {
            justificationDtoList.add(new EmployeeJustificationDto(e.getJustificationId(),
                    e.getTime().getEmployee().getEmployeeName()+' '+e.getTime().getEmployee().getEmployeeLastName(),
                    e.getTimeState(),
                    e.getTime().getTimeDay()));
        }
        long total = timeJustificationRepository.count();
        return new PageImpl<>(justificationDtoList,pageable, total);
    }

    @Override
    public TimeJustification changeJustificationState(Long justificationId, boolean approved) {
        Optional<TimeJustificationEntity> entity = timeJustificationRepository.findById(justificationId);
        if (entity.isPresent() && approved){
            entity.get().setTimeState(2L);
            timeJustificationRepository.save(entity.get());
            return modelMapper.map(entity.get(), TimeJustification.class);
        } else if (entity.isPresent()) {
            entity.get().setTimeState(3L);
            timeJustificationRepository.save(entity.get());
            return modelMapper.map(entity.get(), TimeJustification.class);
        }
        return null;
    }

    @Override
    public TimeJustification getTimeJustificationById(Long justificationId) {
        Optional<TimeJustificationEntity> entity = timeJustificationRepository.findById(justificationId);
        return entity.map(timeJustificationEntity -> modelMapper.map(timeJustificationEntity, TimeJustification.class)).orElse(null);
    }


}
