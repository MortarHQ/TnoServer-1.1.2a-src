package dev.ftb.mods.ftbxmodcompat.neoforge.ftbquests.kubejs;

import dev.ftb.mods.ftbquests.api.event.CustomFilterDisplayItemsEvent;
import dev.latvian.mods.kubejs.event.KubeEvent;
import java.util.Collection;
import net.minecraft.world.item.ItemStack;

public class CustomFilterItemKubeEvent implements KubeEvent {
   private final CustomFilterDisplayItemsEvent event;

   public CustomFilterItemKubeEvent(CustomFilterDisplayItemsEvent event) {
      this.event = event;
   }

   public void addStack(ItemStack stack) {
      this.event.add(stack);
   }

   public void addStacks(Collection<ItemStack> stacks) {
      this.event.add(stacks);
   }
}
