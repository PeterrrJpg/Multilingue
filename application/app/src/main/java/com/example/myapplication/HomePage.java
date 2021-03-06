package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    private FirebaseUser mUser;

    private Button btLogOut;
    private Button btAdd;
    private TextView tvUsername;

    private RecyclerView languageRecyclerview;
    private LanguageAdapter lAdapter;
    private RecyclerView.LayoutManager lLayoutManager;

    private RecyclerView courseRecyclerview;
    private CourseAdapter cAdapter;
    private RecyclerView.LayoutManager cLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.Leaderboard:
                        startActivity(new Intent(getApplicationContext(),LeaderboardPage.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Home:
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(),ProfilePage.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        tvUsername = findViewById(R.id.tvHomeUsername);
        tvUsername.setText(mUser.getDisplayName());

        languageRecyclerview = findViewById(R.id.rvLanguage);
        languageRecyclerview.setHasFixedSize(true);
        lLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        languageRecyclerview.setLayoutManager(lLayoutManager);
        LanguageAdapter.Listener listener = new LanguageAdapter.Listener() {
            @Override
            public void onClick(View view, String name) {
                if (name.equals("Ngunnawal")) {
                    launchNgunnawalInfoPage();
                } else if (name.equals("Ngarigo")) {
                    launchNgarigoInfoPage();
                }
            }
        };

        lAdapter = new LanguageAdapter(new ArrayList<>(), listener);
        languageRecyclerview.setAdapter(lAdapter);
        lAdapter.setData(Language.getLanguages());

        courseRecyclerview = findViewById(R.id.rvCourse);
        courseRecyclerview.setHasFixedSize(true);
        cLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        courseRecyclerview.setLayoutManager(cLayoutManager);
        CourseAdapter.Listener listener1 = new CourseAdapter.Listener() {
            @Override
            public void onClick(View view, String language) {
                if (language.equals("Ngunnawal")) {
                    launchNgunnawalQuizPage();
                } else if (language.equals("Ngarigo")) {
                    launchNgarigoQuizPage();
                } else if (language.equals("ALL")) {
                    launchAllQuizPage();
                }
            }
        };

        cAdapter = new CourseAdapter(new ArrayList<>(), listener1);
        courseRecyclerview.setAdapter(cAdapter);
        cAdapter.setData(Course.getCourses());

        btLogOut = findViewById(R.id.btLogOut);
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(getApplicationContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                launchAuthPage();
                            }
                        });
            }
        });

        btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchAddPage();
            }
        });
    }

    private void launchAuthPage() {
        Intent intent = new Intent(HomePage.this, AuthPage.class);
        startActivity(intent);
    }

    private void launchNgunnawalInfoPage() {
        Intent intent = new Intent(HomePage.this, NgunnawalInfoPage.class);
        startActivity(intent);
    }

    private void launchNgarigoInfoPage() {
        Intent intent = new Intent(HomePage.this, NgarigoInfoPage.class);
        startActivity(intent);
    }

    private void launchAllQuizPage() {
        Intent intent = new Intent(HomePage.this, AllQuizPage.class);
        startActivity(intent);
    }

    private void launchNgunnawalQuizPage() {
        Intent intent = new Intent(HomePage.this, NgunnawalQuizPage.class);
        startActivity(intent);
    }

    private void launchNgarigoQuizPage() {
        Intent intent = new Intent(HomePage.this, NgarigoQuizPage.class);
        startActivity(intent);
    }

    private void launchAddPage() {
        Intent intent = new Intent(HomePage.this, AddNewLanWordPage.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
        super.onBackPressed();
    }
}