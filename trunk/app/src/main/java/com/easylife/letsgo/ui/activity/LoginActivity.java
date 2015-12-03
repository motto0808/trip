package com.easylife.letsgo.ui.activity;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.easylife.letsgo.AppContext;
import com.easylife.letsgo.R;
import com.easylife.letsgo.model.ApiResponse;
import com.easylife.letsgo.model.User;
import com.easylife.letsgo.utils.NetUtil;
import com.easylife.letsgo.utils.StringUtil;
import com.easylife.letsgo.ui.widget.EditTextHolder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity
        implements View.OnClickListener,
        Handler.Callback,
        LoaderCallbacks<Cursor>,
        EditTextHolder.OnEditTextFocusChangeListener {

    private String Tag = "LoginActivity";
    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    private int HANDLER_LOGIN_SUCCESS = 1;
    private int HANDLER_LOGIN_FAILURE = 2;
    private int HANDLER_LOGIN_HAS_FOCUS = 3;
    private int HANDLER_LOGIN_HAS_NO_FOCUS = 4;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private View mProgressView;
    private View mLoginFormView;

    /**
     * 用户账户
     */
    private AutoCompleteTextView mEmailView;
    /**
     * 密码
     */
    private EditText mPasswordView;

    /**
     * 输入用户名删除按钮
     */
    private FrameLayout mFrUserNameDelete;
    /**
     * 输入密码删除按钮
     */
    private FrameLayout mFrPasswordDelete;

    /**
     * 背景图
     */
    private ImageView mImgBackground;

    EditTextHolder mEditUserNameEt;
    EditTextHolder mEditPassWordEt;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_et_email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.login_et_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        SharedPreferences sp = AppContext.getInstance().getSharedPreferences();
        String s_username = sp.getString("Username",null);
        String s_password = sp.getString("Password","");
        if(s_username != null){
            mEmailView.setText(s_username);
            mPasswordView.setText(s_password);
        }

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);

        findViewById(R.id.login_tv_forgot).setOnClickListener(this);
        findViewById(R.id.login_tv_sign_up).setOnClickListener(this);

        mImgBackground = (ImageView) findViewById(R.id.login_img_backgroud);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        mFrUserNameDelete = (FrameLayout) findViewById(R.id.fr_username_delete);
        mFrPasswordDelete = (FrameLayout) findViewById(R.id.fr_password_delete);

        //下面的代码为 EditTextView 的展示以及背景动画
        mEditUserNameEt = new EditTextHolder(mEmailView, mFrUserNameDelete, null);
        mEditPassWordEt = new EditTextHolder(mPasswordView, mFrPasswordDelete, null);
        mHandler = new Handler(this);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translate_backgroud);
                mImgBackground.startAnimation(animation);
                mEditPassWordEt.setmOnEditTextFocusChangeListener(LoginActivity.this);
                mEditUserNameEt.setmOnEditTextFocusChangeListener(LoginActivity.this);
            }
        }, 200);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return StringUtil.isEmail(email);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == HANDLER_LOGIN_FAILURE) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (msg.what == HANDLER_LOGIN_SUCCESS) {


            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (msg.what == HANDLER_LOGIN_HAS_FOCUS) {

        } else if (msg.what == HANDLER_LOGIN_HAS_NO_FOCUS) {

        }

        return false;
    }

    @Override
    public void onEditTextFocusChange(View v, boolean hasFocus) {
        Message mess = Message.obtain();
        switch (v.getId()) {
            case R.id.login_et_email:
            case R.id.login_et_password:
                if (hasFocus) {
                    mess.what = HANDLER_LOGIN_HAS_FOCUS;
                }
                mHandler.sendMessage(mess);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
            case R.id.login_et_email:
            case R.id.login_et_password:
                Message msg = Message.obtain();
                msg.what = HANDLER_LOGIN_HAS_FOCUS;
                mHandler.sendMessage(msg);
                break;

            case R.id.login_tv_forgot:
                Toast.makeText(this,"Forgot password", Toast.LENGTH_SHORT);
                break;
            case R.id.login_tv_sign_up:
                startActivity(new Intent(this, RegisterActivity.class));

                break;
        }
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    public enum LoginResult{
        LoginSuccess,
        LoginNoUser,
        LoginInvalidUsername,
        LoginInvalidPassword,
        LoginWrongPassword,
        LoginInnerError,
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final LoginResult mLoginResult;


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = StringUtil.getMD5(password);
            mLoginResult = LoginResult.LoginSuccess;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.

                String req = String.format("v1/user/login?username=%s&password=%s", mEmail, mPassword);

                String response = NetUtil.sendGetRequest(req);
                if(response != null)
                {
                    Gson gson = new Gson();
                    ApiResponse<User> res = new ApiResponse<>();
                    res = gson.fromJson(response, res.getClass());

                    if(res.getError() == 0) {
                        User user = res.getData();
                        Log.d(Tag, user.getUsername());
                    }
                    else {
                        Log.e(Tag, res.getReason());
                    }
                }
                else
                {
                    Log.e(Tag, "Login Error");
                }
            } catch (IllegalArgumentException e){
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                switch (mLoginResult)
                {
                    case LoginSuccess:
                        SharedPreferences sp = AppContext.getInstance().getSharedPreferences();
                        sp.edit().putString("Username", mEmail);
                        sp.edit().putString("Password", mPassword);
                        Intent it = new Intent();
                        it.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(it);
                        finish();
                        break;
                    case LoginNoUser:
                        mEmailView.setError(getString(R.string.error_no_username));
                        break;
                    case LoginWrongPassword:
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                        break;
                    case LoginInnerError:
                        Snackbar.make(mEmailView,"Server Inner Error",Snackbar.LENGTH_SHORT).show();
                        break;
                }

            } else {
                //网络异常
                Snackbar.make(mEmailView,"Network Error",Snackbar.LENGTH_SHORT).show();

                Intent it = new Intent();
                it.setClass(LoginActivity.this, MainActivity.class);
                startActivity(it);
                finish();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

