package com.trashparadise.lifemanagerserver.bean;

import java.io.Serializable;
import java.util.Calendar;

public class Contact implements Serializable, Comparable<Contact> {
    private String uuid;
    private String remarkName;
    private String contactUuid;
    private Calendar modifiedTime;

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getContactUuid() {
        return contactUuid;
    }

    public void setContactUuid(String contactUuid) {
        this.contactUuid = contactUuid;
    }

    public Calendar getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Calendar modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public void onModify() {
        this.modifiedTime = Calendar.getInstance();
    }

    public Contact(String remarkName, String contactUuid) {
        this.remarkName = remarkName;
        this.contactUuid = contactUuid;
        this.modifiedTime = Calendar.getInstance();
        this.uuid = java.util.UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public int compareTo(Contact o) {
        return o.getContactUuid().compareTo(this.getContactUuid());
    }
}
