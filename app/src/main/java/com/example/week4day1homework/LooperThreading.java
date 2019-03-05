package com.example.week4day1homework;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class LooperThreading extends Thread {


    Handler workerThreadHandler;
    Handler mainThreadHandler;
    MainActivity mainActivity;

    public LooperThreading(Handler handler) {


        mainThreadHandler = handler;

        workerThreadHandler = new Handler(Looper.myLooper()) {

            public void handleMessage(Message msg) {
                Message message = new Message();
                message.what = msg.what;
                String inputString = mainActivity.passStringToThread();
                int i;
                int j;
                char[] letter = inputString.toCharArray();
                char[] word = new char[0];
                String response;
                StringBuilder query = new StringBuilder();

                for (i = 0; i <= inputString.length(); i++) {
                    for (j = 0; j <= inputString.length(); j++) {

                        if (letter[j] == word[i]) {
                            query.append(letter[j]);
                        }
                    }
                }
                response =  "THE FOLLOWING LETTERS ARE DUPLICATES:" + query.toString();


                Bundle bundle = new Bundle();
                bundle.putString("key", response );
                message.setData(bundle);
                mainThreadHandler.sendMessage(message);


            }


        };
    }





    @Override
    public void run() {
        super.run();
        Looper.prepare();
        Looper.loop();
    }

}
