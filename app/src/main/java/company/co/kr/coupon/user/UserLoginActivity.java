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

import org.json.JSONObject;

import java.util.HashMap;

import company.co.kr.coupon.MainActivity;
import company.co.kr.coupon.R;
import company.co.kr.coupon.network.JSONParser;

public class UserLoginActivity extends AppCompatActivity {

    private static final String USER_URL="";

    Button btnUser;
    EditText editTextId, editTextPassword;

    JSONObject user_chk;
    String user_id, user_password;

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
                    user_id = editTextId.getText().toString();
                    user_password = editTextPassword.getText().toString();

                    user_chk = new UserIdCheck().execute(user_id, user_password).get();

                    if(user_chk.toString().equals("1")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                } catch(Exception e) {
                    Log.e("login", "로그인 에러");
                    e.printStackTrace();
                }
            }
        });
    }


    private class UserIdCheck extends AsyncTask<String, String, JSONObject> {
        // uid를 이용해서 couponList를 가져옴

        JSONParser jsonParser = new JSONParser();

        private ProgressDialog pDialog = new ProgressDialog(getApplicationContext());

        protected void onPreExecute() {
            pDialog.setMessage("잠시만 기다려 주세요.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected JSONObject doInBackground(String... args) {

            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", args[0]);
                params.put("user_password", args[1]);

                JSONObject chkLogin = jsonParser.makeHttpRequest(
                        USER_URL, "GET", params);

                if (chkLogin != null) {
                    Log.d("login", "result : " + chkLogin);
                    return chkLogin;
                } else {
                    Log.d("login", "result : null, doInBackground");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(JSONObject jObj) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            super.onPostExecute(jObj);
        }
    }

}
