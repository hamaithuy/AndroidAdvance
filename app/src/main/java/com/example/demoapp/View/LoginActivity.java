package com.example.demoapp.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.demoapp.Model.User;
import com.example.demoapp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    SignInButton btnSignInGoogle;
    DatabaseReference userReff;

    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInApi mGoogleSignInApi;

    private final int RC_SIGN_IN = 3;
    public static final String TAG ="LoginActivity";

    User userAccount = new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Widget
        btnSignInGoogle = findViewById(R.id.btn_sign_in_google);
        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD);
        userReff = FirebaseDatabase.getInstance().getReference("Users");


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSignInGoogle.setOnClickListener(signInButtonListener);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "User not logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

    }
    private Button.OnClickListener btnPostedRoomListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    comeToPostedRoomActivity();
                }
            };
    private void comeToPostedRoomActivity() {
        Intent intent= new Intent(this,com.example.demoapp.View.PostedActivity.class);
        startActivity(intent);
    }

    private SignInButton.OnClickListener signInButtonListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId())
                    {
                        case R.id.btn_sign_in_google: signIn();
                            break;
                    }
                }
            };

    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        mGoogleSignInClient.signOut();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getCurrentUser();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener!=null)
        {
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                handleSignInResult(task);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "u are able to login to google", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }


    private void updateUI(FirebaseUser user) {

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
           // String persionIdFirebase = user.getUid();
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();


            userAccount.setId(personId);
            userAccount.setName(personName);
            userAccount.setEmail(personEmail);


           // Toast.makeText(this, "ID Firebase:"+persionIdFirebase+"----ID CurrentGoogle:"+personId, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Personer Name:"+ personName +"/n"+"ID:"+personId, Toast.LENGTH_SHORT).show();
            //checkUser();
            addUser();

            MainActivity.flag=1;
            Intent intentAccount = new Intent(this,MainActivity.class);
            intentAccount.putExtra("NAME",personName);
            intentAccount.putExtra("PHOTO",personPhoto.toString());
            intentAccount.putExtra("ID",personId);
            startActivity(intentAccount);

            finish();
        }

    }

    private void checkUser() {
        userReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userData : dataSnapshot.getChildren())
                {
                    if(userData!=null)
                    {
                        if(userData.exists())
                        {
                            return ;
                        }
                    }
                    addUser();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addUser() {
        User userFirebase = new User(userAccount.getName(),false,userAccount.getEmail(),"","");
        userReff.child(userAccount.getId()).setValue(userFirebase);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.

            updateUI(null);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


}

