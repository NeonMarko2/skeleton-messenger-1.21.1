package net.neonmarko2.skeletonmessenger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.neonmarko2.skeletonmessenger.entity.ModEntities;
import net.neonmarko2.skeletonmessenger.entity.client.SkeletonMessengerModel;
import net.neonmarko2.skeletonmessenger.entity.client.SkeletonMessengerRenderer;
import net.neonmarko2.skeletonmessenger.screenHandlers.KeyringScreen;
import net.neonmarko2.skeletonmessenger.screenHandlers.ModScreenHandlers;
import org.lwjgl.glfw.GLFW;

public class SkeletonMessengerClient implements ClientModInitializer {
    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {

        if(FabricLoader.getInstance().isModLoaded("trinkets")){
            keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    "key.skeletonmessenger.access_keyring",
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_V,
                    "category.skeletonmessenger"
            ));
        }

//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            while (keyBinding.wasPressed()) {
//                ///client.interactionManager.interactItem()
//            }
//        });

        HandledScreens.register(ModScreenHandlers.KEYRING_SCREEN_HANDLER, KeyringScreen::new);
        EntityModelLayerRegistry.registerModelLayer(SkeletonMessengerModel.SKELETON_MESSENGER, SkeletonMessengerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SKELETON_MESSENGER, SkeletonMessengerRenderer::new);
    }
}
