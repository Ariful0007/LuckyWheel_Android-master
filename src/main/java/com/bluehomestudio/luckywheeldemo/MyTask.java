package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.Calendar;
import java.util.HashMap;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class MyTask extends AppCompatActivity {
    KProgressHUD kProgressHUD;
    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();

    private UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile,username;
    private Drawer result;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String user1;

    IProfile profile;
    TextView nameTv,emailTv;
    ImageView profileImage;
    TextView coinsT1v;
    CardView dailyCheckCard,luckySpinCard,aboutCard1,aboutCard,redeemCard,referCard,taskCard;
    FirebaseFirestore firebaseFirestore;
    String sessionname,sessionmobile,sessionphoto,sessionemail,sessionusername;
    int count,count1,count2,count3;
    String package_actove;
    String daily_bonus;
    String incomeType="Daily Task";
    int main_account;
    int count12,count123;
    int main_refer;
    String main_task ;
    TextView taskid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("My Task");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        nameTv=findViewById(R.id.nameTv);
        emailTv=findViewById(R.id.emailTv);
        //coinsT1v=findViewById(R.id.coinsT1v);
        Calendar calendar=Calendar.getInstance();
        final  int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        taskid=findViewById(R.id.taskid);
        firebaseFirestore.collection("DailyEarn")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection(""+year)
                .document(""+month)
                .collection(""+day)
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                              taskid.setText("6/6");
                            }
                            else {
taskid.setText("1/6");
                            }
                        }
                    }
                });

        CircularProgressButton cirLoginButton=findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore.collection("DailyEarn")
                        .document(firebaseAuth.getCurrentUser().getEmail())
                        .collection(""+year)
                        .document(""+month)
                        .collection(""+day)
                        .document(firebaseAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        new AestheticDialog.Builder(MyTask.this, DialogStyle.FLASH, DialogType.WARNING)
                                                .setTitle("Warning")
                                                .setMessage("You are alreday clime  task for today.\nWait for next day's task.")
                                                .setAnimation(DialogAnimation.SPLIT)
                                                .setOnClickListener(new OnDialogClickListener() {
                                                    @Override
                                                    public void onClick(AestheticDialog.Builder builder) {
                                                        builder.dismiss();

                                                    }
                                                }).show();
                                    }
                                    else {
                                        //startActivity(new Intent(getApplicationContext(),TaskCardActivity.class));
                                    }
                                }
                                else {
                                   // startActivity(new Intent(getApplicationContext(),TaskCardActivity.class));
                                }
                            }
                        });
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
                                    nameTv.setText(task.getResult().getString("username"));
                                    emailTv.setText(task.getResult().getString("email"));
                                }catch (Exception e) {
                                    nameTv.setText(task.getResult().getString("username"));
                                    emailTv.setText(task.getResult().getString("email"));
                                }
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