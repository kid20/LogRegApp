package com.example.ralph.logregapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView click_me;
    Button btn_log;
    EditText txt_username,txt_password;
    String _username,_password;
    String login_url = "http://192.168.43.130/login.php";
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_log = (Button)findViewById(R.id.btn_log);
        click_me = (TextView)findViewById(R.id.lbl_registter);
        txt_username = (EditText)findViewById(R.id.txt_user);
        txt_password = (EditText)findViewById(R.id.txt_pass);

        builder = new AlertDialog.Builder(MainActivity.this);
        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _username = txt_username.getText().toString();
                _password = txt_password.getText().toString();

                if(_username.equals("") || _password.equals(""))
                {
                   builder.setTitle("Something went wrong...");
                   displayAlert("Enter a valid username and password");
                }

                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");

                                        if(code.equals("login_failed"))
                                        {
                                            builder.setTitle("Login Error...");
                                            displayAlert(jsonObject.getString("message"));
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(MainActivity.this, LognSuccess.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("name",jsonObject.getString("name"));
                                            bundle.putString("email",jsonObject.getString("email"));
                                            intent.putExtras(bundle);
                                            startActivity(intent);

                                        }
//
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();

                        }
                    })
                    {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> params = new HashMap<String, String>();
                            params.put("user_name", _username);
                            params.put("password", _password);
                            return params;
                        }
                    };

                    MySingleton.getmInstance(MainActivity.this).addToRequestque(stringRequest);





                    //
                }


                //


            }
        });



        click_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
            }
        });


    }

    public void displayAlert(String message)
    {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                txt_username.setText("");
                txt_password.setText("");

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    //
}
