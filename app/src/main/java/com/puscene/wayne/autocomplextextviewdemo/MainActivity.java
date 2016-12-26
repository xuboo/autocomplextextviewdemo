package com.puscene.wayne.autocomplextextviewdemo;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private AutoCompleteTextView check_auto_tv;
    private Button check_btn;
    private String[] arr = {"A025846", "A098646", "A0259714", "569842", "586969"};
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        check_auto_tv = (AutoCompleteTextView)findViewById(R.id.check_auto_tv);
        check_btn = (Button)findViewById(R.id.check_btn);

        MyAdapter myAdapter = new MyAdapter(this, Arrays.asList(arr));

        check_auto_tv.setAdapter(myAdapter);
        check_auto_tv.setThreshold(1);
        check_auto_tv.setOnItemClickListener(this);

        check_btn.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        check_btn.setVisibility(View.VISIBLE);
        Log.e(TAG, "dianji");
    }



    @Override
    public void onClick(View v) {
        check_auto_tv.setText("");
    }
}
