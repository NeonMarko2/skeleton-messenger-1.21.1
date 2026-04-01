package net.neonmarko2.skeletonmessenger.item.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.neonmarko2.skeletonmessenger.component.ModDataComponentTypes;
import net.neonmarko2.skeletonmessenger.entity.ModEntities;
import net.neonmarko2.skeletonmessenger.entity.custom.SkeletonMessengerEntity;
import net.neonmarko2.skeletonmessenger.item.ModItems;

import java.util.List;

public class SkeletonWhistleItem extends Item {
    public SkeletonWhistleItem(Settings settings) {
        super(settings);
    }

    public final int WHISTLE_COOLDOWN = 20*60;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if(!world.isClient()){
            ActivateWhistle(itemStack, world, user);
        }
        return TypedActionResult.success(itemStack, false);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (player.currentScreenHandler instanceof GenericContainerScreenHandler &&
            !player.getInventory().contains(stack) &&
            clickType == ClickType.RIGHT){
            if(!player.getWorld().isClient){
                ActivateWhistle(stack, player.getWorld(), player);
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
                serverPlayerEntity.closeHandledScreen();
            }
            return true;
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    public void ActivateWhistle(ItemStack whistle, World world, PlayerEntity user){
        var player_uuid = whistle.get(ModDataComponentTypes.OWNER_UUID);
        if(user.getItemCooldownManager().isCoolingDown(ModItems.SKELETON_WHISTLE)){
            return;
        }
        if(player_uuid == null){
            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.PLAYERS);
            whistle.set(ModDataComponentTypes.OWNER_UUID, user.getUuid());
            whistle.set(ModDataComponentTypes.OWNER_NAME, user.getName().getString());
            user.sendMessage(Text.translatable("skeletonmessenger.popup.firstuse"), true);
        }else{
            if(world.getServer().getPlayerManager().getPlayer(player_uuid) == null){
                user.sendMessage(Text.translatable("skeletonmessenger.popup.offline"), true);
                return;
            }
            var whistle_owner = world.getServer().getPlayerManager().getPlayer(player_uuid);
            SkeletonMessengerEntity skeletonMessenger = new SkeletonMessengerEntity(ModEntities.SKELETON_MESSENGER, world);
            skeletonMessenger.SpawnInfrontOf(user);
            skeletonMessenger.caller = user;
            skeletonMessenger.owner = whistle_owner;
            world.spawnEntity(skeletonMessenger);
            world.playSound(null, user.getBlockPos(), SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.PLAYERS, 1, 1);
            world.playSound(null, user.getBlockPos(), SoundEvents.AMBIENT_CAVE.value(), SoundCategory.PLAYERS, 1, 1);
            whistle_owner.getWorld().playSound(null, whistle_owner.getBlockPos(), SoundEvents.AMBIENT_CAVE.value(), SoundCategory.PLAYERS, 1, 1);
            whistle_owner.sendMessage(Text.translatable("skeletonmessenger.popup.summoned"), true);
            user.getItemCooldownManager().set(this, WHISTLE_COOLDOWN); /// MAKE THIS CONFIGURABLE IN GAME
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20*5, 1));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.get(ModDataComponentTypes.OWNER_UUID) != null){
            if (stack.get(ModDataComponentTypes.OWNER_NAME) == null){
                tooltip.add(Text.translatable("tooltip.skeletonmessenger.skeleton_whistle.name_fetch_fail"));
            }else{
                tooltip.add(Text.literal(stack.get(ModDataComponentTypes.OWNER_NAME)));
                var player = MinecraftClient.getInstance().player;
                if(player.currentScreenHandler instanceof GenericContainerScreenHandler && !player.getInventory().contains(stack)){
                    tooltip.add(Text.literal("§2Right click to use!"));
                }
            }
        }else{
            tooltip.add(Text.translatable("tooltip.skeletonmessenger.skeleton_whistle.unbound"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}
