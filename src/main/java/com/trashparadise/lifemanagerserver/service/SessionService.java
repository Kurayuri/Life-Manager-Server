package com.trashparadise.lifemanagerserver.service;

import com.trashparadise.lifemanagerserver.mapper.SessionMapper;
import com.trashparadise.lifemanagerserver.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionService {
    public static final int OK=0;
    public static final int ERROR=1;

    private SessionMapper sessionMapper;

    public SessionService(SessionMapper sessionMapper, UserMapper userMapper) {
        this.sessionMapper = sessionMapper;
    }

    public String renew(String uuid) {
        String session = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            sessionMapper.insert(uuid, session);
        } catch (Exception e) {
            System.err.print(e);
        }
        return session;
    }

    public int authenticate(String uuid, String session) {
        try {
            if (sessionMapper.select(uuid, session) == 1) {
                return OK;
            }
        } catch (Exception e) {
            System.err.print(e);
        }
        return ERROR;
    }

}
