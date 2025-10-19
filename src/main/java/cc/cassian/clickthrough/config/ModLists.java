package cc.cassian.clickthrough.config;

//? if >1.20 {
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.util.registry.Registry;
 *///?}
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Optional;

public class ModLists {
    public static ArrayList<Block> containers = new ArrayList<>();


    public static void loadLists() {
        //? if >1.20 {
        var registry = BuiltInRegistries.BLOCK;
        //?} else {
        /*var registry = Registry.BLOCK;
         *///?}
        containers = new ArrayList<>();
        for (String compassItem : ModConfig.get().containers) {
            Optional<Block> item = registry.
            //? if >1.21.2 {
            getOptional
            //?} else {
            /*getOrEmpty
             *///?}
            (ResourceLocation.tryParse(compassItem));
            item.ifPresent(value -> containers.add(value));
        }
    }

}