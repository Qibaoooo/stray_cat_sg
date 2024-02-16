package nus.iss.sa57.csc_android.utils;

import android.content.Context;
import android.widget.Toast;

public class MessageHelper {
    public static void showErrMessage(Context context){
        Toast.makeText(context, "Something Went Wrong, Please Try Again", Toast.LENGTH_SHORT).show();
    }
}
