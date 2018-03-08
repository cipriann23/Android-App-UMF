package com.example.nemto.umfadmitere;

/**
 * Created by nemto on 02.08.2017.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundWorker extends AsyncTask<String, String, String[]> {


    Context context;
    AlertDialog alertDialog;

    BackgroundWorker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String[] doInBackground(String... params) {
        //String type = params[0];


        try {

            String year = params[0];
            String category = params[1];
            String random_url = "http://webserviceandroid.tk?year="+year+"&category="+category;

            URL url = new URL(random_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            /*
            // set params
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("year","UTF-8")+"="+URLEncoder.encode(year,"UTF-8")+"&"
                    +URLEncoder.encode("category","UTF-8")+"="+URLEncoder.encode(category,"UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            */

            // get result
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String[] result = null;
            String line = "";
            String[] broken_text = null;

            while ((line = bufferedReader.readLine()) != null) {
                result = line.split("      ");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            //result[0]=post_data;

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Random generate");
    }

    @Override
    protected void onPostExecute(String[] result) {
        //alertDialog.setMessage(result[3]);
        //alertDialog.show();
    }


    /*
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    */
}

