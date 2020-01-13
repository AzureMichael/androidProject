package com.example.ultimateorder.client.ui.menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ultimateorder.R;
import com.example.ultimateorder.client.adapter.MealItemAdapter;
import com.example.ultimateorder.model.MealItem;
import com.example.ultimateorder.model.OrderItem;
import com.example.ultimateorder.repo.MealItemRepo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class MenuFragment extends Fragment {

    private List<MealItem> newOrder = new ArrayList<>();

    private MealItemRepo repo = new MealItemRepo();
    private MenuViewModel menuViewModel;
    private FirebaseFirestore firebaseFirestore;
    private MealItemAdapter adapter;
    private List<MealItem> mealItems = new ArrayList<>();
    private List<MealItem> newOrderMeals = new ArrayList<>();

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
                adapter = new MealItemAdapter((ArrayList<MealItem>) mealItems,getContext(),menuViewModel);
                view.setAdapter(adapter);
            }
        });

        menuViewModel.getNewOrderMeals().observe(this, new Observer<List<MealItem>>() {
            @Override
            public void onChanged(List<MealItem> mealItems) {
                newOrderMeals = mealItems;
            }
        });


        Button addNewOrderButton = root.findViewById(R.id.addNewOrderButton);
        addNewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(menuViewModel, v);
            }
        });

        return root;
    }

    public void openDialog(MenuViewModel menuViewModel, View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();

        // Set Custom Title
        TextView title = new TextView(getContext());
        // Title Properties
        title.setText("Add New Order");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        TextView msg = new TextView(getContext());
        // Message Properties
        //msg.setText("Order: " + orderItem.getId() + "\n" + "Total Price: " + orderItem.getPrice() + "\n");

        EditText editText = new EditText(getContext());
        alertDialog.setView(editText);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Add Order", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Random random = new Random();
                OrderItem orderItem = new OrderItem();
                List<MealItem> mealItems = new ArrayList<>();
                mealItems = newOrderMeals;
                AtomicReference<Float> total = new AtomicReference<>((float) 0);
                mealItems.forEach(mealItem -> {
                    total.updateAndGet(v1 -> v1 + mealItem.getPrice());
                });
                orderItem.setPrice(total.get());
                orderItem.setId(random.nextInt(1000000000));
                List<MealItem> finalMealItems = mealItems;
                firebaseFirestore.collection("tables").document("table"+editText.getText()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        DocumentReference documentReference = documentSnapshot.getReference();
                        orderItem.setTable(documentReference);
                        Map<String,DocumentReference> documentReferenceMap = new HashMap<>();
                       finalMealItems.forEach(mealItem -> {
                            firebaseFirestore.collection("menu").whereEqualTo("name", mealItem.getName()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    DocumentReference reference = queryDocumentSnapshots.getDocuments().get(0).getReference();
                                    documentReferenceMap.put("meal", reference);
                                    firebaseFirestore.collection("orders").document("order"+orderItem.getId()).collection("mealItems").document("mealItem" +  random.nextInt(1000000000)).set(documentReferenceMap);
                                    firebaseFirestore.collection("orders").document("order"+orderItem.getId()).set(orderItem);
                                }
                            });
                        });
                    }
                });
                menuViewModel.getNewOrderMeals().setValue(new ArrayList<>());
                Toast.makeText(getContext(), "Order sent.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        alertDialog.show();
    }

}