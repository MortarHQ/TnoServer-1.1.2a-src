package com.github.wal_bos.tensuramorecult.mixin;

import com.benji.netherman.events.AzazelTrophyEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = AzazelTrophyEvents.class, remap = false)
public abstract class MaskComponentsMixin {
   @Redirect(
      method = {"onPlayerDeath", "onPlayerTick"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/server/level/ServerPlayer;setItemSlot(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)V"
      )
   )
   private static void tensuramorecult$preserveGear(ServerPlayer player, EquipmentSlot slot, ItemStack replacement) {
      ItemStack previous = player.getItemBySlot(slot);
      CompoundTag data = ((CustomData)previous.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      data.merge(((CustomData)replacement.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag());
      replacement.applyComponents(previous.getComponentsPatch());
      replacement.set(DataComponents.CUSTOM_DATA, CustomData.of(data));
      player.setItemSlot(slot, replacement);
   }
}
