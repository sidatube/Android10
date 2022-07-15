package com.t2008m.android10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.t2008m.android10.database.AppDatabase;
import com.t2008m.android10.entity.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edUser, edDes;
    Spinner spinner;
    CheckBox checkBox;
    Button button,btnGoList;
    AppDatabase db;
    String gender = "Male";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getAppDatabase(this);
        checkBox = findViewById(R.id.edCheckbox);
        edUser = findViewById(R.id.edUser);
        edDes = findViewById(R.id.edDes);
        button = findViewById(R.id.btnRegister);
        button.setOnClickListener(this);
        btnGoList=findViewById((R.id.btnList));
        btnGoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToList();
            }
        });
        spinner= findViewById(R.id.spinner);
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

    }

    private void onRegister() {
        if (!validate()) {
            return;
        }
        User user = new User();
        user.setUsername(edUser.getText().toString());
        user.setDes(edDes.getText().toString());
        user.setGender(gender);
        long id = db.userDao().insertUser(user);
        if (id > 0) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
        goToList();
    }

    private void goToList() {
        Intent intent = new Intent(this, ListUserActivity.class);
        startActivity(intent);
    }

    private boolean validate() {
        String mes = null;
        if (edUser.getText().toString().trim().isEmpty()) {
            mes = "Chưa nhập username";
        } else if (edUser.getText().toString().trim().isEmpty()) {
            mes = "Chưa nhập giới thiệu";
        } else if (!checkBox.isChecked()) {
            mes = "Chưa chấp thuận điều khoản";
        }
        if (mes != null) {
            Toast.makeText(this, mes, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnRegister) {
            onRegister();
        }

    }
}