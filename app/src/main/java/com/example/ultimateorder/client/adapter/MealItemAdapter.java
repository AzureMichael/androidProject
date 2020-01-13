package com.example.ultimateorder.client.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ultimateorder.R;
import com.example.ultimateorder.client.ui.menu.MenuViewModel;
import com.example.ultimateorder.model.MealItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MealItemAdapter extends ArrayAdapter<MealItem> implements View.OnClickListener {

    private ArrayList<MealItem> mealItems;
    Context mContext;
    private MenuViewModel menuViewModel;

    public MealItemAdapter(ArrayList<MealItem> data, Context context, MenuViewModel menuViewModel) {
        super(context, R.layout.meal_item_layout, data);
        this.mealItems = data;
        this.mContext = context;
        this.menuViewModel = menuViewModel;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.meal_item_layout, parent, false);

        MealItem currentM = mealItems.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(currentM.getName());

        TextView ingredients = (TextView) listItem.findViewById(R.id.ingredients);
        ingredients.setText(currentM.getIngredients());

        TextView price = (TextView) listItem.findViewById(R.id.price);
        price.setText(String.valueOf(currentM.getPrice()));

        Button addButton = listItem.findViewById(R.id.addToOrderButton);
        View finalListItem = listItem;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuViewModel.getNewOrderMeals().getValue().add(currentM);
                if(menuViewModel.getNewOrderMeals().getValue().contains(currentM)){
                    Toast.makeText(getContext(), "Item added to order.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        return listItem;
    }

}


