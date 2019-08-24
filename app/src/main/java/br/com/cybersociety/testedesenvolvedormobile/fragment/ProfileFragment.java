package br.com.cybersociety.testedesenvolvedormobile.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.cybersociety.testedesenvolvedormobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

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

        imageViewProfile = view.findViewById(R.id.imageViewProfile);
        editTextProfileName = view.findViewById(R.id.editTextProfileName);
        buttonProfileSave = view.findViewById(R.id.buttonProfileSave);

        SharedPreferences sp = getActivity().getSharedPreferences("NAME_PREFERENCE",Context.MODE_PRIVATE);

        String name = sp.getString("name", "");

        if (!name.isEmpty()) {
            editTextProfileName.setText(name);
        }

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

}
