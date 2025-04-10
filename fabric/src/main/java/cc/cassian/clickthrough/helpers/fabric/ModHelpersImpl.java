package cc.cassian.clickthrough.helpers.fabric;

//? if >1.21 {
/*import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
*///?} else {
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
 //?}


import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }

    public static boolean isTaggedAsContainer(BlockState state) {
        var stack = state.getBlock().asItem().getDefaultStack();
        return state.isIn(ConventionalBlockTags.CHESTS) || state.isIn(BlockTags.GUARDED_BY_PIGLINS) || stack.isIn(ConventionalItemTags.CHESTS)
        //? if >1.21 {
        /*|| state.isIn(ConventionalBlockTags.BARRELS)  || stack.isIn(ConventionalItemTags.BARRELS)
        *///?}
        ;
    }


    public static boolean architecturyInstalled() {
        return FabricLoader.getInstance().isModLoaded("architectury");
    }

    public static boolean fastItemFramesInstalled() {
        return FabricLoader.getInstance().isModLoaded("fastitemframes");
    }

}
