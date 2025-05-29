package com.scaffold.template.services.impl;

import com.scaffold.template.entities.JustificationCheckEntity;
import com.scaffold.template.entities.TimeJustificationEntity;
import com.scaffold.template.models.JustificationCheck;
import com.scaffold.template.models.TimeJustification;
import com.scaffold.template.repositories.JustificationCheckRepository;
import com.scaffold.template.services.JustificationCheckService;
import com.scaffold.template.services.TimeJustificationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JustificationCheckServiceImpl implements JustificationCheckService {
    @Autowired
    private JustificationCheckRepository checkRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TimeJustificationService timeJustificationService;

    @Override
    public JustificationCheck createCheck(JustificationCheck check) {
        TimeJustification justification = timeJustificationService.getTimeJustificationById(check.getJustificationId());
        if (justification==null){
            throw new RuntimeException("Justification doesn't exist");
        }
        check.setCheckState(1L);
        timeJustificationService.changeJustificationState(check.getJustificationId(), check.getCheckApproval());
        JustificationCheckEntity savedEntity = checkRepository.save(modelMapper.map(check, JustificationCheckEntity.class));
        return modelMapper.map(savedEntity, JustificationCheck.class);
    }

    @Override
    public JustificationCheck updateCheck(JustificationCheck check) {
        TimeJustification justification = timeJustificationService.getTimeJustificationById(check.getJustificationId());
        if (justification==null){
            throw new RuntimeException("Justification doesn't exist");
        }
        Optional<JustificationCheckEntity> checkEntity = checkRepository.findById(check.getJustificationId());
        if (checkEntity.isPresent()){
            checkEntity.get().setCheckApproval(check.getCheckApproval());
            checkEntity.get().setCheckReason(check.getCheckReason());
            checkRepository.save(checkEntity.get());
            return modelMapper.map(checkEntity.get(), JustificationCheck.class);
        }
        return null;
    }

    @Override
    public boolean deleteCheck(Long checkId) {
        Optional<JustificationCheckEntity> checkEntity = checkRepository.findById(checkId);
        if (checkEntity.isPresent()){
            checkEntity.get().setCheckState(0L);
            checkRepository.save(checkEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public JustificationCheck getCheckById(Long checkId) {
        Optional<JustificationCheckEntity> checkEntity = checkRepository.findById(checkId);
        if (checkEntity.isPresent()){
            return modelMapper.map(checkEntity, JustificationCheck.class);
        }
        return null;
    }

    @Override
    public Page<JustificationCheck> getChecksPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<JustificationCheckEntity> checkPage = checkRepository.searchPaged(pageable);

        return checkPage.map(justificationCheckEntity -> modelMapper.map(justificationCheckEntity, JustificationCheck.class));
    }
}
