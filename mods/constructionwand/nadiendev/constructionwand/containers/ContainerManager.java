package nadiendev.constructionwand.containers;

import java.util.ArrayList;
import nadiendev.constructionwand.api.IContainerHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ContainerManager {
   private final ArrayList<IContainerHandler> handlers = new ArrayList<>();

   public boolean register(IContainerHandler handler) {
      return this.handlers.add(handler);
   }

   public boolean hasHandler(Player player, ItemStack itemStack, ItemStack inventoryStack) {
      for (IContainerHandler handler : this.handlers) {
         if (handler.matches(player, itemStack, inventoryStack)) {
            return true;
         }
      }

      return false;
   }

   public int countItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack) {
      for (IContainerHandler handler : this.handlers) {
         if (handler.matches(player, itemStack, inventoryStack)) {
            return handler.countItems(player, trace, itemStack, inventoryStack);
         }
      }

      return 0;
   }

   public int useItems(Player player, ContainerTrace trace, ItemStack itemStack, ItemStack inventoryStack, int count) {
      for (IContainerHandler handler : this.handlers) {
         if (handler.matches(player, itemStack, inventoryStack)) {
            int prevCount = count;
            int remainingCount = handler.useItems(player, trace, itemStack, inventoryStack, count);
            if (remainingCount < prevCount) {
               player.getInventory().setChanged();
            }

            return remainingCount;
         }
      }

      return count;
   }
}
