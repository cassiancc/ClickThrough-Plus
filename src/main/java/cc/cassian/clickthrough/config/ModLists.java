package cc.cassian.clickthrough.config;

//? if >1.20 {
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
//?} else {
/*import net.minecraft.registry.Registry;
 *///?}
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;


import java.util.ArrayList;
import java.util.Optional;

public class ModLists {
    public static ArrayList<Block> containers = new ArrayList<>();


    public static void loadLists() {
        //? if >1.20 {
        var registry = Registries.BLOCK;
        //?} else {
        /*var registry = Registry.BLOCK;
         *///?}
        containers = new ArrayList<>();
        for (String compassItem : ModConfig.get().containers) {
            Optional<Block> item = registry.getOrEmpty(Identifier.tryParse(compassItem));
            item.ifPresent(value -> containers.add(value));
        }
    }

}