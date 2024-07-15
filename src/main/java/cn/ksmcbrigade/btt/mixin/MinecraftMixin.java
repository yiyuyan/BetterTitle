package cn.ksmcbrigade.btt.mixin;

import cn.ksmcbrigade.btt.BetterTitle;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow public abstract void updateTitle();

    @Inject(method = "createTitle",at = @At("HEAD"),cancellable = true)
    public void title(CallbackInfoReturnable<String> cir){
        if(BetterTitle.config.enabled){
            cir.setReturnValue(BetterTitle.config.getTitle());
            cir.cancel();
        }
    }

    @Inject(method = "tick",at = @At("TAIL"))
    public void update(CallbackInfo ci){
        this.updateTitle();
    }
}
