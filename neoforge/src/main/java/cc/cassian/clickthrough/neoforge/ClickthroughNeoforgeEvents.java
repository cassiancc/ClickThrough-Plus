package cc.cassian.clickthrough.neoforge;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.ModLists;
import net.minecraft.client.gui.Click;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.GameShuttingDownEvent;

import static cc.cassian.clickthrough.ClickThrough.*;

@EventBusSubscriber(modid = ClickThrough.MOD_ID)
public class ClickthroughNeoforgeEvents {
    @SubscribeEvent
    public static void loadComplete(FMLClientSetupEvent event) {
        ModLists.loadLists();
    }

    @SubscribeEvent
    public static void saveConfig(GameShuttingDownEvent event) {
        ModConfig.save();
    }

    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.registerCategory(ClickThrough.CATEGORY);
        event.register(ClickThrough.onoff);
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Pre event) {
        while (onoff.wasPressed()) {
            if (ModConfig.get().isActive) {
                setInActive();
            } else {
                setActive();
            }
        }
    }
}
