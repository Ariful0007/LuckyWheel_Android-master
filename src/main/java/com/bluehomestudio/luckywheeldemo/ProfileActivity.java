package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {
    TextView name_button, nameTv, namfeTv;
    private TextView namebutton;
    private CircleImageView primage;
    private TextView updateDetails;
    private LinearLayout wishlistView;
    private ImageSlider imageSlider;

    //to get user session data
    private UserSession session;
    private TextView tvemail, tvphone;
    private HashMap<String, String> user;
    private String name, email, photo, mobile;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    CircleImageView profileImage;
    private StorageReference mStorageRef;
    //
    TextView changeProfilePhoto;
    ImageButton image_button;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;//Firebase

    DocumentReference documentReference;
    Button floatingActionButton;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int CAMERA_REQUEST = 1888;
    Button generate_btn;
    //doctor
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;

    private Uri mainImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        name_button = findViewById(R.id.name_button);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        profileImage = findViewById(R.id.profileImage);
        storageReference = FirebaseStorage.getInstance().getReference();

        nameTv = findViewById(R.id.nameTv);
        namfeTv = findViewById(R.id.namfeTv);
        getValues();
        LinearLayout pinupgrade = findViewById(R.id.pinupgrade);
        pinupgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UpdatePinActivity.class));
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        //profileImage
        String image2 = "https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        firebaseFirestore.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    String image = task.getResult().getString("image");
                                    nameTv.setText("Username : " + task.getResult().getString("username"));
                                    if (image.equals(image2)) {
                                        try {
                                            Picasso.get().load(R.drawable.man__new).into(profileImage);
                                        } catch (Exception e) {
                                            Picasso.get().load(R.drawable.man__new).into(profileImage);
                                        }
                                    } else {
                                        try {
                                            Picasso.get().load(image).into(profileImage);
                                        } catch (Exception e) {
                                            Picasso.get().load(image).into(profileImage);
                                        }
                                    }

                                } catch (Exception e) {
                                    nameTv.setText("Username : " + task.getResult().getString("username"));
                                    String image = task.getResult().getString("image");
                                    if (image.equals(image2)) {
                                        try {
                                            Picasso.get().load(R.drawable.beard).into(profileImage);
                                        } catch (Exception e12) {
                                            Picasso.get().load(R.drawable.beard).into(profileImage);
                                        }
                                    } else {
                                        try {
                                            Picasso.get().load(image).into(profileImage);
                                        } catch (Exception e1) {
                                            Picasso.get().load(image).into(profileImage);
                                        }
                                    }
                                }
                            }
                        }
                    }
                });
        name_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(ProfileActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setGravity(Gravity.CENTER);
                input.setHint("Enter New Name");

                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Name Update ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (TextUtils.isEmpty(input.getText().toString())) {
                                    Toast.makeText(ProfileActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                } else {
                                    final KProgressHUD progressDialog = KProgressHUD.create(ProfileActivity.this)
                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                            .setLabel("Updating Data.....")
                                            .setCancellable(false)
                                            .setAnimationSpeed(2)
                                            .setDimAmount(0.5f)
                                            .show();
                                    firebaseFirestore.collection("Users")
                                            .document(firebaseAuth.getCurrentUser().getUid())
                                            .update("name", input.getText().toString().toLowerCase().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        name_button.setText("Name : " + input.getText().toString());
                                                        progressDialog.dismiss();
                                                        Toasty.success(getApplicationContext(), "Done", Toasty.LENGTH_SHORT).show();
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                firebaseAuth.signOut();
                                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                            }
                                                        }, 2000);
                                                    }
                                                }
                                            });
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setIcon(R.drawable.spinningwheel)
                        .setView(input)
                        .show();
            }
        });


        namfeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(ProfileActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setGravity(Gravity.CENTER);
                input.setHint("Enter New Number ");

                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Phone Number  Update ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (TextUtils.isEmpty(input.getText().toString())) {
                                    Toast.makeText(ProfileActivity.this, "Enter Values", Toast.LENGTH_SHORT).show();
                                } else {
                                    final KProgressHUD progressDialog = KProgressHUD.create(ProfileActivity.this)
                                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                            .setLabel("Updating Data.....")
                                            .setCancellable(false)
                                            .setAnimationSpeed(2)
                                            .setDimAmount(0.5f)
                                            .show();
                                    firebaseFirestore.collection("Users")
                                            .document(firebaseAuth.getCurrentUser().getUid())
                                            .update("number", input.getText().toString().toLowerCase().toString())
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        namfeTv.setText("Phone Number : " + input.getText().toString());
                                                        progressDialog.dismiss();
                                                        Toasty.success(getApplicationContext(), "Done", Toasty.LENGTH_SHORT).show();
                                                        Handler handler = new Handler();
                                                        handler.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                firebaseAuth.signOut();
                                                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                                            }
                                                        }, 2000);
                                                    }
                                                }
                                            });
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setIcon(R.drawable.spinningwheel)
                        .setView(input)
                        .show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profileImage.setImageBitmap(bitmap);
                uploadImage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference ref = storageReference.child("Doctor_images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            final Uri downloadUri = uriTask.getResult();


                            if (uriTask.isSuccessful()) {
                                firebaseFirestore.collection("Users")
                                        .document(firebaseAuth.getCurrentUser().getUid())
                                        .update("image", downloadUri.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    // startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                                    Toasty.success(getApplicationContext(), "Profile image adding successfully", Toast.LENGTH_SHORT, true).show();

                                                }
                                            }
                                        });


                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), SecondHome.class));
    }

    @Override
    public boolean onNavigateUp() {
        startActivity(new Intent(getApplicationContext(), SecondHome.class));
        return true;
    }

    private void getValues() {

        //create new session object by passing application context
        session = new UserSession(getApplicationContext());

        //validating session
        session.isLoggedIn();

        //get User details if logged in
        user = session.getUserDetails();

        name = user.get(UserSession.KEY_NAME);
        email = user.get(UserSession.KEY_EMAIL);
        mobile = user.get(UserSession.KEY_MOBiLE);
        photo = user.get(UserSession.KEY_PHOTO);
        name_button.setText("Name : " + name + "   ");

        namfeTv.setText("Phone Number : " + mobile);
        //profileImage
        String image = "https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";
        if (photo.equals(image)) {
            try {
                Picasso.get().load(R.drawable.beard).into(profileImage);
            } catch (Exception e) {
                Picasso.get().load(R.drawable.beard).into(profileImage);
            }
        } else {
            try {
                Picasso.get().load(photo).into(profileImage);
            } catch (Exception e) {
                Picasso.get().load(photo).into(profileImage);
            }
        }


    }
}