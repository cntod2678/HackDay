package company.co.kr.coupon.network;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Dongjin on 2017. 5. 26..
 */

public class AsyncHttpTask extends AsyncTask<String, Void, JSONObject> {
    private Handler handler;
    private Exception exception;

    public AsyncHttpTask(Handler handler){
        this.handler = handler;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... args) {
        JSONParser jsonParser =new JSONParser();

        try {
            JSONObject =


            return responseData;
        } catch(Exception e) {
            this.exception = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject responseData) {
        if(exception != null) {
            Message msg = handler.obtainMessage();
            msg.what = -1;
            msg.obj = exception;
            handler.sendMessage(msg);
            return;
        }

        else {
            Message msg = handler.obtainMessage();
            msg.what = 0;
            msg.obj = responseData;
            handler.sendMessage(msg);
        }
    }
}
