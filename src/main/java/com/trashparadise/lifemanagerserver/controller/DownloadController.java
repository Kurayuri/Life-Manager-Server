package com.trashparadise.lifemanagerserver.controller;

import com.trashparadise.lifemanagerserver.bean.network.DownloadRequest;
import com.trashparadise.lifemanagerserver.bean.network.DownloadResponse;
import com.trashparadise.lifemanagerserver.service.DataService;
import com.trashparadise.lifemanagerserver.service.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {
    private SessionService sessionService;
    private DataService dataService;

    public DownloadController(SessionService sessionService, DataService dataService) {
        this.sessionService = sessionService;
        this.dataService = dataService;
    }

    @PostMapping("/download")
    public DownloadResponse download(@RequestBody DownloadRequest downloadRequest) {
        int state = sessionService.authenticate(downloadRequest.getUuid(), downloadRequest.getSession());
        String data = "";
        if (state == SessionService.OK) {
            data = dataService.download(downloadRequest.getUuid());
            if (data == null) {
                data = "";
                state = DownloadResponse.MISS;
            } else {
                state = DownloadResponse.OK;
            }
        }
        return new DownloadResponse(state, data);
    }
}
