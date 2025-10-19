package cc.cassian.clickthrough.neoforge;

//? neoforge {

/*import cc.cassian.clickthrough.Platform;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class NeoForgePlatformImpl implements Platform {

    @Override
    public boolean isLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public String loader() {
        return "fabric";
    }

    public boolean isLoadingLoaded(String mod) {
        return isLoaded(mod);
    }

    @Override
    public Path configPath() {
        return FMLPaths.CONFIGDIR.get();
    }
}
*///?}