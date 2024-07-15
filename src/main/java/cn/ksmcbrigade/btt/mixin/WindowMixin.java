package cn.ksmcbrigade.btt.mixin;

import com.mojang.blaze3d.platform.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.charset.StandardCharsets;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

@Mixin(Window.class)
public abstract class WindowMixin {
    @Shadow @Final private long window;

    @Inject(method = "setTitle",at = @At("HEAD"),cancellable = true)
    public void setTitle(String p_85423_, CallbackInfo ci){
        glfwSetWindowTitle(this.window, new String(p_85423_.getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8));
        ci.cancel();
    }
}
