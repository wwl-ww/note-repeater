package chiww6.noterepeater.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import chiww6.noterepeater.state.GlobalBeatState;

public class GetBpmCommand {
    // 注册 /getbpm 命令
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("getbpm")
                .requires(source -> source.hasPermissionLevel(0)) // 所有玩家都可以使用
                .executes(context -> {
                    int bpm = GlobalBeatState.getBpm();
                    // 设置 broadcastToOps 为 true，这样命令方块执行时也会显示消息
                    context.getSource().sendFeedback(() -> Text.literal("§a当前 BPM 为 " + bpm), true);
                    return 1;
                }));
    }
} 