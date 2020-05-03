package com.example.whatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private chatFragment mChatFragment;
    private callsFragment mCallsFragment;
    private statusFragment mStatusFragment;
    private fragAdapter mFragAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findview();
        fragmentView();
        setViewPager();
        setUpToolbar();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("home");

        }
    }


    private void setViewPager() {
        mFragAdapter.addFragment(mCallsFragment, "Calls");
        mFragAdapter.addFragment(mStatusFragment, "Status");
        mFragAdapter.addFragment(mChatFragment, "Chat");
        mViewPager.setAdapter(mFragAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void fragmentView() {
        mCallsFragment = new callsFragment();
        mChatFragment = new chatFragment();
        mStatusFragment = new statusFragment();
        mFragAdapter = new fragAdapter(getSupportFragmentManager());

    }


    private void findview() {
        mTabLayout = findViewById(R.id.tabLayot);
        mViewPager = findViewById(R.id.view_pager);
        mToolbar = findViewById(R.id.main_toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean isClicked = false;
        switch (item.getItemId()) {

            case R.id.search_menu:
                toast("search clicked");
                isClicked = true;

                break;
            case R.id.logout_menu:
                toast("Logout Successfully");
                intend(splash_screen.class);
                //for signout
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                isClicked = true;
                break;
            case R.id.setting_menu:
                isClicked = true;
                toast("setting clicked");
                break;
            case R.id.account_menu:
                intend(my_account.class);
                isClicked = true;
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void intend(Class active) {
        Intent intent = new Intent(MainActivity.this, active);
        startActivity(intent);
        finish();

    }
}
