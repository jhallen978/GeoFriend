package com.firebaseio.geofriend.geofriend;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;

public class LoginFragment extends Fragment {

    private TextView mTextDetails;

    private CallbackManager mCallbackManager;

    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;

    private void nextActivity(User info){
        Intent main = new Intent(getActivity(),LobbyActivity.class);

        main.putExtra("fname", info.getFName());
        main.putExtra("lname", info.getLName());
        main.putExtra("id", info.getId());
        main.putExtra("pic", info.getPic());

        startActivity(main);
    }

    private void dealWithData(User thisUser){
        Firebase usersRef = new Firebase("https://geofriend.firebaseio.com/users");
        usersRef.child(thisUser.getId()).setValue(thisUser);
    }

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Log.d("LOGIN:", "onSuccess");

            if(Profile.getCurrentProfile() == null) {
                mProfileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                        Profile profile1 = Profile.getCurrentProfile();

                        String fname = profile1.getFirstName();
                        String lname = profile1.getLastName();
                        String id = profile1.getId();

                        Uri pic = profile1.getProfilePictureUri(150, 150);
                        String uriToString = pic.toString();

                        User userCheck = new User(id, fname, lname, uriToString);

                        Log.d("LOGIN GRAPH:", id + " " + fname + " " + lname + " " + userCheck.getPic());
                        //Facebook info ->
                        dealWithData(userCheck);
                        nextActivity(userCheck);
                        mProfileTracker.stopTracking();
                    }
                };
                mProfileTracker.startTracking();
            }else {

                Profile profile = Profile.getCurrentProfile();

                String fname = profile.getFirstName();
                String lname = profile.getLastName();
                String id = profile.getId();

                Uri pic = profile.getProfilePictureUri(150, 150);
                String uriToString = pic.toString();

                User userCheck = new User(id, fname, lname, uriToString);

                Log.d("LOGIN GRAPH:", id + " " + fname + " " + lname + " " + userCheck.getPic());
                //Facebook info ->
                dealWithData(userCheck);
                nextActivity(userCheck);
            }
        }


        @Override
        public void onCancel() {
            Log.d("CallbackonCancel", "cancelled");
        }

        @Override
        public void onError(FacebookException e) {
            Log.d("CallbackError", "error");
        }

    };


    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("OCV", "on create view");

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupLoginButton(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OR", "on resume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ST", "stoped tracking");
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        Log.d("AR", "activity result");
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                Log.d("ATT", "" + currentAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                Log.d("PT", "" + currentProfile);

            }
        };
    }

    private void setupLoginButton(View view) {
        LoginButton mButtonLogin = (LoginButton) view.findViewById(R.id.login_button);
        mButtonLogin.setFragment(this);
        mButtonLogin.setCompoundDrawables(null, null, null, null);
        mButtonLogin.setReadPermissions("public_profile");
        mButtonLogin.registerCallback(mCallbackManager, mFacebookCallback);
    }
}