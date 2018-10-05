package com.example.lifecycle;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.lifecycle.model.UserViewModel;
import com.example.lifecycle.ui.MainFragment;
import com.example.lifecycle.util.Logger;

public class MainActivity extends AppCompatActivity {


    TextView mUserText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("activity " + this.toString());
        setContentView(R.layout.activity_main);

        mUserText = findViewById(R.id.user);

        getLifecycle().addObserver(new SelfLifeCycleObserver());

//        new LoaderTask().execute();
        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.getUser().observe(this, (user) -> {
            Logger.d("this is called in observe onchange");
            if (user != null) {
                mUserText.setText(user.mName);
            }
        });

        Logger.d(viewModel.toString());

        Intent secondIntent = new Intent();
        secondIntent.setComponent(new ComponentName(getApplicationContext(),SecondActivity.class));
        mUserText.setOnClickListener((self)->startActivity(secondIntent));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new MainFragment(),"main_fragment").commit();
        }
    }


}
