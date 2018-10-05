package com.example.lifecycle;

import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lifecycle.model.SecondUserViewModel;
import com.example.lifecycle.room.UserDatabase;
import com.example.lifecycle.room.entries.UserEntry;
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

        vm.observe(this, (user) -> {
            Logger.d("second " + user.toString());
            mUser.setText(user.toString());
        });

        if (savedInstanceState == null) {
            vm.setIdentification(3);
        }

        mUser.setOnClickListener(v -> {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    UserEntry[] entries = new UserEntry[]{
                            new UserEntry("tangmin", "1234567891"),
                            new UserEntry("tangmin", "1234567892"),
                            new UserEntry("tangmin", "1234567893")
                    };


                    long[] ids = UserDatabase.db(getApplicationContext()).userDao().insertUsers(entries);
                    return ids != null && ids.length > 0;
                }

                @Override
                protected void onPostExecute(Boolean r) {
                    if(r != null && r.booleanValue()) {
                        Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        });
    }


}
