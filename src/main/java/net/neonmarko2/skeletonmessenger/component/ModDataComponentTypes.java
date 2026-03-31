package net.neonmarko2.skeletonmessenger.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.collection.DefaultedList;
import net.neonmarko2.skeletonmessenger.SkeletonMessenger;

import java.util.List;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<UUID> OWNER_UUID = register("owner_uuid", builder -> builder.codec(Uuids.CODEC));
    public static final ComponentType<String> OWNER_NAME = register("owner_name", builder -> builder.codec(Codec.STRING));
    public static final ComponentType<List<ItemStack>> WHISTLE_KEYRING_ITEMS = register("whistle_keyring_items", builder -> builder.codec(ItemStack.OPTIONAL_CODEC.listOf()));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderUnaryOperator){
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(SkeletonMessenger.MOD_ID, name),
                builderUnaryOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponents(){
        SkeletonMessenger.LOGGER.info("Registering data component types for " + SkeletonMessenger.MOD_ID);
    }
}
