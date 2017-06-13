package com.karolina.check;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.karolina.check.databinding.ActivityLoginBinding;
import com.karolina.check.models.SimpleResponse;
import com.karolina.check.models.User;
import com.karolina.check.net.UserService;
import com.karolina.check.util.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityLoginBinding binding;
    SharedPreferences preferences;
    UserService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("preferencias",MODE_PRIVATE);
        boolean logged = preferences.getBoolean("logged", false);
        if(logged){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(this);
        binding.register.setOnClickListener(this);

        service = Data.retrofit.create(UserService.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                break;
            case R.id.register:
                Intent intentR = new Intent(this, RegisterActivity.class);
                startActivity(intentR);
                break;
        }
    }

    private void login(){

        String u = binding.user.getText().toString();
        String p = binding.password.getText().toString();
        User user = new User(u, p);

        final Call<SimpleResponse> request = service.login(user);
        request.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if(response.isSuccessful()){
                    SimpleResponse res = response.body();
                    if(res.isSuccess()){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("logged", true);
                        editor.commit();
                        Intent intentL = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intentL);
                    }else {
                        Toast.makeText(LoginActivity.this, res.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de coneccion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
