package com.trashparadise.lifemanagerserver.controller;

import com.trashparadise.lifemanagerserver.bean.Answer;
import com.trashparadise.lifemanagerserver.bean.network.*;
import com.trashparadise.lifemanagerserver.service.DataService;
import com.trashparadise.lifemanagerserver.service.MessageService;
import com.trashparadise.lifemanagerserver.service.SessionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class MessageController {
    private SessionService sessionService;
    private MessageService messageService;

    public MessageController(SessionService sessionService, MessageService messageService) {
        this.sessionService = sessionService;
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public SendResponse send(@RequestBody SendRequest sendRequest) {
        int state = sessionService.authenticate(sendRequest.getUuid(), sendRequest.getSession());
        if (state == SessionService.OK) {
            state = messageService.send(sendRequest.getUuid(), sendRequest.getDst(), sendRequest.getData())
                    == MessageService.OK ? SendResponse.OK : SendResponse.MISS;
        }
        return new SendResponse(state);
    }

    @PostMapping("/receive")
    public ReceiveResponse receive(@RequestBody ReceiveRequest receiveRequest) {
        int state = sessionService.authenticate(receiveRequest.getUuid(), receiveRequest.getSession());

        String data = "";
        if (state == SessionService.OK) {
            Answer ans = messageService.receive(receiveRequest.getUuid());
            state = ans.getState();
            data = ans.getData();
            state = state == MessageService.OK ?
                    ReceiveResponse.OK : ReceiveResponse.UNKNOWN;
        }
        return new ReceiveResponse(state, data);
    }
}
