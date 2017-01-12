package munjal.mohit.asynctaskdownloadimage;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText editText;
    ListView listView;
    ProgressBar progressBar;
    String[] listOfImages;
    NonUITaskFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this layout has been linked
        setContentView(R.layout.activity_main);
        editText= (EditText) findViewById(R.id.editText);
        listView= (ListView) findViewById(R.id.listView);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        listOfImages=getResources().getStringArray(R.array.imageUrls);
        listView.setOnItemClickListener(this);
        if(savedInstanceState==null)
        {
            fragment=new NonUITaskFragment();
            getSupportFragmentManager().beginTransaction().add(fragment,"MyTaskFragment").commit();
        }
        else
        {
            fragment= (NonUITaskFragment) getSupportFragmentManager().findFragmentByTag("MyTaskFragment");
        }
        if(fragment!=null)
        {
            if(fragment.myTask!=null && fragment.myTask.getStatus()==AsyncTask.Status.RUNNING)
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        }

    }
    public void downloadImage(View view)
    {
        if(editText.getText().toString()!=null && editText.getText().toString().length()>0)
        {

            fragment.beginTask(editText.getText().toString());
        }


    }
    public void updateProgress(int progress)
    {
        progressBar.setProgress(progress);
    }

    public void showProgressBarBeforeDownloading()
    {
        if(fragment.myTask!=null )
        {
            if(progressBar.getVisibility()!=View.VISIBLE && progressBar.getProgress()!=progressBar.getMax())
            {
                progressBar.setVisibility(View.VISIBLE);
            }

        }
    }
    public void hideProgressBarAfterDownloading()
    {
        if(fragment.myTask!=null)
        {
            if(progressBar.getVisibility()==View.VISIBLE)
            {
                progressBar.setVisibility(View.GONE);
            }
        }
    }







    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        editText.setText(listOfImages[i]);
    }
}
