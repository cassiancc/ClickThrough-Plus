package cc.cassian.clickthrough.neoforge;

//? if neoforge {

/*import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.helpers.ModHelpers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.ModLists;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.GameShuttingDownEvent;

import static cc.cassian.clickthrough.ClickThrough.*;


@Mod(value = ClickThrough.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = ClickThrough.MOD_ID)
public final class ClickthroughNeoForge {
    public ClickthroughNeoForge(IEventBus eventBus, ModContainer container) {
        // Run our common setup.
        ClickThrough.init();
    }

    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        //? if >1.21.8
        /^event.registerCategory(ClickThrough.CATEGORY);^/
        event.register(onoff);
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Pre event) {
        ModHelpers.handleKeybind(null);
    }

}

*///?}