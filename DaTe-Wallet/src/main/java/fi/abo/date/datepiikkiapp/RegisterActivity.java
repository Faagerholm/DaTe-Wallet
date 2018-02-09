package fi.abo.date.datepiikkiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jimmy on 12/10/2017.
 *
 * Registration
 */

public class RegisterActivity extends AppCompatActivity{
/*
    List<Account> newUser = new LinkedList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void SendRequest(View view) {

        final EditText etUsername = findViewById(R.id.etUsername);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etName = findViewById(R.id.etFullname);
        final EditText etPassword = findViewById(R.id.etPassword);
        EditText etPasswordCheck = findViewById(R.id.etReenterpassword);
        CheckBox cbMember = findViewById(R.id.cbMember);

        //check if all fields are filled correctly
        try {
            boolean correctInput = true;
            if (!etEmail.getText().toString().contains("@abo.fi")) {
                showPopup("Invalid E-mail entered");
                correctInput = false;
            }
            if (etPassword.getText().toString().length() < 5) {
                showPopup("Password must be at least 5 characters long");
                correctInput = false;
            }
            if (!etPassword.getText().toString().equals(etPasswordCheck.getText().toString())) {
                showPopup("Passwords didn't match");
                correctInput = false;
            }
            if (!cbMember.isChecked()) {
                showPopup("Please verify your DaTe membership");
                correctInput = false;
            }
            if(correctInput) {
                final Account account = new Account(etUsername.getText().toString(), etPassword.getText().toString(),"NORMAL");
                account.setName(etName.getText().toString());
                account.setMail(etEmail.getText().toString());
                account.setType("NEW");

                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Please confirm your registration");
                builder.setMessage("Your request will be sent for admins to confirm.\nAre you sure the entered information is correct.");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {

                        /*
                            Creates an intent with data on the Account just created.
                            will be sent back when intent closes.

                        */
/*                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent retData = new Intent();
                                retData.putExtra("newAccount",account);
                                RegisterActivity.super.setResult(RESULT_OK,retData);
                                RegisterActivity.super.finish();
                            }
                        });
                builder.setNegativeButton("I'm not sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }catch (NullPointerException e){
            throw new RuntimeException("Something is null in your register!");
        }
    }
    private void showPopup(String text){
        Snackbar.make(findViewById(R.id.registerRelativeLayout), text, Snackbar.LENGTH_LONG)
                .show();
    }
*/
}

