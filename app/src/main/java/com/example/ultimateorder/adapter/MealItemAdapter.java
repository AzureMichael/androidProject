package com.example.ultimateorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ultimateorder.R;
import com.example.ultimateorder.model.MealItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MealItemAdapter extends ArrayAdapter<MealItem> implements View.OnClickListener {

    private ArrayList<MealItem> mealItems;
    Context mContext;

    public MealItemAdapter(ArrayList<MealItem> data, Context context) {
        super(context, R.layout.meal_item_layout, data);
        this.mealItems = data;
        this.mContext = context;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        MealItem MealItem = (MealItem) object;

        switch (v.getId()) {
            case R.id.name:
                Snackbar.make(v, "Release date " + MealItem.getIngredients(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

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

        return listItem;
    }
}


