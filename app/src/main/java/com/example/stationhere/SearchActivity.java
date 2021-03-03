package com.example.stationhere;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class SearchActivity extends AppCompatActivity {

    private List<String> itemsKor = new ArrayList<String>();
    private List<String> itemsEng = new ArrayList<String>();
    private List<String> itemsLine = new ArrayList<String>();

    int colTotal;
    int rowTotal;
    String allResult;
    AutoCompleteTextView suggest;
    Button btn_toExplain;

    //recyclerView
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<SearchData> mDataset;

    /*private void getResult() {
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("tests.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    colTotal = sheet.getColumns();    // 전체 컬럼
                    rowTotal = sheet.getRows();
                    for (int j = 1; j < 729; j++) {
                        String name = sheet.getCell(1, j).getContents();
                        itemsKor.add(name);
                    }
                    for (int j = 1; j < 729; j++) {
                        String name = sheet.getCell(2, j).getContents();
                        itemsEng.add(name);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

    }

    private String getTogether() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itemsKor.size(); i++) {
            String item = itemsKor.get(i);
            sb.append(item);
            if (i != itemsKor.size() - 1) {
                sb.append("\n");
            }
        }
        for (int i = 0; i < itemsEng.size(); i++) {
            String item = itemsEng.get(i);
            sb.append(item);
            if (i != itemsEng.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }*/

    /*private String search(String query) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < itemsKor.size(); i++) {
            String item = itemsKor.get(i);
            if (item.contains(query)) {
                sb.append(item);
            }
            if (i != itemsKor.size() - 1) {
                sb.append("\n");
            }
        }
        for (int i = 0; i < itemsEng.size(); i++) {
            String item = itemsEng.get(i);
            if (item.contains(query)) {
                sb.append(item);
            }
            if (i != itemsEng.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }*/

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button search_bar = findViewById(R.id.search_bar);
        suggest = findViewById(R.id.search_view);
        final TextView tv_result = findViewById(R.id.textView);
        final SearchView searchView = findViewById(R.id.searchView);
        //getResult();
        //String stationList = getTogether();

        //tv_result.setText(stationList);

        btn_toExplain = findViewById(R.id.btn_toExplain);

        //suggest부분
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
        //suggest.setAdapter(adapter);


        btn_toExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (suggest.getText().toString().length() == 0) {
                    Toast toast = Toast.makeText(SearchActivity.this, "역명을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(SearchActivity.this, ExplainActivity.class);
                    intent.putExtra("stationName", suggest.getText().toString());
                    startActivity(intent);
                    finish();
                }

            }
        });

        searchView.setQueryHint("Search Station...");
        searchView.getBaseline();
        searchView.setBackgroundColor(R.color.realWhite);
        searchView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                searchView.setIconified(false);

            }
        });



        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 입력받은 문자열 처리

                if (searchView.getQuery().length() == 0) {
                    Toast toast = Toast.makeText(SearchActivity.this, "역명을 입력하세요.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(SearchActivity.this, ExplainActivity.class);
                    intent.putExtra("stationName", suggest.getText().toString());
                    startActivity(intent);
                    finish();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 입력란의 문자열이 바뀔 때 처리
                tv_result.setText(search(newText));
                return false;

            }
        });
        */
        //here, import search info and intent to explainActivity

        //recyclerView

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getData();


    }
    //recyclerView에 Data 넣어주는 부분
    public void getData() {
        try {
            AssetManager am = getAssets();
            InputStream is = am.open("tests.xls");
            Workbook wb = Workbook.getWorkbook(is);

            if (wb != null) {
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if (sheet != null) {
                    colTotal = sheet.getColumns();    // 전체 컬럼
                    rowTotal = sheet.getRows();
                    for (int j = 1; j < 729; j++) {
                        String name = sheet.getCell(1, j).getContents();
                        itemsKor.add(name);
                    }
                    for (int j = 1; j < 729; j++) {
                        String name = sheet.getCell(2, j).getContents();
                        itemsEng.add(name);
                    }
                    for (int j = 1; j < 729; j++) {
                        String name = sheet.getCell(4, j).getContents();
                        itemsLine.add(name);
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        List<SearchData> searchData = new ArrayList<>();

        for (int i = 401; i < 408; i++) {
            SearchData sd = new SearchData();
            Log.d("asdf: ", itemsLine.get(i));
            sd.setLine(itemsLine.get(i));
            sd.setEngName(itemsEng.get(i));
            sd.setKorName(itemsKor.get(i));
            searchData.add(sd);
        }

        mAdapter = new MyAdapter(searchData);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void onBackPressed() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
