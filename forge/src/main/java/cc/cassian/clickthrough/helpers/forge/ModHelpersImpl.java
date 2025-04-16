package cc.cassian.clickthrough.helpers.forge;


import net.minecraft.block.BlockState;
//? if >1.20 {
import net.minecraft.registry.tag.BlockTags;
//?} else {
/*import net.minecraft.tag.BlockTags;
 *///?}
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return ModList.get().isLoaded("cloth_config");
    }

    public static boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }

    public static boolean isTaggedAsContainer(BlockState state) {
        var stack = state.getBlock().asItem().getDefaultStack();
        return state.isIn(Tags.Blocks.CHESTS) || state.isIn(BlockTags.GUARDED_BY_PIGLINS)
                || stack.isIn(Tags.Items.CHESTS)
                || state.isIn(Tags.Blocks.BARRELS)  || stack.isIn(Tags.Items.BARRELS);
    }

}
