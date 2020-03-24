package com.example.feesmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class accept_fees extends AppCompatActivity {

    public Spinner mMonth;
    private DatabaseReference mdb, mdb1;
    private EditText sname;
    private EditText sdate;
    private String currentDate;
    public String month;
    public String name;
    public String date;
    public HashMap<String, String> map;
    private Button accept;
    public String currentyear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_fees);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        currentDate = df.format(c);
        currentyear = currentDate.substring(7, currentDate.length());

        mdb = FirebaseDatabase.getInstance().getReference().child("Fees").child(currentyear);
        //mdb1 = FirebaseDatabase.getInstance().getReference().child("Students");
        accept = (Button) findViewById(R.id.submitBtn);
        sname = (EditText) findViewById(R.id.nameEd);
        sdate = (EditText) findViewById(R.id.dateEd);
        mMonth = (Spinner) findViewById(R.id.monthEd);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMonth.setAdapter(adapter);
        mMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptFees();
            }
        });
    }

    void acceptFees() {
        try {
            name = sname.getText().toString().trim();
            date = sdate.getText().toString().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(date)) {
                Toast.makeText(this, "Fields Cannot be Empty!!!", Toast.LENGTH_SHORT).show();
            } else {
                map = new HashMap<String, String>();
                map.put("name", name);
                map.put("date",date);
                map.put("month", month);
                Log.i("Details", name+" " + month + " " + date);

                mdb.child(month).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(name).exists()){
                            startActivity(new Intent(accept_fees.this, MainActivity.class));
                            Toast.makeText(accept_fees.this, "Payment Already Made!!!!", Toast.LENGTH_SHORT).show();
                        }else{
                            mdb.child(map.get("month")).child(name).setValue(map);
                            startActivity(new Intent(accept_fees.this, MainActivity.class));
                            Toast.makeText(accept_fees.this, "Payment Added Successfully!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        } catch (Exception e) {
            Toast.makeText(this, "Exception Occured " + e, Toast.LENGTH_SHORT).show();
        } finally {
            sname.setText("");
            mMonth.setSelection(0);
            sdate.setText("");
        }
    }
}
