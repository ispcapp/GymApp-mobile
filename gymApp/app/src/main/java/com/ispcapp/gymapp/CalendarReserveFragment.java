package com.ispcapp.gymapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class CalendarReserveFragment extends Fragment {

    ImageView btn_back_categories;

    private Spinner spinner_horario, spinner_notifications;
    private String[] horarios = {"4:00 PM", "6:00 PM", "8:00 PM"};
    private String[] options_notifications = {"SI", "NO"};

    public CalendarReserveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View componentFragment = inflater.inflate(R.layout.fragment_calendar_reserve, container, false);

        // Configuración del Spinner
        spinner_horario = componentFragment.findViewById(R.id.spinner_horario);
        ArrayAdapter<String>adapterHorarios = new ArrayAdapter<>(getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, horarios);
        spinner_horario.setAdapter(adapterHorarios);

        spinner_notifications = componentFragment.findViewById(R.id.spinner_notifications);
        ArrayAdapter<String>adapterOptions_notifications = new ArrayAdapter<>(getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, options_notifications);
        spinner_notifications.setAdapter(adapterOptions_notifications);

        btn_back_categories = componentFragment.findViewById(R.id.btn_back_categories);
        btn_back_categories.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            startActivity(intent);
        });

        return componentFragment;
    }
}