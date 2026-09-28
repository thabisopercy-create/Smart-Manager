import com.example.smartpantrymanager.R;

public class PantryAdapter {package com.example.pantrycrudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

    public class PantryAdapter extends ArrayAdapter<Pantry> {

        public PantryAdapter(Context context, List<Pantry> pantryList) {
            super(context, 0, pantryList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_pantry, parent, false);
            }

            Pantry pantry = getItem(position);

            TextView tvName = convertView.findViewById(R.id.tvName);
            TextView tvQuantity = convertView.findViewById(R.id.tvQuantity);
            TextView tvCategory = convertView.findViewById(R.id.tvCategory);

            if (pantry != null) {

                tvName.setText(pantry.getName());

                tvQuantity.setText(
                        "Quantity: " + pantry.getQuantity()
                );

                tvCategory.setText(
                        "Category: " + pantry.getCategory()
                );
            }

            return convertView;
        }
    }
}
