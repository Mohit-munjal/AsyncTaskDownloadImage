package munjal.mohit.asynctaskdownloadimage;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dell on 1/8/2017.
 */

public class MyTask extends AsyncTask<String,Integer,Boolean> {
    int contentLength = -1;
    int counter = 0;
    int calculatedProgress = 0;

    Activity activity;
    public MyTask(Activity activity)
    {
        onAttach(activity);
    }


    public void onAttach(Activity activity) {
        this.activity = activity;
    }

    public void onDetch()
    {
        activity=null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(activity!=null)
            ((MainActivity)activity).showProgressBarBeforeDownloading();

    }

    @Override
    protected Boolean doInBackground(String... strings) {
        boolean success=false;
        URL url=null;
        HttpURLConnection connection=null;
        InputStream inputStream=null;
        File file=null;
        FileOutputStream fileOutputStream=null;
        try {
            url=new URL(strings[0]);
            connection= (HttpURLConnection) url.openConnection();
            contentLength=connection.getContentLength();
            inputStream=connection.getInputStream();
            file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+ Uri.parse(strings[0]).getLastPathSegment());
            fileOutputStream=new FileOutputStream(file);
            byte[] buffer=new byte[1024];
            int read=-1;
            while((read=inputStream.read(buffer))!=-1)
            {
                fileOutputStream.write(buffer,0,read);
                counter=counter+read;
                publishProgress(counter);

            }
            success=true;
        } catch (MalformedURLException e) {
            L.s(activity,e+"");
        } catch (IOException e) {
            L.s(activity,e+"");
        }
        finally {
            if(connection!=null)
            {
                connection.disconnect();
            }
            if(inputStream!=null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    L.s(activity,e+"");
                }
            }
            if(fileOutputStream!=null)
            {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    L.s(activity,e+"");
                }
            }
        }


        return success;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(activity==null)
        {
            L.m("skipping progress update since activity is null");
        }
        else
        {
            calculatedProgress=(int)(((double)values[0]/contentLength)*100);
            ((MainActivity)activity).updateProgress(calculatedProgress);
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        ((MainActivity)activity).hideProgressBarAfterDownloading();
        if(aBoolean==true)
            L.s(activity,"Image has been successfully downloaded");
    }
}
