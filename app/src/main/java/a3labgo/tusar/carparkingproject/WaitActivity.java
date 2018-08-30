package a3labgo.tusar.carparkingproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WaitActivity extends AppCompatActivity {

    TextView t1,t2,t3;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        t1 = (TextView) findViewById(R.id.textView1);
        t2 = (TextView) findViewById(R.id.textView2);
        t3 = (TextView) findViewById(R.id.textView3);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);

        Intent intent = getIntent();
        int cc = intent.getIntExtra("cc",0);
        int pp = intent.getIntExtra("pp",0);
        final int oid = intent.getIntExtra("orderid",0);
        t1.setText("Order Id : "+oid);
        t2.setText("Confirmation Code : "+cc);
        t3.setText("Per hour price : "+pp);
        Toast.makeText(getApplicationContext(), cc+" "+oid, Toast.LENGTH_SHORT).show();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog ad = new AlertDialog.Builder(WaitActivity.this).create();
                ad.setMessage("Press Confirm only if you are in parking gate");
                ad.setTitle("Warning");
                ad.setButton(DialogInterface.BUTTON_POSITIVE, "Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Retrofit ret = new Retrofit.Builder()
                                .baseUrl(Api.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Api6 api = ret.create(Api6.class);
                        Call<List<Model3>> call = api.getModel(oid);
                        call.enqueue(new Callback<List<Model3>>() {
                            @Override
                            public void onResponse(Call<List<Model3>> call, Response<List<Model3>> response) {
                                List<Model3> Teams = response.body();
                                if(Teams.size()==1){
                                    List<Integer> id = new ArrayList<>();
                                    List<Integer> state = new ArrayList<>();
                                    for (int i = 0; i < Teams.size(); i++) {
                                        id.add(Integer.valueOf(Teams.get(i).getId()));
                                        state.add(Integer.valueOf(Teams.get(i).getCurrentstate()));
                                        if(state.get(i)==2){
                                            b1.setAlpha(.3f);
                                            b1.setClickable(false);
                                            Toast.makeText(getApplicationContext(), "Command Transferred", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Command Failed", Toast.LENGTH_LONG).show();
                                }

                            }
                            @Override
                            public void onFailure(Call<List<Model3>> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                ad.show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit ret = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api7 api = ret.create(Api7.class);
                Call<List<Model4>> call2 = api.getModel(oid);
                call2.enqueue(new Callback<List<Model4>>() {
                    @Override
                    public void onResponse(Call<List<Model4>> call2, Response<List<Model4>> response) {
                        List<Model4> Teams = response.body();
                        if(Teams.size()==1){
                            List<Integer> status = new ArrayList<>();
                            List<Integer> money = new ArrayList<>();
                            for (int i = 0; i < Teams.size(); i++) {
                                status.add(Integer.valueOf(Teams.get(i).getStatus()));
                                money.add(Integer.valueOf(Teams.get(i).getMoney()));
                                if(status.get(i)==1){
                                    Toast.makeText(getApplicationContext(), "You can leave", Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "Remaining money "+money.get(i)+" Taka", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "You can't leave", Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "You need "+ Math.abs(money.get(i))+" Taka", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Command Failed", Toast.LENGTH_LONG).show();
                        }

                    }
                    @Override
                    public void onFailure(Call<List<Model4>> call2, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
