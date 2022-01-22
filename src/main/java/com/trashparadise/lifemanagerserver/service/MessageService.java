package com.trashparadise.lifemanagerserver.service;

import com.google.gson.Gson;
import com.trashparadise.lifemanagerserver.bean.Answer;
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

    public Answer receive(String dst) {
        Gson gson = new Gson();
        ArrayList<String> data;
        try {
            data = messageMapper.select(dst);
            messageMapper.delete(dst);
            return new Answer(OK, gson.toJson(data));

        } catch (Exception e) {
            System.err.print(e);
        }
        data = new ArrayList<>();
        return new Answer(ERROR, gson.toJson(data));
    }
}
