package com.example.travelshare.library;

import android.content.Context;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.util.List;
import java.util.Map;

public class Cloudinary {

    private Context context;


    public Cloudinary(Context context) {
        this.context = context;
        //    MediaManager.init(this.context);
    }

    public void uploadFile(String filePath, String key) {
        String requestId = MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
                // your code here
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
                // example code starts here
                Double progress = (double) bytes / totalBytes;
                // post progress to app UI (e.g. progress bar, notification)
                // example code ends here
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {

            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
                // your code here
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                // your code here
            }
        })
                .dispatch();
    }
}
