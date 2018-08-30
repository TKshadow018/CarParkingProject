package a3labgo.tusar.carparkingproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = (EditText) findViewById(R.id.email);
        e2 = (EditText) findViewById(R.id.password);
        b1 = (Button) findViewById(R.id.email_sign_in_button);
        int numofteams = 1;
        final int id[] = new int[numofteams];
        final String[] name = new String[numofteams];
        final String[] email = new String[numofteams];
        final String[] password = new String[numofteams];
        final int[] balance = new int[numofteams];


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "wait", Toast.LENGTH_LONG).show();
                String em,ps;
                em = e1.getText().toString();
                ps = e2.getText().toString();

                Retrofit ret = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api = ret.create(Api.class);
                Call<List<Model>> call = api.getModel(em,ps);
                call.enqueue(new Callback<List<Model>>() {
                    @Override
                    public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {
                        List<Model> Teams = response.body();
                        if(Teams.size()==1){
                            for (int i = 0; i < Teams.size(); i++) {
                                id[i]= Integer.valueOf(Teams.get(i).getId());
                                name[i]= String.valueOf(Teams.get(i).getName());
                                email[i]= String.valueOf(Teams.get(i).getEmail());
                                password[i]= String.valueOf(Teams.get(i).getPassword());
                                balance[i]= Integer.valueOf(Teams.get(i).getBalance());
                                //Toast.makeText(getApplicationContext(), name[i], Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                intent.putExtra("id",id[i]);
                                intent.putExtra("name",name[i]);
                                intent.putExtra("balance",balance[i]);
                                startActivity(intent);
                                finish();
                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<List<Model>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
