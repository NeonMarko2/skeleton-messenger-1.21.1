package net.neonmarko2.skeletonmessenger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.neonmarko2.skeletonmessenger.component.ModDataComponentTypes;

import java.util.List;

public class WhistleKeyringInventory implements Inventory {
    public ItemStack keyring;
    public List<ItemStack> inventory;

    public WhistleKeyringInventory(ItemStack keyring){
        this.keyring = keyring;
        List<ItemStack> stored = keyring.getOrDefault(ModDataComponentTypes.WHISTLE_KEYRING_ITEMS, List.of());
        inventory = DefaultedList.ofSize(18, ItemStack.EMPTY);

        for (int i = 0; i < stored.size() && i < inventory.size(); i++) {
            inventory.set(i, stored.get(i));
        }
        ///inventory = keyring.get(ModDataComponentTypes.WHISTLE_KEYRING_ITEMS);
//        if(inventory == null){
//            inventory = DefaultedList.ofSize(18, ItemStack.EMPTY);
//        }
    }

    @Override
    public int size() {
        return 18;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack removedItem = inventory.get(slot).copy();
        inventory.set(slot, ItemStack.EMPTY);
        markDirty();
        return removedItem;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack removedItem = inventory.get(slot).copy();
        inventory.set(slot, ItemStack.EMPTY);
        markDirty();
        return removedItem;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
        markDirty();
    }

    @Override
    public void markDirty() {
        keyring.set(ModDataComponentTypes.WHISTLE_KEYRING_ITEMS, inventory);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {

    }
}
