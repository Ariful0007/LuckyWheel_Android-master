package com.bluehomestudio.luckywheeldemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    LottieAnimationView empty_cart;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    UserAdapter getDataAdapter1;
    List<UserModel> getList;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    private StaggeredGridLayoutManager mLayoutManager;


    ///
    private SliderAdapter adapter;
    private SliderAdapter2 adapter1;
    private ArrayList<Model_admin> sliderDataArrayList;
    FirebaseFirestore db;
    private SliderView imageSlider,imageSlider1;
    FirebaseFirestore firebaseFirestore1;
ImageSlider slider1;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        getList = new ArrayList<>();
        getDataAdapter1 = new UserAdapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference  =  firebaseFirestore.collection("Users").document();
        recyclerView =view.findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();
        CardView luckySpinCard=view.findViewById(R.id.luckySpinCard);
        luckySpinCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),SendMyDarling.class));
            }
        });

        //ImageSLider
        imageSlider = view.findViewById(R.id.slider);
        slider1=view.findViewById(R.id.slider1);
        sliderDataArrayList = new ArrayList<>();

        // initializing our slider view and
        // firebase firestore instance.
        db = FirebaseFirestore.getInstance();

        // calling our method to load images.
        loadImages();

        CardView referCard=view.findViewById(R.id.referCard);
        referCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(view.getContext(),"Coming Soon",Toasty.LENGTH_SHORT,true).show();
            }
        });


        CardView dailyCheckCard=view.findViewById(R.id.dailyCheckCard);
        dailyCheckCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),BuyCoin.class));
            }
        });
        CardView taskCard=view.findViewById(R.id.taskCard);
        taskCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(view.getContext(),MySpinn.class));
            }
        });




        return  view;
    }
    private void reciveData() {

        firebaseFirestore.collection("Users").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        UserModel get = ds.getDocument().toObject(UserModel.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });

    }
    private void loadImages() {
        // getting data from our collection and after
        // that calling a method for on success listener.
        db.collection("Post_Slider").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int ncount = 0;
                    for (DocumentSnapshot document : task.getResult()) {
                        ncount++;
                    }
                    if (ncount==0) {
                        slider1.setVisibility(View.VISIBLE);
                        imageSlider.setVisibility(View.GONE);
                        ArrayList<SlideModel> slideModels = new ArrayList<>();
      /*
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/G/31/img19/Wireless/Apple/iPhone11/RiverImages/11Pro/IN_iPhone11Pro_DESKTOP_01._CB437064827_.jpg"));
        slideModels.add(new SlideModel("https://piunikaweb.com/wp-content/uploads/2019/08/oneplus_7_pro_5g_experience_the_power_of_5g_banner-750x354.jpg"));
        slideModels.add(new SlideModel("https://lh3.googleusercontent.com/RSyeouwiFX4XVq6iw3H94al0VcXD693tBy2MxhBKCxAHCIfIpdt7wDV47_j2HanPSnTli7JgZ0fYHxESjz0uvVgeCBT3=w1000"));
        slideModels.add(new SlideModel("https://cdn.metrobrands.com/mochi/media/images/content/Homepage/HOTTMARZZ-BANNER-MOCHI.webp"));
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/b2/78/7c/b2787cea792bff7d2c33e26ada6436bb.jpg"));
        slideModels.add(new SlideModel("https://cdnb.artstation.com/p/assets/images/images/016/802/459/large/shuja-shuaib-banner.jpg?1553535424"));
       */
                        slideModels.add(new SlideModel(R.drawable.spinningwheel));
                        slideModels.add(new SlideModel(R.drawable.sppp));
                        //slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/salary.png?alt=media&token=627d0a8e-2bf4-4e84-84e9-5574d9b8fbaa"));

                        slider1.setImageList(slideModels, true);

                    }
                    else {
                        slider1.setVisibility(View.GONE);
                        imageSlider.setVisibility(View.VISIBLE);

                        db.collection("Post_Slider").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                // inside the on success method we are running a for loop
                                // and we are getting the data from Firebase Firestore
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                    // after we get the data we are passing inside our object class.
                                    Model_admin sliderData = documentSnapshot.toObject(Model_admin.class);
                                    Model_admin model = new Model_admin();

                                    // below line is use for setting our
                                    // image url for our modal class.
                                    model.setImage(sliderData.getImage());

                                    // after that we are adding that
                                    // data inside our array list.
                                    sliderDataArrayList.add(model);

                                    // after adding data to our array list we are passing
                                    // that array list inside our adapter class.
                                    adapter = new SliderAdapter(getContext(), sliderDataArrayList);

                                    // belows line is for setting adapter
                                    // to our slider view
                                    imageSlider.setSliderAdapter(adapter);

                                    // below line is for setting animation to our slider.
                                    imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

                                    // below line is for setting auto cycle duration.
                                    imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);

                                    // below line is for setting
                                    // scroll time animation
                                    imageSlider.setScrollTimeInSec(3);

                                    // below line is for setting auto
                                    // cycle animation to our slider
                                    imageSlider.setAutoCycle(true);

                                    // below line is use to start
                                    // the animation of our slider view.
                                    imageSlider.startAutoCycle();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // if we get any error from Firebase we are
                                // displaying a toast message for failure
                                Toast.makeText(getContext(), "Fail to load slider data..", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        });

    }
}