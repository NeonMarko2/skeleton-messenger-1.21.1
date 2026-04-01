package net.neonmarko2.skeletonmessenger.screenHandlers;


import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.neonmarko2.skeletonmessenger.SkeletonMessenger;

public class ModScreenHandlers{
    public static final ScreenHandlerType<KeyringScreenHandler> KEYRING_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            Identifier.of(SkeletonMessenger.MOD_ID, "keyring_screen_handler"),
            new ScreenHandlerType<>(KeyringScreenHandler::new, FeatureSet.empty())
    );
    public static void registerModScreens(){
        SkeletonMessenger.LOGGER.info("Registering mod screen handlers for " + SkeletonMessenger.MOD_ID);
    }
}