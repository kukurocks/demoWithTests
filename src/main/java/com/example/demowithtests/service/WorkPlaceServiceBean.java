package com.example.demowithtests.service;

import com.example.demowithtests.repository.WorkPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.example.demowithtests.domain.WorkPlace;

@Service
@RequiredArgsConstructor
public class WorkPlaceServiceBean implements WorkPlaceService{

    private final WorkPlaceRepository workPlaceRepository;

    @Override
    public WorkPlace addWorkPlace(WorkPlace workPlace) {
        workPlace.setFreePlacesCount(workPlace.getQuantity());
        return workPlaceRepository.save(workPlace);
    }

    @Override
    public WorkPlace getWorkPlace(Integer id) {

       return workPlaceRepository.findById(id).orElse(null);
    }

    @Override
    public WorkPlace updateWorkPlace(Integer id, WorkPlace workPlace) {
        return null;
    }

    @Override
    public void removeWorkPlace(Integer id) {

    }

}
