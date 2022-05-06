package com.bluehomestudio.luckywheeldemo;

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

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.myview> {
    public List<UserModel> data;
    FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    public UserAdapter(List<UserModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public UserAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activeuser,parent,false);
        return new UserAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.myview holder, final int position) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
holder.customer_name.setText("Name : "+data.get(position).getName());
holder.customer_number.setText("Email : "+data.get(position).getEmail());
firebaseFirestore.collection("All_UserID")
        .document(data.get(position).getNumber()+"@gmail.com")
        .get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        try {
                            String uuid=task.getResult().getString("uuid");
                            firebaseFirestore.collection("Users").document(uuid)
                                    .collection("Main_Balance")
                                    .document( data.get(position).getNumber()+"@gmail.com")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    try {
                                                        holder.logout.setText("Diamond :"+task.getResult().getString("main_balance"));
                                                    }catch (Exception e) {
                                                        holder.logout.setText("Diamond : "+task.getResult().getString("main_balance"));
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }catch (Exception e) {
                            String uuid=task.getResult().getString("uuid");
                            firebaseFirestore.collection("Users").document(uuid)
                                    .collection("Main_Balance")
                                    .document( data.get(position).getNumber()+"@gmail.com")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    try {
                                                        holder.logout.setText("Total Amount : "+task.getResult().getString("main_balance"));
                                                    }catch (Exception e) {
                                                        holder.logout.setText("Total Amount : "+task.getResult().getString("main_balance"));
                                                    }
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                }
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
customer_name=itemView.findViewById(R.id.customer_name);
customer_number=itemView.findViewById(R.id.customer_number10);
logout=itemView.findViewById(R.id.logout10);



        }
    }
}