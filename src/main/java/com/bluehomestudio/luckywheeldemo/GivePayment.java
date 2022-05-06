package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.owater.library.CircleTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import es.dmoral.toasty.Toasty;

public class GivePayment extends AppCompatActivity {
FirebaseAuth firebaseAuth;
FirebaseFirestore firebaseFirestore;
    CircleTextView cir;
    MaterialEditText number;
    CircularProgressButton circularProgressButton;
    KProgressHUD kProgressHUD;
    String payment,payment_name,points;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_payment);
        try {
            username=getIntent().getStringExtra("username");
        }catch (Exception e) {
            username=getIntent().getStringExtra("username");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Payment");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        number=findViewById(R.id.number);
        circularProgressButton=findViewById(R.id.cirLoginButton);
        circularProgressButton.setEnabled(false);
        number.setEnabled(false);
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
                                    cir.setText(task.getResult().getString("main_balance"));
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                   // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    cir.setText(task.getResult().getString("main_balance"));
                                }
                            }
                        }
                    }
                });
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    number.setText(task.getResult().getString("number"));
                                }catch (Exception e) {
                                    number.setText(task.getResult().getString("number"));
                                }
                            }
                        }
                    }
                });
        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb_given=number.getText().toString().toLowerCase();
                if (TextUtils.isEmpty(numb_given)) {
                    Toasty.error(getApplicationContext(),
                            "Give all required information.",Toasty.LENGTH_SHORT,
                            true).show();
                }
                else {
                    final KProgressHUD progressDialog=  KProgressHUD.create(GivePayment.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Checking Data.....")
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    String main_get=cir.getText().toString().toLowerCase();
                    int  pointss=Integer.parseInt(points);
                    int main__ba=Integer.parseInt(main_get)-pointss;
                    firebaseFirestore.collection("Users")
                            .document(firebaseAuth.getCurrentUser().getUid())
                            .collection("Main_Balance")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .update("main_balance",""+main__ba)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        final String uuid = UUID.randomUUID().toString();
                                        Calendar calendar = Calendar.getInstance();
                                        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                        String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                        Long tsLong = System.currentTimeMillis()/1000;
                                        String ts = tsLong.toString();
                                        final Payment_request payment_request = new Payment_request(firebaseAuth.getCurrentUser().getEmail(),
                                                payment_name, number.getText().toString().toLowerCase(), payment, current1, "Pending", uuid, username,ts);
                                        firebaseFirestore.collection("Admin__Request")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                .set(payment_request)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            firebaseFirestore.collection("MyPayment").document(firebaseAuth.getCurrentUser().getEmail()).collection("List")
                                                                    .document(uuid).set(payment_request).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        progressDialog.dismiss();
                                                                        Toasty.success(getApplicationContext(),"Payment Request  Done",Toasty.LENGTH_SHORT,true).show();
                                                                        new AestheticDialog.Builder(GivePayment.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                .setTitle("Conformation")
                                                                                .setMessage("Withdraw request is Successfully Done ")
                                                                                .setAnimation(DialogAnimation.SPLIT)
                                                                                .setOnClickListener(new OnDialogClickListener() {
                                                                                    @Override
                                                                                    public void onClick(AestheticDialog.Builder builder) {
                                                                                        builder.dismiss();
                                                                                        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

                                                                                    }
                                                                                }).show();

                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                    }

                                }
                            });


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

    public void first(View view) {
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
                                   // cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>1500) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+15;
                                        points=""+1500;
                                        payment_name="Recharge";

                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>1500) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+15;
                                        points=""+1500;
                                        payment_name="Recharge";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void second(View view) {
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
                                    // cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>3000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+30;
                                        points=""+3000;
                                        payment_name="Recharge";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>3000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+30;
                                        points=""+3000;
                                        payment_name="Recharge";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void third(View view) {
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
                                    // cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>10000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+100;
                                        points=""+10000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>10000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+100;
                                        points=""+10000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void fot(View view) {
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
                                    // cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>20000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+200;
                                        points=""+20000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>20000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+200;
                                        points=""+20000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void fot5(View view) {
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
                                    // cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>50000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+500;
                                        points=""+50000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>50000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+500;
                                        points=""+50000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void fot6(View view) {
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
                                    // cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>100000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+1000;
                                        points=""+100000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }catch (Exception e) {
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    // Toast.makeText(GivePayment.this, ""+task.getResult().getString("main_balance"), Toast.LENGTH_SHORT).show();
                                    //cir.setText(task.getResult().getString("main_balance"));
                                    String main_balance=task.getResult().getString("main_balance");
                                    if (Integer.parseInt(main_balance)>100000) {
                                        circularProgressButton.setEnabled(true);
                                        number.setEnabled(true);
                                        payment=""+1000;
                                        points=""+100000;
                                        payment_name="Bkash";
                                    }
                                    else {
                                        Toasty.error(getApplicationContext(),
                                                "You have not enough points.",Toasty.LENGTH_SHORT,
                                                true).show();
                                        circularProgressButton.setEnabled(false);
                                        number.setEnabled(false);

                                    }
                                }
                            }
                        }
                    }
                });
    }
}