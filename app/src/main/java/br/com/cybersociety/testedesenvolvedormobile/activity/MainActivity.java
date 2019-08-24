package br.com.cybersociety.testedesenvolvedormobile.activity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.fragment.MovieFragment;
import br.com.cybersociety.testedesenvolvedormobile.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navView;

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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_change_layout:
                mf.changeLayout();
                break;
            case R.id.menu_search:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

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

        pf = new ProfileFragment();
        mf = new MovieFragment();

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, mf);
        transaction.commit();
    }

    /**
     * Mantém a lógica de mudança entre os fragments
     *
     * @param fragment
     */
    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}
