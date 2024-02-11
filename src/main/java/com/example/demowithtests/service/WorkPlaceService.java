package com.example.demowithtests.service;

import com.example.demowithtests.domain.WorkPlace;

public interface WorkPlaceService {

    WorkPlace addWorkPlace(WorkPlace workPlace);

    WorkPlace getWorkPlace(Integer id);

    WorkPlace updateWorkPlace(Integer id, WorkPlace workPlace);

    void removeWorkPlace(Integer id);
}
