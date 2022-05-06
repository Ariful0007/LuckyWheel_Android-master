package com.bluehomestudio.luckywheeldemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;
import com.bluehomestudio.luckywheeldemo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LastWhile extends AppCompatActivity {

    ActivityMainBinding binding;
    List<WheelItem> wheelItems;
    String points;
    LuckyWheel speen;
    Button speen_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        speen=findViewById(R.id.speen);
        speen_btn=findViewById(R.id.speen_btn);
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#4d85bd"), BitmapFactory.decodeResource(getResources(),
                R.drawable.mydollae) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#867666"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa) , "0 "));
        wheelItems.add(new WheelItem(Color.parseColor("#ff420e"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "10 "));
        wheelItems.add(new WheelItem(Color.parseColor("#80bd9e"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "20 "));
        wheelItems.add(new WheelItem(Color.parseColor("#d9b44a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "30 "));
        wheelItems.add(new WheelItem(Color.parseColor("#90afc5"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "40 "));
        wheelItems.add(new WheelItem(Color.parseColor("#336b87"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "50 "));
        wheelItems.add(new WheelItem(Color.parseColor("#2a3132"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "60 "));
        wheelItems.add(new WheelItem(Color.parseColor("#763626"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "70 "));
        wheelItems.add(new WheelItem(Color.parseColor("#c0b2b5"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "80"));
        wheelItems.add(new WheelItem(Color.parseColor("#003b46"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "90 "));
        wheelItems.add(new WheelItem(Color.parseColor("#3f6c45"), BitmapFactory.decodeResource(getResources(),
                R.drawable.iaaa), "110 "));
        speen.addWheelItems(wheelItems);

      //  speen.addWheelItems(wheelItemList);



       speen.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                WheelItem itemSelected=wheelItems.get(Integer.parseInt(points)-1);
                String poins_amount=itemSelected.text;
                Toast.makeText(getApplicationContext(), poins_amount, Toast.LENGTH_SHORT).show();
            }
        });



        speen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random=new Random();
                Toast.makeText(getApplicationContext(), "spin", Toast.LENGTH_SHORT).show();
                points=String.valueOf(random.nextInt(10));
                if(points.equals("0"))
                {points=String.valueOf(1);}

         speen.rotateWheelTo(Integer.parseInt(points));


            }
        });





    }
}