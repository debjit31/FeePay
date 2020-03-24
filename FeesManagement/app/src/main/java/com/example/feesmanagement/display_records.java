package com.example.feesmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.app.ProgressDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class display_records extends AppCompatActivity {

    private RecyclerView postlist;
    private DatabaseReference detailsRef;
    String year,month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_records);

        final ProgressDialog loading = ProgressDialog.show(this,"Loading","Please wait");


        year = getIntent().getStringExtra("year");
        month = getIntent().getStringExtra("month");


        postlist=(RecyclerView)findViewById(R.id.details_recycler_view);
        detailsRef= FirebaseDatabase.getInstance().getReference().child("Fees").child(year).child(month);


        if(month!=null)
        {
            Toast.makeText(display_records.this, "Available Records..", Toast.LENGTH_SHORT).show();
        }
        postlist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        postlist.setLayoutManager(linearLayoutManager);


        DisplayDetails();
        loading.dismiss();
    }

    private void DisplayDetails() {

        FirebaseRecyclerAdapter<Details,DetailsViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Details,DetailsViewHolder>
                (
                  Details.class,
                  R.layout.details_layout,
                  DetailsViewHolder.class,
                  detailsRef
                ) {
            @Override
            protected void populateViewHolder(DetailsViewHolder viewHolder, Details model, int i) {



                viewHolder.setName(model.getName());
                viewHolder.setDate(model.getDate());
            }
        };

         postlist.setAdapter(firebaseRecyclerAdapter);


    }

    public static class DetailsViewHolder extends RecyclerView.ViewHolder
    {

        View mView;
        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setName(String name)
        {
            TextView n = (TextView)mView.findViewById(R.id.name_layout);
            n.setText(name);

        }

        public void setDate(String date)
        {
            TextView d= (TextView)mView.findViewById(R.id.date_layout);
            d.setText(date);
        }

    }
}
