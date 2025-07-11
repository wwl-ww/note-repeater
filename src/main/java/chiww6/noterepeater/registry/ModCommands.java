package chiww6.noterepeater.registry;

import chiww6.noterepeater.command.SetBpmCommand;
import chiww6.noterepeater.command.GetBpmCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

public class ModCommands {
    // 注册所有自定义命令
    public static void registerModCommands() {
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) -> {
                    SetBpmCommand.register(dispatcher);
                    GetBpmCommand.register(dispatcher);
                }
        );
    }
}
