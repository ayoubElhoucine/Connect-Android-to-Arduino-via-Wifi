package com.tofaha.Android_wifi.connection;

import android.os.AsyncTask;

import com.tofaha.Android_wifi.app.MyData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by ayoub on 11/26/17.
 */

public class SendMessages extends AsyncTask<Void , String , Void> {


    String msg = "" ;

    public SendMessages(String msg) {
        this.msg = msg;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(MyData.INSTANCE.getSocket()
                    .getOutputStream())) , true);

            out.println(msg);
            System.out.println("message send");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
