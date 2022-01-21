package com.trashparadise.lifemanagerserver.service;

import com.trashparadise.lifemanagerserver.bean.network.LoginResponse;
import com.trashparadise.lifemanagerserver.bean.network.RegisterResponse;
import com.trashparadise.lifemanagerserver.bean.User;
import com.trashparadise.lifemanagerserver.mapper.UserMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int register(String username, String password) {
        if(username.length()<1 || password.length()<1){
            return RegisterResponse.ERROR;
        }
        String uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        try {
            return userMapper.insert(new User(uuid, username, hashPassword)) == 1
                    ? RegisterResponse.OK : RegisterResponse.ERROR;
        } catch (Exception e) {
            return RegisterResponse.EXIST;
        }
    }

    public String getUuid(String username) {
        ArrayList<String> uuidList = userMapper.selectUuid(username);
        if (uuidList.size() == 1) {
            return uuidList.get(0);
        }
        return "";
    }

    public int login(String username, String password) {
        ArrayList<String> hashPasswords = userMapper.selectPassword(username);
        if(username.length()<1 || password.length()<1){
            return LoginResponse.ERROR;
        }
        if (hashPasswords.size() != 1) {
            return LoginResponse.MISS;
        } else {
            return BCrypt.checkpw(password, hashPasswords.get(0)) ?
                    LoginResponse.OK : LoginResponse.ERROR;
        }
    }
}
