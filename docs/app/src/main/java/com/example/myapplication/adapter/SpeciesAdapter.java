package com.example.myapplication.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView; // 👈 導入 ImageView
import android.widget.LinearLayout; // 👈 導入 LinearLayout
import android.widget.TextView; // 👈 導入 TextView
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.model.TurtleSpecies;
import java.util.List;

public class SpeciesAdapter extends RecyclerView.Adapter<SpeciesAdapter.ViewHolder> {
    private final List<TurtleSpecies> speciesList;

    public SpeciesAdapter(List<TurtleSpecies> speciesList) {
        this.speciesList = speciesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Button button = new Button(parent.getContext());
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 15, 10, 15);
        button.setLayoutParams(params);
        button.setTextSize(18);
        button.setBackgroundColor(android.graphics.Color.parseColor("#0097A7"));
        button.setTextColor(android.graphics.Color.WHITE);
        return new ViewHolder(button);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TurtleSpecies species = speciesList.get(position);
        holder.speciesButton.setText("🐢 查看 " + species.getName());

        holder.speciesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 動態建立一個垂直排版的容器
                LinearLayout contentLayout = new LinearLayout(v.getContext());
                contentLayout.setOrientation(LinearLayout.VERTICAL);
                contentLayout.setPadding(30, 20, 30, 20);
                contentLayout.setGravity(Gravity.CENTER_HORIZONTAL); // 內容居中

                // 2. 建立 ImageView 並載入海龜圖片
                ImageView turtleImage = new ImageView(v.getContext());
                turtleImage.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 600)); // 設定固定高度
                turtleImage.setScaleType(ImageView.ScaleType.CENTER_CROP); // 圖片填滿切齊
                turtleImage.setAdjustViewBounds(true);
                turtleImage.setImageResource(species.getImageResource()); // 👈 載入我們設定好的圖片路徑！
                turtleImage.setPadding(0, 0, 0, 30); // 圖片下方留白

                // 3. 建立 TextView 並載入詳細圖文資料
                TextView detailText = new TextView(v.getContext());
                detailText.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                detailText.setTextSize(16);
                detailText.setTextColor(android.graphics.Color.DKGRAY);
                detailText.setText("【英文學名】\n" + species.getSubTitle() + "\n\n" +
                        "【外觀與生態特徵】\n" + species.getFeatures());

                // 4. 把圖片和文字元件都塞進去容器裡
                contentLayout.addView(turtleImage);
                contentLayout.addView(detailText);

                // 5. 彈出對話框，這次內容不再只有文字，而是呈現整個 contentLayout 元件
                new AlertDialog.Builder(v.getContext())
                        .setTitle("📖 " + species.getName() + "（" + species.getStatus() + "）")
                        .setView(contentLayout) // 👈 關鍵！把整個包含圖片文字的容器放進去
                        .setPositiveButton("返回品種清單", null)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return speciesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button speciesButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            speciesButton = (Button) itemView;
        }
    }
}
