package com.scaffold.template.services.impl;

import com.scaffold.template.entities.AreaEntity;
import com.scaffold.template.models.Area;
import com.scaffold.template.repositories.AreaRepository;
import com.scaffold.template.services.AreaService;
import jakarta.persistence.EntityExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    @Qualifier("modelMapper")
    private ModelMapper mapper;

    @Override
    public Area getArea(Long areaId) {
        Optional<AreaEntity> areaEntity = areaRepository.findById(areaId);
        return areaEntity.map(entity -> mapper.map(entity, Area.class)).orElse(null);
    }

    @Override
    public List<Area> getAreas() {
        List<AreaEntity> entities = areaRepository.findAll();
        List<Area> areas = new ArrayList<>();
        for (AreaEntity e : entities){
            areas.add(mapper.map(e,Area.class));
        }
        return areas;
    }

    @Override
    public Area createArea(Area area) {
        Optional<AreaEntity> areaEntity = areaRepository.findByDescription(area.getDescription());
        if (areaEntity.isPresent()){
            throw new EntityExistsException("The area already exists");
        }
        area.setState(1L);
        area.setUser(1L);
        AreaEntity createdEntity = areaRepository.save(mapper.map(area, AreaEntity.class));
        return mapper.map(createdEntity, Area.class);
    }

    @Override
    public boolean deleteArea(Long areaId) {
        Optional<AreaEntity> areaEntity = areaRepository.findById(areaId);
        if (areaEntity.isPresent()){
            areaEntity.get().setState(0L);
            areaRepository.save(areaEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public Area updateArea(Area area) {
        Optional<AreaEntity> areaEntity = areaRepository.findById(area.getId());
        if (areaEntity.isPresent()){
            AreaEntity areaToSave = mapper.map(area, AreaEntity.class);
            areaToSave.setId(areaEntity.get().getId());
            areaRepository.save(areaToSave);
            return mapper.map(areaToSave,Area.class);
        }
        return null;
    }

    @Override
    public boolean areaResponsibleExists(Long responsibleId) {
        Optional<AreaEntity> areaEntity = areaRepository.findByAreaResponsible(responsibleId);
        return areaEntity.isPresent();
    }
}
