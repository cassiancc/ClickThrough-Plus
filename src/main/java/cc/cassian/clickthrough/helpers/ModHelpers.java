package cc.cassian.clickthrough.helpers;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.compat.FastItemFramesCompat;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.ModLists;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBannerBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
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

    //Check if Architectury API is installed and its methods can be used.
    public static boolean architecturyInstalled() {
        return isLoaded("architectury");
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
    public static Text fieldName(Field field) {
        return Text.translatable("clickthrough.config." + field.getName());
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

    public static boolean isClickableBlockAt(BlockPos pos, ClientWorld world) {
        if (!ModConfig.get().onlycontainers) {
            return true;
        }
        BlockEntity entity = world.getBlockEntity(pos);
        var state = world.getBlockState(pos);
        if (entity instanceof LockableContainerBlockEntity)
            return true;
        return ModHelpers.isTaggedAsContainer(state) || ModLists.containers.contains(state.getBlock());
    }

    public static HitResult switchCrosshairTarget(HitResult crosshairTarget, ClientPlayerEntity player, ClientWorld world) {
        if (!ModConfig.get().isActive) {
            return crosshairTarget;
        }
        ClickThrough.isDyeOnSign = false;
        if (crosshairTarget != null) {
            if (crosshairTarget.getType() == HitResult.Type.ENTITY && ((EntityHitResult) crosshairTarget).getEntity() instanceof ItemFrameEntity itemFrame) {
                // copied from AbstractDecorationEntity#canStayAttached
                BlockPos attachedPos = itemFrame
                        //? if >1.21 {
                        .getAttachedBlockPos()
                        //?} else {
                        /*.getDecorationBlockPos()
                         *///?}
                        .offset(itemFrame.getHorizontalFacing().getOpposite());
                // System.out.println("Item frame attached to "+state.getBlock().getTranslationKey()+" at "+blockPos.toShortString());
                if (!player.isSneaking() && isClickableBlockAt(attachedPos, world)) {
                    return new BlockHitResult(crosshairTarget.getPos(), itemFrame.getHorizontalFacing(), attachedPos, false);
                }
            }
            else if (crosshairTarget instanceof BlockHitResult blockHitResult) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                BlockState state = world.getBlockState(blockPos);
                Block block = state.getBlock();
                if (block instanceof WallSignBlock) {
                    BlockPos attachedPos = blockPos.offset(state.get(WallSignBlock.FACING).getOpposite());
                    if (!isClickableBlockAt(attachedPos, world)) {
                        return crosshairTarget;
                    }
                    BlockEntity entity = world.getBlockEntity(blockPos);
                    if (!(entity instanceof SignBlockEntity)) {
                        return crosshairTarget;
                    }

                    Item item = player.getStackInHand(Hand.MAIN_HAND).getItem();
                    if (item instanceof DyeItem || item == Items.GLOW_INK_SAC) {
                        if (ModConfig.get().sneaktodye) {
                            ClickThrough.isDyeOnSign = true;                // prevent sneaking from cancelling the interaction
                            if (!player.isSneaking()) {
                                return new BlockHitResult(crosshairTarget.getPos(), blockHitResult.getSide(), attachedPos, false);
                            }
                        }
                    } else {
                        if (!player.isSneaking()) {
                            return new BlockHitResult(crosshairTarget.getPos(), blockHitResult.getSide(), attachedPos, false);
                        }
                    }
                } else if (block instanceof WallBannerBlock) {
                    BlockPos attachedPos = blockPos.offset(state.get(WallBannerBlock.FACING).getOpposite());
                    if (ModHelpers.isClickableBlockAt(attachedPos, world)) {
                        return new BlockHitResult(crosshairTarget.getPos(), blockHitResult.getSide(), attachedPos, false);
                    }
                } else if (ModHelpers.isLoaded("fastitemframes")) {
                    return FastItemFramesCompat.passthrough(block, state, blockPos, world, crosshairTarget, player);
                }
            }
        }
        return crosshairTarget;
    }
}
