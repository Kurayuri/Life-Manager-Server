package com.trashparadise.lifemanagerserver.bean;

import java.util.TreeSet;

public class DataBundleBean {
    private TreeSet<Bill> billList;
    private TreeSet<Work> workList;
    private TreeSet<Contact> contactList;
    private Preference preference;
    private TreeSet<String> deletedList;

    public TreeSet<String> getDeletedList() {
        return deletedList;
    }

    public void setDeletedList(TreeSet<String> deletedList) {
        this.deletedList = deletedList;
    }

    public DataBundleBean(TreeSet<Bill> billList, TreeSet<Work> workList, TreeSet<Contact> contactList, Preference preference, TreeSet<String> deletedList) {
        this.billList = billList;
        this.workList = workList;
        this.contactList = contactList;
        this.preference = preference;
        this.deletedList = deletedList;
    }

    public TreeSet<Bill> getBillList() {
        return billList;
    }

    public void setBillList(TreeSet<Bill> billList) {
        this.billList = billList;
    }

    public TreeSet<Work> getWorkList() {
        return workList;
    }

    public void setWorkList(TreeSet<Work> workList) {
        this.workList = workList;
    }

    public TreeSet<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(TreeSet<Contact> contactList) {
        this.contactList = contactList;
    }

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }
}
