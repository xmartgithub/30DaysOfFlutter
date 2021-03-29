package com.uberdoktor.android;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class CustomDialog extends AppCompatDialogFragment {

    private EditText editTextDegreeName;
    private EditText editTextDegreeYear;
    private EditText editTextInstitutionName;
    private CustomDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();


        View view = inflater.inflate(R.layout.custom_dialog_box, null, false);

        builder.setView(view)
                .setTitle("Add Degree")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String degree = editTextDegreeName.getText().toString();
                        String year = editTextDegreeYear.getText().toString();
                        String institution = editTextInstitutionName.getText().toString();
                        listener.applyTexts(degree, year, institution);
                    }
                });

        editTextDegreeName = view.findViewById(R.id.editTextDegreeName);
        editTextDegreeYear = view.findViewById(R.id.editTextDegree_Year);
        editTextInstitutionName = view.findViewById(R.id.editText_institution_name);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (CustomDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + "must implement CustomDialogListener");
        }
    }

    public interface CustomDialogListener {
        void applyTexts(String degree, String year, String institutionName);
    }


}
