package com.passwordmanager.handler;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class BaseStorageHandler {
    private static final String TAG = "BaseStorageHandler";
    private String fileName;
    private Context mContext;

    BaseStorageHandler(Context context, String fileName) {
        mContext = context;
        this.fileName = fileName;
    }

    /**
     * get saved password from device storage in string form
     *
     * @return string
     */
    String getData() {
        StringBuilder str = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(mContext.openFileInput(fileName));
            int i;
            while ((i = reader.read()) != -1) {
                str.append((char) i);
                // Log.d(TAG, String.format("loadData: i=%s (char) i=%s", i, str));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


    /**
     * save data to device storage
     *
     * @param data {@link String}
     * @return boolean
     */
    boolean saveData(String data) {
        boolean saved = false;
        try {
            FileOutputStream fos = mContext.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
            // Log.d(TAG, "file created: " + fileName);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            saved = true;
        } catch (Exception e) {
            Log.d(TAG, "error: ");
            e.printStackTrace();
        }
        return saved;
    }

}
