package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SeaSurvivalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sea_survival);

        // 綁定救難頁面的按鈕元件
        Button btnSimulateSOS = findViewById(R.id.btnSimulateSOS);
        Button btnSurvivalTips = findViewById(R.id.btnSurvivalTips);
        Button btnCall118 = findViewById(R.id.btnCall118);
        Button btnSurvivalBack = findViewById(R.id.btnSurvivalBack);

        // 1. 模擬 SOS 燈號（跳出模擬閃爍視窗）
        if (btnSimulateSOS != null) {
            btnSimulateSOS.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("🚨 SOS 模式啟動")
                        .setMessage("手機螢幕將模擬高亮度閃爍，並發出求救訊號以吸引搜救船隻注意。\n\n(此為模擬功能：在真實求生中，請將螢幕對向搜救者，或利用反光物品反射陽光)")
                        .setPositiveButton("確認", null)
                        .show();
            });
        }

        // 2. 🔥 大升級：求生指南大禮包（加入離岸流自救核心知識）
        if (btnSurvivalTips != null) {
            btnSurvivalTips.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("🎒 海上調查求生與離岸流自救指南")
                        .setMessage("🌊【核心痛點：遭遇強烈離岸流自救】\n" +
                                "保育員或潛水員在岸際調查海龜時，若不幸被離岸流強行捲向外海，請記住「切勿逆流硬拼」三步驟：\n" +
                                "1. 🛑 保持冷靜與漂浮：離岸流只會把你帶往外海，並不會把你吸入海底。請放鬆身體順流漂浮，保留體力。\n" +
                                "2. 📐 向兩側平行游出：離岸流的寬度通常不超過30公尺。請「不要」朝岸邊游，而是向左或向右「平行於海岸線」游動，即可快速脫離強流區。\n" +
                                "3. 岸邊救援：若流速過強無法脫離，請隨流漂出。等流勢在外海減弱後，再繞道游回岸邊，或吹哨子、舉手呼救。\n\n" +
                                "----------------------------------------\n\n" +
                                "💧【基本生存：淡水收集】\n" +
                                "嚴禁飲用海水！應利用雨衣或塑膠布收集雨水。若無雨水，可於清晨觀察植物或浮具上的露水進行收集。\n\n" +
                                "🧥【防範要點：防止失溫】\n" +
                                "即便在熱帶海域，長期浸泡水中也會導致體溫迅速流失。請盡量讓身體高於水面（如爬上礁石或浮具），夥伴之間緊靠以增加體溫。")
                        .setPositiveButton("我知道了，安全第一", null)
                        .show();
            });
        }

        // 3. 一鍵撥號模擬
        if (btnCall118 != null) {
            btnCall118.setOnClickListener(v -> {
                new AlertDialog.Builder(this)
                        .setTitle("📞 海巡署 118 通報系統")
                        .setMessage("您正在進行緊急通報：\n\n1. 調查人員海上遇險/受困離岸流\n2. 受傷海龜救援與擱淺通報\n3. 違法騷擾保育類動物檢舉\n\n確定要模擬撥打 118 專線嗎？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("立即撥號", (dialog, which) -> {
                            Toast.makeText(this, "📞 正在啟動撥號系統，呼叫海巡署 118...", Toast.LENGTH_LONG).show();
                        })
                        .show();
            });
        }

        // 返回主選單
        if (btnSurvivalBack != null) {
            btnSurvivalBack.setOnClickListener(v -> finish());
        }
    }
}