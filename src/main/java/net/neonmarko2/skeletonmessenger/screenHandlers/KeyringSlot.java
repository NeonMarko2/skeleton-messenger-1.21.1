package net.neonmarko2.skeletonmessenger.screenHandlers;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.neonmarko2.skeletonmessenger.SkeletonMessenger;
import net.neonmarko2.skeletonmessenger.item.ModItems;

public class KeyringSlot extends Slot {
    public KeyringSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() == ModItems.SKELETON_WHISTLE;
    }
}
