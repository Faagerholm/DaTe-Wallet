package fi.abo.date.datepiikkiapp;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Jimmy on 2/10/2018.
 *
 * this class fetch different JSONs from server.
 */

public class fetchData {

    static public Account fetchServerData(final Account account, final String api_url, final String fetcher) throws InterruptedException {
        final JSONObject jsonObject = new JSONObject();
        final Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    HttpURLConnection connection;
                    if (fetcher.equals("accountDetails")) {
                        System.out.println(api_url + "?username=" + account.getUsername());
                        System.out.println("token: " + account.getToken());
                        JSONObject data;
                        URL url = new URL(api_url + "?username=" + account.getUsername());
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Content-Type", "charset=UTF-8");
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setDoOutput(false);
                        connection.setDoInput(true);
                        connection.setRequestProperty("Authorization", "Bearer " + account.getToken());
                        Log.i("JSON", jsonObject.toString());
                        int HttpResult = connection.getResponseCode();
                        if (HttpResult == HttpURLConnection.HTTP_OK) {
                            StringBuilder sb = new StringBuilder();
                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    connection.getInputStream(), "utf-8"));
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            br.close();
                            data = new JSONObject(sb.toString());
                            System.out.println("balance: " + data);
                                account.setBalance(data.getString("balance"));
                                account.setID(data.getString("id"));
                                account.setName(data.getString("name"));
                                account.setType(data.getInt("level"));
                        }

                        Log.i("STATUS", String.valueOf(connection.getResponseCode()));
                        Log.i("MSG",connection.getResponseMessage());
                        connection.disconnect();
                    }
                    if (fetcher.equals("authorize")) {

                        System.out.println("starting new thread...");
                        URL url = new URL(api_url);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "charset=UTF-8");
                        connection.setRequestProperty("Accept","application/json");
                        connection.setDoOutput(true);
                        connection.setDoInput(true);

                        System.out.println("Creating JSON object...");
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.accumulate("username", account.getUsername());
                        jsonObject.accumulate("password", account.getPassword());

                        Log.i("JSON",jsonObject.toString());
                        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                        dos.writeBytes(jsonObject.toString());
                        dos.flush();
                        dos.close();
                        int HttpResult =connection.getResponseCode();
                        JSONObject data = null;
                        if(HttpResult ==HttpURLConnection.HTTP_OK) {
                            StringBuilder sb = new StringBuilder();
                            BufferedReader br = new BufferedReader(new InputStreamReader(
                                    connection.getInputStream(), "utf-8"));
                            String line;
                            while ((line = br.readLine()) != null) {
                                sb.append(line + "\n");
                            }
                            br.close();
                            data = new JSONObject(sb.toString());
                            if(!data.getString("token").isEmpty()){
                                account.setToken(data.getString("token"));
                            }
                        }
                        Log.i("STATUS", String.valueOf(connection.getResponseCode()));
                        Log.i("MSG",connection.getResponseMessage());
                        connection.disconnect();
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
        return account;
    }
}
