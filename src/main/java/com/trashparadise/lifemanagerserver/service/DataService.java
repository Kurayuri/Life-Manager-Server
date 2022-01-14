package com.trashparadise.lifemanagerserver.service;

import com.trashparadise.lifemanagerserver.bean.network.Response;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class DataService {
    private static final String path = "./userdata";

    public DataService() {
        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdir();
        }
    }

    public int upload(String uuid, String data) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + uuid);
            fileOutputStream.write(data.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();
            return Response.OK;
        } catch (IOException e) {
            return Response.ERROR;
        }
    }

    public String download(String uuid) {
        try {
            FileInputStream fileOutputStream = new FileInputStream(path + "/" + uuid);
            byte[] bytes=fileOutputStream.readAllBytes();
            fileOutputStream.close();
            return new String(bytes,StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }
}
