package com.example.feesmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CardView add_student, delete_student, accept_fees, show_records;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_student = (CardView) findViewById(R.id.add_students);
        delete_student = (CardView) findViewById(R.id.delete_students);
        accept_fees = (CardView) findViewById(R.id.accept_fees);
        show_records = (CardView) findViewById(R.id.show_records);

        add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,add_student_details.class));
            }
        });
        delete_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, delete_student.class));
            }
        });
        accept_fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, accept_fees.class));
            }
        });
        show_records.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.example.feesmanagement.show_records.class));
            }
        });
    }
}
