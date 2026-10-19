package nadiendev.constructionwand.events;

import nadiendev.constructionwand.items.containeritems.ItemVoidSack;
import nadiendev.constructionwand.wand.action.ActionDestruction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class VoidSackDropHandler {
   @SubscribeEvent(priority = EventPriority.HIGH)
   public void onEntityJoinLevel(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide()) {
         if (event.getEntity() instanceof ItemEntity itemEntity) {
            ActionDestruction.VoidSackDropContext ctx = ActionDestruction.ACTIVE_CONTEXT.get();
            if (ctx != null) {
               ServerLevel level = ctx.level();
               ItemStack sack = ctx.sack();
               if (!sack.isEmpty() && sack.getItem() instanceof ItemVoidSack) {
                  ItemStack drop = itemEntity.getItem().copy();
                  if (!drop.isEmpty()) {
                     int remaining = ItemVoidSack.receive(level, sack, drop);
                     if (remaining <= 0) {
                        event.setCanceled(true);
                     } else if (remaining < drop.getCount()) {
                        itemEntity.getItem().setCount(remaining);
                     }
                  }
               }
            }
         }
      }
   }
}
