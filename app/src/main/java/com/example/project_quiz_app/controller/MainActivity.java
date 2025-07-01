package com.example.project_quiz_app.controller;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.project_quiz_app.R;
import com.example.project_quiz_app.view.fragments.CollectionFragment;
import com.example.project_quiz_app.view.fragments.CreateFragment;
import com.example.project_quiz_app.view.fragments.HomeFragment;
import com.example.project_quiz_app.view.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    //Khoi tao fragment
    HomeFragment homeFragment;

    private void bindingView(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
    }
    private void bindingAction(){
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }
    // viết hàm xử ly khi chon item
    private boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, homeFragment).commit();
            return true;
        }
        if (id == R.id.nav_create) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new CreateFragment()).commit();
            return true;
        }
        if (id == R.id.nav_collection) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new CollectionFragment()).commit();
            return true;
        }
        if (id == R.id.nav_profile) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, new ProfileFragment()).commit();
            return true;
        }

        return false;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, homeFragment).commit();
    }
}
