package com.ispcapp.gymapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ForgotPasswordFragment extends Fragment {


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btn_reset_pass;

        // Inflate the layout for this fragment
        View componentFragment = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        // Manejador de Fragments
        FragmentManager fragmentManager = getChildFragmentManager();

        btn_reset_pass = componentFragment.findViewById(R.id.btn_forgot_pass);
        btn_reset_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView2, ResetPasswordFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        return componentFragment;
    }
}