package com.scaffold.template.services;

import com.scaffold.template.models.JustificationCheck;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface JustificationCheckService{
    JustificationCheck createCheck(JustificationCheck check);
    JustificationCheck updateCheck(JustificationCheck check);
    boolean deleteCheck(Long checkId);
    JustificationCheck getCheckById(Long checkId);
    Page<JustificationCheck> getChecksPaged(int page, int size);
}
