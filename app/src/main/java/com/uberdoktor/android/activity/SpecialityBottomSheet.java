package com.uberdoktor.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.uberdoktor.android.R;
import com.uberdoktor.android.activity.SpecialityActivity;
import com.uberdoktor.android.adapter.GridViewAdapter;


public class SpecialityBottomSheet extends BottomSheetDialogFragment {

    String[] numberOfSpecialities = {"General Physician", "Dermatologist", "Gynecologist", "ENT Specialist", "Psychiatrist", "Cardiologist", "Dentist", "Gastroenteritis", "Neuro Surgen", "Laser Specialist", "Diabetologist", "Pulmonologist", "Family Physician", "Dietitian", "Nutritionist", "Obstetrician", "Cosmetic Dentists", "Oncologist", "Orthopedic Surgeons", "Urologist", "Internal Medicine", "Nephrologist", "Homeopaths", "Counselor"};
    int[] specialitiesImages = {R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy, R.drawable.gen_phy,};
    GridView gridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);


//        String specialityName = specialityItemTextView.getText().toString();
        gridView = view.findViewById(R.id.grid_view);

        GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(), numberOfSpecialities, specialitiesImages);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SpecialityActivity.class);
                intent.putExtra("specialityName", numberOfSpecialities[+position]);
                startActivity(intent);
            }
        });

        return view;
    }


}
