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
import android.widget.Toolbar;

public class LoginActivity extends AppCompatActivity {

      TextView signup;
        Button login;

    AlertDialog.Builder builder;

    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        signup=(TextView)  findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
               startActivity(intent);
            }
        });

        login=(Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( email.getText().toString().equals("") || password.getText().toString().equals("") ){
                    builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Fix this error");
                    builder.setMessage("Enter email and password");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    //Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                   // startActivity(intent);
                    BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
                    backgroundTask.execute("login", email.getText().toString(), password.getText().toString());
                }
            }

        });
    }
}
