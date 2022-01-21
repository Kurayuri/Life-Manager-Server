package com.trashparadise.lifemanagerserver.service;

import com.google.gson.Gson;
import com.trashparadise.lifemanagerserver.bean.*;
import com.trashparadise.lifemanagerserver.bean.network.Response;
import org.apache.ibatis.javassist.compiler.ast.Pair;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;
import java.util.TreeSet;

@Service
public class DataService {
    private static final String path = "./userdata";
    public static final int OK = 0;
    public static final int ERROR = 1;

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
            return OK;
        } catch (IOException e) {
            return ERROR;
        }
    }

    public Answer sync(String uuid, String data) {
        Gson gson = new Gson();
        try {
            String json = download(uuid);
            DataBundleBean oldBean;
            DataBundleBean currBean = gson.fromJson(data, DataBundleBean.class);
            try {
                oldBean = gson.fromJson(json, DataBundleBean.class);
                if (oldBean == null) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.err.println("Server data error: " + uuid);
                oldBean = currBean;
            }

            TreeMap<String, Bill> oldBills = new TreeMap<>();
            TreeMap<String, Work> oldWorks = new TreeMap<>();
            TreeMap<String, Contact> oldContacts = new TreeMap<>();
            TreeSet<String> deletedList = oldBean.getDeletedList();

            for (Bill bill : oldBean.getBillList()) {
                oldBills.put(bill.getUuid(), bill);
            }
            for (Work work : oldBean.getWorkList()) {
                oldWorks.put(work.getUuid(), work);
            }
            for (Contact contact : oldBean.getContactList()) {
                oldContacts.put(contact.getUuid(), contact);
            }

            // Merge
            for (Bill currBill : currBean.getBillList()) {
                Bill oldBill = oldBills.get(currBill.getUuid());
                if (oldBill == null) {
                    oldBills.put(currBill.getUuid(), currBill);
                } else {
                    if (oldBill.getModifiedTime().compareTo(currBill.getModifiedTime()) < 0) {
                        oldBills.put(currBill.getUuid(), currBill);
                    }
                }
            }

            for (Work currWork : currBean.getWorkList()) {
                Work oldWork = oldWorks.get(currWork.getUuid());
                if (oldWork == null) {
                    oldWorks.put(currWork.getUuid(), currWork);
                } else {
                    if (oldWork.getModifiedTime().compareTo(currWork.getModifiedTime()) < 0) {
                        oldWorks.put(currWork.getUuid(), currWork);
                    }
                }
            }

            for (Contact currContact : currBean.getContactList()) {
                Contact oldContact = oldContacts.get(currContact.getUuid());
                if (oldContact == null) {
                    oldContacts.put(currContact.getUuid(), currContact);
                } else {
                    if (oldContact.getModifiedTime().compareTo(currContact.getModifiedTime()) < 0) {
                        oldContacts.put(currContact.getUuid(), currContact);
                    }
                }
            }

            // Delete
            for (String itemUuid : currBean.getDeletedList()) {
                deletedList.add(itemUuid);
            }
            for (String itemUuid : deletedList) {
                oldBills.remove(itemUuid);
                oldWorks.remove(itemUuid);
                oldContacts.remove(itemUuid);
            }

            Preference preference = oldBean.getPreference();
            if (preference.getModifiedTime().compareTo(currBean.getPreference().getModifiedTime()) < 0) {
                preference = currBean.getPreference();
            }

            DataBundleBean newBean = new DataBundleBean(
                    new TreeSet<>(oldBills.values()), new TreeSet<>(oldWorks.values()), new TreeSet<>(oldContacts.values()),
                    preference, deletedList);

            data = gson.toJson(newBean);
            upload(uuid, data);
            newBean.setDeletedList(new TreeSet<>());
            data = gson.toJson(newBean);
            return new Answer(OK, data);
        } catch (Exception e) {
            System.err.println(e.toString());
            return new Answer(ERROR, "");
        }
    }

    public String download(String uuid) {
        try {
            FileInputStream fileOutputStream = new FileInputStream(path + "/" + uuid);
            byte[] bytes = fileOutputStream.readAllBytes();
            fileOutputStream.close();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }
}
