package net.neonmarko2.skeletonmessenger.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.neonmarko2.skeletonmessenger.screenHandlers.KeyringScreenHandler;
import net.neonmarko2.skeletonmessenger.WhistleKeyringInventory;
import net.neonmarko2.skeletonmessenger.screenHandlers.ModScreenHandlers;

public class WhistleKeyring extends Item {

    public WhistleKeyring(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            WhistleKeyringInventory inventory = new WhistleKeyringInventory(user.getStackInHand(hand));
            user.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                    (syncId, playerInventory, player) ->
                            new KeyringScreenHandler(ModScreenHandlers.KEYRING_SCREEN_HANDLER, syncId, playerInventory, inventory),
                    getName()
            ));
            return TypedActionResult.success(user.getStackInHand(hand), false);
        }
        return TypedActionResult.success(user.getStackInHand(hand), false);
    }
}