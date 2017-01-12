package munjal.mohit.asynctaskdownloadimage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dell on 1/5/2017.
 */

public class L {
    public static void m(String message)
    {
        Log.e("mohit",message);
    }
    public static void s(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
