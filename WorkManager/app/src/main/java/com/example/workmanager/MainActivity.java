package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;

import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button statrtWorkmanager_btn;
    private Button stoptWorkmanager_btn;
    private WorkManager workManager;
    private WorkRequest workRequest,workRequest_1,workRequest_2,workRequest_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intializeViews();
        intializeWorkRequest();
    }

    private void intializeViews(){
        statrtWorkmanager_btn=(Button) findViewById(R.id.start_work_btn);
        stoptWorkmanager_btn=(Button) findViewById(R.id.stop_work_btn);
        statrtWorkmanager_btn.setOnClickListener(this);
        stoptWorkmanager_btn.setOnClickListener(this);
    }

    private void intializeWorkRequest(){
        workManager=WorkManager.getInstance(getApplicationContext());
        workRequest=new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker.class).addTag("WorkManager").build();
        workRequest_1=new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker_1.class).addTag("WorkManager_1").build();;
        workRequest_2=new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker_2.class).addTag("WorkManager_2").build();
        workRequest_3=new OneTimeWorkRequest.Builder(RandomNumberGeneratorWorker_3.class).addTag("WorkManager_3").build();
    }

    /**
     *
     * Work Chain ing for the periodic workRequest is not supported as per the Documentation.
     * We can do in only with OneTimeWorkRequest.
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_work_btn:
/**
 * In this Execution workRequest and workRequest_1 will run in parallel.
 * Then workRequest_2 and workRequest_3 will Execute after that.
 */
                WorkManager.getInstance(getApplicationContext()).beginWith(Arrays.asList((OneTimeWorkRequest)workRequest,(OneTimeWorkRequest)workRequest_1)).then((OneTimeWorkRequest) workRequest_2).then((OneTimeWorkRequest) workRequest_3).enqueue();
                break;
            case R.id.stop_work_btn:
              workManager.cancelAllWorkByTag("WorkManager_2"); // use Tag used for the corresponding WorkRequest.
                break;
        }
    }
}