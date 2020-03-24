package com.example.feesmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class delete_student extends AppCompatActivity {

    private DatabaseReference mdb1;
    private EditText sname;
    private Button delete;
    public String name_key;
    public String id_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        mdb1 = FirebaseDatabase.getInstance().getReference().child("Students");

        sname = (EditText) findViewById(R.id.students_nameEd);

        delete = (Button) findViewById(R.id.deleteBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createAlert();
                delete_student();
            }
        });
    }
    /*void createAlert(){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(delete_student.this);
        a_builder.setMessage("Do you wish to continue ? ").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete_student();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Selected Records will be Deleted from the Database!!!");
    }*/
    void delete_student() {
        try {
            name_key = sname.getText().toString().trim();
            if (TextUtils.isEmpty(name_key)) {
                Toast.makeText(this, "Name Field Cannot be empty!!!!!", Toast.LENGTH_SHORT).show();
            } else {
                mdb1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(name_key).exists()) {
                            mdb1.child(name_key).removeValue();
                            startActivity(new Intent(delete_student.this, MainActivity.class));
                            Toast.makeText(delete_student.this, "Successfuly Deleted!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(delete_student.this, "Student Not Found!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(this, "Exception Occured" + e, Toast.LENGTH_SHORT).show();
        } finally {
            sname.setText("");
        }

    }
}
