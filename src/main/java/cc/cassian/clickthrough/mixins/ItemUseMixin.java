package cc.cassian.clickthrough.mixins;

import cc.cassian.clickthrough.helpers.ModHelpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Minecraft.class)
public class ItemUseMixin {

    @Shadow public HitResult hitResult;
    @Shadow public LocalPlayer player;
    @Shadow public ClientLevel level;

    @Inject(method="startUseItem", at=@At(value="INVOKE",
            target="Lnet/minecraft/client/player/LocalPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"))
    public void switchCrosshairTargetItemUse(CallbackInfo ci) {
        this.hitResult = ModHelpers.switchCrosshairTarget(hitResult, player, level);
    }

}