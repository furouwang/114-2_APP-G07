package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvHighScore, tvQuestion;
    private Button btnOption1, btnOption2, btnQuizBack;

    // 🔑 SharedPreferences 核心變數
    private SharedPreferences sharedPreferences;
    private int highScore = 0;

    // 🎮 遊戲進度控制變數
    private int currentScore = 0;      // 每次執行初始得分為 0，隨著回答提高
    private int currentQuestionIndex = 0; // 目前回答到第幾題
    private List<Integer> quizOrderList = new ArrayList<>(); // 用於隨機打亂題目的順序清單

    // 🎯 題庫結構定義
    private static class Question {
        String questionText;
        String optionA;
        String optionB;
        char correctAnswer; // 'A' 或 'B'
        String explanation;

        Question(String q, String a, String b, char ans, String exp) {
            this.questionText = q;
            this.optionA = a;
            this.optionB = b;
            this.correctAnswer = ans;
            this.explanation = exp;
        }
    }

    // 📚 固定 10 題精選保育題庫
    private final Question[] questionBank = new Question[]{
            new Question("觸摸野生海龜，依我國法律最高可罰款多少元？", "(A) 罰款 6 萬元", "(B) 罰款 30 萬元", 'B', "依據野生動物保育法，觸摸騷擾海龜最高可處 30 萬元罰鍰！"),
            new Question("台灣周邊海域最常見、野生密度最高的海龜品種是哪一種？", "(A) 綠蠵龜", "(B) 赤蠵龜", 'A', "小琉球等離島最常見的就是綠蠵龜（Green Sea Turtle）。"),
            new Question("下水看海龜時，為了保護珊瑚與海龜，應該如何防曬？", "(A) 塗抹大量防水防曬乳", "(B) 穿著長袖水母衣防曬", 'B', "防曬乳中的化學成分會導致珊瑚白化與毒害海洋生態，應採物理防曬！"),
            new Question("在海中觀察野生海龜時，應該保持多長的安全距離？", "(A) 至少 5 公尺以上", "(B) 1 公尺以內方便拍照", 'A', "請與海龜保持 5 公尺以上距離，嚴禁阻擋去路、伸手觸摸或追逐。"),
            new Question("夜間在沙灘如果遇到海龜上岸產卵，以下哪種行為是正確的？", "(A) 關閉所有手電筒與閃光燈", "(B) 打開強光幫海龜照明引路", 'A', "強光會讓海龜媽媽驚嚇、迷失方向，甚至放棄產卵逃回海中。"),
            new Question("海洋中的什麼廢棄物常被海龜誤認為是美味的水母而吞食？", "(A) 廢棄漁網", "(B) 塑膠袋", 'B', "漂流的塑膠袋在水中極像水母，海龜誤食後會造成腸胃阻塞死亡。"),
            new Question("世界上現存體型最大、殼像皮革一樣、沒有硬殼的海龜是？", "(A) 欖蠵龜", "(B) 革龜", 'B', "革龜（Leatherback）是體型最大的海龜，成熟體重可達 300-500 公斤。"),
            new Question("海龜是用什麼器官來進行呼吸的？", "(A) 用肺呼吸，需定期浮出水面", "(B) 用鰓呼吸，可永遠待在水下", 'A', "海龜是爬蟲類，必須定期浮出水面用「肺」吸氣，憋氣太久會溺水。"),
            new Question("如果在海邊發現受傷、擱淺的活體海龜，應該撥打什麼電話求助？", "(A) 撥打海巡署專線 118", "(B) 撥打氣象局 166", 'A', "若發現擱淺海龜，請立刻撥打海巡署 118 專線，會有專業海事救難人員協助！"),
            new Question("孵化海龜寶寶的沙灘「溫度」，會如何決定小海龜的性別？", "(A) 溫度越高，母海龜比例越高", "(B) 溫度越高，公海龜比例越高", 'A', "海龜性別由巢穴溫度決定。高溫孵出母海龜，低溫孵出公海龜。")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // 1. 綁定介面元件
        tvHighScore = findViewById(R.id.tvHighScore);
        tvQuestion = findViewById(R.id.tvQuestion);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnQuizBack = findViewById(R.id.btnQuizBack);

        // 2. 初始化 SharedPreferences（讀取歷史最高分紀錄）
        sharedPreferences = getSharedPreferences("QuizRecord", Context.MODE_PRIVATE);
        highScore = sharedPreferences.getInt("high_score", 0);

        // 3. 🎲 初始化遊戲：生成 0~9 的數字並打亂，確保每次進來題目順序都隨機
        for (int i = 0; i < questionBank.length; i++) {
            quizOrderList.add(i);
        }
        Collections.shuffle(quizOrderList);

        // 4. 開始第一題
        currentScore = 0;
        currentQuestionIndex = 0;
        updateHighScoreLayout();
        showNextQuestion();

        // 5. 設定選項點擊事件 (A)
        if (btnOption1 != null) {
            btnOption1.setOnClickListener(v -> checkAnswer('A'));
        }

        // 6. 設定選項點擊事件 (B)
        if (btnOption2 != null) {
            btnOption2.setOnClickListener(v -> checkAnswer('B'));
        }

        // 7. 返回主選單
        if (btnQuizBack != null) {
            btnQuizBack.setOnClickListener(v -> finish());
        }
    }

    /**
     * 📊 更新頂部面板（顯示歷史最高分，以及這次執行從 0 開始累加的分數）
     */
    private void updateHighScoreLayout() {
        tvHighScore.setText("🏆 歷史最高：" + highScore + " 分  |  🎯 當前得分：" + currentScore + " 分");
    }

    /**
     * 📝 載入下一題的內容到畫面上
     */
    private void showNextQuestion() {
        if (currentQuestionIndex < questionBank.length) {
            // 依據被打亂的順序取出題目
            int actualQuestionId = quizOrderList.get(currentQuestionIndex);
            Question currentQuestion = questionBank[actualQuestionId];

            // 渲染文字，並加上 (第 X / 10 題) 的溫馨提示
            tvQuestion.setText("【第 " + (currentQuestionIndex + 1) + " / 10 題】\n\n" + currentQuestion.questionText);
            btnOption1.setText(currentQuestion.optionA);
            btnOption2.setText(currentQuestion.optionB);
        } else {
            // 10 題全部答完了，進行總結算
            handleQuizFinished();
        }
    }

    /**
     * 🧮 判斷單題答案（每對一題加 10 分）
     */
    private void checkAnswer(char userChoice) {
        int actualQuestionId = quizOrderList.get(currentQuestionIndex);
        Question currentQuestion = questionBank[actualQuestionId];

        String titleStatus;
        String explanationMessage;

        if (userChoice == currentQuestion.correctAnswer) {
            titleStatus = "🎉 答對了！ (+10分)";
            currentScore += 10; // 隨著回答問題，當前得分提高 10 分
            explanationMessage = currentQuestion.explanation;
        } else {
            titleStatus = "❌ 答錯囉！ (+0分)";
            explanationMessage = "正確答案是：\n" +
                    (currentQuestion.correctAnswer == 'A' ? currentQuestion.optionA : currentQuestion.optionB) +
                    "\n\n💡 解析：\n" + currentQuestion.explanation;
        }

        // 即時更新分數面板
        updateHighScoreLayout();

        // 彈出單題解析對話框
        new AlertDialog.Builder(this)
                .setTitle(titleStatus)
                .setMessage(explanationMessage)
                .setCancelable(false) // 強迫看解析，不准點外面關閉
                .setPositiveButton("下一題", (dialog, which) -> {
                    currentQuestionIndex++; // 前進到下一題索引
                    showNextQuestion();     // 載入新題目
                })
                .show();
    }

    /**
     * 🏁 10 題全部答完的總結算與 SharedPreferences 寫入機制
     */
    private void handleQuizFinished() {
        String finishMessage = "您已完成全部 10 題挑戰！\n🌟 最終獲得總分：" + currentScore + " 分\n\n";
        boolean isNewRecord = false;

        // 💾 總分大於歷史紀錄時，才在最後一刻將分數存入小帳本！
        if (currentScore > highScore) {
            highScore = currentScore;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("high_score", highScore);
            editor.apply(); // 寫入本機

            finishMessage += "🥇 太強了！您刷新了歷史最高分數紀錄！";
            isNewRecord = true;
        } else {
            finishMessage += "繼續加油！離歷史最高紀錄還差 " + (highScore - currentScore) + " 分！";
        }

        final boolean finalRecordFlag = isNewRecord;

        new AlertDialog.Builder(this)
                .setTitle("🏁 挑戰結束 (總結算)")
                .setMessage(finishMessage)
                .setCancelable(false)
                .setPositiveButton("回到主選單", (dialog, which) -> {
                    if (finalRecordFlag) {
                        Toast.makeText(QuizActivity.this, "🏆 紀錄已同步儲存！", Toast.LENGTH_SHORT).show();
                    }
                    finish(); // 結束問答，關閉頁面
                })
                .show();
    }
}