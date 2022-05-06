package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;
import com.bluehomestudio.luckywheeldemo.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class MySpinn extends AppCompatActivity {
    ActivityMainBinding binding;
    private LuckyWheel lw;
    List<WheelItem> wheelItems;
    // private LuckyWheel lw;
    // List<WheelItem> wheelItems=new ArrayList<>() ;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    CardView card_view2;
    KProgressHUD progressHUD;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String  email;
    int count = 0,count1=0;
    TextView totaluser,paid,pending,blocking,notificationn,invalid,todaystask,margo_signup;
    int count11=0,countpaid,block=0;


    //
    EditText methodename,minwith,hint;
    Button logo,login_button;
    ImageView myImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    Dialog mDialog;
    EditText name,ammount;
    TextView earning,deposite;
    Animation animation;

    private ObjectAnimator lftToRgt,rgtToLft;
    private ImageView imageView;
    private float halfW;
    private AnimatorSet animatorSet;//required to set the sequence
    String points;
    int flag=0;
    int rann,main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spinn);
        //generateWheelItems();

        lw = findViewById(R.id.lwv);
        wheelItems=new ArrayList<>();

        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#4d85bd"), BitmapFactory.decodeResource(getResources(),
                R.drawable.mydollae) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#867666"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa) , "0 "));
        wheelItems.add(new WheelItem(Color.parseColor("#ff420e"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "10 "));
        wheelItems.add(new WheelItem(Color.parseColor("#80bd9e"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "20 "));
        wheelItems.add(new WheelItem(Color.parseColor("#d9b44a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "30 "));
        wheelItems.add(new WheelItem(Color.parseColor("#90afc5"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "40 "));
        wheelItems.add(new WheelItem(Color.parseColor("#336b87"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "50 "));
        wheelItems.add(new WheelItem(Color.parseColor("#2a3132"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "60 "));
        wheelItems.add(new WheelItem(Color.parseColor("#763626"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "70 "));
        wheelItems.add(new WheelItem(Color.parseColor("#c0b2b5"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "80"));
        wheelItems.add(new WheelItem(Color.parseColor("#003b46"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "90 "));
        wheelItems.add(new WheelItem(Color.parseColor("#3f6c45"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "110 "));
        lw.addWheelItems(wheelItems);


lw.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
    @Override
    public void onReachTarget() {

      ///  Toast.makeText(MySpinn.this, ""+flag, Toast.LENGTH_SHORT).show();

            getting__gift(""+rann,""+main);


    }
});



        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String options[]={"Spin 1$      Luckily 100$","Spin 100$  Luckily 10K$","Spin 1K$    Luckily 100K$"};
                AlertDialog.Builder builder=new AlertDialog.Builder(MySpinn.this);
                builder.setTitle("Lucky Wheel Options")
                        .setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i==0) {
                                    wheelItems = new ArrayList<>();
                                    wheelItems.add(new WheelItem(Color.parseColor("#4d85bd"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.mydollae) , "100 $"));
                                    wheelItems.add(new WheelItem(Color.parseColor("#867666"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa) , "0 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#ff420e"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "10 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#80bd9e"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "20 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#d9b44a"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "30 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#90afc5"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "40 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#336b87"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "50 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#2a3132"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "60 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#763626"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "70 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#c0b2b5"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "80"));
                                    wheelItems.add(new WheelItem(Color.parseColor("#003b46"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "90 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#3f6c45"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "110 "));
                                    lw.addWheelItems(wheelItems);
                                    Random r = new Random();
                                    int randomNumber = r.nextInt(80);
                                    lw.setTarget(randomNumber);
                                    //Toast.makeText(MySpinn.this, ""+randomNumber, Toast.LENGTH_SHORT).show();


                                    //lw.rotateWheelTo(randomNumber);
                                    firebaseFirestore.collection("Wheel")
                                            .document("abc@gmail.com")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {
                                                            try {
                                                                String get=task.getResult().getString("get");
                                                                int a=Integer.parseInt(get);
                                                                if (a==0) {
                                                                    //  lw.rotateWheelTo(1);


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
                                                                                            String shoping_balance=task.getResult().getString("main_balance");
                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                            String self_income=task.getResult().getString("monthly_income");
                                                                                            String daily_income=task.getResult().getString("daily_income");
                                                                                            if (Integer.parseInt(purches_balance)==0) {
                                                                                                Toasty.info(getApplicationContext(),"You have not enough money.",Toasty.LENGTH_SHORT,true).show();
                                                                                            }
                                                                                            else {
                                                                                                flag=1;
                                                                                                lw.rotateWheelTo(randomNumber);
                                                                                                //getting__gift(""+randomNumber,shoping_balance);
                                                                                                rann=randomNumber;
                                                                                                main=Integer.parseInt(shoping_balance);
                                                                                                int pur=Integer.parseInt(purches_balance)-1;
                                                                                                firebaseFirestore.collection("Users")
                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                        .collection("Main_Balance")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .update("purches_balance",""+pur)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    Toasty.success(getApplicationContext(),"Go.",Toasty.LENGTH_SHORT,true).show();

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
                                                                    Toasty.error(getApplicationContext(),"Now Spin is off.",Toasty.LENGTH_SHORT,true).show();
                                                                }
                                                            }catch (Exception e) {
                                                                String get=task.getResult().getString("get");
                                                                int a=Integer.parseInt(get);
                                                                if (a==0) {



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
                                                                                            String shoping_balance=task.getResult().getString("main_balance");
                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                            String self_income=task.getResult().getString("monthly_income");
                                                                                            String daily_income=task.getResult().getString("daily_income");
                                                                                            if (Integer.parseInt(purches_balance)==0) {
                                                                                                Toasty.info(getApplicationContext(),"You have not enough money.",Toasty.LENGTH_SHORT,true).show();
                                                                                            }
                                                                                            else {
                                                                                                flag=1;
                                                                                                lw.rotateWheelTo(randomNumber);
                                                                                                rann=randomNumber;
                                                                                                main=Integer.parseInt(shoping_balance);
                                                                                                int pur=Integer.parseInt(purches_balance)-1;
                                                                                                firebaseFirestore.collection("Users")
                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                        .collection("Main_Balance")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .update("purches_balance",""+pur)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    Toasty.success(getApplicationContext(),"Go.",Toasty.LENGTH_SHORT,true).show();

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
                                                                    Toasty.error(getApplicationContext(),"Now Spin is off.",Toasty.LENGTH_SHORT,true).show();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                }
                                else if(i==1) {
                                    wheelItems = new ArrayList<>();
                                    wheelItems.add(new WheelItem(Color.parseColor("#4d85bd"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.mydollae) , "10 K$ "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#867666"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa) , "0 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#ff420e"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "100 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#80bd9e"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "200 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#d9b44a"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "300 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#90afc5"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "400 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#336b87"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "500 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#2a3132"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "600 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#763626"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "700 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#c0b2b5"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "800"));
                                    wheelItems.add(new WheelItem(Color.parseColor("#003b46"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "900 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#3f6c45"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "1100 "));
                                    lw.addWheelItems(wheelItems);
                                    Random r = new Random();
                                    int randomNumber = r.nextInt(800);
                                    lw.setTarget(randomNumber);
                                    //Toast.makeText(MySpinn.this, ""+randomNumber, Toast.LENGTH_SHORT).show();


                                    //lw.rotateWheelTo(randomNumber);
                                    firebaseFirestore.collection("Wheel")
                                            .document("abc@gmail.com")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {
                                                            try {
                                                                String get=task.getResult().getString("get");
                                                                int a=Integer.parseInt(get);
                                                                if (a==0) {
                                                                    //  lw.rotateWheelTo(1);

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
                                                                                            String shoping_balance=task.getResult().getString("main_balance");
                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                            String self_income=task.getResult().getString("monthly_income");
                                                                                            String daily_income=task.getResult().getString("daily_income");
                                                                                            if (Integer.parseInt(purches_balance)<=99) {
                                                                                                Toasty.info(getApplicationContext(),"You have not enough money.",Toasty.LENGTH_SHORT,true).show();
                                                                                            }
                                                                                            else {
                                                                                                flag=2;
                                                                                                lw.rotateWheelTo(randomNumber);
                                                                                                int pur=Integer.parseInt(purches_balance)-100;
                                                                                                rann=randomNumber;
                                                                                                main=Integer.parseInt(shoping_balance);
                                                                                                firebaseFirestore.collection("Users")
                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                        .collection("Main_Balance")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .update("purches_balance",""+pur)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    Toasty.success(getApplicationContext(),"Go.",Toasty.LENGTH_SHORT,true).show();

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
                                                                    Toasty.error(getApplicationContext(),"Now Spin is off.",Toasty.LENGTH_SHORT,true).show();
                                                                }
                                                            }catch (Exception e) {
                                                                String get=task.getResult().getString("get");
                                                                int a=Integer.parseInt(get);
                                                                if (a==0) {



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
                                                                                            String shoping_balance=task.getResult().getString("main_balance");
                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                            String self_income=task.getResult().getString("monthly_income");
                                                                                            String daily_income=task.getResult().getString("daily_income");
                                                                                            if (Integer.parseInt(purches_balance)<=99) {
                                                                                                Toasty.info(getApplicationContext(),"You have not enough money.",Toasty.LENGTH_SHORT,true).show();
                                                                                            }
                                                                                            else {
                                                                                                flag=2;
                                                                                                lw.rotateWheelTo(randomNumber);
                                                                                                int pur=Integer.parseInt(purches_balance)-100;
                                                                                                rann=randomNumber;
                                                                                                main=Integer.parseInt(shoping_balance);
                                                                                                firebaseFirestore.collection("Users")
                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                        .collection("Main_Balance")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .update("purches_balance",""+pur)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    Toasty.success(getApplicationContext(),"Go.",Toasty.LENGTH_SHORT,true).show();

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
                                                                    Toasty.error(getApplicationContext(),"Now Spin is off.",Toasty.LENGTH_SHORT,true).show();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                }
                                else if(i==2) {
                                    wheelItems = new ArrayList<>();
                                    wheelItems.add(new WheelItem(Color.parseColor("#4d85bd"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.mydollae) , "100 K$ "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#867666"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa) , "0 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#ff420e"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "1000 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#80bd9e"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "2000 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#d9b44a"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "3000 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#90afc5"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "4000 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#336b87"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "5000 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#2a3132"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "600 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#763626"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "7000 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#c0b2b5"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "8000"));
                                    wheelItems.add(new WheelItem(Color.parseColor("#003b46"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "9000 "));
                                    wheelItems.add(new WheelItem(Color.parseColor("#3f6c45"), BitmapFactory.decodeResource(getResources(),
                                            R.drawable.iaaa), "11000 "));
                                    lw.addWheelItems(wheelItems);
                                    Random r = new Random();
                                    int randomNumber = r.nextInt(8000);
                                    lw.setTarget(randomNumber);
                                    //Toast.makeText(MySpinn.this, ""+randomNumber, Toast.LENGTH_SHORT).show();


                                    //lw.rotateWheelTo(randomNumber);
                                    firebaseFirestore.collection("Wheel")
                                            .document("abc@gmail.com")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().exists()) {
                                                            try {
                                                                String get=task.getResult().getString("get");
                                                                int a=Integer.parseInt(get);
                                                                if (a==0) {
                                                                    //  lw.rotateWheelTo(1);

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
                                                                                            String shoping_balance=task.getResult().getString("main_balance");
                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                            String self_income=task.getResult().getString("monthly_income");
                                                                                            String daily_income=task.getResult().getString("daily_income");
                                                                                            if (Integer.parseInt(purches_balance)<=999) {
                                                                                                Toasty.info(getApplicationContext(),"You have not enough money.",Toasty.LENGTH_SHORT,true).show();
                                                                                            }
                                                                                            else {
                                                                                                flag=3;
                                                                                                lw.rotateWheelTo(randomNumber);
                                                                                                int pur=Integer.parseInt(purches_balance)-1000;
                                                                                                rann=randomNumber;
                                                                                                main=Integer.parseInt(shoping_balance);
                                                                                                firebaseFirestore.collection("Users")
                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                        .collection("Main_Balance")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .update("purches_balance",""+pur)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    Toasty.success(getApplicationContext(),"Go.",Toasty.LENGTH_SHORT,true).show();

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
                                                                    Toasty.error(getApplicationContext(),"Now Spin is off.",Toasty.LENGTH_SHORT,true).show();
                                                                }
                                                            }catch (Exception e) {
                                                                String get=task.getResult().getString("get");
                                                                int a=Integer.parseInt(get);
                                                                if (a==0) {



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
                                                                                            String shoping_balance=task.getResult().getString("main_balance");
                                                                                            String purches_balance=task.getResult().getString("purches_balance");
                                                                                            String self_income=task.getResult().getString("monthly_income");
                                                                                            String daily_income=task.getResult().getString("daily_income");
                                                                                            if (Integer.parseInt(purches_balance)<=999) {
                                                                                                Toasty.info(getApplicationContext(),"You have not enough money.",Toasty.LENGTH_SHORT,true).show();
                                                                                            }
                                                                                            else {
                                                                                                flag=3;
                                                                                                lw.rotateWheelTo(randomNumber);
                                                                                                int pur=Integer.parseInt(purches_balance)-1000;
                                                                                                rann=randomNumber;
                                                                                                main=Integer.parseInt(shoping_balance);
                                                                                                firebaseFirestore.collection("Users")
                                                                                                        .document(firebaseAuth.getCurrentUser().getUid())
                                                                                                        .collection("Main_Balance")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .update("purches_balance",""+pur)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    Toasty.success(getApplicationContext(),"Go.",Toasty.LENGTH_SHORT,true).show();

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
                                                                    Toasty.error(getApplicationContext(),"Now Spin is off.",Toasty.LENGTH_SHORT,true).show();
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            });
                                }
                            }
                        }).create().show();

                /*

                 */

            }
        });
        earning=findViewById(R.id.earning);
        deposite=findViewById(R.id.deposite);
        firebaseAuth= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
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
                                String purches_balance=task.getResult().getString("main_balance");
                                String shoping_balance=task.getResult().getString("purches_balance");
                                String self_income=task.getResult().getString("monthly_income");
                                String daily_income=task.getResult().getString("daily_income");
                                earning.setText("Diamond : ◇"+purches_balance);
                                deposite.setText("Earning : $"+shoping_balance);



                            }
                        }
                    }
                });


    }

    private void getting__gift(String s, String shoping_balance) {
        int total=Integer.parseInt(s)+Integer.parseInt(shoping_balance);
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .collection("Main_Balance")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .update("main_balance",""+total)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            firebaseFirestore.collection("User2")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    try {
                                                        String username=task.getResult().getString("username");
                                                        Calendar calendar = Calendar.getInstance();
                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                        String ts = tsLong.toString();
                                                        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                        String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                        Income income = new Income(task.getResult().getString("username"), "Wheel Income", firebaseAuth.getCurrentUser().getEmail(),
                                                                ""+s, current1,ts,"23564","5455");
                                                      //  String username=task.getResult().getString("username");
                                                        firebaseFirestore.collection("Income_History").document(firebaseAuth.getCurrentUser().getEmail())
                                                                .collection("List")
                                                                .document(UUID.randomUUID().toString())
                                                                .set(income)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            new AestheticDialog.Builder(MySpinn.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                    .setTitle("Conformation")
                                                                                    .setMessage("You got diamond for wheeling this spin")
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

                                                    }catch (Exception e) {
                                                        String username=task.getResult().getString("username");
                                                        Calendar calendar = Calendar.getInstance();
                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                        String ts = tsLong.toString();
                                                        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                        String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                                                        Income income = new Income(task.getResult().getString("username"), "Wheel Income", firebaseAuth.getCurrentUser().getEmail(),
                                                                ""+s, current1,ts,"23564","5455");
                                                        //  String username=task.getResult().getString("username");
                                                        firebaseFirestore.collection("Income_History").document(firebaseAuth.getCurrentUser().getEmail())
                                                                .collection("List")
                                                                .document(UUID.randomUUID().toString())
                                                                .set(income)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            new AestheticDialog.Builder(MySpinn.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                    .setTitle("Conformation")
                                                                                    .setMessage("You got diamond for wheeling this spin")
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

                                                    }
                                                }
                                            }
                                        }
                                    });

                        }
                    }
                });
    }




}