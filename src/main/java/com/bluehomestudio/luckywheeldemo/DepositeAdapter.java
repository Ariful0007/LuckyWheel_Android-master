package com.bluehomestudio.luckywheeldemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class DepositeAdapter extends RecyclerView.Adapter<DepositeAdapter.myview> {
    public List<MethodeModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Dialog mDialog;
    EditText methodename,hint,minwith;
    TextView taka,number;
    String check;
    public DepositeAdapter(List<MethodeModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public DepositeAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new DepositeAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepositeAdapter.myview holder, final int position) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        try {
            holder.customer_name.setText(data.get(position).getName());
            holder.customer_number.setText("$"+data.get(position).getC_price());
            String image=data.get(position).getImage();
            try {
                Picasso.get()
                        .load(image)

                        .into(holder.cardimage);
            }catch (Exception e) {
                Picasso.get()
                        .load(image)
                        .into(holder.cardimage);
            }

        }catch (Exception ee) {
            holder.customer_name.setText(data.get(position).getName());
          //  holder.customer_number.setText(data.get(position).getC_name());
            String image=data.get(position).getImage();
            try {
                Picasso.get()
                        .load(image)
                        .into(holder.cardimage);
            }catch (Exception e) {
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
        mDialog.setContentView(R.layout.dialog_contact);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        FloatingActionButton dialogClose=(FloatingActionButton)mDialog.findViewById(R.id.dialogClose);
        methodename=(EditText)mDialog.findViewById(R.id.methodename);
        taka=(TextView)mDialog.findViewById(R.id.taka);
        number=(TextView)mDialog.findViewById(R.id.number);
        hint=(EditText)mDialog.findViewById(R.id.hint);
        methodename.addTextChangedListener(nameWatcher);
        minwith=(EditText)mDialog.findViewById(R.id.minwith);
        minwith.setText(data.get(position).getC_price());
        Button login_button=(Button)mDialog.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(methodename.getText().toString())||TextUtils.isEmpty(hint.getText().toString())) {
                    Toasty.error(view.getContext(),"Enter Information",Toasty.LENGTH_SHORT,true).show();
                    return;
                }
                else {
                    if (Integer.parseInt(methodename.getText().toString())<Integer.parseInt(data.get(position).getMinwithdraw())) {
                        Toasty.info(view.getContext(),"Enter Minimum Deposite",Toasty.LENGTH_SHORT,true).show();
                        return;
                    }
                    else {
                        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Conformation")
                                .setMessage("Are you want to send request for deposite?")
                                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                KProgressHUD   progressDialog = KProgressHUD.create(view.getContext())
                                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                        .setLabel("Uploading Data....")
                                        .setCancellable(false)
                                        .setAnimationSpeed(2)
                                        .setDimAmount(0.5f)
                                        .show();
                                String uuid= UUID.randomUUID().toString();
                                DepositeMM depositeMM=new DepositeMM(firebaseAuth.getCurrentUser().getEmail(),
                                        uuid,methodename.getText().toString(),hint.getText().toString(),firebaseAuth.getCurrentUser().getUid());
                                firebaseFirestore.collection("Deposite_Request")
                                        .document(uuid)
                                        .set(depositeMM)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    mDialog.dismiss();
                                                    progressDialog.dismiss();
                                                    Toasty.success(view.getContext(),"Done",Toasty.LENGTH_SHORT,true).show();

                                                }
                                            }
                                        });

                            }
                        }).create().show();
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
holder.card_view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
mDialog.show();
    }
});

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        ImageView cardimage;
        CardView card_view;
        ImageView notifintro;

        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.cardcategory);
            cardimage=itemView.findViewById(R.id.cardimage);
            customer_number=itemView.findViewById(R.id.priceee);
            card_view=itemView.findViewById(R.id.card_view);
            notifintro=itemView.findViewById(R.id.notifintro);

        }
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
            if (check.equals("")) {
                taka.setText("Send 0 taka to complete deposite");
            }
            else {
               try {
                   String price=minwith.getText().toString();
                   String amount=methodename.getText().toString();
                   double main=Double.parseDouble(price)*Double.parseDouble(amount);
                   taka.setText("Send "+main+" taka to complete deposite");
               }catch (Exception e) {
                   String price=minwith.getText().toString();
                   String amount=methodename.getText().toString();
                   double main=Double.parseDouble(price)*Double.parseDouble(amount);
                   taka.setText("Send "+main+" taka to complete deposite");
               }
            }


        }

    };
}