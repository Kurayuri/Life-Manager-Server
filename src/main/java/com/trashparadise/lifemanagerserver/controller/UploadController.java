package com.trashparadise.lifemanagerserver.controller;

import com.trashparadise.lifemanagerserver.bean.network.Response;
import com.trashparadise.lifemanagerserver.bean.network.UploadRequest;
import com.trashparadise.lifemanagerserver.bean.network.UploadResponse;
import com.trashparadise.lifemanagerserver.service.DataService;
import com.trashparadise.lifemanagerserver.service.SessionService;
import com.trashparadise.lifemanagerserver.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadController {
    private UserService userService;
    private SessionService sessionService;
    private DataService dataService;

    public UploadController(UserService userService, SessionService sessionService, DataService dataService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.dataService = dataService;
    }

    @PostMapping("/upload")
    public UploadResponse upload(@RequestBody UploadRequest uploadRequest) {
        int state = sessionService.authenticate(uploadRequest.getUuid(), uploadRequest.getSession());
        if (state == SessionService.OK) {
            state = dataService.upload(uploadRequest.getUuid(), uploadRequest.getData())
                    == Response.OK ? UploadResponse.OK : UploadResponse.FAIL;
        }
        return new UploadResponse(state);
    }
}

