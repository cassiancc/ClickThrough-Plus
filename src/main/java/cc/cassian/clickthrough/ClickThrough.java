package cc.cassian.clickthrough;

import cc.cassian.clickthrough.config.ModConfig;
import cc.cassian.clickthrough.helpers.ModHelpers;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClickThrough
{
    static public final String MOD_ID = "clickthrough";
    static public final String MOD_NAME = "ClickThrough";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final ModConfig CONFIG = ModConfig.createToml(Platform.INSTANCE.configPath(), "", ClickThrough.MOD_ID, ModConfig.class);
    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("clickthrough", "keybinds")); // The category translation key used to categorize in the Controls screen

    public static void init() {

    }

    static public boolean isDyeOnSign = false;

    // A key mapping with keyboard as the default
    public static final KeyMapping onoff = new KeyMapping(
            "key.clickthrough.toggle", // The translation key of the name shown in the Controls screen
            InputConstants.Type.KEYSYM, // This key mapping is for Keyboards by default
            InputConstants.KEY_F9, // The default keycode
            CATEGORY
    );

    public static void setActive(boolean active) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            var translatable = active ? Component.translatable("clickthrough.msg.active") : Component.translatable("clickthrough.msg.inactive");
            if (ClickThrough.CONFIG.displayActiveTextAsTitle) {
                player.sendOverlayMessage(translatable);
            } else {
                player.sendSystemMessage(translatable);
            }
        }
        CONFIG.isActive = active;
    }

}
