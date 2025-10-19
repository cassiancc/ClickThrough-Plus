package cc.cassian.clickthrough.mixins;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.config.ModConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cc.cassian.clickthrough.ClickThrough.CONFIG;

@Mixin(Player.class)

public class SneakingCancelsInteractionMixin {
    @Inject(method="isSecondaryUseActive", at=@At("HEAD"), cancellable = true)
    private void noCancelWhenDyeing(CallbackInfoReturnable<Boolean> cir) {
        if (!CONFIG.isActive) {
            return;
        }
        if (((Object) this) instanceof LocalPlayer) {
            // System.out.println("on client");
            if (ClickThrough.isDyeOnSign) {
                // System.out.println("  not cancelling!");
                cir.setReturnValue(false);
                cir.cancel();
                ClickThrough.isDyeOnSign = false;
            }
        }
    }
}
