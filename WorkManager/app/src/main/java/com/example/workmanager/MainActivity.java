package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button statrtWorkmanager_btn;
    private Button stoptWorkmanager_btn;
    private WorkManager workManager;
    private WorkRequest workRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeViews();
        intializeWorkManager();
    }

    private void intializeViews(){
        statrtWorkmanager_btn=(Button) findViewById(R.id.start_work_btn);
        stoptWorkmanager_btn=(Button) findViewById(R.id.stop_work_btn);
        statrtWorkmanager_btn.setOnClickListener(this);
        stoptWorkmanager_btn.setOnClickListener(this);
    }

    private void intializeWorkManager(){
        workManager=WorkManager.getInstance(getApplicationContext());
        workRequest=new PeriodicWorkRequest.Builder(RandomNumberGeneratorWorker.class,15, TimeUnit.MINUTES).build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_work_btn:
                workManager.enqueue(workRequest);
                break;
            case R.id.stop_work_btn:
                workManager.cancelWorkById(workRequest.getId());
                break;
        }
    }
}