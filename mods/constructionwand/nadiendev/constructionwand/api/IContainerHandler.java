package nadiendev.constructionwand.api;

import nadiendev.constructionwand.containers.ContainerTrace;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface IContainerHandler {
   boolean matches(Player var1, ItemStack var2, ItemStack var3);

   default int getSignature(Player player, ItemStack inventoryStack) {
      return System.identityHashCode(inventoryStack);
   }

   int countItems(Player var1, ContainerTrace var2, ItemStack var3, ItemStack var4);

   int useItems(Player var1, ContainerTrace var2, ItemStack var3, ItemStack var4, int var5);
}
