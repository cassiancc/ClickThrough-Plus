package cc.cassian.clickthrough.helpers;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.compat.FastItemFramesCompat;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.ModLists;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBannerBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.Field;
import java.util.function.Consumer;


public class ModHelpers {
    //Shorthand for config.
    public static ModConfig config = ModConfig.get();

    //Check if a mod is installed and its configuration can be used.
    @ExpectPlatform
    public static boolean isLoaded(String mod) {
        throw new AssertionError();
    }

    //Check if Cloth Config is installed and its configuration can be used.
    @ExpectPlatform
    public static boolean clothConfigInstalled() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerKeybind() {
        throw new AssertionError();
    }

    public static String getSignRowText(SignBlockEntity sign, int row) {
        StringBuilder builder =  new StringBuilder();
        return sign
            //? if >1.20 {
            .getFrontText().getMessage(row, true)
            //?} else {
            /*.getTextOnRow(row, true)
             *///?}
            .getString();
    }


    //Automatically generate translation keys for config options.
    public static Component fieldName(Field field) {
        return Component.translatable("clickthrough.config." + field.getName());
    }


    //Get the current value of a config field.
    @SuppressWarnings("unchecked")
    public static <T> T fieldGet(Object instance, Field field) {
        try {
            return (T) field.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //Set a config field.
    public static <T> Consumer<T> fieldSetter(Object instance, Field field) {
        return t -> {
            try {
                field.set(instance, t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @ExpectPlatform
    public static boolean isTaggedAsContainer(BlockState state) {
        throw new AssertionError();
    }

    public static boolean isClickableBlockAt(BlockPos pos, ClientLevel world) {
        if (!ModConfig.get().onlycontainers) {
            return true;
        }
        BlockEntity entity = world.getBlockEntity(pos);
        var state = world.getBlockState(pos);
        if (entity instanceof BaseContainerBlockEntity)
            return true;
        return ModHelpers.isTaggedAsContainer(state) || ModLists.containers.contains(state.getBlock());
    }

    public static HitResult switchCrosshairTarget(HitResult crosshairTarget, LocalPlayer player, ClientLevel world) {
        if (!ModConfig.get().isActive) {
            return crosshairTarget;
        }
        ClickThrough.isDyeOnSign = false;
        if (crosshairTarget != null) {
            if (crosshairTarget.getType() == HitResult.Type.ENTITY && ((EntityHitResult) crosshairTarget).getEntity() instanceof ItemFrame itemFrame) {
                // copied from AbstractDecorationEntity#canStayAttached
                BlockPos attachedPos = itemFrame
                        //? if >1.21 {
                        .getPos()
                        //?} else {
                        /*.getDecorationBlockPos()
                         *///?}
                        .offset(itemFrame.getDirection().getUnitVec3i());
                // System.out.println("Item frame attached to "+state.getBlock().getTranslationKey()+" at "+blockPos.toShortString());
                if (!player.isShiftKeyDown() && isClickableBlockAt(attachedPos, world)) {
                    return new BlockHitResult(crosshairTarget.getLocation(), itemFrame.getDirection(), attachedPos, false);
                }
            }
            else if (crosshairTarget instanceof BlockHitResult blockHitResult) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockState state = world.getBlockState(blockPos);
                Block block = state.getBlock();
                if (block instanceof WallSignBlock) {
                    BlockPos attachedPos = blockPos.offset(state.getValue(WallSignBlock.FACING).getUnitVec3i());
                    if (!isClickableBlockAt(attachedPos, world)) {
                        return crosshairTarget;
                    }
                    BlockEntity entity = world.getBlockEntity(blockPos);
                    if (!(entity instanceof SignBlockEntity)) {
                        return crosshairTarget;
                    }

                    Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                    if (item instanceof DyeItem || item == Items.GLOW_INK_SAC) {
                        if (ModConfig.get().sneaktodye) {
                            ClickThrough.isDyeOnSign = true;                // prevent sneaking from cancelling the interaction
                            if (!player.isShiftKeyDown()) {
                                return new BlockHitResult(crosshairTarget.getLocation(), blockHitResult.getDirection(), attachedPos, false);
                            }
                        }
                    } else {
                        if (!player.isShiftKeyDown()) {
                            return new BlockHitResult(crosshairTarget.getLocation(), blockHitResult.getDirection(), attachedPos, false);
                        }
                    }
                } else if (block instanceof WallBannerBlock) {
                    BlockPos attachedPos = blockPos.offset(state.getValue(WallBannerBlock.FACING).getUnitVec3i());
                    if (ModHelpers.isClickableBlockAt(attachedPos, world)) {
                        return new BlockHitResult(crosshairTarget.getLocation(), blockHitResult.getDirection(), attachedPos, false);
                    }
                } else if (ModHelpers.isLoaded("fastitemframes")) {
                    return FastItemFramesCompat.passthrough(block, state, blockPos, world, crosshairTarget, player);
                }
            }
        }
        return crosshairTarget;
    }
}
