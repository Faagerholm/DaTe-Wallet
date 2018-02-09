package fi.abo.date.datepiikkiapp;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jimmy on 2/9/2018.
 */

public class Authorize extends AsyncTask<String, Void, String>{

    @Override
    protected String doInBackground(String ... params){

        String data = "";

        HttpsURLConnection httpsURLConnection = null;
        try{
                httpsURLConnection = (HttpsURLConnection) new URL(params[0]).openConnection();
                httpsURLConnection.setRequestMethod("POST");

                httpsURLConnection.setDoInput(true);

            DataOutputStream wr = new DataOutputStream(httpsURLConnection.getOutputStream());
            wr.writeBytes("Post data=" + params[1]);
            wr.flush();
            wr.close();

            InputStream in = httpsURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data += current;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(httpsURLConnection != null){
                httpsURLConnection.disconnect();
            }
        }
        return data;
    }
}