package cc.cassian.clickthrough.compat;

import cc.cassian.clickthrough.helpers.ModHelpers;
import fuzs.fastitemframes.world.level.block.ItemFrameBlock;
import fuzs.fastitemframes.world.level.block.entity.ItemFrameBlockEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static cc.cassian.clickthrough.helpers.ModHelpers.isClickableBlockAt;

public class FastItemFramesCompat {
    public static HitResult passthrough(Block block, BlockState state, BlockPos blockPos, ClientLevel world, HitResult crosshairTarget, LocalPlayer player) {
        if (block instanceof ItemFrameBlock) {
            BlockPos attachedPos = blockPos.offset(ModHelpers.getOpposite(state.getValue(ItemFrameBlock.FACING)));
            if (!isClickableBlockAt(attachedPos, world)) {
                return crosshairTarget;
            }
            if (world.getBlockEntity(blockPos) instanceof ItemFrameBlockEntity itemFrameBlockEntity) {
                if (itemFrameBlockEntity.getItem().isEmpty() && (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty())) {
                    return crosshairTarget;
                }
                if (!player.isShiftKeyDown()) {
                    return new BlockHitResult(crosshairTarget.getLocation(), ((BlockHitResult)crosshairTarget).getDirection(), attachedPos, false);
                }
            }
        }
        return crosshairTarget;
    }
}
