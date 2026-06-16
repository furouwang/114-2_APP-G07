package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.SpeciesAdapter;
import com.example.myapplication.data.TurtleSpeciesProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

public class EncyclopediaActivity extends AppCompatActivity {

    private static final String TAG = "GBIF_RANDOM_TURTLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);

        // 綁定 UI 元件
        Button btnSubSpecies = findViewById(R.id.btnSubSpecies);
        Button btnSubKnowledge = findViewById(R.id.btnSubKnowledge);
        Button btnSubRescue = findViewById(R.id.btnSubRescue);
        Button btnEncyBack = findViewById(R.id.btnEncyBack);

        btnSubRescue.setText("🏛️ GBIF 全球綠蠵龜真實 API 串接");

        // 1. 五大海龜圖鑑
        btnSubSpecies.setOnClickListener(v -> showSpeciesEncyclopediaDialog());

        // 2. 海龜基本知識
        btnSubKnowledge.setOnClickListener(v -> {
            new AlertDialog.Builder(EncyclopediaActivity.this)
                    .setTitle("💡 海龜基本知識與保育")
                    .setMessage("【性別由溫決定 🌡️】\n" +
                            "海龜的性別是由沙灘孵化時的「溫度」決定的！當沙灘溫度高於 29°C 時，孵化出來的幾乎全是母海龜。\n\n" +
                            "【垃圾致命誘惑 🦟】\n" +
                            "海龜最愛吃水母，但漂流在海中的「塑膠袋」看起來跟水母一模一樣！減少塑膠袋，就是最好的保育！")
                    .setPositiveButton("關閉常識", null)
                    .show();
        });

        // 3. 🏛️ 點擊連線：加入 ScrollView 滾動元件，讓 10 筆真實綠蠵龜資料可以順暢往下拉
        btnSubRescue.setOnClickListener(v -> {
            // 🟢 創建ScrollView（這就是你需要的向下拉的滾動條容器）
            ScrollView scrollView = new ScrollView(EncyclopediaActivity.this);

            TextView messageTextView = new TextView(EncyclopediaActivity.this);
            messageTextView.setPadding(60, 40, 60, 40);
            messageTextView.setTextSize(14);
            messageTextView.setLineSpacing(0, 1.3f);
            messageTextView.setText("🔄 正在隨機跳轉全球觀測庫分頁，跨海檢索最新的綠蠵龜（Chelonia mydas）真實動態數據流...");

            // 🟢 把 TextView 塞進 ScrollView 裡包起來
            scrollView.addView(messageTextView);

            AlertDialog dialog = new AlertDialog.Builder(EncyclopediaActivity.this)
                    .setTitle("🏛️ GBIF 全球綠蠵龜即時觀測大數據")
                    .setView(scrollView) // 🟢 注意：這裡要改成塞入 scrollView，而不是原本的 messageTextView！
                    .setPositiveButton("關閉查詢", null)
                    .create();
            dialog.show();

            new Thread(() -> {
                try {
                    // 隨機生成 offset 偏移量（綠蠵龜在全球有數萬筆觀測，隨機抽樣前 300 筆的分頁）
                    int randomOffset = new Random().nextInt(300);

                    // 限制回傳 10 筆真實科學紀錄
                    String encodedName = URLEncoder.encode("Chelonia mydas", "UTF-8");
                    String urlString = "https://api.gbif.org/v1/occurrence/search"
                            + "?scientificName=" + encodedName
                            + "&hasCoordinate=true"
                            + "&limit=10"
                            + "&offset=" + randomOffset;

                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                    conn.setRequestProperty("Accept", "application/json");
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);

                    int responseCode = conn.getResponseCode();
                    Log.d(TAG, "GBIF 伺服器回應狀態碼: " + responseCode);

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                        reader.close();

                        // 解析 JSON
                        JSONObject jsonResponse = new JSONObject(result.toString());
                        JSONArray resultsArray = jsonResponse.getJSONArray("results");

                        StringBuilder formattedContent = new StringBuilder();
                        formattedContent.append("🎉【GBIF 國際生物大數據網路通道連線成功！】\n");
                        formattedContent.append("📊 數據源：真實全球綠蠵龜野生觀測學術名錄\n");
                        formattedContent.append("🎲 本次隨機分頁抽樣編號：第 ").append(randomOffset).append(" 頁\n");
                        formattedContent.append("====================================\n\n");

                        if (resultsArray.length() == 0) {
                            formattedContent.append("⚠️ 提示：此隨機區段目前無紀錄，請關閉並重新點擊按鈕刷新！");
                        } else {
                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject occurrence = resultsArray.getJSONObject(i);

                                String species = occurrence.optString("species", "綠蠵龜 (Green Sea Turtle)");
                                String scientificName = occurrence.optString("scientificName", "Chelonia mydas");
                                String date = occurrence.optString("eventDate", "官方未紀錄日期");
                                String country = occurrence.optString("country", "未定海域");
                                String dataset = occurrence.optString("datasetName", "自主生態調查");

                                double decimalLatitude = occurrence.optDouble("decimalLatitude", 0.0);
                                double decimalLongitude = occurrence.optDouble("decimalLongitude", 0.0);

                                formattedContent.append("【真實全球觀測紀錄 ").append(i + 1).append("】\n")
                                        .append("🐢 觀測物種：").append(species).append("\n")
                                        .append("🧬 科學學名：").append(scientificName).append("\n")
                                        .append("📅 觀測時間：").append(date).append("\n")
                                        .append("📍 觀測國家/地區：").append(country).append("\n")
                                        .append("🌐 衛星定位：北緯 ").append(decimalLatitude).append("° / 東經 ").append(decimalLongitude).append("°\n")
                                        .append("📊 數據提供：").append(dataset).append("\n")
                                        .append("------------------------------------\n\n");
                            }
                        }

                        runOnUiThread(() -> messageTextView.setText(formattedContent.toString()));

                    } else {
                        runOnUiThread(() -> messageTextView.setText("❌ 國際伺服器連線失敗，狀態碼：" + responseCode));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> messageTextView.setText("❌ 聯網或真實 JSON 解析拋出異常：\n\n" + e.toString()));
                }
            }).start();
        });

        // 4. 返回主選單
        btnEncyBack.setOnClickListener(v -> finish());
    }

    private void showSpeciesEncyclopediaDialog() {
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setPadding(40, 30, 40, 30);
        recyclerView.setClipToPadding(false);

        SpeciesAdapter adapter = new SpeciesAdapter(TurtleSpeciesProvider.getSpeciesList());
        recyclerView.setAdapter(adapter);

        new AlertDialog.Builder(this)
                .setTitle("📖 台灣常見海龜品種圖鑑")
                .setView(recyclerView)
                .setPositiveButton("關閉圖鑑", null)
                .show();
    }
}