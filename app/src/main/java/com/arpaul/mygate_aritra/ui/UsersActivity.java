package com.arpaul.mygate_aritra.ui;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arpaul.mygate_aritra.R;
import com.arpaul.mygate_aritra.constants.Constant;
import com.arpaul.mygate_aritra.models.User;
import com.arpaul.mygate_aritra.viewmodel.UserVM;
import com.arpaul.utilitieslib.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersActivity extends AppCompatActivity {

    @BindView(R.id.rv_Users)
    protected RecyclerView rv_Users;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.fab)
    protected FloatingActionButton fab;
    @BindView(R.id.tvNooneHere)
    protected TextView tvNooneHere;

    private UserAdapter adapter;
    private List<User> mUsers = new ArrayList<>();
    private UserVM userVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsersActivity.this, AddUserActivity.class));
            }
        });

        setAdapter();

        userVM = ViewModelProviders.of(this).get(UserVM.class);
        userVM.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                mUsers = users;
                adapter.refresh(users);
                isUserAvailable();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (new PermissionUtils().checkPermission(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA})
                    != PackageManager.PERMISSION_GRANTED) {
                new PermissionUtils().requestPermission(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.getPermCamera());

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constant.getPermCamera()) {
            for(int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                if(grantResults[i] == PackageManager.PERMISSION_DENIED)
                    Toast.makeText(UsersActivity.this, "The app needs " + permission + " to work properly.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setAdapter() {
        adapter = new UserAdapter(this, mUsers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_Users.setLayoutManager(mLayoutManager);
        rv_Users.setItemAnimator(new DefaultItemAnimator());
        rv_Users.setAdapter(adapter);

        isUserAvailable();
    }

    private void isUserAvailable() {
        if(mUsers.size() > 0)
            tvNooneHere.setVisibility(View.GONE);
        else
            tvNooneHere.setVisibility(View.VISIBLE);
    }
}
