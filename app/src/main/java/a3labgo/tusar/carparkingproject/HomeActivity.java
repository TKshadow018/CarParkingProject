package a3labgo.tusar.carparkingproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    Button btn;
    TextView tv1,tv2;
    Spinner sp;
    int id,tempID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn = (Button) findViewById(R.id.button);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.textView2);
        sp = (Spinner) findViewById(R.id.spinner);

        Intent intent = getIntent();
        id= intent.getIntExtra("id",0);
        Toast.makeText(getApplicationContext(), id+"<-userID ", Toast.LENGTH_SHORT).show();
        tempID = id;
        String name = intent.getStringExtra("name");
        int balance = intent.getIntExtra("balance",0);

        tv1.setText("Welcome "+ name);
        tv2.setText("Please Select your area");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String selectedlocation = sp.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), "wait", Toast.LENGTH_LONG).show();
                Retrofit ret2 = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api2 api2 = ret2.create(Api2.class);
                Call<List<Model2>> call2 = api2.getModel(selectedlocation);

                call2.enqueue(new Callback<List<Model2>>() {
                    @Override
                    public void onResponse(Call<List<Model2>> call2, Response<List<Model2>> response) {
                        List<Model2> Teams = response.body();
                        List<String> Pname = new ArrayList<>();
                        for (int i = 0; i < Teams.size(); i++) {
                            Pname.add(String.valueOf(Teams.get(i).getName()));
                        }
                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                                getBaseContext(),
                                android.R.layout.simple_list_item_1,
                                Pname);
                        ListView listView = (ListView) findViewById(R.id.list);
                        listView.setAdapter(adapter1);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                String clickedItem = (parent.getItemAtPosition(position)).toString().trim();;
                                Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
                                intent.putExtra("ParkingName",clickedItem);
                                intent.putExtra("uid",tempID);
                                Toast.makeText(getApplicationContext(), tempID+"<-userID Later ", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Model2>> call2, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}