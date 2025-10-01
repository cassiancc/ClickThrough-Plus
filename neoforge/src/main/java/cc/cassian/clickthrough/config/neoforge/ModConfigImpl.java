package cc.cassian.clickthrough.config.neoforge;

import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

import static cc.cassian.clickthrough.ClickThrough.MOD_ID;

public class ModConfigImpl {
    public static Path configPath() {
        return FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".json");
    }
}
