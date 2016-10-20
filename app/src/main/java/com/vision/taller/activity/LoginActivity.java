package com.vision.taller.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.vision.taller.R;
import com.vision.taller.Taller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Setup the layout
        setContentView(R.layout.activity_login);

        // get the UI components
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);

       /* Other way to setup the button click action
       findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });*/
        String username = ((Taller) getApplication()).getUsername();
        if (username != null) {
            startMainActivity(username);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadImage().execute("https://pbs.twimg.com/profile_images/565248906619940864/KBWhzUPL.jpeg");
    }

    public void clickLogin(View view) {
        login();
    }

    private void login() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if (username.equalsIgnoreCase(getString(R.string.tmp_username)) && password.equalsIgnoreCase(getString(R.string.tmp_password))) {
            startMainActivity(username);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.login_error_title)
                    .setMessage(R.string.login_error_message)
                    .setNeutralButton(R.string.login_error_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            builder.create().show();
        }
    }

    private void startMainActivity(String username) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.PARAM_USERNAME, username);
        startActivity(intent);
        saveLoginState(username);
        finish();
    }

    private void saveLoginState(String username) {
        ((Taller) getApplication()).saveLoginState(username);
    }

    // Example of a code block running outside of the main thread
    private class LoadImage extends AsyncTask<String, Integer, Bitmap> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(LoginActivity.this, "", "Please wait...", true);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL imageUrl = new URL(strings[0]);
                return BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            dialog.dismiss();
            if (bitmap != null) {
                imgLogo.setImageBitmap(bitmap);
            }
        }
    }
}
