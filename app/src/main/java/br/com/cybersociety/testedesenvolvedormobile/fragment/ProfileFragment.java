package br.com.cybersociety.testedesenvolvedormobile.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import br.com.cybersociety.testedesenvolvedormobile.R;
import br.com.cybersociety.testedesenvolvedormobile.model.dao.UserDAO;
import br.com.cybersociety.testedesenvolvedormobile.model.entities.User;

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

        UserDAO userDAO = new UserDAO(getActivity());
        User user = userDAO.getUser();

        imageViewProfile = view.findViewById(R.id.imageProfile);
        editTextProfileName = view.findViewById(R.id.editTextProfileName);
        buttonProfileSave = view.findViewById(R.id.buttonProfileSave);

        // Verifica se há um nome no banco de dados.
        if (user.getName() != null) {
            editTextProfileName.setText(user.getName());
        }

        // Verifica se há uma imagem no banco de dados.
        if (user.getPhoto() != null) {

            ByteArrayInputStream imageStream = new ByteArrayInputStream(user.getPhoto());
            Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);

            imageViewProfile.setImageBitmap(imageBitmap);
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
                if (!editTextProfileName.getText().toString().isEmpty()) {

                    Bitmap bitmap = ((BitmapDrawable) imageViewProfile.getDrawable()).getBitmap();
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    byte[] imageBytes = outputStream.toByteArray();

                    UserDAO userDAO = new UserDAO(getActivity());
                    User user = userDAO.getUser();

                    // Obtém os dados e atualiza o banco
                    user.setName(editTextProfileName.getText().toString());
                    user.setPhoto(imageBytes);
                    userDAO.update(user);

                    imageViewProfile.setImageBitmap(bitmap);
                    Toast.makeText(getActivity(), "Dados Atualizados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Seu nome não pode ficar em branco!", Toast.LENGTH_SHORT).show();
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
