package a3labgo.tusar.carparkingproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingActivity extends AppCompatActivity {
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    ImageView iv;
    Button btn,btn2;
    Integer parkingId,userid,price;
    Double l1,l2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv6 = (TextView) findViewById(R.id.textView6);
        iv = (ImageView) findViewById(R.id.imageView);
        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button3);
        btn.setAlpha(.3f);
        btn2.setAlpha(.3f);
        btn.setClickable(false);
        btn2.setClickable(false);



        Intent intent = getIntent();
        String name = intent.getStringExtra("ParkingName");
        int uid = intent.getIntExtra("uid",0);
        userid = uid;
        Toast.makeText(getApplicationContext(), "UserID "+userid, Toast.LENGTH_SHORT).show();
        tv1.setText(name);
        if(name.equals("Vai Vai Parking")){
            iv.setImageResource(R.drawable.vai);
        }
        else {
            iv.setImageResource(R.drawable.molla);
        }

        Retrofit ret3 = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api3 api3 = ret3.create(Api3.class);
        Call<List<Model2>> call3 = api3.getModel(name);
        call3.enqueue(new Callback<List<Model2>>() {
            @Override
            public void onResponse(Call<List<Model2>> call3, Response<List<Model2>> response) {
                List<Model2> Teams = response.body();
                List<Integer> Pid = new ArrayList<>();
                List<String> Pname = new ArrayList<>();
                List<Integer> PtotSpot = new ArrayList<>();
                List<Integer> PfreeSpot = new ArrayList<>();
                List<Integer> Pprice = new ArrayList<>();
                List<Integer> Plocation = new ArrayList<>();
                List<Double> Platitude = new ArrayList<>();
                List<Double> Plongitude = new ArrayList<>();
                for (int i = 0; i < Teams.size(); i++) {
                    Pid.add(Integer.valueOf(Teams.get(i).getId()));
                    Pname.add(String.valueOf(Teams.get(i).getName()));
                    PtotSpot.add(Integer.valueOf(Teams.get(i).getTotalspot()));
                    PfreeSpot.add(Integer.valueOf(Teams.get(i).getAvailablespot()));
                    Pprice.add(Integer.valueOf(Teams.get(i).getPrice()));
                    Plocation.add(Integer.valueOf(Teams.get(i).getLocationid()));
                    Platitude.add(Double.valueOf(Teams.get(i).getLatitude()));
                    Plongitude.add(Double.valueOf(Teams.get(i).getLongitude()));
                    tv2.setText(String.valueOf(PtotSpot.get(i)));
                    tv4.setText(String.valueOf(PfreeSpot.get(i)));
                    price = Integer.valueOf(Pprice.get(i));
                    tv6.setText(String.valueOf(Pprice.get(i)));
                    parkingId = Pid.get(i);
                    l1 = Platitude.get(i);
                    l2 = Plongitude.get(i);
                    if((PfreeSpot.get(i))>0){
                        btn.setAlpha(1.0f);
                        btn.setClickable(true);
                    }
                    btn2.setAlpha(1.0f);
                    btn2.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<List<Model2>> call3, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", l1, l2);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit ret4 = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api4 api4 = ret4.create(Api4.class);
                Toast.makeText(getApplicationContext(), userid+" "+parkingId, Toast.LENGTH_SHORT).show();
                Call<List<Model3>> call4 = api4.getModel(userid,parkingId,1);
                call4.enqueue(new Callback<List<Model3>>() {
                    @Override
                    public void onResponse(Call<List<Model3>> call4, Response<List<Model3>> response5) {
                        List<Model3> Teams2 = response5.body();
                        List<Integer> id = new ArrayList<>();
                        List<Integer> state = new ArrayList<>();
                        List<Integer> cc = new ArrayList<>();
                        List<String> time = new ArrayList<>();
                        for (int i = 0; i < Teams2.size(); i++) {
                            id.add(Integer.valueOf(Teams2.get(i).getId()));
                            state.add(Integer.valueOf(Teams2.get(i).getCurrentstate()));
                            time.add(String.valueOf(Teams2.get(i).getStarttime()));
                            cc.add(Integer.valueOf(Teams2.get(i).getConfirmcode()));
                            if((id.get(i))>0){
                                Toast.makeText(getApplicationContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), WaitActivity.class);
                                intent.putExtra("orderid",id.get(i));
                                intent.putExtra("cc",cc.get(i));
                                intent.putExtra("pp",price);
                                Toast.makeText(getApplicationContext(), cc.get(i)+" "+id.get(i), Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Model3>> call4, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
