package com.example.yddmitriytask;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ( !isOnline() ){
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            this.finish();
        }
        final EditText URLInput = (EditText) findViewById(R.id.editText);
        final Button OKButton = (Button) findViewById(R.id.button_ok);
        View.OnClickListener oclBtnOk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Url ="http://"+URLInput.getText().toString()+"/";
                if (!checkWithRegExp(Url)) { // Подробнее, почему были использованы регулярные выражения, читать ниже!
                    Toast.makeText(getApplicationContext(), "Неккоректно введен адрес", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, HTMLView.class);
                    intent.putExtra("URL", Url);
                    startActivity(intent);
                }
            }
        };
        OKButton.setOnClickListener(oclBtnOk);

    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else {
            return true;
        }
    }
    /** Я знаю про URLUtil.isValidUrl, однако в приложении нет поддержки кириллических доменов
     *  Поэтому мне необходимо использовать регулярные выражения */
    public static boolean checkWithRegExp(String userNameString){
        Pattern p = Pattern.compile("^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)");
        Matcher m = p.matcher(userNameString);
        return m.matches();
    }
}
