package com.example.demowithtests.web;

import com.example.demowithtests.domain.WorkPlace;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Employee", description = "Employee API")
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public interface WorkPlaceController {

    @PostMapping("/workPlace")
    @ResponseStatus(HttpStatus.CREATED)
    WorkPlace create(@RequestBody WorkPlace workPlace);
}
