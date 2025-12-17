package cc.cassian.clickthrough.config;

//? if >1.20 {
import cc.cassian.clickthrough.ClickThrough;
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.util.registry.Registry;
 *///?}
import net.minecraft.resources.Identifier;
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
        for (String compassItem : ClickThrough.CONFIG.containers) {
            Optional<Block> item = registry.getOptional(Identifier.tryParse(compassItem));
            item.ifPresent(value -> containers.add(value));
        }
    }

}