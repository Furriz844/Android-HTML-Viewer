package com.example.yddmitriytask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HTMLView extends AppCompatActivity {
    RequestTask DownloadHTML;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmlview);
        String URL = getIntent().getStringExtra("URL");
        setTitle(URL);
        final TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setMovementMethod(new ScrollingMovementMethod());
        DownloadHTML = new RequestTask();
        DownloadHTML.execute(URL);
        try {
            textView.setText(DownloadHTML.get());
        } catch (Exception e) {
            textView.setText("Упс, что-то пошло не так! Вините во всем криворуких разработчиков!");

        }

    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    class RequestTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... url) {
            StringBuilder sb = new StringBuilder();
            try {
                URL pageURL = new URL(url[0]);
                String HtmlString;
                URLConnection urlconnect = pageURL.openConnection();
                BufferedReader buff = new BufferedReader(new InputStreamReader(urlconnect.getInputStream()));
                while ((HtmlString = buff.readLine()) != null) {
                    sb.append(HtmlString+"\n");
                }
                buff.close();
            }
            catch (Exception e) {
            }
            return sb.toString();
        }
    }
}
