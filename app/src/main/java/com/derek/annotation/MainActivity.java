package com.derek.annotation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.derek.annotation.ViewInjector;
import org.derek.butter.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @ViewInjector(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        Log.e("derek","main="+textView.hashCode());
    }
}
