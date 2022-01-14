package com.trashparadise.lifemanagerserver.service;

import com.trashparadise.lifemanagerserver.bean.network.LoginResponse;
import com.trashparadise.lifemanagerserver.bean.network.RegisterResponse;
import com.trashparadise.lifemanagerserver.bean.User;
import com.trashparadise.lifemanagerserver.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int register(String username, String password) {
        String uuid = java.util.UUID.randomUUID().toString().replaceAll("-","");
        String pswd = DigestUtils.sha1Hex(password);
        try {
            return userMapper.insert(new User(uuid, username, pswd)) == 1
                    ? RegisterResponse.OK : RegisterResponse.ERROR;
        } catch (Exception e) {
            return RegisterResponse.ERROR;
        }
    }

    public String getUuid(String username){
        ArrayList<String> uuidList=userMapper.select(username);
        if (uuidList.size()==1){
            return uuidList.get(0);
        }
        return "";
    }

    public int login(String username, String password) {
        String passwordSha1 = DigestUtils.sha1Hex(password);
        if (userMapper.select(username).size() != 1) {
            return LoginResponse.MISS;
        }
        if (userMapper.login(username, passwordSha1) != 1) {
            return LoginResponse.ERROR;
        } else {
            return LoginResponse.OK;
        }
    }
}
