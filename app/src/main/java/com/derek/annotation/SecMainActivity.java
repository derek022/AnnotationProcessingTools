package com.derek.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.derek.annotation.ViewInjector;
import org.derek.butter.ButterKnife;

public class SecMainActivity extends Activity {

    @ViewInjector(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }
}
