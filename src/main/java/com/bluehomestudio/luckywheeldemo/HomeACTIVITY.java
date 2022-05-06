package com.bluehomestudio.luckywheeldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.geniusforapp.fancydialog.FancyAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Scanner;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import static android.content.ContentValues.TAG;

public class HomeACTIVITY extends AppCompatActivity {
    KProgressHUD kProgressHUD;
    Long tsLong = System.currentTimeMillis()/1000;
    String ts = tsLong.toString();

    private UserSession session;
    private HashMap<String, String> user;
    private String name, email, photo, mobile,username;
    private Drawer result;
    private CrossfadeDrawerLayout crossfadeDrawerLayout = null;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String user1;

    IProfile profile;
    TextView nameTv,emailTv;
    ImageView profileImage;
    TextView coinsT1v;
    CardView dailyCheckCard,luckySpinCard,aboutCard1,aboutCard,redeemCard,referCard,taskCard;
    FirebaseFirestore firebaseFirestore;
    String sessionname,sessionmobile,sessionphoto,sessionemail,sessionusername;
    int count,count1,count2,count3;
    String package_actove;
    String daily_bonus;
    String incomeType="Daily Task";
    int main_account;
    int count12,count123;
    int main_refer;
    String main_task ;
    int arra=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_a_c_t_i_v_i_t_y);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        session = new UserSession(getApplicationContext());
        Log.d("xlr8_wlv", String.valueOf(session.getWishlistValue()));
        //retrieve session values and display on listviews
        getValues();

        inflateNavDrawer();
        nameTv=findViewById(R.id.nameTv);
        emailTv=findViewById(R.id.emailTv);
        coinsT1v=findViewById(R.id.coinsT1v);
        nameTv.setText(name);
        emailTv.setText(email);
        // appname.setTypeface(typeface);
        String[] array= new String[100];
        ConnectivityManager cm = (ConnectivityManager)HomeACTIVITY.this.getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        Network[] networks = cm.getAllNetworks();
        Scanner s1 = new Scanner(System. in);

        Log.i(TAG, "Network count: " + networks.length);
        for(int i = 0; i < networks.length; i++) {

            NetworkCapabilities caps = cm.getNetworkCapabilities(networks[i]);
            boolean check=caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
            String mu=""+check;
            array[i]=mu;
            // arra++;

            if (mu.equals("true")) {
                arra++;
            }






            Log.i(TAG, "Network " + i + ": " + networks[i].toString());
            Log.i(TAG, "VPN transport is: " + caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN));
            Log.i(TAG, "NOT_VPN capability is: " + caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_VPN));

        }


        CircularProgressButton cirLoginButton=findViewById(R.id.cirLoginButton);
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*
                if (arra>=1) {
                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

                    builder.setTitle("Connection")
                            .setMessage("Connect US/UK VPN Connection")
                            .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                                    startActivity(intent);
                                }
                            }).create().show();
                }
                */
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                startActivity(intent);
            }
        });
        //Toast.makeText(this, ""+arra, Toast.LENGTH_SHORT).show();



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
                                try {
                                    coinsT1v.setText(task.getResult().getString("main_balance"));
                                }catch (Exception e) {
                                    coinsT1v.setText(task.getResult().getString("main_balance"));
                                }
                            }
                        }
                    }
                });

    }
    private void getValues() {
        //validating session


        try {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
        }catch (Exception e) {
            //get User details if logged in
            session.isLoggedIn();
            user = session.getUserDetails();

            name = user.get(UserSession.KEY_NAME);
            email = user.get(UserSession.KEY_EMAIL);
            mobile = user.get(UserSession.KEY_MOBiLE);
            photo = user.get(UserSession.KEY_PHOTO);
            username=user.get(UserSession.Username);
        }
        //Toast.makeText(this, ""+username, Toast.LENGTH_SHORT).show();
    }
    public void noticve(View view) {

       /*
        if (arra>=1) {
            startActivity(new Intent(getApplicationContext(),NoticeBoard.class));
        }

              else {
                AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

                builder.setTitle("Connection")
                        .setMessage("Connect US/UK VPN Connection")
                        .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                                startActivity(intent);
                            }
                        }).create().show();

        }
        */
        startActivity(new Intent(getApplicationContext(),NoticeBoard.class));

    }
    private void inflateNavDrawer() {

        //set Custom toolbar to activity -----------------------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the AccountHeader ----------------------------------------------------------------

        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.get().load(uri).placeholder(placeholder).into(imageView);
            }
            @Override
            public void cancel(ImageView imageView) {
                Picasso.get().cancelRequest(imageView);
            }
        });


        //Profile Making
       /*
        IProfile profile = new ProfileDrawerItem()
                .withName(name)
                .withEmail(email)
                .withIcon(photo);
        */
        String image22="https://firebasestorage.googleapis.com/v0/b/cash-money-express-ltd.appspot.com/o/profile_images%2Fo8Dnqf5LFodKSwocGQ4nKB7ZEkW2.jpg?alt=media&token=c22700e2-67ca-4497-8bf1-204ac83b6749";

        //String image22="https://firebasestorage.googleapis.com/v0/b/mydex-91780.appspot.com/o/profile_images%2FNt4rITrPbqc9e1AHPKPaLb4IcFI3.jpg?alt=media&token=d2da7b65-ed6a-41c9-8d1c-ade16adddc3a";

            profile = new ProfileDrawerItem()
                    .withName(name)
                    .withEmail(email)
                    .withIcon(R.drawable.beard);


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.gradient_background)
                .addProfiles(profile)
                .withCompactStyle(true)
                .build();

        //Adding nav drawer items ------------------------------------------------------------------
        //Adding nav drawer items ------------------------------------------------------------------
        //Adding nav drawer items ------------------------------------------------------------------
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.home).withIcon(R.drawable.home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.myprofile).withIcon(R.drawable.profile);
       // PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.wishlist).withIcon(R.drawable.wishlist);
       // PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.cart).withIcon(R.drawable.cart);
      //  PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.order_history).withIcon(R.drawable.order_hist_icon);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.logout).withIcon(R.drawable.logout);

        // SecondaryDrawerItem item12 = new SecondaryDrawerItem().withIdentifier(12).withName("App Tour").withIcon(R.drawable.tour);
        //SecondaryDrawerItem item13 = new SecondaryDrawerItem().withIdentifier(13).withName("Explore").withIcon(R.drawable.explore);
        //SecondaryDrawerItem item14 = new SecondaryDrawerItem().withIdentifier(14).withName("Privecy Policey").withIcon(R.drawable.ic_baseline_policy_24);
        //creating navbar and adding to the toolbar ------------------------------------------------
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.crossfade_drawer)
                .withAccountHeader(headerResult)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withTranslucentStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(item1,item2,item5, new DividerDrawerItem(),new DividerDrawerItem()).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        switch (position){

                            case 1:
                                if (result != null && result.isDrawerOpen()) {
                                    result.closeDrawer();
                                }
                                break;

                            case 2:
                                startActivity(new Intent(HomeACTIVITY.this, MyProfile.class));
                              /*
                                if (arra>=1) {

                                    startActivity(new Intent(HomeACTIVITY.this, MyProfile.class));
                                }
                                else {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

                                    builder.setTitle("Connection")
                                            .setMessage("Connect US/UK VPN Connection")
                                            .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    Intent intent=new Intent(Intent.ACTION_VIEW);
                                                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                                                    startActivity(intent);
                                                }
                                            }).create().show();
                                }
                               */
                                break;

                            case 3:
                                AlertDialog.Builder warning = new AlertDialog.Builder(HomeACTIVITY.this)
                                        .setTitle("Logout")
                                        .setMessage("Are you want to logout?")
                                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();



                                            }
                                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // ToDO: delete all the notes created by the Anon user

                                                // TODO: delete the anon user

                                                firebaseAuth.signOut();
                                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                finish();


                                            }
                                        });

                                warning.show();

                                result.closeDrawer();
                                break;




                            default:
                                Toast.makeText(HomeACTIVITY.this, "Default", Toast.LENGTH_LONG).show();
                        }

                        return true;
                    }
                }).build();




        crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();

        //define maxDrawerWidth
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));

        //add second view (which is the miniDrawer)
        final MiniDrawer miniResult = result.getMiniDrawer();

        //build the view for the MiniDrawer
        View view = miniResult.build(this);

        //set the background of the MiniDrawer as this would be transparent
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));

        //we do not have the MiniDrawer view during CrossfadeDrawerLayout creation so we will add it here
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                //only close the drawer if we were already faded and want to close it now
                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
            }
        });
    }
    public void Notifications(View view) {
    }

    public void viewProfile(View view) {
       /*
        if (arra>=1) {

            startActivity(new Intent(getApplicationContext(), MyProfile.class));
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
        */
        startActivity(new Intent(getApplicationContext(), MyProfile.class));
    }

    public void howw(View view) {
        String url = "https://www.youtube.com/watch?v=EvV0cxNcVOY&t=3s";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
        /*
        if (arra>=1) {
            String url = "https://www.youtube.com/watch?v=EvV0cxNcVOY&t=3s";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
         */
    }

    public void contact(View view) {
        startActivity(new Intent(getApplicationContext(), ContactUs.class));
      /*
        if (arra>=1) {
            startActivity(new Intent(getApplicationContext(), ContactUs.class));
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
       */
    }

    public void mypay(View view) {
        startActivity(new Intent(getApplicationContext(), My_Payment.class));

        /*
        if (arra>=1) {
            startActivity(new Intent(getApplicationContext(), My_Payment.class));
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
         */

    }

    public void all(View view) {
        startActivity(new Intent(getApplicationContext(), AllPayment.class));

       /*
        if (arra>=1) {
            startActivity(new Intent(getApplicationContext(), AllPayment.class));
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }

        */
    }

    public void mytask(View view) {
        startActivity(new Intent(getApplicationContext(), WorkingHistory.class));

     /*
        if (arra>=1) {
            startActivity(new Intent(getApplicationContext(), WorkingHistory.class));
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
      */

    }

    public void invalid(View view) {
        startActivity(new Intent(getApplicationContext(), InValidActivity.class));
      /*
        if (arra>=1) {
            startActivity(new Intent(getApplicationContext(), InValidActivity.class));
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
       */
    }

    public void givemepoints(View view) {

        Intent intent = new Intent(getApplicationContext(), GivePayment.class);
        intent.putExtra("username", username);
        startActivity(intent);
        /*
        if (arra>=1) {
            Intent intent = new Intent(getApplicationContext(), GivePayment.class);
            intent.putExtra("username", username);
            startActivity(intent);
        }  else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
         */

    }
    @Override
    public void onBackPressed() {
        final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(HomeACTIVITY.this)
                .setBackgroundColor(R.color.white)
                .setTextTitle("Exit")
                .setCancelable(false)
                .setTextSubTitle("Are you want to exit")
                .setBody("User is not stay at app when user click exit button.")
                .setPositiveButtonText("No")
                .setPositiveColor(R.color.toolbar)
                .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();

                    }
                })
                .setNegativeButtonText("Exit")
                .setNegativeColor(R.color.colorPrimaryDark)
                .setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        dialog.dismiss();
                        finishAffinity();

                    }
                })
                .setBodyGravity(FancyAlertDialog.TextGravity.CENTER)
                .setTitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER)
                .setCancelable(false)
                .build();
        alert.show();

    }

    public void metask(View view) {
        startActivity(new Intent(getApplicationContext(), MyTask.class));
       /*
        if (arra>=1) {
            startActivity(new Intent(getApplicationContext(), MyTask.class));
        }  else {
            AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);

            builder.setTitle("Connection")
                    .setMessage("Connect US/UK VPN Connection")
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=free.vpn.unblock.proxy.turbovpn"));
                            startActivity(intent);
                        }
                    }).create().show();
        }
        */

    }
}