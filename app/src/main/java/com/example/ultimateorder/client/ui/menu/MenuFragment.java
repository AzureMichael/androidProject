package com.example.ultimateorder.client.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ultimateorder.R;
import com.example.ultimateorder.adapter.MealItemAdapter;
import com.example.ultimateorder.model.MealItem;
import com.example.ultimateorder.repo.MealItemRepo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private MealItemRepo repo = new MealItemRepo();
    private MenuViewModel menuViewModel;
    private FirebaseFirestore firebaseFirestore;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("menu").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<MealItem> mealItems = new ArrayList<>();
                mealItems.addAll(queryDocumentSnapshots.toObjects(MealItem.class));
                menuViewModel.setmMealItems(mealItems);
            }
        });

        View root = inflater.inflate(R.layout.fragment_menu_client, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        menuViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final ListView view = root.findViewById(R.id.list);
        menuViewModel.getmMealItems().observe(this, new Observer<List<MealItem>>() {
            @Override
            public void onChanged(List<MealItem> mealItems) {
                MealItemAdapter adapter = new MealItemAdapter((ArrayList<MealItem>) mealItems,getContext());
                view.setAdapter(adapter);
            }
        });
        return root;
    }
}