//.  Set up a activity with:
//        a) A edittext for user input
//        b) A textview to report results of the Java Thread
//        c) A textview to report results of the AsyncTask
//        b) A textview to report results of the looper
//        e) A button to start the Java Thread
//        f)  A button to start the asyncTask
//        g) A button to start the looper
//
//        2. Set up a java thread to get the count of how big the UserInput String is
//        3. Setup a async task that will reverse the string (Do not use String.reverse)
//        4. Setup a looper that will send back a message that will report any duplicate chars in the String
//        5. Link the UI elements to the appropriate multithreading method





        package com.example.week4day1homework;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    TextView tvJavaResults;
    TextView tvAsyncResults;
    TextView tvLooperResults;
    EditText etUserInput;
    Button btnJavaExecute;
    Button btnAsyncThreadExecute;
    Button btnLooperExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvJavaResults = findViewById(R.id.tvJavaThreadResults);
        etUserInput = findViewById(R.id.etUserInput);
        tvAsyncResults = findViewById(R.id.tvAsyncThreadResults);
        tvLooperResults = findViewById(R.id.tvLooperResult);
        btnJavaExecute = findViewById(R.id.btnRunJavaThread);
        btnAsyncThreadExecute = findViewById(R.id.btnRunAsyncThread);
        btnLooperExecute = findViewById(R.id.btnRunLooper);

    }

    private Runnable runnableForThread() {

        return new Runnable() {
            @Override
            public void run() {

                String input = etUserInput.getText().toString();
                tvJavaResults.setText("" + input.length());
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnRunJavaThread:

                startThread();

                break;

            case R.id.btnRunAsyncThread:

                AsyncThread asyncThread;
                asyncThread = new AsyncThread();
                asyncThread.execute();

                break;

            case R.id.btnRunLooper:

                LooperThreading looperThreading;
                looperThreading = new LooperThreading(new Handler(Looper.getMainLooper()){

                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Bundle bundle = msg.getData();
                        tvLooperResults.setText(bundle.getString("key"));
                    }
                });
                looperThreading.start();
                looperThreading.workerThreadHandler.sendMessage(new Message());


                break;


        }
    }

    public String passStringToThread() {

        return etUserInput.getText().toString();

    }

    private void startThread() {
        Thread javaThread = new Thread(runnableForThread());
        javaThread.start();


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAsyncMessageReceived(AsyncTaskEvent asyncTaskEvent) {
        tvAsyncResults.setText(asyncTaskEvent.getMessage());
    }
}
