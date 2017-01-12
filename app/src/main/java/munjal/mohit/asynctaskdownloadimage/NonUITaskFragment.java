package munjal.mohit.asynctaskdownloadimage;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;


/**
 * Created by dell on 1/8/2017.
 */

public class NonUITaskFragment extends android.support.v4.app.Fragment {
    Activity activity;
    MyTask myTask;
    public void beginTask(String... arguments)
    {
        myTask=new MyTask(activity);
        myTask.execute(arguments[0]);
    }

    @Override

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
        if(myTask!=null) {
            myTask.onAttach(activity);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(myTask!=null)
        myTask.onDetch();
    }
}
