package nadiendev.constructionwand.events;

import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent.Pre;

public class VoidSackPickupHandler {
   @SubscribeEvent
   public void onItemPickup(Pre event) {
      Player player = event.getPlayer();
      if (player instanceof ServerPlayer sp) {
         if (player.level() instanceof ServerLevel level) {
            ItemStack sack = findActiveSack(sp);
            if (sack != null) {
               ItemStack picked = event.getItemEntity().getItem().copy();
               if (!picked.isEmpty()) {
                  int originalCount = picked.getCount();
                  int remaining = ItemVoidSack.interceptPickup(level, sack, picked);
                  if (remaining < originalCount) {
                     int absorbed = originalCount - remaining;
                     ItemStack entityStack = event.getItemEntity().getItem();
                     entityStack.shrink(absorbed);
                     if (remaining == 0) {
                        event.setCanPickup(TriState.FALSE);
                        if (event.getItemEntity().getItem().isEmpty()) {
                           event.getItemEntity().discard();
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static ItemStack findActiveSack(ServerPlayer player) {
      for (InteractionHand hand : InteractionHand.values()) {
         ItemStack stack = player.getItemInHand(hand);
         if (stack.getItem() instanceof ItemVoidSack && ItemVoidSack.isActive(stack)) {
            return stack;
         }
      }

      return null;
   }
}
