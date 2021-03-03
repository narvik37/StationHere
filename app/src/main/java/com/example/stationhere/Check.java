package com.example.stationhere;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Check extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        TextView x = (TextView) findViewById(R.id.tv_test2);

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

                    StringBuilder sb;
                    for(int row=0; row<2; row++) {
                        sb = new StringBuilder();
                        for(int col=0; col<2; col++) {
                            String contents = sheet.getCell(col, row).getContents();
                            sb.append("col"+col+" : "+contents+" , ");
                            x.setText(contents);
                        }

                        Log.i("test", sb.toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }



    /*public void order (View v){
        try {
            InputStream is = getBaseContext().getResources().getAssets().open("test.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Sheet sheet = wb.getSheet(0);
            int row = sheet.getRows();
            int col = sheet.getColumns();
            String xx = "";
            for (int i = 0; i < row; i++) {
                for (int c = 0; c < col; c++) {
                    Cell z = sheet.getCell(c, i);
                    xx = xx + z.getContents();
                }
                xx = xx + "\n";
            }
            display(xx);
        } catch (Exception e) {

        }
    }

    public void display (String value){
        TextView x = (TextView) findViewById(R.id.tv_test2);
        x.setText(value);
    }*/
}






