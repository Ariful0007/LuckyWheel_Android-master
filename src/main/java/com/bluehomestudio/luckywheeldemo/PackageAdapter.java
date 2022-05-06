package com.bluehomestudio.luckywheeldemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.myview> {
    public List<MethodeModel> data;
    FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    public PackageAdapter(List<MethodeModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PackageAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_cardview_layout,parent,false);
        return new PackageAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdapter.myview holder, final int position) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
   try {
       holder.customer_name.setText(data.get(position).getName());
      holder.customer_number.setText("$"+data.get(position).getC_name());
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
      holder.customer_number.setText("$"+data.get(position).getC_name());
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

holder.card_view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
        builder.setTitle("Active Package")
                .setMessage("Are You Want To Active Package?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Buy Now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                firebaseFirestore.collection("PackagessList")
                        .document(data.get(position).getName().toLowerCase().toString())
                        .collection("List")
                        .document(firebaseAuth.getCurrentUser().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        Toasty.error(view.getContext(),"Already Active This Package."+data.get(position).getC_name()+" to active this package.",Toasty.LENGTH_SHORT,true).show();
                                        return;
                                    }
                                    else {
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
                                                                String shoping_balance=task.getResult().getString("third_level");
                                                                String self_income=task.getResult().getString("monthly_income");
                                                                String daily_income=task.getResult().getString("daily_income");
                                                                int mainbalance=Integer.parseInt(purches_balance);
                                                                int  packageprice=Integer.parseInt(data.get(position).getC_name());
                                                                if (mainbalance==0) {
                                                                    Toasty.error(view.getContext(),"Have not any balance",Toasty.LENGTH_SHORT,true).show();
                                                                    return;
                                                                }
                                                                else {
                                                                    if (mainbalance<packageprice) {
                                                                        Toasty.error(view.getContext(),"Sorry ! You need $"+data.get(position).getC_name()+" to active this package.",Toasty.LENGTH_SHORT,true).show();
                                                                        return;
                                                                    }
                                                                    else {
                                                                        final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                                                                                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                                                                                .setLabel("Please wait")
                                                                                .setCancellable(false)
                                                                                .setAnimationSpeed(2)
                                                                                .setDimAmount(0.5f)
                                                                                .show();
                                                                        int baki=mainbalance-packageprice;
                                                                        int deposite=Integer.parseInt(shoping_balance)+packageprice;
                                                                        firebaseFirestore.collection("Users")
                                                                                .document(firebaseAuth.getCurrentUser().getUid())
                                                                                .collection("Main_Balance")
                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                .update("main_balance",""+baki,"third_level",""+deposite)
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            Map<String, Object> user = new HashMap<>();
                                                                                            user.put("package", data.get(position).getName());
                                                                                            user.put("price", data.get(position).getC_price());
                                                                                            user.put("dates", data.get(position).getHint());
                                                                                            firebaseFirestore.collection("MyPackages")
                                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                    .set(user)
                                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            if (task.isSuccessful()) {
                                                                                                                firebaseFirestore.collection("PackagessList")
                                                                                                                        .document(data.get(position).getName().toLowerCase().toString())
                                                                                                                        .collection("List")
                                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                        .set(user)
                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                if (task.isSuccessful()) {
                                                                                                                                    progressDialog.dismiss();
                                                                                                                                    new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                            .setTitle("Conformation")
                                                                                                                                            .setMessage("Withdraw request is Successfully Done in Global Earn.")
                                                                                                                                            .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                            .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                                @Override
                                                                                                                                                public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                                    builder.dismiss();
                                                                                                                                                    //   startActivity(new Intent(getApplicationContext(),TranscationMain.class));
                                                                                                                                                    Toasty.success(view.getContext(),data.get(position).getName()+" Package Actived Sucessfully",Toasty.LENGTH_SHORT,true).show();

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
                                                    }
                                                });
                                    }
                                }
                            }
                        });

            }
        }).create().show();
    }
});

        firebaseFirestore.collection("PackagessList")
                .document(data.get(position).getName().toLowerCase().toString())
                .collection("List")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                               // Toast.makeText(holder.itemView.getContext(), "gfhfgh", Toast.LENGTH_SHORT).show();
                                holder.notifintro.setVisibility(View.VISIBLE);
                               // holder.card_view.setBackgroundColor(Color.parseColor("#66A5AD"));
                            }
                            else {
                                holder.notifintro.setVisibility(View.GONE);
                            }
                        }
                        else {
                            holder.notifintro.setVisibility(View.GONE);
                        }
                    }
                });

   holder.cardimage.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           firebaseFirestore.collection("PackagessList")
                   .document(data.get(position).getName().toLowerCase().toString())
                   .collection("List")
                   .document(firebaseAuth.getCurrentUser().getEmail())
                   .get()
                   .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                           if (task.isSuccessful()) {
                               if (task.getResult().exists()) {
                                   Intent intent=new Intent(holder.itemView.getContext(),SecondWork.class);
                                   view.getContext().startActivity(intent);

                               }
                               else {
                                   Toasty.error(holder.itemView.getContext(),"Do Not Active This Package",Toasty.LENGTH_SHORT,true).show();
                                   return;
                               }
                           }
                           else {
                               Toasty.error(holder.itemView.getContext(),"Do Not Active This Package",Toasty.LENGTH_SHORT,true).show();
                               return;
                           }
                       }
                   });
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
}