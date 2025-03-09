package cc.cassian.clickthrough.compat;

import fuzs.fastitemframes.world.level.block.ItemFrameBlock;
import fuzs.fastitemframes.world.level.block.entity.ItemFrameBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import static cc.cassian.clickthrough.helpers.ModHelpers.isClickableBlockAt;

public class FastItemFramesCompat {
    public static HitResult passthrough(Block block, BlockState state, BlockPos blockPos, ClientWorld world, HitResult crosshairTarget, ClientPlayerEntity player) {
        if (block instanceof ItemFrameBlock) {
            BlockPos attachedPos = blockPos.offset(state.get(ItemFrameBlock.FACING).getOpposite());
            if (!isClickableBlockAt(attachedPos, world)) {
                return null;
            }
            BlockEntity entity = world.getBlockEntity(blockPos);
            if (!(entity instanceof ItemFrameBlockEntity)) {
                return null;
            }
            if (entity instanceof ItemFrameBlockEntity itemFrameBlockEntity) {
                if (!itemFrameBlockEntity.getItem().isEmpty() && !player.isSneaking()) {
                    return new BlockHitResult(crosshairTarget.getPos(), ((BlockHitResult)crosshairTarget).getSide(), attachedPos, false);
                }
                else {
                    return null;
                }
            }
            else return null;
        }
        return null;
    }
}
