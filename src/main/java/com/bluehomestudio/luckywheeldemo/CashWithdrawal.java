package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

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
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.owater.library.CircleTextView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class CashWithdrawal extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CircleTextView cir;
    MaterialEditText number;
    CircularProgressButton circularProgressButton;
    KProgressHUD kProgressHUD;
    String payment,payment_name,points;
    String username;
    //
    DocumentReference documentReference;
    RecyclerView recyclerView;
    MethodeAdapter getDataAdapter1;
    List<MethodeModel> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_withdrawal);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Diamond Wallet");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        cir=findViewById(R.id.cir);
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("Main_Balance")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    cir.setText("◇ "+task.getResult().getString("main_balance"));
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    cir.setText("◇ "+task.getResult().getString("main_balance"));
                                }
                            }
                        }
                    }
                });

        //
        getList = new ArrayList<>();
        getDataAdapter1 = new MethodeAdapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference  =  firebaseFirestore.collection("Payment_Methode").document();
        recyclerView =findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();
    }
    private void reciveData() {

        firebaseFirestore.collection("Payment_Methode").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        MethodeModel get = ds.getDocument().toObject(MethodeModel.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });

    }
}