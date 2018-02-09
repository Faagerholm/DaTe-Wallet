package fi.abo.date.datepiikkiapp;
import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jimmy on 12/2/2017.
 */

public class MainActivity extends Activity {

    private TextView tvSaldoSum, tvUsername, tvUserInfo, tvHistorydate, tvHistoryLog;
    private ScrollView svHistory;
    private Button btnUsers,btnProducts;
    private List historyLog;
    private Account account;
    private ArrayList<Account> accountDatabase;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        tvSaldoSum =  findViewById(R.id.saldoSum);
        svHistory =  findViewById(R.id.historyTransactions);

        account = getIntent().getParcelableExtra("account");
        accountDatabase = getIntent().getParcelableArrayListExtra("database");
        btnUsers =  findViewById(R.id.btnUsers);
        btnProducts = findViewById(R.id.btnProducts);


        if(!account.getTyp().equals("ADMIN")){

            btnUsers.setVisibility(View.GONE);
            btnProducts.setVisibility(View.GONE);
        }

        //for debug purpose only
        for(Account a: accountDatabase){
            System.out.println(a.getUsername());
        }

        //Scrollview state is saved by scrollView:id accessible with historyTransaction id.
        showPopup("Login successful");
        historyLog = new ArrayList();
        updateUserInfo();
        updateHistory();
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
        tvSaldoSum = findViewById(R.id.saldoSum);
        float floatSum = 0;
        for(Object log: historyLog){
            floatSum += Float.parseFloat(((String[]) log)[1]);
        }
        tvSaldoSum.setText(String.format("%.2f",floatSum));
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
