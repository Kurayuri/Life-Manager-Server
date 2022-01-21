package com.trashparadise.lifemanagerserver.controller;

import com.trashparadise.lifemanagerserver.bean.Answer;
import com.trashparadise.lifemanagerserver.bean.network.DownloadResponse;
import com.trashparadise.lifemanagerserver.bean.network.UploadRequest;
import com.trashparadise.lifemanagerserver.service.DataService;
import com.trashparadise.lifemanagerserver.service.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {
    private SessionService sessionService;
    private DataService dataService;

    public SyncController(SessionService sessionService, DataService dataService) {
        this.sessionService = sessionService;
        this.dataService = dataService;
    }

    @PostMapping("/sync")
    public DownloadResponse sync(@RequestBody UploadRequest uploadRequest) {
        int state = sessionService.authenticate(uploadRequest.getUuid(), uploadRequest.getSession());
        String data = "";
        if (state == sessionService.OK) {
            Answer ans = dataService.sync(uploadRequest.getUuid(), uploadRequest.getData());
            state = ans.getState();
            data = ans.getData();
            state = state == DataService.OK ?
                    DownloadResponse.OK : DownloadResponse.MISS;

        }
        return new DownloadResponse(state, data);
    }
}