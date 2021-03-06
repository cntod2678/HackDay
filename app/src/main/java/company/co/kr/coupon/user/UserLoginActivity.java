package company.co.kr.coupon.user;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import company.co.kr.coupon.AdminActivity;
import company.co.kr.coupon.Application;
import company.co.kr.coupon.MainActivity;
import company.co.kr.coupon.R;
import company.co.kr.coupon.network.AsyncHttpTask;
import company.co.kr.coupon.network.JSONParser;

public class UserLoginActivity extends AppCompatActivity {

    private static final String USER_URL= Application.URL + "/user/user_check/";

    Button btnUser;
    EditText editTextId;

    JSONObject user_result;
    String uid;
    String user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        initView();
        setEvent();
    }

    private void initView() {
        btnUser = (Button) findViewById(R.id.btnLogin);
        editTextId = (EditText) findViewById(R.id.editTextId);
    }

    private void setEvent() {
        btnUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    uid = editTextId.getText().toString();

                    user_result = new UserIdCheck().execute(uid).get();

                    user_type = user_result.getString("user_type");

                    if(user_type.equals("client")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("uid", uid);
                        startActivity(intent);
                        finish();
                    } else if(user_type.equals("owner")) {
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(user_result.getString("error").equals("error"))
                    {
                        Toast.makeText(getApplicationContext(), "등록된 번호가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        throw new RuntimeException("로그인 연결 안됨");

                } catch(Exception e) {
                    Log.e("login", "버튼 로그인 에러");
                    Toast.makeText(getApplicationContext(), "연결을 확인하세요", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }


    // uid를 이용해서 couponList를 가져옴
//    private class UserIdCheck extends AsyncTask<String, String, JSONObject> {
//
//        JSONParser jsonParser = new JSONParser();
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected JSONObject doInBackground(String... args) {
//
//            try {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("uid", args[0]);
//
//                JSONObject chkLogin = jsonParser.makeHttpRequest(
//                        USER_URL, "GET", params);
//
//                if (chkLogin != null) {
//                    Log.d("login", "result : " + chkLogin.toString());
//                    return chkLogin;
//                } else {
//                    Log.d("login", "result : null, doInBackground");
//                }
//
//            } catch (Exception e) {
//                Log.d("login", "로그인 연결 에러");
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jObj) {
//            super.onPostExecute(jObj);
//        }
//    }


    public void checkUser(String uid) {
        JSONParser jsonParser = new JSONParser();

        HashMap<String, String> params = new HashMap<>();

        JSONObject jObj = jsonParser.makeHttpRequest(USER_URL, "GET", params);
        params.put("uid", uid);

        new AsyncHttpTask(handler).execute(jObj.toString());
    }


    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            switch(msg.what) {
                case -1:
                    // 에러 처리

                    break;
                case 0:
                    // 정상 응답 처리

                    break;

            }

        }

    };

}
