package cc.cassian.clickthrough;

//? fabric {
import cc.cassian.clickthrough.fabric.FabricPlatformImpl;
//?}
import net.minecraft.world.item.ItemStack;
import java.io.File;
import java.nio.file.Path;
//? neoforge {
/*import cc.cassian.clickthrough.neoforge.NeoForgePlatformImpl;
*///?}

public interface Platform {

    //? fabric {
    Platform INSTANCE = new FabricPlatformImpl();
    //?}
    //? neoforge {
    /*Platform INSTANCE = new NeoForgePlatformImpl();
    *///?}


    boolean isLoaded(String modid);
    boolean isLoadingLoaded(String mod);
    String loader();
    Path configPath();
}
