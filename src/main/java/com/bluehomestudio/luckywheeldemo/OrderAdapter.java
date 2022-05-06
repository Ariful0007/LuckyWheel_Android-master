package com.bluehomestudio.luckywheeldemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.myView> {
    private List<WithdrawModel> data;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    public OrderAdapter(List<WithdrawModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public OrderAdapter.myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb, parent, false);
        return new OrderAdapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.myView holder, final int position) {
        holder.add_notes_title.setText("Amount : $"+data.get(position).getAmount()
        +"\nWithdraw Date : "+data.get(position).getDate());
        holder.blog_detail_desc.setText("Payment Gateway : "+data.get(position).getGname());
        holder.logout10.setText("Status : "+data.get(position).getStatus());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder {
        TextView blog_detail_desc,add_notes_title,logout10;

        public myView(@NonNull View itemView) {

            super(itemView);
            logout10=itemView.findViewById(R.id.logout10);
            blog_detail_desc=itemView.findViewById(R.id.customer_name);
            add_notes_title=itemView.findViewById(R.id.customer_number10);

        }
    }
}
