package com.example.demowithtests.web;

import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.service.WorkPlaceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor

public class WorkPlaceControllerBean implements WorkPlaceController{


    private WorkPlaceService workPlaceService;

    @Override
    public WorkPlace create(WorkPlace workPlace) {

        return workPlaceService.addWorkPlace(workPlace);
    }
}
