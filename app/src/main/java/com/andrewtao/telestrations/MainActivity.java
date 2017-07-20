package com.andrewtao.telestrations;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// Imports for Facebook SDK
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // printHash();
        if (findViewById(R.id.fb_login_holder) != null) {
            if (savedInstanceState != null) {
                return;
            }

            // we are extending AppCompatActivity, so we need supportFragmentManager
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fb_login_holder, new FbLoginFragment()).commit();
        }
    }

    // Called when the user taps the "Start online game" button.
    public void startOnlineGame(View view) {
        // Do something in response to button
        Profile profile = Profile.getCurrentProfile();
        if (profile == null) {
            Context context = getApplicationContext();
            CharSequence text = "Please sign in!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            Intent intent = new Intent(this, CreateGameActivity.class);
            startActivity(intent);
        }
    }

    // Called when the user taps the "Start offline game" button.
    public void startOfflineGame(View view) {
        // Do something in response to button
    }

    public void printHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.andrewtao.telestrations",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
