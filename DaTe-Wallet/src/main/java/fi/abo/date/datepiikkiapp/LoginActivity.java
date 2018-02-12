package fi.abo.date.datepiikkiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity{

    //API v1
    final private String API_AUTHENTICATE_USER = "http://37.59.100.46:8085/api/v1/account_login";
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
        Intent intent = new Intent(this, MainActivity.class);
        account = new Account(txVUsername.getText().toString(), txVPassWord.getText().toString());
        authorize(account);
        if (!account.getToken().isEmpty()) {
            intent.putExtra("account", account);
            startActivity(intent);
        }
        else{
            showPopup("Wrong username or password..");
        }
    }
    private void authorize(final Account account){
        try{
            fetchData.fetchServerData(account,API_AUTHENTICATE_USER,"authorize");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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