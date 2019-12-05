package io.xylia.platform.jsonapi.dummy.app.controllers;

import io.xylia.platform.jsonapi.dummy.app.data.DummyUserData;
import io.xylia.platform.jsonapi.exceptions.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DummyUserController.RESOURCE)
public class DummyUserController {

    public static final String RESOURCE = "users";
    public static final String REQUEST_1 = "request1";
    public static final String RESOURCE_NOT_FOUND_ID = "1";

    @PostMapping(REQUEST_1)
    public DummyUserData userRequest(@RequestBody DummyUserData userDataTest) {
        return userDataTest;
    }

    @GetMapping(RESOURCE_NOT_FOUND_ID)
    public void userNotFound() {
        throw new ResourceNotFoundException();
    }
}
