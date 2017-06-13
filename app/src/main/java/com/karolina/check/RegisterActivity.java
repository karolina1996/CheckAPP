package com.karolina.check;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.karolina.check.databinding.ActivityRegisterBinding;
import com.karolina.check.models.SimpleResponse;
import com.karolina.check.models.User;
import com.karolina.check.net.UserService;
import com.karolina.check.util.Data;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    ActivityRegisterBinding binding;
    UserService service;
    Date birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.register.setOnClickListener(this);
        binding.cancel.setOnClickListener(this);
        binding.birthday.setOnClickListener(this);

        birthday = new Date();
        service = Data.retrofit.create(UserService.class);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                register();
                break;
            case  R.id.cancel:
                finish();
                break;

            case R.id.birthday:
                datePicker();
                break;
        }
    }

    private void register(){

        User user = new User();
        user.setName(binding.name.getText().toString());
        user.setUser(binding.user.getText().toString());
        user.setPassword(binding.password.getText().toString());
        user.setBirthday(birthday);
        Call<SimpleResponse> request = service.signin(user);
        request.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                if(response.isSuccessful()){
                    SimpleResponse res = response.body();
                    if(res.isSuccess()){
                        Toast.makeText(RegisterActivity.this, "El registro fue exitoso", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this, "No se pudo hacer el registro", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error de coneccion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void datePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                binding.birthday.setText(year + "-" + (monthOfYear+1)+ "-" +dayOfMonth);
                birthday = newDate.getTime();
            }

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
