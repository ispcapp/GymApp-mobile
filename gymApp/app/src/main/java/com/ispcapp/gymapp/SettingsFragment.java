package com.ispcapp.gymapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tv_password_settings, tv_delete_account_settings;

        // Inflate the layout for this fragment
        View componentFragment = inflater.inflate(R.layout.fragment_settings, container, false);
        // Manejador de Fragments
        FragmentManager fragmentManager = getChildFragmentManager();

        tv_password_settings = componentFragment.findViewById(R.id.tv_password_settings);
        tv_password_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fcv_settings, ChangePasswordFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        return componentFragment;
    }
}