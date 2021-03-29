//package com.uberdoktor.android;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.tabs.TabLayout;
//import com.google.gson.JsonArray;
//
//import org.jetbrains.annotations.NotNull;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class DynamicFragment extends Fragment {
//
//    View view;
//    SharedPrefManager sharedPrefManager;
//    public static String appDate, slot, pendingID;
//    private String position;
//    private RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    CustomAdapter customAdapter;
//    private TextView slotTextView;
//
//
//    public static DynamicFragment newInstance(String val) {
//        DynamicFragment fragment = new DynamicFragment();
//        Bundle args = new Bundle();
//        args.putString("pos", val);
//        fragment.setArguments(args);
//
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        position = getArguments().getString("pos");
//
//    }
//
//    int val;
//    TextView c;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.appointment_vp_layout, container, false);
////        assert getArguments() != null;
////        val = getArguments().getInt("someInt", 0);
////        c = view.findViewById(R.id.c);
////        c.setText("" + val);
//
//
//        sharedPrefManager = new SharedPrefManager(getActivity());
//
//        fetchAndShowAppointmentsTime();
//
//        return view;
//    }
//
//    private void fetchAndShowAppointmentsTime() {
//        String id = String.valueOf(SpecialityActivity.doctorID);
//
//
//        Call<DoctorScheduleResponse> call = RetrofitClient.getInstance().getMyInterface().getAppointmentTime("Bearer " + sharedPrefManager.getAccessToken(), id);
//        call.enqueue(new Callback<DoctorScheduleResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<DoctorScheduleResponse> call, @NotNull Response<DoctorScheduleResponse> response) {
//
//                int index = 0;
//                if (response.isSuccessful()) {
//
////                    kust = response.body().getSlots().get(position);
//
//                    List<String> sllots = new ArrayList<>();
//
//                    for (List<Slot> slots : response.body().getSlot()) {
//                        for (Slot slot : slots)
//                            if (slot.getScheduleDate().equals(position)) {
//                                sllots = slot.getAvailSlots();
//                                System.out.println("Slot from dynamic fragment" + sllots);
//                                System.out.println(position + slot.getScheduleDate());
//                                break;
//
//                            }
//                    }
//                    recyclerView = view.getRootView().findViewById(R.id.simpleGridView);
//                    layoutManager = new GridLayoutManager(getContext(), 3);
//                    recyclerView.setLayoutManager(layoutManager);
//                    customAdapter = new CustomAdapter(sllots);
//
//                    recyclerView.setAdapter(customAdapter);
//                    recyclerView.setHasFixedSize(true);
//
//                    // recycler
//                    // layoutmanager grid
//                    // kust
//
//                    // list<Slot> slot
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<DoctorScheduleResponse> call, @NotNull Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void selectSlot() {
//        String id = String.valueOf(SpecialityActivity.doctorID);
//        Call<SelectSlotResponse> call = RetrofitClient.getInstance().getMyInterface().selectSlot("Bearer " + sharedPrefManager.getAccessToken(), id, appDate, slot);
//        call.enqueue(new Callback<SelectSlotResponse>() {
//            @Override
//            public void onResponse(@NotNull Call<SelectSlotResponse> call, @NotNull Response<SelectSlotResponse> response) {
//
//                Intent newIntent = new Intent(getActivity(), PaymentMethodActivity.class);
//                startActivity(newIntent);
//                if (response.isSuccessful()) {
//                    assert response.body() != null;
//                    pendingID = response.body().getPendingPaymentId().toString();
//                    Toast.makeText(getContext(), "Slot selected successfully", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull Call<SelectSlotResponse> call, @NotNull Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//}