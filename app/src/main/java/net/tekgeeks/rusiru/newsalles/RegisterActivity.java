package net.tekgeeks.rusiru.newsalles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

       EditText mName,mEmail,mPassword;
     Button mRegister;
     TextView mBacktoLogin;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       mName=(EditText) findViewById(R.id.app_title);
        mEmail=(EditText) findViewById(R.id.email);
        mPassword=(EditText) findViewById(R.id.password);
        mBacktoLogin=(TextView) findViewById(R.id.btnLinkToLoginScreen);
        mBacktoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        mRegister=(Button) findViewById(R.id.btnRegister);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(mName.getText().toString().equals("")||mEmail.getText().toString().equals("")||mPassword.getText().toString().equals("")){builder=new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Your data Filling Error");
                builder.setMessage("Please fill all the fields");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                }
                else {
                    BackgroundTask backgroundTask=new BackgroundTask(RegisterActivity.this);
                    backgroundTask.execute("register",mName.getText().toString(),mEmail.getText().toString(),mPassword.getText().toString());
                    Toast.makeText(RegisterActivity.this, "register Success",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
