package com.example.pantrycrudapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;

import java.util.ArrayList;

public class SmartPantryManagerextends AppCompatActivity {

    EditText etName;
    EditText etQuantity;
    EditText etCategory;

    Button btnAdd;
    Button btnUpdate;
    Button btnDelete;

    ListView listViewPantry;

    DatabaseHelper databaseHelper;

    ArrayList<Pantry> pantryList;

    PantryAdapter adapter;

    int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etCategory = findViewById(R.id.etCategory);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        listViewPantry = findViewById(R.id.listViewPantry);

        databaseHelper = new DatabaseHelper(this);

        pantryList = new ArrayList<>();

        adapter = new PantryAdapter(this, pantryList);

        listViewPantry.setAdapter(adapter);

        loadPantryItems();

        btnAdd.setOnClickListener(v -> addItem());

        btnUpdate.setOnClickListener(v -> updateItem());

        btnDelete.setOnClickListener(v -> deleteItem());

        listViewPantry.setOnItemClickListener(
                (parent, view, position, id) -> {

                    Pantry pantry = pantryList.get(position);

                    selectedId = pantry.getId();

                    etName.setText(pantry.getName());

                    etQuantity.setText(
                            String.valueOf(pantry.getQuantity())
                    );

                    etCategory.setText(pantry.getCategory());

                    Toast.makeText(
                            this,
                            "Item selected",
                            Toast.LENGTH_SHORT
                    ).show();
                }
        );
    }

    private void addItem() {

        String name = etName.getText().toString().trim();

        String quantityText =
                etQuantity.getText().toString().trim();

        String category =
                etCategory.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please enter name and quantity",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int quantity = Integer.parseInt(quantityText);

        boolean success =
                databaseHelper.addPantryItem(
                        name,
                        quantity,
                        category
                );

        if (success) {

            Toast.makeText(
                    this,
                    "Pantry item added",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();

            loadPantryItems();

        } else {

            Toast.makeText(
                    this,
                    "Failed to add item",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void updateItem() {

        if (selectedId == -1) {

            Toast.makeText(
                    this,
                    "Select an item first",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        String name = etName.getText().toString().trim();

        String quantityText =
                etQuantity.getText().toString().trim();

        String category =
                etCategory.getText().toString().trim();

        if (name.isEmpty() || quantityText.isEmpty()) {

            Toast.makeText(
                    this,
                    "Please enter name and quantity",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int quantity = Integer.parseInt(quantityText);

        boolean success =
                databaseHelper.updatePantryItem(
                        selectedId,
                        name,
                        quantity,
                        category
                );

        if (success) {

            Toast.makeText(
                    this,
                    "Pantry item updated",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();

            loadPantryItems();

        } else {

            Toast.makeText(
                    this,
                    "Update failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void deleteItem() {

        if (selectedId == -1) {

            Toast.makeText(
                    this,
                    "Select an item first",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        boolean success =
                databaseHelper.deletePantryItem(selectedId);

        if (success) {

            Toast.makeText(
                    this,
                    "Pantry item deleted",
                    Toast.LENGTH_SHORT
            ).show();

            clearFields();

            loadPantryItems();

        } else {

            Toast.makeText(
                    this,
                    "Delete failed",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void loadPantryItems() {

        pantryList.clear();

        Cursor cursor =
                databaseHelper.getAllPantryItems();

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_ID
                        )
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_NAME
                        )
                );

                int quantity = cursor.getInt(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_QUANTITY
                        )
                );

                String category = cursor.getString(
                        cursor.getColumnIndexOrThrow(
                                DatabaseHelper.COLUMN_CATEGORY
                        )
                );

                pantryList.add(
                        new Pantry(
                                id,
                                name,
                                quantity,
                                category
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }

    private void clearFields() {

        etName.setText("");

        etQuantity.setText("");

        etCategory.setText("");

        selectedId = -1;
    }
}