package com.example.stationhere;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.stationhere.R.drawable.line7;


public class LineActivity extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;

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
    ImageView line_image;
    TextView tv;


    public boolean onTouchEvent(MotionEvent motionEvent) {
        //변수로 선언해 놓은 ScaleGestureDetector
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        line_image=(ImageView)findViewById(R.id.line_image);

        // 스케일제스쳐 디텍터 인스턴스
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Intent intent = getIntent();
        int lineNum = intent.getExtras().getInt("line");

        if (lineNum == 7) {
            line_image.setImageResource(line7);

        }
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            // ScaleGestureDetector에서 factor를 받아 변수로 선언한 factor에 넣고
            mScaleFactor *= scaleGestureDetector.getScaleFactor();

            // 최대 10배, 최소 10배 줌 한계 설정
            mScaleFactor = Math.max(0.1f,
                    Math.min(mScaleFactor, 10.0f));

            // 이미지뷰 스케일에 적용
            line_image.setScaleX(mScaleFactor);
            line_image.setScaleY(mScaleFactor);
            return true;
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(LineActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}



