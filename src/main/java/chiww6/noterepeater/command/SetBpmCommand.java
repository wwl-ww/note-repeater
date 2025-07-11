package chiww6.noterepeater.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import chiww6.noterepeater.state.GlobalBeatState;

public class SetBpmCommand {
    // 预设的BPM值列表
    private static final int[] VALID_BPM = {40, 50, 60, 75, 100, 120, 150, 200, 240, 300, 600, 1200};

    // 注册 /setbpm 命令
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("setbpm")
                .requires(source -> source.hasPermissionLevel(2)) // 仅 OP 可用
                .then(CommandManager.argument("bpm", IntegerArgumentType.integer(40, 1200))
                        .executes(context -> {
                            int requestedBpm = IntegerArgumentType.getInteger(context, "bpm");
                            
                            // 检查是否是预设的BPM值
                            boolean isValidBpm = false;
                            for (int validBpm : VALID_BPM) {
                                if (validBpm == requestedBpm) {
                                    isValidBpm = true;
                                    break;
                                }
                            }
                            
                            if (!isValidBpm) {
                                // 找到最接近的有效值
                                int closestBpm = findClosestValidBpm(requestedBpm);
                                context.getSource().sendError(Text.literal("§c无效的BPM值: " + requestedBpm + 
                                    "\n§c请使用以下预设值之一: " + formatValidBpmList() +
                                    "\n§e最接近的有效值是: " + closestBpm));
                                return 0;
                            }
                            
                            // 设置BPM
                            GlobalBeatState.setBpm(requestedBpm);
                            context.getSource().sendFeedback(
                                () -> Text.literal("§a已设置 BPM 为 " + requestedBpm), true);
                            return 1;
                        })));
    }

    // 格式化有效BPM值列表
    private static String formatValidBpmList() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < VALID_BPM.length; i++) {
            sb.append(VALID_BPM[i]);
            if (i < VALID_BPM.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    
    // 找到最接近的有效BPM值
    private static int findClosestValidBpm(int requestedBpm) {
        int closestBpm = VALID_BPM[0];
        int minDiff = Math.abs(requestedBpm - VALID_BPM[0]);
        
        for (int validBpm : VALID_BPM) {
            int diff = Math.abs(requestedBpm - validBpm);
            if (diff < minDiff) {
                minDiff = diff;
                closestBpm = validBpm;
            }
        }
        return closestBpm;
    }
} 