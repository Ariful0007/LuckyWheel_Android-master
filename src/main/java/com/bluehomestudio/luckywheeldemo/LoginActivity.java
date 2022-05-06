package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText edtemail,edtpass;
    private String email,pass,email_gating;
    private TextView appname,forgotpass,registernow;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser user;
    private String userID;
    UserSession session;
    FirebaseAuth firebaseAuth;
    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Validating login details
        Log.e("Login CheckPoint","LoginActivity started");
        //check Internet Connection
        session= new UserSession(getApplicationContext());
        //new CheckInternetConnection(this).checkConnection();
        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseApp.initializeApp(LoginActivity.this);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        // appname = findViewById(R.id.appname);
        // appname.setTypeface(typeface);
        new CheckInternetConnection(this).checkConnection();
        edtemail= findViewById(R.id.editTextEmail);
        edtpass= findViewById(R.id.editTextPassword);

        Button login_button=findViewById(R.id.cirLoginButton);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email_gating=edtemail.getText().toString();
                email=email_gating+"@gmail.com";
                pass=edtpass.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){

                    String text1=email_gating;
                    String tet2=pass;
                    if (TextUtils.isEmpty(text1)||TextUtils.isEmpty(tet2)) {
                        Toasty.error(getApplicationContext(),"Give all information", Toast.LENGTH_SHORT,true).show();
                    }
                    else {
                        final KProgressHUD progressDialog=  KProgressHUD.create(LoginActivity.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Checking Data.....")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        firebaseFirestore.collection("BlockList")
                                .document(text1.toLowerCase().toString()+"@gmail.com")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                progressDialog.dismiss();
                                                Toasty.error(getApplicationContext(),"You  are in blook list.", Toast.LENGTH_SHORT,true).show();
                                            }
                                            else {
                                                firebaseAuth.signInWithEmailAndPassword(text1.toString().toLowerCase() +"@gmail.com","123456").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        if(task.isSuccessful()){
                                                            firebaseFirestore.collection("Password")
                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                if (task.getResult().exists()) {
                                                                                    String pass=task.getResult().getString("uuid");
                                                                                    if (pass.contains(tet2.toLowerCase())) {
                                                                                        userID = firebaseAuth.getCurrentUser().getUid();
                                                                                        firebaseFirestore.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                                                                                if (task.isSuccessful()) {

                                                                                                    if (task.getResult().exists()) {


                                                                                                        String sessionname = task.getResult().getString("name");
                                                                                                        String sessionmobile = task.getResult().getString("number");
                                                                                                        String sessionphoto = task.getResult().getString("image");
                                                                                                        String sessionemail = task.getResult().getString("email");
                                                                                                        String sessionusername = task.getResult().getString("username");


                                                                                                        session.createLoginSession(sessionname,sessionemail,sessionmobile,sessionphoto,sessionusername);


                                                                                                        Toasty.success(getApplicationContext(), "Login Successfully .", Toasty.LENGTH_SHORT, true).show();

                                                                                                        Intent loginSuccess = new Intent(LoginActivity.this, HomeACTIVITY.class);

                                                                                                        startActivity(loginSuccess);
                                                                                                        finish();

                                                                                                    }
                                                                                                } else {
                                                                                                    progressDialog.dismiss();
                                                                                                    Toast.makeText(LoginActivity.this, "Login Error. Please try again.", Toast.LENGTH_SHORT).show();
                                                                                                }

                                                                                            }
                                                                                        });
                                                                                    }
                                                                                    else {
                                                                                        progressDialog.dismiss();
                                                                                        Toasty.error(getApplicationContext(),"Password not match", Toast.LENGTH_SHORT,true).show();
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    });





                                                        } else {
                                                            progressDialog.dismiss();
                                                            Toasty.error(LoginActivity.this,"Couldn't Log In. Please check your Email/Password",2000).show();
                                                        }
                                                    }
                                                });

                                            }

                                        }
                                    }
                                });
                    }


//////////////////////////
                }

            }
        });
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void onclieck(View view) {
        Toasty.info(getApplicationContext(),"Coming....",Toasty.LENGTH_SHORT,true).show();
    }
    @Override
    protected void onResume() {
        new CheckInternetConnection(this).checkConnection();
        super.onResume();
    }

    public void onmyu(View view) {
        final FlatDialog flatDialog1 = new FlatDialog(LoginActivity.this);
        flatDialog1.setTitle("Forget Password")
                .setSubtitle("User forget his/her password.Now  you change it.")
                .setFirstTextFieldHint("Username")
                .setSecondTextFieldHint("password")
                .setFirstButtonText("Change")
                .setSecondButtonText("Cancel")
                .withFirstButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flatDialog1.dismiss();
                        final KProgressHUD progressDialog=  KProgressHUD.create(LoginActivity.this)
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Please wait")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
                        firebaseFirestore.collection("Password")
                                .document(flatDialog1.getFirstTextField().toLowerCase()+"@gmail.com")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                firebaseFirestore.collection("Password")
                                                        .document(flatDialog1.getFirstTextField().toLowerCase()+"@gmail.com")
                                                        .update("uuid", flatDialog1.getSecondTextField().toLowerCase())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    firebaseFirestore.collection("All_UserID")
                                                                            .document(flatDialog1.getFirstTextField().toLowerCase()+"@gmail.com")
                                                                            .get()
                                                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        if (task.getResult().exists()) {
                                                                                            String uuid=task.getResult().getString("uuid");
                                                                                            firebaseFirestore.collection("Users")
                                                                                                    .document(uuid)
                                                                                                    .get()
                                                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                            if (task.isSuccessful()) {
                                                                                                                if (task.getResult().exists()) {
                                                                                                                    firebaseFirestore.collection("Users")
                                                                                                                            .document(uuid)
                                                                                                                            .update("pass", flatDialog1
                                                                                                                                    .getSecondTextField().toLowerCase())
                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                        progressDialog.dismiss();

                                                                                                                                        Toasty.success(getApplicationContext(),"Done",Toasty.LENGTH_SHORT,true).show();
                                                                                                                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                                                                                                        finish();
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
                                            else {
                                                progressDialog.dismiss();
                                                Toasty.error(getApplicationContext(),"No user found",Toasty.LENGTH_SHORT,true).show();
                                            }
                                        }
                                    }
                                });
                    }
                })
                .withSecondButtonListner(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        flatDialog1.dismiss();
                    }
                })
                .show();
    }
}