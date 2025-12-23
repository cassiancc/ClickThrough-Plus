package cc.cassian.clickthrough.config;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

import java.util.ArrayList;
import java.util.List;

public class ModConfig extends WrappedConfig {

    //General settings
    public int version = 0;
    public boolean isActive = true;
    public boolean onlycontainers = true;
    public boolean sneaktodye = false;
    public boolean displayActiveTextAsTitle = true;
    public ValueList<String> containers = ValueList.create("minecraft:ender_chest", "minecraft:vault", "minecraft:composter", "minecraft:respawn_anchor", "minecraft:jukebox", "minecraft:decorated_pot", "minecraft:chiseled_bookshelf", "minecraft:beacon", "minecraft:stonecutter", "minecraft:grindstone", "minecraft:crafting_table");



}