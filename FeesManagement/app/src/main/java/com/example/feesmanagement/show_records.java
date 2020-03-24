package com.example.feesmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class show_records extends AppCompatActivity {

    private Spinner mYear, mMonth;
    public String currentyear, month, year;
    private Button display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df.format(c);
        currentyear = currentDate.substring(7, currentDate.length());

        mMonth = (Spinner) findViewById(R.id.monthSpinner);
        mYear = (Spinner) findViewById(R.id.yearSpinner);
        display = (Button)findViewById(R.id.displayRecordsButton);

        ArrayAdapter<CharSequence> monthadapter = ArrayAdapter.createFromResource(this, R.array.month, android.R.layout.simple_spinner_item);
        monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMonth.setAdapter(monthadapter);
        mMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Selected Month from DropDown
                month = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> yearadapter = ArrayAdapter.createFromResource(this, R.array.year, android.R.layout.simple_spinner_item);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mYear.setAdapter(yearadapter);
        mYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Selected year from DropDown
                year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(year.equals(currentyear)){
                    Intent i = new Intent(show_records.this, display_records.class);
                    i.putExtra("year", year);
                    i.putExtra("month", month);
                    startActivity(i);
                }
                else{
                    Toast.makeText(show_records.this, "Invalid Year!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}
