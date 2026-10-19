package dev.ftb.mods.ftbxmodcompat.neoforge.ftbfiltersystem.kubejs;

import dev.latvian.mods.kubejs.event.KubeEvent;
import net.minecraft.world.item.ItemStack;

public record CustomFilterKubeEvent(ItemStack stack, String data) implements KubeEvent {
   public ItemStack getStack() {
      return this.stack;
   }

   public String getData() {
      return this.data;
   }
}
