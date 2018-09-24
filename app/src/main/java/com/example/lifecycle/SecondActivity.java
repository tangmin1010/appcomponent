package com.example.lifecycle;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lifecycle.model.SecondUserViewModel;
import com.example.lifecycle.model.UserViewModel;
import com.example.lifecycle.util.Logger;

public class SecondActivity extends AppCompatActivity {

    TextView mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mUser = findViewById(R.id.second_user);

        SecondUserViewModel vm = ViewModelProviders.of(this).get(SecondUserViewModel.class);

        Logger.d("second " + vm.toString());

        vm.observe(this, (user)->{
            Logger.d("second " + user.toString());
            mUser.setText(user.toString());
        });

        if (savedInstanceState == null) {
            vm.setIdentification(3);
        }
    }
}
