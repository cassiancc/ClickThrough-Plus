package cc.cassian.clickthrough.fabric;

//? if fabric {

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.ModLists;
import cc.cassian.clickthrough.helpers.ModHelpers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import static cc.cassian.clickthrough.ClickThrough.*;
import static cc.cassian.clickthrough.ClickThrough.setActive;

public final class ClickthroughFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ClickThrough.init();
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraftClient -> ModLists.loadLists()));
        KeyBindingHelper.registerKeyBinding(onoff);
        ClientTickEvents.END_CLIENT_TICK.register(ModHelpers::handleKeybind);
    }

}

//?}