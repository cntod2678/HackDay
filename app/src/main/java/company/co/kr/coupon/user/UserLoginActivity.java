package company.co.kr.coupon.user;

/**
 * Created by Dongjin on 2017. 5. 25..
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import company.co.kr.coupon.Application;
import company.co.kr.coupon.MainActivity;
import company.co.kr.coupon.R;
import company.co.kr.coupon.network.JSONParser;

public class UserLoginActivity extends AppCompatActivity {

    private static final String USER_URL= Application.URL + "/user/point_check/";

    Button btnUser;
    EditText editTextId, editTextPassword;

    JSONObject user_chk;
    String uid;

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
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
    }

    private void setEvent() {
        btnUser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    uid = editTextId.getText().toString();

                    user_chk = new UserIdCheck().execute(uid).get();

//                    if(user_chk.toString().equals("1")) {
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        intent.putExtra("uid", uid);
//                        startActivity(intent);
//                    }
//                    else  {
//                        Toast.makeText(getApplicationContext(), "등록된 번호가 없습니다.", Toast.LENGTH_SHORT);
//                    }

                    //--------- 바로 연결 ------------
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                    //finish();

                } catch(Exception e) {
                    Log.e("login", "버튼 로그인 에러");
                    e.printStackTrace();
                }
            }
        });
    }


    // uid를 이용해서 couponList를 가져옴
    private class UserIdCheck extends AsyncTask<String, String, JSONObject> {

        JSONParser jsonParser = new JSONParser();


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("uid", args[0]);

                JSONObject chkLogin = jsonParser.makeHttpRequest(
                        USER_URL, "GET", params);

                if (chkLogin != null) {
                    Log.d("login", "result : " + chkLogin.toString());
                    return chkLogin;
                } else {
                    Log.d("login", "result : null, doInBackground");
                }

            } catch (Exception e) {
                Log.d("login", "로그인 연결 에러");
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jObj) {
            super.onPostExecute(jObj);
        }
    }

}
