package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;


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

import java.util.Random;

public class SpinActivityy extends AppCompatActivity {
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
    String points="0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_activityy);

        //generateWheelItems();
    //   lw = findViewById(R.id.lwv);
       // wheelItems = new ArrayList<>();
       /*
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.chat) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.coupon) , "0 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ice_cream), "30 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.lemonade), "6000 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.orange), "9 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "5 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "8 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "3 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "1 $"));
        */


     /*
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.chat) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.coupon) , "0 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ice_cream), "10 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.lemonade), "20 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.orange), "30 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "40 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "50 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "60 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "70 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "80$"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "90 $"));
        Random r = new Random();
        int randomNumber = r.nextInt(1000);
        lw.setTarget(randomNumber);

lw.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
    @Override
    public void onReachTarget() {


    }
});
      */


        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int randomNumber = r.nextInt(1000);
                //lw.rotateWheelTo(randomNumber);
                /*
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
                                                                            lw.rotateWheelTo(1);
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
                                                                            lw.rotateWheelTo(1);
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

                               /*
                                AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);
                                builder.setTitle("All Balance")
                                        .setMessage("Purchase Balance : "+purches_balance+"\n" +
                                                "Income Balance : "+self_income+"\n" +
                                                "Shopping Balabce : "+shoping_balance+"\n" +
                                                "Work Balance : "+daily_income);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                                */


                            }
                        }
                    }
                });


    }

   /*
    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.chat) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.coupon) , "0 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ice_cream), "30 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.lemonade), "6000 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.orange), "9 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "20 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "20 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "20 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3FBD51"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "20 $"));
    }
    */



}