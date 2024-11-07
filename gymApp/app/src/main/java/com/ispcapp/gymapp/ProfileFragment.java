package com.ispcapp.gymapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView tv_edit_profile, tv_contact_profile, tv_settings_profile;

        // Inflate the layout for this fragment
        View componentFragment = inflater.inflate(R.layout.fragment_profile, container, false);
        // Manejador de Fragments
        FragmentManager fragmentManager = getChildFragmentManager();

        tv_edit_profile = componentFragment.findViewById(R.id.tv_edit_profile);
        tv_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fcv_edit_profile, EditProfileFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        tv_settings_profile = componentFragment.findViewById(R.id.tv_settings_profile);
        tv_settings_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fcv_edit_profile, SettingsFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        tv_contact_profile = componentFragment.findViewById(R.id.tv_contact_profile);
        tv_contact_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fcv_edit_profile, ContactFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        return componentFragment;
    }
}