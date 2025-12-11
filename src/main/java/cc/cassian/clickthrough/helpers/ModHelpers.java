package cc.cassian.clickthrough.helpers;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.Platform;
import cc.cassian.clickthrough.compat.FastItemFramesCompat;
import cc.cassian.clickthrough.config.ModLists;
//? if fabric && >1.21 {
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
//?} else if fabric && <1.21 {
/*import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
*///?} else if neoforge {

/*import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.neoforged.neoforge.common.Tags;
*///?}
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
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

import static cc.cassian.clickthrough.ClickThrough.*;


public class ModHelpers {

    public static void handleKeybind(Minecraft minecraft) {
        while (onoff.isDown()) {
            if (CONFIG.isActive) {
                setInActive();
            } else {
                setActive();
            }
        }
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

    public static boolean isTaggedAsContainer(BlockState state) {
        var stack = state.getBlock().asItem().getDefaultInstance();
        //? if fabric {
        return state.is(ConventionalBlockTags.CHESTS) || state.is(BlockTags.GUARDED_BY_PIGLINS)
                //? if >1.20 {
                || stack.is(ConventionalItemTags.CHESTS)
                //?}
                //? if >1.21 {
                || state.is(ConventionalBlockTags.BARRELS)  || stack.is(ConventionalItemTags.BARRELS)
                //?}
                ;
        //?} else {
        /*return state.is(Tags.Blocks.CHESTS) || state.is(Tags.Blocks.BARRELS)
                || stack.is(Tags.Items.CHESTS)  || stack.is(Tags.Items.BARRELS)
                || state.is(BlockTags.GUARDED_BY_PIGLINS);
        *///?}
    }

    public static boolean isClickableBlockAt(BlockPos pos, ClientLevel world) {
        if (!CONFIG.onlycontainers) {
            return true;
        }
        BlockEntity entity = world.getBlockEntity(pos);
        var state = world.getBlockState(pos);
        if (entity instanceof BaseContainerBlockEntity)
            return true;
        return ModHelpers.isTaggedAsContainer(state) || ModLists.containers.contains(state.getBlock());
    }

    public static HitResult switchCrosshairTarget(HitResult crosshairTarget, LocalPlayer player, ClientLevel world) {
        if (!CONFIG.isActive) {
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
                        /*.getOnPos()
                         *///?}
                        .offset(getOpposite(itemFrame.getDirection()));
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
                    BlockPos attachedPos = blockPos.offset(getOpposite(state.getValue(WallSignBlock.FACING)));
                    if (!isClickableBlockAt(attachedPos, world)) {
                        return crosshairTarget;
                    }
                    BlockEntity entity = world.getBlockEntity(blockPos);
                    if (!(entity instanceof SignBlockEntity)) {
                        return crosshairTarget;
                    }

                    Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
                    if (item instanceof DyeItem || item == Items.GLOW_INK_SAC) {
                        if (CONFIG.sneaktodye) {
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
                    BlockPos attachedPos = blockPos.offset(getOpposite(state.getValue(WallBannerBlock.FACING)));
                    if (ModHelpers.isClickableBlockAt(attachedPos, world)) {
                        return new BlockHitResult(crosshairTarget.getLocation(), blockHitResult.getDirection(), attachedPos, false);
                    }
                } else if (Platform.INSTANCE.isLoaded("fastitemframes")) {
                    return FastItemFramesCompat.passthrough(block, state, blockPos, world, crosshairTarget, player);
                }
            }
        }
        return crosshairTarget;
    }

    public static Vec3i getOpposite(Direction direction) {
        return direction.getOpposite().
                //? if >1.21.2 {
                getUnitVec3i()
                //?} else {
                /*getNormal()
                 *///?}
        ;
    }
}
