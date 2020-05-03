package com.example.whatsappclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build());

    private static final int RC_SIGN_IN = 3434;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loading();
    }



    private void authUser() {
        Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build();
        startActivityForResult(intent, RC_SIGN_IN);

    }
    public void loading(){


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            move();
        } else {
            authUser();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {
                IdpResponse response = IdpResponse.fromResultIntent(data);

                if (response != null) {
                    Toast.makeText(LoginActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                    move();


                } else {
                    Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }



        }
    }


    private void move() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
