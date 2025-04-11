package cc.cassian.clickthrough.forge;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.forge.ModConfigFactory;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.GameShuttingDownEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(ClickThrough.MOD_ID)
public final class ClickthroughForge {
    public ClickthroughForge() {
        // Run our common setup.
        ClickThrough.init();
        registerModsPage();
        MinecraftForge.EVENT_BUS.addListener(ClickthroughForge::saveConfig);
    }

    @SubscribeEvent
    public static void saveConfig(GameShuttingDownEvent event) {
        ModConfig.save();
    }

    //Integrate Cloth Config screen (if mod present) with Forge mod menu.
    public void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory(ModConfigFactory::createScreen));
    }


}
