package com.example.week4day1homework;

import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

public class AsyncThread extends AsyncTask <String, String, String> {

MainActivity mainActivity;
String output;
    private String getString() {

        return mainActivity.passStringToThread();


    }

    @Override
    protected String doInBackground(String... strings) {

        String input = getString();
        char[] stringToCharArray = input.toCharArray();
        char[] backwardsString = new char[0];
        StringBuilder charArraytoBackwards = new StringBuilder();
        int i;
        int j;
        for (i = stringToCharArray.length; i >= 0; i--) {
            for (j = 0; j <= stringToCharArray.length; j++) {

                backwardsString[j] = stringToCharArray[i];
                charArraytoBackwards.append(backwardsString[j]);
            }


        }
        return charArraytoBackwards.toString();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        EventBus.getDefault().post(new AsyncTaskEvent(s));
    }


}
