package br.com.cybersociety.testedesenvolvedormobile.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.cybersociety.testedesenvolvedormobile.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final int CAMERA_REQUEST = 100;
    private static final int GALLERY_REQUEST = 200;

    private ImageView imageViewProfile;
    private EditText editTextProfileName;
    private Button buttonProfileSave;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imageViewProfile = view.findViewById(R.id.imageProfile);
        editTextProfileName = view.findViewById(R.id.editTextProfileName);
        buttonProfileSave = view.findViewById(R.id.buttonProfileSave);

        SharedPreferences sp = getActivity().getSharedPreferences("NAME_PREFERENCE",Context.MODE_PRIVATE);

        String name = sp.getString("name", "");

        if (!name.isEmpty()) {
            editTextProfileName.setText(name);
        }

        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Imagem de Perfil");
                builder.setMessage("Deseja obter uma nova foto?");
                builder.setPositiveButton("CÂMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }
                    }
                });

                builder.setNegativeButton("GALERIA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivityForResult(intent, GALLERY_REQUEST);
                        }
                    }
                });

                builder.setNeutralButton("CANCELAR", null);
                builder.setCancelable(false);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        buttonProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tempName = editTextProfileName.getText().toString();

                if (tempName != null && !tempName.isEmpty()) {
                    SharedPreferences sp = getActivity().getSharedPreferences("NAME_PREFERENCE",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name", tempName);
                    editor.commit();
                    Toast.makeText(getActivity(), "Nome Salvo", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Preencha o Seu Nome", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap image = null;

            try {
                switch (requestCode) {
                    case CAMERA_REQUEST:
                        image = (Bitmap) data.getExtras().get("data");
                        break;
                    case GALLERY_REQUEST:
                        Uri uri = data.getData();
                        image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (image != null) imageViewProfile.setImageBitmap(image);
        }

    }
}
