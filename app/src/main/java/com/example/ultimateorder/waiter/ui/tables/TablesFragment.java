package com.example.ultimateorder.waiter.ui.tables;

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
import com.example.ultimateorder.adapter.TableItemAdapter;
import com.example.ultimateorder.model.TableItem;
import com.example.ultimateorder.repo.TableItemRepo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TablesFragment extends Fragment {
    private static final String TAG = "Tables Fragment";

    private TableItemRepo repo = new TableItemRepo();
    private TablesViewModel tablesViewModel;
    private FirebaseFirestore firebaseFirestore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tablesViewModel =
                ViewModelProviders.of(this).get(TablesViewModel.class);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("tables").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<TableItem> tableItems = new ArrayList<>();
                tableItems.addAll(queryDocumentSnapshots.toObjects(TableItem.class));
                tablesViewModel.setmTableItems(tableItems);
            }
        });

        View root = inflater.inflate(R.layout.tables_fragment_waiter, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        tablesViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final ListView view = root.findViewById(R.id.list);
        tablesViewModel.getmTableItems().observe(this, new Observer<List<TableItem>>() {
            @Override
            public void onChanged(List<TableItem> tableItems) {
                TableItemAdapter adapter = new TableItemAdapter((ArrayList<TableItem>)tableItems,getContext());
                view.setAdapter(adapter);
            }
        });

        return root;
    }
}