package com.example.travelshare.library;

import android.app.ActionBar;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;




import java.util.Map;

public class Cloudinary {

    private Context context;


    public Cloudinary(Context context){
        this.context=context;
    //    MediaManager.init(this.context);
    }

    public String uploadFile(String filePath){
        return "";
                //MediaManager.get().upload(filePath).startNow(this.context);
    }

    public String getURLFromFile(String imageName){
        return "";
                //(String) MediaManager.get().url().generate(imageName);
    }
}
