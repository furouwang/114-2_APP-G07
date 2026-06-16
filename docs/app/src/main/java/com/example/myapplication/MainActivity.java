package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 綁定主畫面的五大核心按鈕
        Button btnGoMap = findViewById(R.id.btnGoMap);
        Button btnGoEncyclopedia = findViewById(R.id.btnGoEncyclopedia);
        Button btnGoRules = findViewById(R.id.btnGoRules);
        Button btnGoQuiz = findViewById(R.id.btnGoQuiz);
        Button btnGoSOS = findViewById(R.id.btnGoSOS);

        // 🎯 核心 1：點擊跳轉到全新設計的「台灣方位互動地圖」
        if (btnGoMap != null) {
            btnGoMap.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            });
        }

        // 🎯 核心 2：點擊跳轉到「海龜生態百科」頁面
        if (btnGoEncyclopedia != null) {
            btnGoEncyclopedia.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, EncyclopediaActivity.class);
                startActivity(intent);
            });
        }

        // 🎯 核心 3：點擊跳出「友善賞龜守則」對話框
        if (btnGoRules != null) {
            btnGoRules.setOnClickListener(v -> {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("⚖️ 友善賞龜不觸法守則")
                        .setMessage("【不觸摸、不騷擾 ❌】\n" +
                                "與海龜保持 5 公尺以上安全距離，嚴禁伸手觸摸、阻擋海龜去路或追逐，違者依野生動物保育法最高可罰 30 萬元！\n\n" +
                                "【不塗抹防曬乳下水 🧴】\n" +
                                "防曬乳中的化學成分（如羥苯甲酮）會導致珊瑚白化，破壞海龜的棲地與食物來源。請改穿長袖水母衣防曬。\n\n" +
                                "【不使用強光與閃光燈 📸】\n" +
                                "水下拍照時請關閉閃光燈，夜間在沙灘切勿拿手電筒直射海龜，強光會讓海龜驚嚇、迷失方向或放棄上岸產卵。")
                        .setPositiveButton("遵照守則", null)
                        .show();
            });
        }

        // 🎯 核心 4：點擊跳轉到帶有計分勳章系統的「趣味小檢定」頁面
        if (btnGoQuiz != null) {
            btnGoQuiz.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            });
        }

        // 🎯 核心 5：點擊跳轉到升級了離岸流自救的「海上救難與通報」頁面
        if (btnGoSOS != null) {
            btnGoSOS.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SeaSurvivalActivity.class);
                startActivity(intent);
            });
        }
    }
}