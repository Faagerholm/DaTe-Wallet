package fi.abo.date.datepiikkiapp;
import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy on 12/2/2017.
 */

public class MainActivity extends Activity {

    private TextView tvSaldoSum, tvUsername, tvUserInfo, tvHistorydate, tvHistoryLog;
    private ScrollView svHistory;
    private Button btnCheckBalance,btnAddBalance,btnEditUser,btnEditProduct;
    private List historyLog;
    private Account account;

    private PopupWindow popupWindow;
    private LinearLayout mainLayout, containerLayout;
    private LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        tvSaldoSum =  findViewById(R.id.saldoSum);
        svHistory =  findViewById(R.id.historyTransactions);

        account = getIntent().getParcelableExtra("account");

        btnCheckBalance = (Button) findViewById(R.id.btnCheckBalance);
        btnAddBalance = (Button) findViewById(R.id.btnEditBalance);
        btnEditUser = (Button) findViewById(R.id.btnEditUser);
        btnEditProduct = (Button) findViewById(R.id.btnEditProduct);


        if(!account.getTyp().equals("ADMIN")){

            btnCheckBalance.setVisibility(View.GONE);
            btnAddBalance.setVisibility(View.GONE);
            btnEditUser.setVisibility(View.GONE);
            btnEditProduct.setVisibility(View.GONE);
        }else {


            containerLayout = new LinearLayout(this);
            mainLayout = new LinearLayout(this);
            popupWindow = new PopupWindow(this);


            btnAddBalance = new Button(this);
            btnAddBalance.setText("Click Here For Pop Up Window !!!");
            btnAddBalance.setOnClickListener(new View.OnClickListener() {

                Boolean isClicked = true;
                public void onClick(View v) {
                    if (isClicked) {
                        isClicked = false;
                        popupWindow.showAtLocation(mainLayout, Gravity.BOTTOM, 10, 10);
                        popupWindow.update(50, 50, 320, 90);
                    } else {
                        isClicked = true;
                        popupWindow.dismiss();
                    }
                }

            });

            TextView tvMsg = new TextView(this);
            tvMsg.setText("Hi this is pop up window...");

            layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            containerLayout.setOrientation(LinearLayout.VERTICAL);
            containerLayout.addView(tvMsg, layoutParams);
            popupWindow.setContentView(containerLayout);
            mainLayout.addView(btnAddBalance,layoutParams);
            setContentView(mainLayout);


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


    //is run when Admin clicks on Add balance to user
    public void addUserBalance(View view){

    }
}
