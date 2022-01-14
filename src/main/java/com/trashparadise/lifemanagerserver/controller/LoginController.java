package com.trashparadise.lifemanagerserver.controller;

import com.trashparadise.lifemanagerserver.bean.network.LoginRequest;
import com.trashparadise.lifemanagerserver.bean.network.LoginResponse;
import com.trashparadise.lifemanagerserver.service.SessionService;
import com.trashparadise.lifemanagerserver.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private UserService userService;
    private SessionService sessionService;

    public LoginController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String username=loginRequest.getUsername();
        String password=loginRequest.getPassword();
        int state = userService.login(username,password);

        String uuid="";
        String session="";
        if (state == LoginResponse.OK) {
            uuid=userService.getUuid(username);
            session = sessionService.renew(uuid);
        }
        return new LoginResponse(state,session,uuid,username);
    }
}
