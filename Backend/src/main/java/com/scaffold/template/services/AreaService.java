package com.scaffold.template.services;

import com.scaffold.template.models.Area;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AreaService {
    public Area getArea(Long areaId);
    public List<Area> getAreas();
    public Area createArea(Area area);
    public boolean deleteArea(Long areaId);
    public Area updateArea(Area area);
    public boolean areaResponsibleExists(Long responsibleId);
}
