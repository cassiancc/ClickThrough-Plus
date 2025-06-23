package cc.cassian.clickthrough.client.fabric;

import cc.cassian.clickthrough.ClickThrough;
import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.config.ModLists;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public final class ClickthroughFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ClickThrough.init();
        ClientLifecycleEvents.CLIENT_STARTED.register((minecraftClient -> ModLists.loadLists()));
        ClientLifecycleEvents.CLIENT_STOPPING.register(minecraftClient -> ModConfig.save());
    }

}
