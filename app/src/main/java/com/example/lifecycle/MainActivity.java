package com.example.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.lifecycle.observer.SelfLifeCycleObserver;
import com.example.lifecycle.ui.AddUserFragment;
import com.example.lifecycle.ui.MainFragment;
import com.example.lifecycle.util.Logger;
import com.example.lifecycle.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("activity " + this.toString());
        setContentView(R.layout.activity_main);

        getLifecycle().addObserver(new SelfLifeCycleObserver());
//      getLifecycle().addObserver(new SelfDefaultLifecycleObserver());

        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.observe(this,user -> {
            if(user != null && getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
                Toast.makeText(this,"the user is " + user.toString(), Toast.LENGTH_SHORT).show();
            }
        });



//        new LoaderTask().execute();
//        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        viewModel.getUser().observe(this, (user) -> {
//            Logger.d("this is called in observe onchange");
//            if (user != null) {
//                mUserText.setText(user.mName);
//            }
//        });

//        Logger.d(viewModel.toString());

//        Intent secondIntent = new Intent();
//        secondIntent.setComponent(new ComponentName(getApplicationContext(),SecondActivity.class));
//        mUserText.setOnClickListener((self)->startActivity(secondIntent));
//
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, new AddUserFragment(), "add_fragment")
                    .add(R.id.main_container, new MainFragment(),"main_fragment").commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
