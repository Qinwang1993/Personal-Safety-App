package com.sjsu.sister.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.sjsu.sister.R;
import com.sjsu.sister.model.User;
import com.sjsu.sister.sqlite.DatabaseHelper;
import com.sjsu.sister.util.CustomToast;
import com.sjsu.sister.util.StringUtils;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends BaseActivity {
    private EditText etEmail;
    private EditText etPwd;
    private Button btnLogin;
    private TextView notMember;
    private DatabaseHelper databaseHelper;

    //facebook login
    private CallbackManager callbackManager;
    private LoginButton fbloginButton;
    private ImageView facebook;
    private static final String EMAIL = "email";

    //google login
    private ImageView google;
    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
        notMember = findViewById(R.id.not_member);
        databaseHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(email, pwd);
            }
        });

        notMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(RegisterActivity.class);
            }
        });

        //facebook login
        callbackManager = CallbackManager.Factory.create();
        facebook = findViewById(R.id.facebook);


        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
            }
        });

        //google login
        google = findViewById(R.id.google);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
    }

    private void login(String email, String pwd) {
        if (StringUtils.isEmpty(email)) {
            CustomToast.popWarning(this,"Pleas enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            CustomToast.popError(this,"Pleas enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            CustomToast.popWarning(this,"Pleas enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (databaseHelper.checkUser(email, pwd)) {
            signIn("", email);
        } else {
            CustomToast.popError(this,"Wrong Email or Password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            if (object == null) {
                                return;
                            }
                            Log.d("Demo", object.toString());
                            String email = null;
                            String name = null;
                            try {
                                email = object.getString("email");
                                name = object.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            User user = new User(name, email, "123456", null, null);
                            databaseHelper.addUser(user);
                            signIn(name, email);
                        }
                    });
            Bundle bundle = new Bundle();
            bundle.putString("fields", "name, id, first_name, last_name, email");
            graphRequest.setParameters(bundle);
            graphRequest.executeAsync();
        }
    }

    //googleSignin
    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signIn(String name, String email) {
        Intent in = new Intent(LoginActivity.this, HomeActivity.class);
        SharedPreferences sp = getSharedPreferences("Sister", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Status", true);
        editor.putString("Email", email);
        editor.commit();
        in.putExtra("Email", email);
        startActivity(in);
        finish();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.w("test", account.getEmail());
            Log.w("test", account.getDisplayName());
            Log.w("test", account.getGivenName());
            User user = new User(account.getDisplayName(), account.getEmail(), null, null, null);
            databaseHelper.addUser(user);
            signIn(account.getDisplayName(), account.getEmail());


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}