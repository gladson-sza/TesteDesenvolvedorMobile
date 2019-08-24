package br.com.cybersociety.testedesenvolvedormobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.prefs.Preferences;

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.fragment.MovieFragment;
import br.com.cybersociety.testedesenvolvedormobile.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;

    private String name;

    private MovieFragment mf;
    private ProfileFragment pf;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_movies:
                    changeFragment(mf);
                    return true;
                case R.id.navigation_profile:
                    changeFragment(pf);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * Inicializa e configura os componentes.
     */
    private void init() {


        SharedPreferences p = getSharedPreferences("NAME_PREFERENCE",MODE_PRIVATE);
        name = p.getString("name", "");

        pf = new ProfileFragment();
        mf = new MovieFragment();

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (name == null || name.isEmpty()) {
            transaction.add(R.id.frameLayout, pf);
            navView.setSelectedItemId(R.id.navigation_profile);
        }
        else transaction.add(R.id.frameLayout, mf);

        transaction.commit();
    }

    public void hideNavigation() {
        navView.setVisibility(View.GONE);
    }

    public void showNavigation() {
        navView.setVisibility(View.VISIBLE);
    }

    /**
     * Mantém a lógica de mudança entre os fragments
     *
     * @param fragment
     */
    public void changeFragment(Fragment fragment) {

        if (name == null || name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Você não informou o seu nome", Toast.LENGTH_SHORT).show();
        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frameLayout, fragment);
            transaction.commit();
        }


    }

}
