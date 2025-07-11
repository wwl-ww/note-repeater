package chiww6.noterepeater.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class NoteRepeaterMixin {
    // 注入到 MinecraftServer.loadWorld() 方法头部
    // 可用于全局初始化（如后续需要）
	@Inject(at = @At("HEAD"), method = "loadWorld")
	private void init(CallbackInfo info) {
        // 目前未实现实际功能，如需全局初始化可在此处编写
	}
}