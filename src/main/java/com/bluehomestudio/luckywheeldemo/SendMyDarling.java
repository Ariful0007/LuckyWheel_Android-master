package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class SendMyDarling extends AppCompatActivity {
    EditText spinner1,transcationpin,toid;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    TextView textammount,showing;
    Button cirLoginButton;
    KProgressHUD kProgressHUD;
    String check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_my_darling);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Send Money ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        kProgressHUD=KProgressHUD.create(SendMyDarling.this);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        spinner1=findViewById(R.id.spinner1);
        textammount=findViewById(R.id.textammount);
        transcationpin=findViewById(R.id.transcationpin);
        cirLoginButton=findViewById(R.id.cirLoginButton);
        showing=findViewById(R.id.showing);
        toid=findViewById(R.id.toid);
        toid=findViewById(R.id.toid);
        toid.addTextChangedListener(nameWatcher);
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
                                    String balance=task.getResult().getString("purches_balance");
                                    int main_=Integer.parseInt(balance);
                                    if (main_==0) {
                                        //cirLoginButton.setEnabled(false);

                                    }
                                    else {
                                        // cirLoginButton.setEnabled(true);
                                    }
                                    showing.setText("Available Dollar : "+task.getResult().getString("purches_balance"));
                                }catch (Exception e) {
                                    String balance=task.getResult().getString("purches_balance");
                                    int main_=Integer.parseInt(balance);
                                    if (main_==0) {
                                        //cirLoginButton.setEnabled(false);

                                    }
                                    else {
                                        //cirLoginButton.setEnabled(true);
                                    }
                                    showing.setText("Available Dollar : "+task.getResult().getString("purches_balance"));
                                }
                            }
                            else {

                                showing.setText("Available Dollar : 0");
                            }
                        }
                        else {

                            showing.setText("Available Dollar : 0");
                        }
                    }
                });
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ammount=spinner1.getText().toString().toLowerCase();
                String pin=transcationpin.getText().toString().toLowerCase();
                String toidi=toid.getText().toString().toLowerCase().toString();
                if (TextUtils.isEmpty(ammount)||TextUtils.isEmpty(pin)||TextUtils.isEmpty(toidi)) {
                    Toasty.error(getApplicationContext(), "Error give right information.", Toast.LENGTH_SHORT, true).show();
                    return;
                }
                else {
                    progress_check();
                    pisehast(ammount);
                    firebaseFirestore.collection("Password")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        if (task.getResult().exists()) {

                                            try {
                                                String pass=task.getResult().getString("uuid");
                                                if (pass.toLowerCase().toString().equals(pin)) {
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

                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                            String third_level=task.getResult().getString("third_level");

                                                                            String ammount1=ammount;
                                                                            int  tax=0;
                                                                            int main=Integer.parseInt(ammount1)+tax;
                                                                            int  subpurches_balance=Integer.parseInt(purches_balance)-main;
                                                                            int subthird_level=Integer.parseInt(third_level)+Integer.parseInt(ammount1);
                                                                            if ((main)>Float.parseFloat(purches_balance)) {
                                                                                kProgressHUD.dismiss();
                                                                                Toasty.error(getApplicationContext(), "You have not much money.", Toast.LENGTH_SHORT, true).show();
                                                                            }
                                                                            else {
                                                                                firebaseFirestore.collection("Users")
                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                        .collection("Main_Balance")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .update("purches_balance",
                                                                                                ""+subpurches_balance,"third_level",""+subthird_level)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @RequiresApi(api = Build.VERSION_CODES.N)
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    firebaseFirestore.collection("All_UserID")
                                                                                                            .document(toidi+"@gmail.com")
                                                                                                            .get()
                                                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        if (task.getResult().exists()) {
                                                                                                                            String uuid=task.getResult().getString("uuid");
                                                                                                                            firebaseFirestore.collection("Users")
                                                                                                                                    .document(uuid)
                                                                                                                                    .collection("Main_Balance")
                                                                                                                                    .document(toidi+"@gmail.com")
                                                                                                                                    .get()
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                if (task.getResult().exists()) {
                                                                                                                                                    String purches_balance=task.getResult().getString("main_balance");

                                                                                                                                                    String ammount1=ammount;
                                                                                                                                                    int subpurches_balance=Integer.parseInt(purches_balance)+Integer.parseInt(ammount1);
                                                                                                                                                    String monthly_income=task.getResult().getString("purches_balance");
                                                                                                                                                    int submonthly_income=Integer.parseInt(monthly_income)+Integer.parseInt(ammount1);
                                                                                                                                                    firebaseFirestore.collection("Users")
                                                                                                                                                            .document(uuid)
                                                                                                                                                            .collection("Main_Balance")
                                                                                                                                                            .document(toidi+"@gmail.com")
                                                                                                                                                            .update("main_balance",
                                                                                                                                                                    ""+subpurches_balance,"purches_balance",""+submonthly_income)
                                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                                                                        String ts = tsLong.toString();
                                                                                                                                                                        Random r = new Random();
                                                                                                                                                                        int randomNumber = r.nextInt(1002563985);
                                                                                                                                                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                                                                                                                        Date date = new Date();
                                                                                                                                                                        String s=dateFormat.format(date);
                                                                                                                                                                        String randomid="GTR"+randomNumber;
                                                                                                                                                                        String ammount1=ammount;
                                                                                                                                                                        float tax=(Float.parseFloat(ammount1)*1)/100;
                                                                                                                                                                        SendingModel sendingModel=new SendingModel("Transfer",ts,s,
                                                                                                                                                                                randomid,"Receiver ID : ",ammount1,""+tax,ammount1,toidi.toLowerCase().toString());
                                                                                                                                                                        firebaseFirestore.collection("My_Sending")
                                                                                                                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                                .collection("List")
                                                                                                                                                                                .add(sendingModel)
                                                                                                                                                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                                                        if (task.isSuccessful()) {

                                                                                                                                                                                            Calendar calendar = Calendar.getInstance();
                                                                                                                                                                                            String current = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());
                                                                                                                                                                                            String current1 = java.text.DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                                                                                                            Random r = new Random();
                                                                                                                                                                                            int randomNumber = r.nextInt(1002563985);

                                                                                                                                                                                            //int ttt = Integer.parseInt(packageprice)*5/100;;
                                                                                                                                                                                            // int randomNumber = r.nextInt(1002563985);
                                                                                                                                                                                            String randomid="DLB"+randomNumber;
                                                                                                                                                                                            Income income=new Income("refername1","Fund receive from user",firebaseAuth.getCurrentUser().getEmail(),
                                                                                                                                                                                                    String.valueOf(ammount1),current1,ts,randomid,"0");
                                                                                                                                                                                            firebaseFirestore.collection("Fund_History") .document(toidi+"@gmail.com")
                                                                                                                                                                                                    .collection("List")
                                                                                                                                                                                                    .add(income)
                                                                                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                                                                                                        @Override
                                                                                                                                                                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                                                                                kProgressHUD.dismiss();
                                                                                                                                                                                                                new AestheticDialog.Builder(SendMyDarling.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                                                                        .setTitle("Conformation")
                                                                                                                                                                                                                        .setMessage("Balance Transfering is Successfully Done.")
                                                                                                                                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                                                                builder.dismiss();
                                                                                                                                                                                                                                startActivity(new Intent(getApplicationContext(),SecondHome.class));

                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }).show();
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }
                                                                                                                                                                                                    });

                                                                                                                                                                                            //Toasty.success(getApplicationContext(), "Balance transfer is done.", Toast.LENGTH_SHORT, true).show();
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });


                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            });
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            });

                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }


                                                                        }
                                                                    }
                                                                }
                                                            });

                                                }
                                                else {
                                                    kProgressHUD.dismiss();
                                                    Toasty.error(getApplicationContext(), " Password is incorrect.", Toast.LENGTH_SHORT, true).show();
                                                }
                                            }catch (Exception e) {
                                                String pass=task.getResult().getString("uuid");
                                                if (pass.toLowerCase().toString().equals(pin)) {
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

                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                            String third_level=task.getResult().getString("third_level");

                                                                            String ammount1=ammount;
                                                                            int  tax=0;
                                                                            int main=Integer.parseInt(ammount1)+tax;
                                                                            int  subpurches_balance=Integer.parseInt(purches_balance)-main;
                                                                            int subthird_level=Integer.parseInt(third_level)+Integer.parseInt(ammount1);
                                                                            if ((main)>Float.parseFloat(purches_balance)) {
                                                                                kProgressHUD.dismiss();
                                                                                Toasty.error(getApplicationContext(), "You have not much money.", Toast.LENGTH_SHORT, true).show();
                                                                            }
                                                                            else {
                                                                                firebaseFirestore.collection("Users")
                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                        .collection("Main_Balance")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .update("purches_balance",
                                                                                                ""+subpurches_balance,"third_level",""+subthird_level)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @RequiresApi(api = Build.VERSION_CODES.N)
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    firebaseFirestore.collection("All_UserID")
                                                                                                            .document(toidi+"@gmail.com")
                                                                                                            .get()
                                                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        if (task.getResult().exists()) {
                                                                                                                            String uuid=task.getResult().getString("uuid");
                                                                                                                            firebaseFirestore.collection("Users")
                                                                                                                                    .document(uuid)
                                                                                                                                    .collection("Main_Balance")
                                                                                                                                    .document(toidi+"@gmail.com")
                                                                                                                                    .get()
                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                if (task.getResult().exists()) {
                                                                                                                                                    String purches_balance=task.getResult().getString("main_balance");

                                                                                                                                                    String ammount1=ammount;
                                                                                                                                                    int subpurches_balance=Integer.parseInt(purches_balance)+Integer.parseInt(ammount1);
                                                                                                                                                    String monthly_income=task.getResult().getString("purches_balance");
                                                                                                                                                    int submonthly_income=Integer.parseInt(monthly_income)+Integer.parseInt(ammount1);
                                                                                                                                                    firebaseFirestore.collection("Users")
                                                                                                                                                            .document(uuid)
                                                                                                                                                            .collection("Main_Balance")
                                                                                                                                                            .document(toidi+"@gmail.com")
                                                                                                                                                            .update("main_balance",
                                                                                                                                                                    ""+subpurches_balance,"purches_balance",""+submonthly_income)
                                                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                                                                                                                                        String ts = tsLong.toString();
                                                                                                                                                                        Random r = new Random();
                                                                                                                                                                        int randomNumber = r.nextInt(1002563985);
                                                                                                                                                                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                                                                                                                                                        Date date = new Date();
                                                                                                                                                                        String s=dateFormat.format(date);
                                                                                                                                                                        String randomid="GTR"+randomNumber;
                                                                                                                                                                        String ammount1=ammount;
                                                                                                                                                                        float tax=(Float.parseFloat(ammount1)*1)/100;
                                                                                                                                                                        SendingModel sendingModel=new SendingModel("Transfer",ts,s,
                                                                                                                                                                                randomid,"Receiver ID : ",ammount1,""+tax,ammount1,toidi.toLowerCase().toString());
                                                                                                                                                                        firebaseFirestore.collection("My_Sending")
                                                                                                                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                                                                .collection("List")
                                                                                                                                                                                .add(sendingModel)
                                                                                                                                                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                                                        if (task.isSuccessful()) {

                                                                                                                                                                                            Calendar calendar = Calendar.getInstance();
                                                                                                                                                                                            String current = java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL).format(calendar.getTime());
                                                                                                                                                                                            String current1 = java.text.DateFormat.getDateInstance().format(calendar.getTime());
                                                                                                                                                                                            Random r = new Random();
                                                                                                                                                                                            int randomNumber = r.nextInt(1002563985);

                                                                                                                                                                                            //int ttt = Integer.parseInt(packageprice)*5/100;;
                                                                                                                                                                                            // int randomNumber = r.nextInt(1002563985);
                                                                                                                                                                                            String randomid="DLB"+randomNumber;
                                                                                                                                                                                            Income income=new Income("refername1","Fund receive from user",firebaseAuth.getCurrentUser().getEmail(),
                                                                                                                                                                                                    String.valueOf(ammount1),current1,ts,randomid,"0");
                                                                                                                                                                                            firebaseFirestore.collection("Fund_History") .document(toidi+"@gmail.com")
                                                                                                                                                                                                    .collection("List")
                                                                                                                                                                                                    .add(income)
                                                                                                                                                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                                                                                                        @Override
                                                                                                                                                                                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                                                                                                                                                            if (task.isSuccessful()) {
                                                                                                                                                                                                                kProgressHUD.dismiss();
                                                                                                                                                                                                                new AestheticDialog.Builder(SendMyDarling.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                                                                                                        .setTitle("Conformation")
                                                                                                                                                                                                                        .setMessage("Balance Transfering is Successfully Done.")
                                                                                                                                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                                                                                                builder.dismiss();
                                                                                                                                                                                                                                startActivity(new Intent(getApplicationContext(),SecondHome.class));

                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }).show();
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }
                                                                                                                                                                                                    });

                                                                                                                                                                                            //Toasty.success(getApplicationContext(), "Balance transfer is done.", Toast.LENGTH_SHORT, true).show();
                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                });


                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            });
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    });

                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            });

                                                                                                }
                                                                                            }
                                                                                        });
                                                                            }


                                                                        }
                                                                    }
                                                                }
                                                            });

                                                }
                                                else {
                                                    kProgressHUD.dismiss();
                                                    Toasty.error(getApplicationContext(), "Password is incorrect.", Toast.LENGTH_SHORT, true).show();
                                                }
                                            }
                                        }
                                    }
                                }
                            });

                }
            }
        });

    }

    private void pisehast(String ammount) {
        firebaseFirestore.collection("TotalCoin")
                .document("abc@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                String amount=task.getResult().getString("amount");
                                float main=(Float.parseFloat(ammount))+(Float.parseFloat(amount));
                                firebaseFirestore.collection("TotalCoin")
                                        .document("abc@gmail.com")
                                        .update("amount",""+main)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        }
                    }
                });
    }


    private void progress_check() {
        kProgressHUD = KProgressHUD.create(SendMyDarling.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Checking Data")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),SecondHome.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(),SecondHome.class));
        return true;
    }
    TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //none
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //none
        }

        @Override
        public void afterTextChanged(Editable s) {

            check = s.toString();
            ;


            textammount.setVisibility(View.VISIBLE);
            firebaseFirestore.collection("User2")
                    .document(check+"@gmail.com")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    try {
                                        textammount.setText(task.getResult().getString("name"));
                                    }catch (Exception e ) {
                                        textammount.setText(task.getResult().getString("name"));
                                    }
                                }
                                else {
                                    textammount.setText("No Current User");
                                }
                            }
                            else {
                                textammount.setText("No Current User");
                            }
                        }
                    });
        }

    };
}