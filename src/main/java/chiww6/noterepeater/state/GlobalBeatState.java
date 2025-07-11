package chiww6.noterepeater.state;

import java.util.HashMap;
import java.util.Map;

public class GlobalBeatState {
    // 预设的BPM值列表
    private static final int[] VALID_BPM = {40, 50, 60, 75, 100, 120, 150, 200, 240, 300, 600, 1200};
    private static volatile int bpm = 75; // 默认75 BPM
    
    // BPM和分频到tick数的映射
    private static final Map<Integer, Map<Integer, Integer>> TICK_MAP = new HashMap<>();
    
    static {
        // 40 BPM
        Map<Integer, Integer> bpm40 = new HashMap<>();
        bpm40.put(4, 30);  // 四分音符
        bpm40.put(8, 15);  // 八分音符
        bpm40.put(16, 7);  // 十六分音符（向下取整）
        bpm40.put(32, 3);  // 三十二分音符（向下取整）
        TICK_MAP.put(40, bpm40);
        
        // 50 BPM
        Map<Integer, Integer> bpm50 = new HashMap<>();
        bpm50.put(4, 24);
        bpm50.put(8, 12);
        bpm50.put(16, 6);
        bpm50.put(32, 3);
        TICK_MAP.put(50, bpm50);
        
        // 60 BPM
        Map<Integer, Integer> bpm60 = new HashMap<>();
        bpm60.put(4, 20);
        bpm60.put(8, 10);
        bpm60.put(16, 5);
        bpm60.put(32, 2);
        TICK_MAP.put(60, bpm60);
        
        // 75 BPM
        Map<Integer, Integer> bpm75 = new HashMap<>();
        bpm75.put(4, 16);
        bpm75.put(8, 8);
        bpm75.put(16, 4);
        bpm75.put(32, 2);
        TICK_MAP.put(75, bpm75);
        
        // 100 BPM
        Map<Integer, Integer> bpm100 = new HashMap<>();
        bpm100.put(4, 12);
        bpm100.put(8, 6);
        bpm100.put(16, 3);
        bpm100.put(32, 1);
        TICK_MAP.put(100, bpm100);
        
        // 120 BPM
        Map<Integer, Integer> bpm120 = new HashMap<>();
        bpm120.put(4, 10);
        bpm120.put(8, 5);
        bpm120.put(16, 2);
        bpm120.put(32, 1);
        TICK_MAP.put(120, bpm120);
        
        // 150 BPM
        Map<Integer, Integer> bpm150 = new HashMap<>();
        bpm150.put(4, 8);
        bpm150.put(8, 4);
        bpm150.put(16, 2);
        bpm150.put(32, 1);
        TICK_MAP.put(150, bpm150);
        
        // 200 BPM
        Map<Integer, Integer> bpm200 = new HashMap<>();
        bpm200.put(4, 6);
        bpm200.put(8, 3);
        bpm200.put(16, 1);
        bpm200.put(32, 1);
        TICK_MAP.put(200, bpm200);
        
        // 240 BPM
        Map<Integer, Integer> bpm240 = new HashMap<>();
        bpm240.put(4, 5);
        bpm240.put(8, 2);
        bpm240.put(16, 1);
        bpm240.put(32, 1);
        TICK_MAP.put(240, bpm240);
        
        // 300 BPM
        Map<Integer, Integer> bpm300 = new HashMap<>();
        bpm300.put(4, 4);
        bpm300.put(8, 2);
        bpm300.put(16, 1);
        bpm300.put(32, 1);
        TICK_MAP.put(300, bpm300);
        
        // 600 BPM
        Map<Integer, Integer> bpm600 = new HashMap<>();
        bpm600.put(4, 2);
        bpm600.put(8, 1);
        bpm600.put(16, 1);
        bpm600.put(32, 1);
        TICK_MAP.put(600, bpm600);
        
        // 1200 BPM
        Map<Integer, Integer> bpm1200 = new HashMap<>();
        bpm1200.put(4, 1);
        bpm1200.put(8, 1);
        bpm1200.put(16, 1);
        bpm1200.put(32, 1);
        TICK_MAP.put(1200, bpm1200);
    }
    
    // 获取当前BPM
    public static int getBpm() {
        return bpm;
    }

    // 设置BPM，只允许使用预设值
    public static void setBpm(int newBpm) {
        // 找到最接近的有效BPM值
        int closestBpm = VALID_BPM[0];
        int minDiff = Math.abs(newBpm - VALID_BPM[0]);
        
        for (int validBpm : VALID_BPM) {
            int diff = Math.abs(newBpm - validBpm);
            if (diff < minDiff) {
                minDiff = diff;
                closestBpm = validBpm;
            }
        }
        bpm = closestBpm;
    }

    // 直接从映射表获取tick数
    public static int getTicksForDivision(int division) {
        Map<Integer, Integer> divisionMap = TICK_MAP.get(bpm);
        if (divisionMap != null) {
            Integer ticks = divisionMap.get(division);
            if (ticks != null) {
                return ticks;
            }
        }
        return 1; // 默认返回1tick
    }
    
    // 获取所有有效的BPM值
    public static int[] getValidBpms() {
        return VALID_BPM.clone();
    }

    // 获取最小有效tick数（用于验证调度是否有效）
    public static int getMinimumValidTicks() {
        return 1; // 最小tick数为1，因为这是Minecraft能处理的最小时间单位
    }
}
