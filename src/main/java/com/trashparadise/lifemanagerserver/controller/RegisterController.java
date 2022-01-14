package com.trashparadise.lifemanagerserver.controller;

import com.trashparadise.lifemanagerserver.bean.network.RegisterRequest;
import com.trashparadise.lifemanagerserver.bean.network.RegisterResponse;
import com.trashparadise.lifemanagerserver.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        int state=userService.register(registerRequest.getUsername(),registerRequest.getPassword());
        return new RegisterResponse(state);
    }
}
