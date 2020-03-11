package com.example.loginin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);


        Button btn=(Button) findViewById(R.id.SIGNIN);
        final EditText editText=(EditText) findViewById(R.id.username);
        final EditText et_psw=(EditText) findViewById(R.id.psw);



// Login Button to Access The Counter

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString().trim();
                String psw = et_psw.getText().toString().trim();
                if(name.equals("")|| name.isEmpty()){
                    editText.setError("field is required");
                    editText.requestFocus();
                }
                else if(psw.equals("")|| psw.isEmpty())
                {
                    et_psw.setError("field is required");
                    et_psw.requestFocus();
                }
                else {
                    intent = new Intent(LoginActivity.this,MainActivity.class);
                    //Passing Name to Counter ACTIVITY
                    intent.putExtra("name",name);
                    startActivity(intent);
                }



            }
        });



    }
}
