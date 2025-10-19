package cc.cassian.clickthrough.helpers.fabric;

//? if >1.21 {

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
//?} else {
/*import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
 *///?}
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import cc.cassian.clickthrough.config.ModConfig;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.tags.BlockTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.block.state.BlockState;


import static cc.cassian.clickthrough.ClickThrough.*;


public class ModHelpersImpl {
    public static boolean clothConfigInstalled() {
        return FabricLoader.getInstance().isModLoaded("cloth-config");
    }

    public static void registerKeybind() {
        KeyBindingHelper.registerKeyBinding(onoff);
        ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
            while (onoff.isDown()) {
                if (ModConfig.get().isActive) {
                    setInActive();
                } else {
                    setActive();
                }
            }
        });
    }

    public static boolean isTaggedAsContainer(BlockState state) {
        var stack = state.getBlock().asItem().getDefaultInstance();
        return state.is(ConventionalBlockTags.CHESTS) || state.is(BlockTags.GUARDED_BY_PIGLINS)
        //? if >1.20 {
         || stack.is(ConventionalItemTags.CHESTS)
         //?}
        //? if >1.21 {
        || state.is(ConventionalBlockTags.BARRELS)  || stack.is(ConventionalItemTags.BARRELS)
        //?}
        ;
    }


    public static boolean isLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

}
