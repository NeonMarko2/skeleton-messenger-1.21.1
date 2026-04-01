package net.neonmarko2.skeletonmessenger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.neonmarko2.skeletonmessenger.entity.ModEntities;
import net.neonmarko2.skeletonmessenger.entity.client.SkeletonMessengerModel;
import net.neonmarko2.skeletonmessenger.entity.client.SkeletonMessengerRenderer;
import net.neonmarko2.skeletonmessenger.screenHandlers.KeyringScreen;
import net.neonmarko2.skeletonmessenger.screenHandlers.ModScreenHandlers;

public class SkeletonMessengerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlers.KEYRING_SCREEN_HANDLER, KeyringScreen::new);
        EntityModelLayerRegistry.registerModelLayer(SkeletonMessengerModel.SKELETON_MESSENGER, SkeletonMessengerModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.SKELETON_MESSENGER, SkeletonMessengerRenderer::new);
    }
}
