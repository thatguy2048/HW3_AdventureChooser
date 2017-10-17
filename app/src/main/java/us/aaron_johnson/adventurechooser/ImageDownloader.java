package us.aaron_johnson.adventurechooser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by combu on 10/16/2017.
 */

public class ImageDownloader extends AsyncTask<ImageDownloaderPrameters, Void, ImageDownloaderPrameters[]>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("AC_IMAGE_DOWNLOADER","onPreExecute");
    }

    @Override
    protected void onPostExecute(ImageDownloaderPrameters[] params) {
        Log.d("AC_IMAGE_DOWNLOADER","ON_POST");
        super.onPostExecute(params);
        for(int i = 0; i < params.length; ++i) {
            if (params[i].btmp != null) {
                params[i].setImageFromBitmap();
            } else {
                Log.e("AC_IMAGE_DOWNLOADER", "Failed to get image");
            }
        }
    }

    InputStream getStream(String urlString) throws IOException{
        Log.d("AC_IMAGE_DOWNLOADER", "GetStream: "+urlString);
        InputStream in = new java.net.URL(urlString).openStream();
        if(in == null){
            Log.d("AC_IMAGE_DOWNLOADER", "Stream Is Null");
        }
        return in;
    }



    @Override
    protected ImageDownloaderPrameters[] doInBackground(ImageDownloaderPrameters... parameters){
        Log.d("AC_IMAGE_DOWNLOADER","doInBackground");

        for(int i = 0; i < parameters.length; ++i){
            try{
                InputStream in = getStream(parameters[i].imageUrl);
                if(in != null){
                    parameters[i].btmp = BitmapFactory.decodeStream(in);
                }else{
                    parameters[i].btmp = null;
                    Log.d("AC_IMAGE_DOWNLOADER", "Input stream is null");
                }
            }catch (Exception e){
                Log.d("AC_IMAGE_DOWNLOADER","Exception Downloading Image: "+e.toString());
            }
        }
        Log.d("AC_IMAGE_DOWNLOADER","doInBackground finished");
        return parameters;
    }

}

