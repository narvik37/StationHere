package com.example.stationhere;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class MainActivity extends AppCompatActivity {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private String toKorStation(int randNum) {
        String result = "";
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("tests.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    result = sheet.getCell(1, randNum).getContents();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return result;

    }
    private String toEngStation(int randNum) {
        String result = "";
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("tests.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    result = sheet.getCell(2, randNum).getContents();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return result;

    }
    private String toNewStation(int randNum) {
        String result = "";
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("tests.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    result = sheet.getCell(3, randNum).getContents();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return result;

    }


    Button btn_toSearch;
    Button btn_toLine1;
    Button btn_toLine2;
    Button btn_toLine3;
    Button btn_toLine4;
    Button btn_toLine5;
    Button btn_toLine6;
    Button btn_toLine7;
    Button btn_toLine8;
    Button btn_toLine9;
    ImageView lineImage;
    Button touchToView;
    TextView korStation, engStation, todayStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_toSearch = findViewById(R.id.btn_toSearch);
        touchToView = findViewById(R.id.touchToView);
        korStation = findViewById(R.id.quizKor);
        engStation = findViewById(R.id.quizEng);
        todayStation = findViewById(R.id.todayStation);

        btn_toLine1 = findViewById(R.id.btn_line1);
        btn_toLine2 = findViewById(R.id.btn_line2);
        btn_toLine3 = findViewById(R.id.btn_line3);
        btn_toLine4 = findViewById(R.id.btn_line4);
        btn_toLine5 = findViewById(R.id.btn_line5);
        btn_toLine6 = findViewById(R.id.btn_line6);
        btn_toLine7 = findViewById(R.id.btn_line7);
        btn_toLine8 = findViewById(R.id.btn_line8);
        btn_toLine9 = findViewById(R.id.btn_line9);

        btn_toSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_toLine7.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LineActivity.class);
                intent.putExtra("line", 7);
                startActivity(intent);
                finish();
            }
        });

        int rand = 0;
        Random random = new Random();
        rand = random.nextInt(37)+400;

        todayStation.setText(toNewStation(rand));
        korStation.setText(toKorStation(rand));
        engStation.setText(toEngStation(rand));

        touchToView.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                korStation.setTextColor(Color.parseColor("#000000"));
                engStation.setTextColor(Color.parseColor("#000000"));
                touchToView.setVisibility(View.INVISIBLE);
            }
        });
        }

    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }
}

