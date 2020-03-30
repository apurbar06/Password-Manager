package com.passwordmanager.handler;

import android.content.Context;

public class MasterPasswordHandler extends BaseStorageHandler {
    public MasterPasswordHandler(Context context) {
        super(context, "master.txt");
    }

    public boolean hasPassword() {
        String password = getData();
        return password.length() > 0;
    }

    public void setPassword(String password) {
        saveData(password);
    }

    public boolean verifyPassword(String password) {
        String storedPassword = getData();
        return storedPassword.equals(password);
    }
}
