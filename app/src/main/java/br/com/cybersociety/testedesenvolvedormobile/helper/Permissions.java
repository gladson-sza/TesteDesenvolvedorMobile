package br.com.cybersociety.testedesenvolvedormobile.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissions {

    public static boolean validatePermissions(String[] permissions, Activity activity, int requestCode) {

        if (Build.VERSION.SDK_INT >= 23) {

            List<String> permissionsList = new ArrayList<>();

            for (String permission : permissions) {
                boolean havePermission = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
                if (!havePermission) permissionsList.add(permission);
            }

            // Caso a lista esteja vazia, não é necessário solicitar permissão
            if (permissionsList.isEmpty()) return true;
            String[] newPermissions = new String[permissionsList.size()];
            permissionsList.toArray(newPermissions);

            //Solicita permissão
            ActivityCompat.requestPermissions(activity, newPermissions, requestCode);


        }

        return true;

    }

}