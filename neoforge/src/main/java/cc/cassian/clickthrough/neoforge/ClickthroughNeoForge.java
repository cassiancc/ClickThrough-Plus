package cc.cassian.clickthrough.neoforge;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.ModLists;
import cc.cassian.clickthrough.config.neoforge.ModConfigFactory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.GameShuttingDownEvent;

import static cc.cassian.clickthrough.helpers.ModHelpers.clothConfigInstalled;


@Mod(ClickThrough.MOD_ID)
public final class ClickthroughNeoForge {
    public ClickthroughNeoForge(IEventBus eventBus, ModContainer container) {
        if (FMLEnvironment.dist.isClient()) {
            // Run our common setup.
            ClickThrough.init();
            registerModsPage();
            eventBus.addListener(ClickthroughNeoForge::loadComplete);
            NeoForge.EVENT_BUS.addListener(ClickthroughNeoForge::saveConfig);
        }
    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    @SubscribeEvent
    public static void saveConfig(GameShuttingDownEvent event) {
        ModConfig.save();
    }

    //Integrate Cloth Config screen (if mod present) with NeoForge mod menu.
    public void registerModsPage() {
        if (clothConfigInstalled()) ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, ModConfigFactory::new);
    }

}
