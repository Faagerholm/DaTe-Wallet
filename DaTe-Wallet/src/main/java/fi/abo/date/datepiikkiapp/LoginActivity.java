package fi.abo.date.datepiikkiapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity{
    TextView txVUsername;
    TextView txVPassWord;
    protected Account account;

    //TODO: UPDATE to secure password...
    private List<Account> userDataBase = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txVPassWord = findViewById(R.id.passwordInput);

        updateUserDatabase();
    }
    public void login(View view) {
        txVUsername = findViewById(R.id.userNameInput);
        System.out.println("passing to account: " + txVUsername.getText().toString() + ", " + txVPassWord.getText().toString());
        Intent intent = new Intent(this,MainActivity.class);
        account = authorize(txVUsername.getText().toString(),txVPassWord.getText().toString());
        if(account != null) {
            intent.putExtra("account", account);
            startActivity(intent);
        }
        else Snackbar.make(findViewById(R.id.myCoordinatorLayout), "invalid username and password", Snackbar.LENGTH_LONG).show();
    }
    private Account authorize(String name,String password){
        for(Account a: userDataBase){
            //if superUsers contains account with matching username and password return true
            if(name.equals(a.getUsername()) && password.equals(a.getPassword())){
                return a;
            }
        }
        return null;
    }
    private void updateUserDatabase(){
        System.out.println("Init users");
        InputStream inputStream = getResources().openRawResource(R.raw.users);
        CSVFile csvFile = new CSVFile(inputStream);
        List tempUsers = csvFile.read();
        for(Object user:tempUsers){
            String name = ((String[]) user)[0];
            String password = ((String[]) user)[1];
            String type = ((String[]) user)[2];
            userDataBase.add(0,new Account(name,password,type));
        }
    }

    public void registerUser(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);

    }
}