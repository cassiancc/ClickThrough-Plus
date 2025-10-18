package cc.cassian.clickthrough.mixins;

import cc.cassian.clickthrough.helpers.ModHelpers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftClient.class)
public class ItemUseMixin {

    @Shadow public HitResult crosshairTarget;
    @Shadow public ClientPlayerEntity player;
    @Shadow public ClientWorld world;

    @Inject(method="doItemUse", at=@At(value="INVOKE",
            target="Lnet/minecraft/client/network/ClientPlayerEntity;getStackInHand(Lnet/minecraft/util/Hand;)Lnet/minecraft/item/ItemStack;"))
    public void switchCrosshairTargetItemUse(CallbackInfo ci) {
        this.crosshairTarget = ModHelpers.switchCrosshairTarget(crosshairTarget, player, world);
    }

}