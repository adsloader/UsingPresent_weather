package com.test.snake.usingpresent_weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShowSimpleInfo();
    }

    // 예제에서 메소드만 그대로 긁어왔음.
    private void ShowSimpleInfo() {
        // 1. 날씨 웹페이지에 들려 정보를 긁어온다.
        MyHttpRequest http = new MyHttpRequest(getApplicationContext());
        // eb8e2c1eaec9fdbb42ca9b3cd43b373f
        String sUrl = "http://api.openweathermap.org/data/2.5/weather?q=seoul&units=metric&appid=eb8e2c1eaec9fdbb42ca9b3cd43b373f";

        try {
            int nResultCode = http.execute(sUrl, "GET").get();
            if(nResultCode == MyHttpRequest.REQUEST_FAIL) return;

            // 2. 가져온 문자열(JSON)을 처리한다.
            String sJson = http.getString();
            JSONObject json = new JSONObject(sJson);

            JSONArray json_weather  = json.getJSONArray("weather");
            JSONObject json_item     = json_weather.getJSONObject(0);

            String sToday  = json_item.getString("description");
            String sStatus = json_item.getString("main");

            // 최소, 최대 온도구한다.
            JSONObject  json_main  = json.getJSONObject("main");

            String sMin = json_main.getString("temp_min");
            String sMax   = json_main.getString("temp_max");

            Toast.makeText(getApplicationContext(), sToday + "\n" + "최소:" + sMin + " 최대:" + sMax, Toast.LENGTH_LONG).show();
            //txtWeather.setText(sToday);

            // 3. 날씨와 비슷한 이미지를 설정한다.
            Toast.makeText(getApplicationContext(), sStatus, Toast.LENGTH_LONG).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
