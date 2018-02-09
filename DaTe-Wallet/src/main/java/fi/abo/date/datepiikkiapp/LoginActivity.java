package fi.abo.date.datepiikkiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity{

    //v1
    private String authenticationURL = "http://37.59.100.46:8085/api/v1/account_login";

    TextView txVUsername;
    TextView txVPassWord;
    protected Account account;
    static final int ADD_NEW_USER_REQUEST = 1; //the code that returns when RegisterActivity is closed.

    //TODO: UPDATE to secure password...
    public ArrayList<Account> userDataBase = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //get reference to the view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txVPassWord = findViewById(R.id.passwordInput);
        Button btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setVisibility(View.GONE);
    }
    public void login(View view) {

        txVUsername = findViewById(R.id.userNameInput);
        Intent intent = new Intent(this,MainActivity.class);
        account = new Account(txVUsername.getText().toString(),txVPassWord.getText().toString());
        authorize(account);
        if(account != null) {
            //intent.putExtra("account", account);
            //intent.putParcelableArrayListExtra("database",userDataBase);
            //startActivity(intent);
        }
        else showPopup("Invalid username and password");}

    //TODO: add way to authorize user
    private void authorize(final Account account){

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println("starting new thread...");
                    URL url = new URL(authenticationURL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "charset=UTF-8");
                    connection.setRequestProperty("Accept","application/json");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);

                    System.out.println("Creating JSON object...");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("user", account.getUsername());
                    jsonObject.accumulate("password", account.getPassword());

                    Log.i("JSON",jsonObject.toString());
                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                    //dos.writeBytes("{\"user\": \"jifagerh\",\"password\": \"jimmy123\"}");
                    dos.writeBytes(jsonObject.toString());
                    dos.flush();
                    dos.close();

                    Log.i("STATUS", String.valueOf(connection.getResponseCode()));
                    Log.i("MSG",connection.getResponseMessage());
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    showPopup("login failed.");
                    e.printStackTrace();
                } catch (IOException e){
                    showPopup("login failed. IOException");
                    e.printStackTrace();
                } catch (JSONException e1) {
                    showPopup("error reading JSON");
                    e1.printStackTrace();
                }
            }
        });
        thread.start();
    }
    /*  run by clicking on register User button
        will be disabled for now
    */
    public void registerUser(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivityForResult(intent,ADD_NEW_USER_REQUEST);
    }

    //for easier snackbar popup handling
    private void showPopup(String text){
        Snackbar.make(findViewById(R.id.myCoordinatorLayout), text, Snackbar.LENGTH_LONG).show();
    }

    //returning from registerUser
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == ADD_NEW_USER_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Account tempAccount = data.getParcelableExtra("newAccount");
                for(Account a: userDataBase){
                    System.out.println("username: " + a.getUsername() + " password: " + a.getPassword() + " type: " + a.getTyp());
                }
            }
        }
    }
}