package net.neonmarko2.skeletonmessenger.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.neonmarko2.skeletonmessenger.WhistleKeyringInventory;

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
                            new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X2, syncId, playerInventory, inventory, 2),
                    getName()
            ));
            return TypedActionResult.success(user.getStackInHand(hand), false);
        }
        return TypedActionResult.success(user.getStackInHand(hand), false);
    }
}
