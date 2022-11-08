package leaf.cosmere.sandmastery.client;

import leaf.cosmere.sandmastery.common.Sandmastery;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import static leaf.cosmere.api.Constants.Strings.*;
import static leaf.cosmere.api.Constants.Strings.KEYS_CATEGORY;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Sandmastery.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SandmasteryKeybindings {

    public static KeyMapping SANDMASTERY_LAUNCH;
    public static KeyMapping SANDMASTERY_ELEVATE;

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event)
    {
        event.register(SANDMASTERY_LAUNCH = new KeyMapping(KEY_SANDMASTERY_LAUNCH, GLFW.GLFW_INVALID_VALUE, KEYS_CATEGORY));
        event.register(SANDMASTERY_ELEVATE = new KeyMapping(KEY_SANDMASTERY_ELEVATE, GLFW.GLFW_INVALID_VALUE, KEYS_CATEGORY));
    }
}
