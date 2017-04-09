package com.kaige.sharedpreferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText accountEdit;
    private EditText passwordEdit;
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox seeEdit;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.bt_login);
        accountEdit = (EditText) findViewById(R.id.et_name);
        rememberPass = (CheckBox) findViewById(R.id.cb_choose);
        seeEdit = (CheckBox) findViewById(R.id.cb_see);
        passwordEdit = (EditText) findViewById(R.id.et_password);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isSee=pref.getBoolean("remember_see",false);
        if(isSee){
            seeEdit.setChecked(true);
        }
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        seeEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edit = pref.edit();
                if (isChecked) {
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (seeEdit.isChecked()) {
                    edit.putBoolean("remember_see", true);
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (account.equals("admin") && password.equals("123")) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(getApplication(), LoginActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "account or password is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
