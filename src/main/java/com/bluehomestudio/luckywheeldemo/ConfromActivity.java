package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class ConfromActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinnerTextSize, spinnerTextSize1, spinnerTextSize2;
    EditText Email_Log;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    TextView no_of_items, total_amount, spinner4;
    String package_name, package_price, packing;
    EditText spinner1, spinner2;
    Button upgrade;
    KProgressHUD kProgressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrom);
        upgrade = findViewById(R.id.upgrade);
        kProgressHUD = KProgressHUD.create(ConfromActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Payment");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        no_of_items = findViewById(R.id.no_of_items);
        spinner4 = findViewById(R.id.spinner4);
        total_amount = findViewById(R.id.total_amount);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        try {
            packing = getIntent().getStringExtra("packing");
            package_name = getIntent().getStringExtra("package");
            package_price = getIntent().getStringExtra("price");
            no_of_items.setText(package_name);
            total_amount.setText(package_price);
        } catch (Exception e) {
            packing = getIntent().getStringExtra("packing");
            package_name = getIntent().getStringExtra("package");
            package_price = getIntent().getStringExtra("price");
            no_of_items.setText(package_name);
            total_amount.setText(package_price);
        }
        spinnerTextSize = findViewById(R.id.spinner3);
        spinnerTextSize.setOnItemSelectedListener(this);

        String[] textSizes = getResources().getStringArray(R.array.payment);
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_row, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(valueFromSpinner) || TextUtils.isEmpty(spinner1.getText().toString()) ||
                        TextUtils.isEmpty(spinner2.getText().toString()) ) {
                    Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_SHORT, true).show();
                    return;

                } else {
                    if (valueFromSpinner.contains("Select Payment Methode")) {
                        Toasty.info(getApplicationContext(), "Select Your Payment Method", Toast.LENGTH_SHORT, true).show();
                        return;
                    } else {
                        progress_check();
                        Calendar calendar = Calendar.getInstance();
                        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                        String current1 = DateFormat.getDateInstance().format(calendar.getTime());
                        firebaseFirestore.collection("Users")
                                .document(firebaseAuth.getCurrentUser().getUid())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                String username = task.getResult().getString("username");
                                                String uuid = UUID.randomUUID().toString();
                                                Package packag1 = new Package(firebaseAuth.getCurrentUser().getEmail(),
                                                        packing, package_price, valueFromSpinner, spinner1.getText().toString(), spinner2.getText().toString()
                                                        , uuid, firebaseAuth.getCurrentUser().getUid(), "Pending", username, current1);
                                                firebaseFirestore.collection("Admin_PackageRequest")
                                                        .document(uuid)
                                                        .set(packag1)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    firebaseFirestore.collection("Subadmin")
                                                                            .document("Packages")
                                                                            .collection("101")
                                                                            .document(uuid)
                                                                            .set(packag1)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        firebaseFirestore.collection("MyPackage")
                                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                .collection("List")
                                                                                                .document(uuid)
                                                                                                .set(packag1)
                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        if (task.isSuccessful()) {
                                                                                                            kProgressHUD.dismiss();
                                                                                                            Toasty.success(getApplicationContext(), "Package Request  Successfully Done.\nAdmin Will Check Your Request.", Toast.LENGTH_SHORT, true).show();
                                                                                                            startActivity(new Intent(getApplicationContext(), BuyCoin.class));
                                                                                                            finish();
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
                                    }
                                });

                    }
                }
            }
        });


        //

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner3) {
            valueFromSpinner = parent.getItemAtPosition(position).toString();
            if (valueFromSpinner.contains("PayPal")) {
                firebaseFirestore.collection("Payment")
                        .document("abc@gmail.com")
                        .collection("1")
                        .document("abc@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        try {
                                            spinner4.setText(task.getResult().getString("number"));
                                            spinner1.setVisibility(View.VISIBLE);
                                            spinner2.setVisibility(View.VISIBLE);
                                        }catch (Exception e) {
                                            spinner4.setText(task.getResult().getString("number"));
                                            spinner1.setVisibility(View.VISIBLE);
                                            spinner2.setVisibility(View.VISIBLE);
                                        }
                                    }
                                    else {
                                        spinner4.setText("No account Found");
                                        spinner1.setVisibility(View.VISIBLE);
                                        spinner2.setVisibility(View.VISIBLE);
                                        spinner1.setText("");
                                        spinner2.setText("");
                                    }
                                }
                                else {
                                    spinner4.setText("No account Found");
                                    spinner1.setVisibility(View.VISIBLE);
                                    spinner2.setVisibility(View.VISIBLE);
                                    spinner1.setText("");
                                    spinner2.setText("");
                                }
                            }
                        });
                // spinner4.setText("Call Admin");
            }  else if (valueFromSpinner.contains("Payoneer")) {
                firebaseFirestore.collection("Payment")
                        .document("abc@gmail.com")
                        .collection("2")
                        .document("abc@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {

                                        try {
                                            spinner4.setText(task.getResult().getString("number"));
                                            spinner1.setVisibility(View.VISIBLE);
                                            spinner2.setVisibility(View.VISIBLE);
                                        }catch (Exception e) {
                                            spinner1.setVisibility(View.VISIBLE);
                                            spinner2.setVisibility(View.VISIBLE);
                                            spinner4.setText(task.getResult().getString("number"));
                                        }
                                    }
                                    else {
                                        spinner4.setText("No account Found");
                                        spinner1.setVisibility(View.VISIBLE);
                                        spinner2.setVisibility(View.VISIBLE);
                                        spinner1.setText("");
                                        spinner2.setText("");
                                    }
                                }
                                else {
                                    spinner4.setText("No account Found");
                                    spinner1.setVisibility(View.VISIBLE);
                                    spinner2.setVisibility(View.VISIBLE);
                                    spinner1.setText("");
                                    spinner2.setText("");
                                }
                            }
                        });
            }

            //
            else if (valueFromSpinner.contains("MasterCard")) {
                firebaseFirestore.collection("Payment")
                        .document("abc@gmail.com")
                        .collection("3")
                        .document("abc@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {

                                        try {
                                            spinner4.setText(task.getResult().getString("number"));
                                            spinner1.setVisibility(View.GONE);
                                            spinner2.setVisibility(View.GONE);
                                            spinner1.setText("MasterCard");
                                            spinner2.setText("MasterCard");

                                            //
                                            mDialog = new Dialog(ConfromActivity.this);
                                            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            //LayoutInflater factory = LayoutInflater.from(this);
                                            //final View deleteDialogView = factory.inflate(R.layout.dialog_contact, null);
                                            //  mDialog.setContentView(deleteDialogView);
                                            mDialog.setContentView(R.layout.dialouge2);
                                            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            FloatingActionButton dialogClose=(FloatingActionButton)mDialog.findViewById(R.id.dialogClose);
                                            EditText methodename=(EditText)mDialog.findViewById(R.id.methodename);
                                            EditText minwith=(EditText)mDialog.findViewById(R.id.minwith);
                                            EditText distance=(EditText)mDialog.findViewById(R.id.distance);
                                            EditText minwit23h=(EditText)mDialog.findViewById(R.id.minwit23h);

                                            Button login_button=(Button)mDialog.findViewById(R.id.login_button);
                                            login_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(TextUtils.isEmpty(methodename.getText().toString())||
                                                            TextUtils.isEmpty(minwith.getText().toString())||
                                                            TextUtils.isEmpty(distance.getText().toString())||
                                                            TextUtils.isEmpty(minwit23h.getText().toString())) {
                                                        Toasty.error(getApplicationContext(),"Enter  all required field's data",Toasty.LENGTH_SHORT,true).show();
                                                        return;
                                                    }
                                                    else {
                                                        mDialog.dismiss();
                                                        Toasty.success(getApplicationContext(),"Now press the SEND button for complete next steps",Toasty.LENGTH_SHORT,true).show();

                                                    }
                                                }
                                            });


                                            dialogClose.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    mDialog.dismiss();
                                                }
                                            });
                                            mDialog.show();
                                        }catch (Exception e) {
                                            spinner4.setText(task.getResult().getString("number"));
                                            spinner1.setVisibility(View.GONE);
                                            spinner2.setVisibility(View.GONE);
                                            spinner1.setText("MasterCard");
                                            spinner2.setText("MasterCard");
                                        }
                                    }
                                    else {
                                        spinner4.setText("No account Found");
                                        spinner1.setVisibility(View.VISIBLE);
                                        spinner2.setVisibility(View.VISIBLE);
                                        spinner1.setText("");
                                        spinner2.setText("");
                                    }
                                }
                                else {
                                    spinner4.setText("No account Found");
                                    spinner1.setVisibility(View.VISIBLE);
                                    spinner2.setVisibility(View.VISIBLE);
                                    spinner1.setText("");
                                    spinner2.setText("");
                                }
                            }
                        });
            }


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    Dialog mDialog;
    private void progress_check() {
        kProgressHUD = KProgressHUD.create(ConfromActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), BuyCoin.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), BuyCoin.class));
    }
}