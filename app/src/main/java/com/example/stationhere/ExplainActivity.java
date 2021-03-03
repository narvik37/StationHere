package com.example.stationhere;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExplainActivity extends AppCompatActivity {



    TextView input;
    TextView output;
    TextView tv_engName;
    TextView tv_korName;
    TextView tv_newEngName;
    //TextView tv_test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);

        tv_newEngName = (TextView)findViewById(R.id.tv_newEngName);
        tv_engName = (TextView)findViewById(R.id.tv_engName);
        tv_korName = (TextView)findViewById(R.id.tv_korName);
        input = (TextView) findViewById(R.id.tv_input); // 한국어 역명 풀이

        Intent intent = getIntent();
        String getStationName = intent.getExtras().getString("stationName");
        Log.i("String check", getStationName);

        try {
            AssetManager am = getAssets();
            InputStream is = am.open("tests.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if(wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    //int rowIndexStart = 0;                  // row 인덱스 시작
                    int rowTotal = sheet.getRows();

                    String engName;
                    String korName;


                    //input.setText(getStationName);

                    for(int i=1; i<729; i++){
                        engName = sheet.getCell(2, i).getContents();
                        korName = sheet.getCell(1, i).getContents();
                        if(getStationName.equals(engName) || getStationName.equals(korName)){
                            String newEngName = sheet.getCell(3, i).getContents();
                            tv_newEngName.setText(newEngName);
                            tv_engName.setText(engName);
                            tv_korName.setText(korName);
                            String explain = sheet.getCell(6, i).getContents();
                            if(explain.length() == 0){
                                input.setText("< 알림 >\n\n" +
                                        " 본 설명은 서울역사편찬원을 참조하여 기술되었습니다.\n\n" +
                                        " 만약 설명이 없다면, 서울지명사전의 설명 목록에 기술되어있지 않은 것임을 알려드립니다.");
                                Toast toast = Toast.makeText(this, "No Explanation", Toast.LENGTH_SHORT);
                                toast.show();
                            }else{
                                input.setText(explain);
                            }

                            break;
                        }
                    }

                    //Log.i("test", sb.toString());
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }


        output = (TextView) findViewById(R.id.tv_output); // 영어 역명 풀이(버튼 누르면 영어 해석)
        Button btn_trans = findViewById(R.id.btn_translate);
        //TranslateTask translateTask = new TranslateTask();
        //translateTask.execute();

        btn_trans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(input.getText().toString().length() == 0){
                    Toast.makeText(ExplainActivity.this, "내용 입력", Toast.LENGTH_SHORT).show();
                    input.requestFocus();
                    return;
                }

                TranslateTask runTask = new TranslateTask();
                String inputText = input.getText().toString();
                runTask.execute(inputText);
            }
        });
    }



    class TranslateTask extends AsyncTask<String, Void, String> {


        String clientId = "";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "";//애플리케이션 클라이언트 시크릿값";


        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String transText = strings[0];

            try {
                //String text = URLEncoder.encode("안녕 세상아!", "UTF-8");
                String text = URLEncoder.encode(transText, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/papago/n2mt";

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                // post request
                String postParams = "source=ko&target=en&text=" + text;
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());

                wr.writeBytes(postParams);
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) { // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {  // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                //System.out.println(response.toString());
                return response.toString();

            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("background result", s.toString());

            //결과를 받아서 tv_output에 집어넣는다.


            Gson gson = new GsonBuilder().create();
            JsonParser parser = new JsonParser();
            JsonElement rootObject = parser.parse(s.toString()).getAsJsonObject()
                    .getAsJsonObject().get("message")
                    .getAsJsonObject().get("result");
            TranslatedItem items = gson.fromJson(rootObject.toString(), TranslatedItem.class);
            output.setText(items.getTranslatedText());


        }

        private class TranslatedItem{//해셕된 말 담기용 자바 클래스
            String translatedText;
            public String getTranslatedText(){
                return translatedText;
            }
        }


    }

    public void onBackPressed() {
        Intent intent = new Intent(ExplainActivity.this, SearchActivity.class);
        startActivity(intent);
        finish();
    }
}