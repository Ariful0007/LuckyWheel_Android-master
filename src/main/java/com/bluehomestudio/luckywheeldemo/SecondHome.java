package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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


import es.dmoral.toasty.Toasty;

public class SecondHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_home);
        Toolbar toolbar = findViewById(R.id.toolbar);

        blocking=findViewById(R.id.blocking);
        HomeFragment fragment2 = new HomeFragment();


        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.content, fragment2, "");
        ft2.commit();
        earning=findViewById(R.id.earning);
        deposite=findViewById(R.id.deposite);



        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        progressHUD = KProgressHUD.create(SecondHome.this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
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

    private void anim() {
        lftToRgt = ObjectAnimator.ofFloat( deposite,"translationX",0f,halfW )
                .setDuration(700); // to animate left to right
        rgtToLft = ObjectAnimator.ofFloat( deposite,"translationX",halfW,0f )
                .setDuration(700); // to deposite right to left

        animatorSet.play( lftToRgt ).before( rgtToLft ); // manage sequence
        animatorSet.start(); // play the animation
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {


            case R.id.addNote:
               drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                HomeFragment homeFragment = new HomeFragment();


                FragmentTransaction ft2w = getSupportFragmentManager().beginTransaction();
                ft2w.replace(R.id.content, homeFragment, "");

                ft2w.commit();
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                Toasty.success(getApplicationContext(),"You Are Home Now",Toasty.LENGTH_SHORT,true).show();
                break;

            case R.id.addTab:
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case R.id.shareapp2:
                AlertDialog.Builder warning = new AlertDialog.Builder(SecondHome.this)
                        .setTitle("Logout")
                        .setMessage("Are you want to logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();



                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ToDO: delete all the notes created by the Anon user


                                firebaseAuth.signOut();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();


                            }
                        });

                warning.show();


                break;
        }


        return false;
    }

    public void logout(View view) {
        Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {


            AlertDialog.Builder builder = new AlertDialog.Builder(SecondHome.this);
            builder.setTitle("Exit")
                    .setMessage("Are you want to exit?")
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                        }
                    }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finishAffinity();
                }
            });
            builder.create().show();
        }

    public void checkdata(View view) {
        Intent intent=new Intent(getApplicationContext(),DepositeActivity.class);
        startActivity(intent);
    }

    public void checkdata2(View view) {
        Intent intent=new Intent(getApplicationContext(),CashWithdrawal.class);
        startActivity(intent);
    }
}