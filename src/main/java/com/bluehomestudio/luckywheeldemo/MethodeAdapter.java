package com.bluehomestudio.luckywheeldemo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class MethodeAdapter  extends RecyclerView.Adapter<MethodeAdapter.myview> {
    public List<MethodeModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Dialog mDialog;
    EditText methodename,hint,minwith;
    TextView taka,number,pahira;
    String check;
    public MethodeAdapter(List<MethodeModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MethodeAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.newcard, parent, false);
        return new MethodeAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MethodeAdapter.myview holder, final int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        try {
            holder.customer_name.setText(data.get(position).getName()+" $"+data.get(position).getMinwithdraw());
            holder.customer_number.setText("$" + data.get(position).getC_name());
            String image = data.get(position).getImage();
            try {
                Picasso.get()
                        .load(image)

                        .into(holder.cardimage);
            } catch (Exception e) {
                Picasso.get()
                        .load(image)
                        .into(holder.cardimage);
            }

        } catch (Exception ee) {
            holder.customer_name.setText(data.get(position).getName());
            holder.customer_number.setText("$" + data.get(position).getC_name());
            String image = data.get(position).getImage();
            try {
                Picasso.get()
                        .load(image)
                        .into(holder.cardimage);
            } catch (Exception e) {
                Picasso.get()
                        .load(image)
                        .into(holder.cardimage);
            }

        }
        mDialog = new Dialog(holder.itemView.getContext());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //LayoutInflater factory = LayoutInflater.from(this);
        //final View deleteDialogView = factory.inflate(R.layout.dialog_contact, null);
        //  mDialog.setContentView(deleteDialogView);
        mDialog.setContentView(R.layout.newdialouge);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FloatingActionButton dialogClose=(FloatingActionButton)mDialog.findViewById(R.id.dialogClose);
        pahira=(TextView)mDialog.findViewById(R.id.pahira);
        methodename=(EditText)mDialog.findViewById(R.id.methodename);
       try {
         //  Double main=(Double.parseDouble(data.get(position).getC_name())*(Double.parseDouble(data.get(position).getMinwithdraw())));
       }catch (Exception e) {
        //   Double main=(Double.parseDouble(data.get(position).getC_name())*(Double.parseDouble(data.get(position).getMinwithdraw())));
       }

holder.card_view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int c_price=Integer.parseInt(data.get(position).getC_price());
        int minwith=Integer.parseInt(data.get(position).getMinwithdraw());
        int mainnm=c_price*minwith;
        String mainwin=""+mainnm;
        pahira.setText(" You  Will Receive "+mainwin+" Taka\n" +
                ""+data.get(position).getHint());
mDialog.show();
    }
});
        Button login_button=(Button)mDialog.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(methodename.getText().toString())) {
                    Toasty.error(view.getContext(),"Enter Information",Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    if (data.get(position).getAgents().equals("")) {
                        Toasty.info(view.getContext(),"No Agents Found In This Methode",Toasty.LENGTH_SHORT,true).show();
                       // return;
                    }
                    else {
                        KProgressHUD   progressDialog = KProgressHUD.create(view.getContext())
                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                .setLabel("Uploading Data....")
                                .setCancellable(false)
                                .setAnimationSpeed(2)
                                .setDimAmount(0.5f)
                                .show();
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
                                                if (Integer.parseInt(purches_balance)==0) {
                                                    Toasty.error(view.getContext(),"Sorry you do not have enough banalce!",Toasty.LENGTH_SHORT,true).show();
                                                }
                                                else {
                                                    int bal=Integer.parseInt(purches_balance)-Integer.parseInt(data.get(position).getMinwithdraw());
                                                    firebaseFirestore.collection("Users")
                                                            .document(firebaseAuth.getCurrentUser().getUid())
                                                            .collection("Main_Balance")
                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                            .update("main_balance",""+bal)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        String uuid= UUID.randomUUID().toString();
                                                                        Long tsLong = System.currentTimeMillis()/1000;
                                                                        String  ts = tsLong.toString();
                                                                        Calendar calendar = Calendar.getInstance();
                                                                        String current = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                                                                        String current1 = DateFormat.getDateInstance().format(calendar.getTime());

                                                                        WithdrawModel withdrawModel=new WithdrawModel(firebaseAuth.getCurrentUser().getEmail(),
                                                                                uuid,data.get(position).getMinwithdraw(),data.get(position).getName(),
                                                                                current1,ts,"Pending",methodename.getText().toString());
                                                                        firebaseFirestore.collection("MyWithdraw")
                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                .collection("List")
                                                                                .document(uuid)
                                                                                .set(withdrawModel)
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            firebaseFirestore.collection("AgentsPayment")
                                                                                                    .document(data.get(position).getAgents()+"@gmail.com")
                                                                                                    .collection("List")
                                                                                                    .document(uuid)
                                                                                                    .set(withdrawModel)
                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            if (task.isSuccessful()) {
                                                                                                                progressDialog.dismiss();
                                                                                                               // Toasty.success(getApplicationContext(),"Payment Request  Done",Toasty.LENGTH_SHORT,true).show();
                                                                                                                new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                        .setTitle("Conformation")
                                                                                                                        .setMessage("Withdraw request is Successfully Done ")
                                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                                            @Override
                                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                builder.dismiss();
                                                                                                                                view.getContext().startActivity(new Intent(view.getContext(),SecondHome.class));

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
                                        }
                                    }
                                });
                    }

                }
            }
        });

        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder {
        TextView customer_name, customer_number, customer_area, logout;
        ImageView cardimage;
        CardView card_view;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name = itemView.findViewById(R.id.cardcategory);
            cardimage = itemView.findViewById(R.id.cardimage);
            customer_number = itemView.findViewById(R.id.priceee);
            card_view = itemView.findViewById(R.id.card_view);



        }
    }
}