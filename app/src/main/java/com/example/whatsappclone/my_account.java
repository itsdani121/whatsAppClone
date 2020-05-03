package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class my_account extends AppCompatActivity {
    private EditText mName, mCity, mAddress;
    private Button mUpdate;
    private database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        initlize();
        initRef();
        click();
    }

    private void initlize() {
        mDatabase = database.getInstance();
    }

    private void click() {
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isempty(mName, mCity, mAddress) == false) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {


                        String userid = user.getUid();
                        String emailAdd= user.getEmail();
                        String name = mName.getText().toString().trim();
                        String city = mCity.getText().toString().trim();
                        String address = mAddress.getText().toString().trim();

                        account_details accountDetails = new account_details();
                        accountDetails.setUserId(userid);
                        accountDetails.setmName(name);
                        accountDetails.setEmail(emailAdd);
                        accountDetails.setmCity(city);
                        accountDetails.setmAddress(address);
                        mDatabase.createAccount(userid, accountDetails, new databasecallback() {
                            @Override
                            public void onsuccessfull(String msg) {
                                Toast.makeText(my_account.this, "database created", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onfailed(String msg) {
                                Toast.makeText(my_account.this, "create failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });


    }

    private boolean isempty(EditText mName, EditText mCity, EditText mAddress) {
        String myName = mName.getText().toString().trim();
        String myCity = mCity.getText().toString().trim();
        String myAddress = mAddress.getText().toString().trim();
        if (TextUtils.isEmpty(myName)) {
            mName.setError("This field is required");
            return true;
        }
        if (TextUtils.isEmpty(myCity)) {
            mName.setError("This field is required");
            return true;
        }
        if (TextUtils.isEmpty(myAddress)) {
            mName.setError("This field is required");
            return true;
        } else {
            return false;
        }
    }

    private void initRef() {
        mName = findViewById(R.id.account_name);
        mCity = findViewById(R.id.account_city);
        mAddress = findViewById(R.id.account_address);
        mUpdate = findViewById(R.id.account_update);

    }


}
