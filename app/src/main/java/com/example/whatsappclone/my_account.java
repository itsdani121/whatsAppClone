package com.example.whatsappclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.List;

public class my_account extends AppCompatActivity {
    private EditText mName, mCity, mAddress;
    private ImageView mImage;
    private Button mUpdate;
    private String userId, imagePath;
    private database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        initlize();
        initRef();
        click();
        fetchProfile();
    }


    private void initlize() {
        mDatabase = database.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userid = user.getUid();
        } else {
            Intent intent = new Intent(my_account.this, splash_screen.class);
            startActivity(intent);
            finish();
        }
    }

    private void click() {
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isempty(mName, mCity, mAddress)) {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {


                        String emailAdd = user.getEmail();
                        String name = mName.getText().toString().trim();
                        String city = mCity.getText().toString().trim();
                        String address = mAddress.getText().toString().trim();

                        account_details accountDetails = new account_details();
                        accountDetails.setUserId(userId);
                        accountDetails.setmName(name);
                        accountDetails.setEmail(emailAdd);
                        accountDetails.setmCity(city);
                        accountDetails.setImageLocalPath(imagePath);
                        accountDetails.setmAddress(address);
                        mDatabase.createAccount("profile", userId, accountDetails, new databasecallback() {
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
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages();
            }
        });

    }

    private boolean isempty(EditText... fields) {


        for (EditText field : fields) {
            String value = field.getText().toString().trim();
            if (TextUtils.isEmpty(value)) {
                field.setError("This field is required");
                return true;
            }
        }
        return false;
    }

    private void initRef() {
        mName = findViewById(R.id.account_name);
        mCity = findViewById(R.id.account_city);
        mAddress = findViewById(R.id.account_address);
        mUpdate = findViewById(R.id.account_update);
        mImage = findViewById(R.id.account_image);
    }


    private void fetchProfile() {
              mDatabase.getDateSingle("profile", userId, new databasefetchSingle() {
                @Override
                public void onGetDataSuccessful(DataSnapshot dataSnapshot, String msg) {
                    if (dataSnapshot.exists()) {
                        Toast.makeText(my_account.this, "data get it", Toast.LENGTH_SHORT).show();
                        account_details details = dataSnapshot.getValue(account_details.class);
                        if (details != null) {
                            mName.setText(details.getmName());
                            mCity.setText(details.getmCity());
                            mAddress.setText(details.getmAddress());

                        }
                    }
                }

                @Override
                public void onGetDataFailed(String error) {

                }
            });


    }

    private void getImages() {

        new ImagePicker.Builder(this)
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG)
                .scale(600, 600)
                .allowMultipleImages(true)
                .enableDebuggingMode(true)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
            //Your Code
            if (mPaths != null) {
                String image = mPaths.get(0);
                mImage.setImageURI(Uri.parse(image));
                uploadImage(image);
            }
        }
    }

    private void uploadImage(String image) {
        String filename = userId + "jpg";
        mDatabase.upload("pofile", filename, image, new fileuploadcallback() {
            @Override
            public void onfileuploadsuccess(String msg) {
                Toast.makeText(my_account.this, "image sucessfull", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onfileuploadfailed(String error) {
                Toast.makeText(my_account.this, "image failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
