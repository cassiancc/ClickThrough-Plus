package cc.cassian.clickthrough.helpers.fabric;

//? if >1.21 {
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
//?} else {
/*import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
 *///?}

//? if >1.20 {
import net.minecraft.registry.tag.BlockTags;
//?} else {
/*import net.minecraft.tag.BlockTags;
*///?}
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;


public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }

    public static boolean isTaggedAsContainer(BlockState state) {
        var stack = state.getBlock().asItem().getDefaultStack();
        return state.isIn(ConventionalBlockTags.CHESTS) || state.isIn(BlockTags.GUARDED_BY_PIGLINS)
        //? if >1.20 {
         || stack.isIn(ConventionalItemTags.CHESTS)
         //?}
        //? if >1.21 {
        || state.isIn(ConventionalBlockTags.BARRELS)  || stack.isIn(ConventionalItemTags.BARRELS)
        //?}
        ;
    }


    public static boolean isLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

}
