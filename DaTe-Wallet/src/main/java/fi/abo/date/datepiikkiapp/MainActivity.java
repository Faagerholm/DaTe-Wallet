package fi.abo.date.datepiikkiapp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jimmy on 12/2/2017.
 *
 * This is the main window for the application
 */

public class MainActivity extends Activity {

    private TextView tvSaldoSum, tvUsername, tvUserInfo, tvHistorydate, tvHistoryLog;
    private ScrollView svHistory;
    private Button btnUsers,btnProducts;
    private List historyLog;
    private Account account;
    final private String API_GET_ACCOUNT = "http://37.59.100.46:8085/api/v1/account/get";

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        tvSaldoSum =  findViewById(R.id.saldoSum);
        svHistory =  findViewById(R.id.historyTransactions);

        account = getIntent().getParcelableExtra("account");
        btnUsers =  findViewById(R.id.btnUsers);
        btnProducts = findViewById(R.id.btnProducts);
        if(!account.getTyp().equals("ADMIN")){
            btnUsers.setVisibility(View.GONE);
            btnProducts.setVisibility(View.GONE);
        }

        //Scrollview state is saved by scrollView:id accessible with historyTransaction id.
        showPopup("Login successful");
        updateUserInfo();
        updateBalance(tvSaldoSum);
    }
    private void updateUserInfo() {
        tvUserInfo = findViewById(R.id.userInfoA);
        tvUsername = findViewById(R.id.adminUsername);
        try {
            tvUsername.setText(account.getUsername());
            //TODO: implement smart way to access Accounts
        } catch (NullPointerException e) {
            throw new RuntimeException("Error reading account username and password... continue");
        }
    }
    //TODO: add more secure way to access all data. now reads a csv-file saved in ./res/raw/data
    private void updateHistory(){
        tvHistorydate = findViewById(R.id.historyDate);
        tvHistoryLog = findViewById(R.id.historyLog);

        InputStream inputStream = getResources().openRawResource(R.raw.data);
        CSVFile csvFile = new CSVFile(inputStream);
        historyLog = csvFile.read();
        tvHistoryLog.setText("");
        tvHistorydate.setText("");
        for(Object logData:historyLog){
            tvHistorydate.append(((String[]) logData)[0]+ "\n");

            if(!((String[]) logData)[1].contains("-")) tvHistoryLog.append("+");
            float sum = Float.valueOf(((String[]) logData)[1]);
            tvHistoryLog.append(String.format("%.2f",sum) + "\n");
        }
    }
    //Updated balance when user clicks on the balance
    public void updateBalance(View view){
        try {
            account = fetchData.fetchServerData(account,API_GET_ACCOUNT,"balance");
            tvSaldoSum.setText(account.getBalance().substring(0,account.getBalance().length()-2) + "," + account.getBalance().substring(account.getBalance().length()-2,account.getBalance().length())+ " Cr");
        }catch (NullPointerException e){
            tvSaldoSum.setText("NULL");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void showPopup(String text){
        Snackbar.make(findViewById(R.id.myAdminCoordinatorLayout), text, Snackbar.LENGTH_LONG)
                .show();
    }
    /*
    *   ADMIN PANEL STUFF
    */


    //is run when Admin clicks Users
    public void showUsersList(View view){

        PopupMenu popup = new PopupMenu(this, view);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.dropdownlist,popup.getMenu());
        // custom inflater

        popup.show();
    }
}
