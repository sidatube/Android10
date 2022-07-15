package com.t2008m.android10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.t2008m.android10.database.AppDatabase;
import com.t2008m.android10.entity.User;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edUser, edDes;
    Spinner spinner;
    Button update,delete;
    AppDatabase database;
    String gender;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent shipper = getIntent();
        int shipperData=shipper.getIntExtra("data",0);
        edDes = findViewById(R.id.edDes);
        edUser = findViewById(R.id.edUser);
        spinner = findViewById(R.id.spinner);
        update = findViewById(R.id.updateBtn);
        delete = findViewById(R.id.deleteBtn);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        String[] spinList = {"Male", "Female", "3rd Gender", "Unknown"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = spinList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        database = AppDatabase.getAppDatabase(this);
        getData(shipperData);
    }
    public void getData(int id){
         user = database.userDao().findById(id);
        edUser.setText(user.getUsername());
        edDes.setText(user.getDes());
        gender = user.getGender();
        ArrayAdapter<String> myAdap = (ArrayAdapter<String>) spinner.getAdapter();
        int spinnerPosition = myAdap.getPosition(gender);
        spinner.setSelection(spinnerPosition);

    }

    private void goToList() {
        Intent intent = new Intent(this, ListUserActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.updateBtn:
                onUpdate();
                break;
            case R.id.deleteBtn:
                onDelete();
                break;
            default:
                break;
        }
    }
    private boolean validate() {
        String mes = null;
        if (edUser.getText().toString().trim().isEmpty()) {
            mes = "Chưa nhập username";
        } else if (edUser.getText().toString().trim().isEmpty()) {
            mes = "Chưa nhập giới thiệu";
        }
        if (mes != null) {
            Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void onDelete() {
        if (!validate()){
            return;
        }
        int check = database.userDao().deleteUser(user);
        if(check>0){
            goToList();
        }
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

    }

    private void onUpdate() {
        if (!validate()){
            return;
        }
        user.setUsername(edUser.getText().toString());
        user.setDes(edDes.getText().toString());
        user.setGender(gender);
        int check = database.userDao().updateUser(user);
        if(check>0){
            goToList();
        }

        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();

    }
}