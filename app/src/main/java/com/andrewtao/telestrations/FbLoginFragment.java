package com.andrewtao.telestrations;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class FbLoginFragment extends Fragment {

    public FbLoginFragment() {
        // Required empty public constructor
    }

    CallbackManager callbackManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }


    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fb_login, container, false);
        final TextView textFbLoginStatus = (TextView) view.findViewById(R.id.text_fb_login_status);
        updateFbLoginStatusText(textFbLoginStatus);

        Profile profile = Profile.getCurrentProfile();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    updateFbLoginStatusText(textFbLoginStatus);
                }
            }
        };
        if (profile == null) {
            LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
            loginButton.setReadPermissions("email");
            // If using in a fragment
            loginButton.setFragment(this);

            // Callback registration
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    Log.d("Debug", "Success!");
                    updateFbLoginStatusText(textFbLoginStatus);
                }

                @Override
                public void onCancel() {
                    // App code
                    Log.d("Debug", "Cancel!");
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Log.d("Debug", "Error!");
                }
            });
        }
        accessTokenTracker.startTracking();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void updateFbLoginStatusText(TextView textFbLoginStatus) {
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            textFbLoginStatus.setText("Logged in as " + profile.getName());
        } else {
            textFbLoginStatus.setText("Not Logged In");
        }
    }

}
