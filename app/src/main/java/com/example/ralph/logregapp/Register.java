package com.example.ralph.logregapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Button reg;
    EditText _name,_email,_uname,_pass,_cpass;
    String _Name, _Email, _Uname, _Pass, _Cpass;
    AlertDialog.Builder builder;
    String reg_url ="http://192.168.43.130/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg = (Button)findViewById(R.id.btn_reg);
        _name = (EditText)findViewById(R.id.txt_name);
        _email = (EditText)findViewById(R.id.txt_mail);
        _uname = (EditText)findViewById(R.id.txt_uname);
        _pass =  (EditText) findViewById(R.id.txt_password);
        _cpass = (EditText) findViewById(R.id.txt_cpassword);
        builder = new AlertDialog.Builder(Register.this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             _Name =  _name.getText().toString();
             _Email =  _email.getText().toString();
             _Uname =  _uname.getText().toString();
              _Pass =  _pass.getText().toString();
             _Cpass =  _cpass.getText().toString();

                if(_Name.equals("") || _Email.equals("") || _Uname.equals("") || _Pass.equals("") || _Cpass.equals(""))
                {
                    builder.setTitle("Something went wrong...");
                    builder.setMessage("Please fill all the fields");
                    displayAlert("input_error");
                }

                else
                {


                    if(!(_Pass.equals(_Cpass)))
                    {
                        builder.setTitle("Something went wrong...");
                        builder.setMessage("Your password not matching");
                        displayAlert("input_error");

                    }
                    else
                    {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, reg_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            String message = jsonObject.getString("message");
                                            builder.setTitle("Server Response....");
                                            builder.setMessage(message);
                                            displayAlert(code);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("name",_Name);
                                params.put("email",_Email);
                                params.put("user_name",_Uname);
                                params.put("password",_Pass);

                                return params;
                            }
                        };

                        MySingleton.getmInstance(Register.this).addToRequestque(stringRequest);
                    //
                    }


                    ///
                }


                //
            }
        });


    }


    public void displayAlert(final String code)
    {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(code.equals("imput_error"))
                {
                    _pass.setText("");
                    _cpass.setText("");
                }
                else if(code.equals("reg_success"))
                {
                    finish();

                }
                else if(code.equals("res_failed"))
                {
                    _name.setText("");
                    _email.setText("");
                    _uname.setText("");
                    _pass.setText("");
                    _cpass.setText("");
                }





//
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    ///
}
