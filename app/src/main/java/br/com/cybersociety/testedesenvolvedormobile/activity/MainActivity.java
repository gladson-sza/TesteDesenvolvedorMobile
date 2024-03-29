package br.com.cybersociety.testedesenvolvedormobile.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
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

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.fragment.MovieFragment;
import br.com.cybersociety.testedesenvolvedormobile.fragment.ProfileFragment;
import br.com.cybersociety.testedesenvolvedormobile.helper.Permissions;
import br.com.cybersociety.testedesenvolvedormobile.model.dao.UserDAO;
import br.com.cybersociety.testedesenvolvedormobile.model.entities.User;

public class MainActivity extends AppCompatActivity {

    private String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    private BottomNavigationView navView;

    private MovieFragment mf;
    private ProfileFragment pf;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_movies: {
                    UserDAO userDAO = new UserDAO(getApplicationContext());
                    User user = userDAO.getUser();

                    if (user.getName() == null || user.getName().isEmpty()) {
                        Toast.makeText(MainActivity.this, "Você ainda não salvou seu nome", Toast.LENGTH_SHORT).show();
                        navView.setSelectedItemId(R.id.navigation_profile);
                    } else {
                        changeFragment(mf);
                        getSupportActionBar().setTitle("Filmes Populares");
                    }

                    return true;
                }
                case R.id.navigation_profile:
                    changeFragment(pf);
                    getSupportActionBar().setTitle("Meu Perfil");
                    return true;
            }
            return false;
        }
    };

    /**
     * Métodos para exibir e escondar o BottomNavigation
     */
    public void hideBottomNavigation() {
        navView.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        navView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permissions.validatePermissions(permissions, this, 1);
        init();
    }

    /**
     * Inicializa e configura os componentes.
     */
    private void init() {

        UserDAO userDAO = new UserDAO(getApplicationContext());
        User user = userDAO.getUser();

        pf = new ProfileFragment();
        mf = new MovieFragment();

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


        if (user.getName() == null || user.getName().isEmpty())
            navView.setSelectedItemId(R.id.navigation_profile);
        else navView.setSelectedItemId(R.id.navigation_movies);

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

    /**
     * Cria uma janela informando que a permição foi negada.
     */
    public void permissionDeniedMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissão negada!");
        builder.setMessage("Para utilizar o app corretamente, você deve aceitar as permições.");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissionResult : grantResults) {
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                permissionDeniedMessage();
            }
        }
    }

}
