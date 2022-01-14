package com.trashparadise.lifemanagerserver.service;

import com.trashparadise.lifemanagerserver.mapper.MessageMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageService {
    private MessageMapper messageMapper;
    public static final int OK = 0;
    public static final int ERROR = 1;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public int send(String src, String dst, String data) {
        try {
            if (messageMapper.insert(src, dst, data) == 1) {
                return OK;
            }
        } catch (Exception e) {
            System.err.print(e);
        }
        return ERROR;
    }

    public ArrayList<String> receive(String src) {
        ArrayList<String> data = null;
        try {
            data = messageMapper.select(src);
            messageMapper.delete(src);
            return data;

        } catch (Exception e) {
            System.err.print(e);
        }
        return null;
    }
}
