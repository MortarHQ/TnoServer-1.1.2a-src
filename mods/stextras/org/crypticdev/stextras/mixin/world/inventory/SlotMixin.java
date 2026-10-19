package org.crypticdev.stextras.mixin.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.stextras.event.MenuOutputTakeTracker;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slot.class)
public abstract class SlotMixin {
   private static final String MINING_STATION_OUTPUT_SLOT = "io.github.manasmods.tensura.menu.MiningStationMenu$1";
   private static final String WOODCUTTER_OUTPUT_SLOT = "io.github.manasmods.tensura.menu.WoodcutterMenu$2";

   @Inject(method = "onTake(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;)V", at = @At("TAIL"))
   private void stextras$trackTensuraOutputTake(Player player, ItemStack stack, CallbackInfo ci) {
      if (!MenuOutputTakeTracker.isSuppressingSlotEvents()) {
         if (player instanceof ServerPlayer sp && stack != null && !stack.isEmpty()) {
            String slotClass = this.getClass().getName();
            if ("io.github.manasmods.tensura.menu.MiningStationMenu$1".equals(slotClass)) {
               STPlayerEvents.fireMiningStation(sp, stack.copy());
            } else if ("io.github.manasmods.tensura.menu.WoodcutterMenu$2".equals(slotClass)) {
               STPlayerEvents.fireWoodcut(sp, stack.copy());
            }
         }
      }
   }
}
