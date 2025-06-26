package cc.cassian.clickthrough.helpers.neoforge;

import cc.cassian.clickthrough.ArchitecturyImpl;
import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.Tags;

import static cc.cassian.clickthrough.ClickThrough.*;

public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return ModList.get().isLoaded("cloth_config");
    }

    public static void registerKeybind() {
        if (isLoaded("architectury")) {
            ArchitecturyImpl.load();
        } else {
            LOGGER.info("ClickThrough Plus running without Architectury. Keybinds are not avaialble!");
        }
    }

    public static boolean isTaggedAsContainer(BlockState state) {
        var stack = state.getBlock().asItem().getDefaultStack();
        return state.isIn(Tags.Blocks.CHESTS) || state.isIn(Tags.Blocks.BARRELS)
                || stack.isIn(Tags.Items.CHESTS)  || stack.isIn(Tags.Items.BARRELS)
                || state.isIn(BlockTags.GUARDED_BY_PIGLINS);
    }
    public static boolean isLoaded(String mod) {
        return ModList.get().isLoaded(mod);
    }
}
