package com.easylife.letsgo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.easylife.letsgo.R;
import com.easylife.letsgo.utils.Callable;
import com.easylife.letsgo.utils.Callback;
import com.easylife.letsgo.utils.NetUtil;

public class RegisterActivity extends BaseActivity
        implements View.OnClickListener{

    private String Tag = RegisterActivity.class.toString();

    /**
     * 用户账户
     */
    private EditText mUsername;
    /**
     * 密码
     */
    private EditText mPassword;

    private EditText mPassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUIMembers();

        initUIHandler();
    }

    private void initUIMembers(){
        mUsername = (EditText) findViewById(R.id.et_username);
        mPassword = (EditText) findViewById(R.id.et_password);
        mPassword2 = (EditText) findViewById(R.id.et_password2);


    }

    private void initUIHandler(){
        Button btn = (Button) findViewById(R.id.btn_sign_up);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_up:
                attemptRegister();
                break;
        }
    }


    private void attemptRegister() {
        doAsync(new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                String req = String.format("v1/user");

                String response = NetUtil.sendPostRequest(req);

                return null;
            }
        }, new Callback<Void>() {
            @Override
            public void onCallback(Void pCallbackValue) {

            }
        });
    }
}
