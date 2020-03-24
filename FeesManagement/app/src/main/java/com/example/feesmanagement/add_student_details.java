package com.example.feesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class add_student_details extends AppCompatActivity {

    private Button add;
    private DatabaseReference mdb1;
    private EditText sname, gname, address, phn;
    private String reg_id;
    public String name;
    public HashMap<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_details);

        mdb1 = FirebaseDatabase.getInstance().getReference().child("Students");
        sname = (EditText) findViewById(R.id.nameEd);
        gname = (EditText) findViewById(R.id.guardians_nameEd);
        address = (EditText) findViewById(R.id.addressEd);
        phn = (EditText) findViewById(R.id.phoneEd);

        add = (Button) findViewById(R.id.submitBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_student();
            }
        });
    }
    void add_student()
    {
        try {
            name = sname.getText().toString().trim();
            final String guardian = gname.getText().toString().trim();
            String add = address.getText().toString().trim();
            String phone = phn.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(guardian) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(add)) {
                Toast.makeText(this, "Fields cannot be empty!!!", Toast.LENGTH_SHORT).show();
            } else {
                map = new HashMap<String, String>();
                map.put("name", name);
                map.put("guardian", guardian);
                map.put("address", add);
                map.put("phone", phone);

                mdb1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(name).exists()){
                            startActivity(new Intent(add_student_details.this, MainActivity.class));
                            Toast.makeText(add_student_details.this, "Student Already Exists!!!", Toast.LENGTH_SHORT).show();

                        }else{
                            mdb1.child(name).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(add_student_details.this, MainActivity.class));
                                        Toast.makeText(add_student_details.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Exception Occured" + e, Toast.LENGTH_SHORT).show();
        }
        finally {
            sname.setText("");
            gname.setText("");
            add.setText("");
            phn.setText("");
        }
    }
}
