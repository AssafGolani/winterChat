package com.example.winterchat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;



public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_ON_CREATE = 1;
    private static final int REQ_CODE_AFTER_SIGN_OUT = 2;

    public void signIn(int requestCode){
        Intent signInUp = AuthUI.getInstance().createSignInIntentBuilder().build();
            /*
            In order to open an activity using an Intent object:
            1. startActivity
            2. startActivityForResult
             */
        startActivityForResult(signInUp, requestCode);
    }

    /**
     * Handle signOut procedure asynchronously
     * if signOut is done asynchronously - it will happen sometime in the future
     * Google Task Services API - signal completion
     * Then and only then will we continue with the rest of the code in the method
     * Use CompletionListener to get updated upon signout completion
     */
    private void signOut(){
        Task<Void> signOutTask = AuthUI.getInstance().signOut(this);
        signOutTask.addOnCompleteListener(task ->{
            Toast.makeText(MainActivity.this,
                    "Signed out successfully",
                    Toast.LENGTH_SHORT).show();
            signIn(REQ_CODE_AFTER_SIGN_OUT);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Check if user is signed-in. if not - prompt user to sign-in/sign-up
         */
        if (FirebaseAuth.getInstance().getCurrentUser() == null){  //user is not signed-in
            //prompt user to sign-in/sign-up
            signIn(REQ_CODE_ON_CREATE);
        }
        else{ // user is signed in
            showDetails(true);
            enterChatRoom();
        }

        FloatingActionButton button = (FloatingActionButton)findViewById(R.id.fab);
        //TODO: push data to firebase realtime-db
    }

    /**
     * Similar to setContentView invoked in onCreate method
     * this method populates the menu-bar section of the screen
     * with items declared in a menu resource xml file
     * @param menu the reference to the menu section in runtime
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        // if app doesn't crash, return true
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // only one menu item can be selected at a time
        if (item.getItemId() == R.id.log_out_item) signOut();
        return true;
    }

    /**
     *This method is invoked automatically after the current activity is popped from android stack.
     * @param requestCode - the code passed to startActivityForResult, representing the method
     *                    that initiated the activity
     * @param resultCode - represents the status of the finished activity.
     * @param data - further data passed with the intent from the finished activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_ON_CREATE){
            //
            if (resultCode == RESULT_OK){
                //user successfully signed in
                showDetails(true);
                enterChatRoom();
            }else{
                showDetails(false);
                //if MainActivity finishes, the application will close
                finish();
            }
        }
    }



    /**
     * This method shows a pop-up on MainActivity with either the user details in case
     * of success login or a termination error in case the user didn't login successfully
     * @param success
     */
    public void showDetails(boolean success){
        if(success){
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userDetails = "Your display name: " + currentUser.getDisplayName()
                    + ", your ID: "
                    + currentUser.getUid()
                    + ", provider: "
                    + currentUser.getProviderId();
            Toast.makeText(this, userDetails, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, R.string.failed_login, Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * This method opens a new activity representing 2 authenticated users that can chat
     */
    public void enterChatRoom(){

    }

    public void signInAfterSignOut(){

    }




}