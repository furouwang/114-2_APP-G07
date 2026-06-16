package com.example.myapplication.data;

import com.example.myapplication.R; // 👈 必須導入專案的 R，因為圖片放在這裡
import com.example.myapplication.model.TurtleSpecies;
import java.util.ArrayList;
import java.util.List;

public class TurtleSpeciesProvider {
    public static List<TurtleSpecies> getSpeciesList() {
        List<TurtleSpecies> list = new ArrayList<>();

        // 我在每一行最後增加了 R.drawable.xxx，這必須對應你放進去 drawable 的檔案名字！
        list.add(new TurtleSpecies("綠蠵龜", "Green Sea Turtle",
                "台灣最常見、也是唯一會在台灣離島（如小琉球、澎湖）上岸產卵的海龜。主食為海草與海藻，體內脂肪因為葉綠素累積呈現淡綠色，因而得名。背甲是平滑的心形。",
                "一級保育類 / 瀕危", R.drawable.turtle_green)); // 👈 請確認檔名對應

        list.add(new TurtleSpecies("玳瑁", "Hawksbill Turtle",
                "常出現於珊瑚礁海域。最明顯的特徵是嘴尖如鷹喙（老鷹嘴），且背甲邊緣有像鋸齒一樣的形狀。牠們的花紋極其華麗，主食為珊瑚礁區的海綿。",
                "一級保育類 / 極危", R.drawable.turtle_hawksbill));

        list.add(new TurtleSpecies("赤蠵龜", "Loggerhead Turtle",
                "頭部比例特別巨大，下顎肌肉發達，外表看起來呆萌。全身顏色偏紅褐色，主食是螃蟹、貝類等硬殼甲殼類。台灣東部海域偶爾能見到牠們跟著洋流路過。",
                "一級保育類 / 瀕危", R.drawable.turtle_loggerhead));

        list.add(new TurtleSpecies("欖蠵龜", "Olive Ridley Turtle",
                "體型是這五種裡面最小的。背甲顏色看起來像橄欖綠色，且背甲形狀非常圆，像一顆大菠蘿。牠們在國外最著名的行為是成千上萬隻同時上岸集體產卵（Arribada）。",
                "一級保育類 / 易危", R.drawable.turtle_oliveridley));

        list.add(new TurtleSpecies("革龜", "Leatherback Turtle",
                "世界上體型最大的海龜！牠們最奇特的是「沒有堅硬的甲殼」，而是由類似皮革、橡膠般的黑色軟皮包裹身體，背上有 7 條明顯的縱棱。主食是水母，外海深水區極罕見。",
                "一級保育類 / 易危", R.drawable.turtle_leatherback));

        return list;
    }
}
