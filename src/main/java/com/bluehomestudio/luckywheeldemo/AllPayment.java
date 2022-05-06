package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class AllPayment extends AppCompatActivity {
    private Spinner spinnerTextSize, spinnerTextSize1, spinnerTextSize2;
    EditText Email_Log;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
    TextView coinsT1v;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    EditText spinner1, spinner2;
    Button upgrade;
    KProgressHUD kProgressHUD;
    LottieAnimationView empty_cart;


    DocumentReference documentReference;
    RecyclerView recyclerView;
    Payment_Adapter getDataAdapter1;
    List<Payment_request> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    String main_account;
    Long tsLong = System.currentTimeMillis() / 1000;
    String ts = tsLong.toString();
    String userpackage;
    int subtotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("All Payment");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        empty_cart = findViewById(R.id.empty_cart);
        firebaseFirestore.collection("Done")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            if (count == 0) {
                                empty_cart.setVisibility(View.VISIBLE);

                            } else {
                                empty_cart.setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                });

        getList = new ArrayList<>();
        getDataAdapter1 = new Payment_Adapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("Done").document();
        recyclerView = findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllPayment.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


    }

    private void reciveData() {

        firebaseFirestore.collection("Done").orderBy("time", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Payment_request get = ds.getDocument().toObject(Payment_request.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
    }
}