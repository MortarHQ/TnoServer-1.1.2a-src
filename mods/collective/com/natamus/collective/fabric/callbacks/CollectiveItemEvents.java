package com.natamus.collective.fabric.callbacks;

import java.util.List;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1268;
import net.minecraft.class_1536;
import net.minecraft.class_1542;
import net.minecraft.class_1657;
import net.minecraft.class_1799;

public class CollectiveItemEvents {
   public static final Event<CollectiveItemEvents.Item_Expire> ON_ITEM_EXPIRE = EventFactory.createArrayBacked(
      CollectiveItemEvents.Item_Expire.class, callbacks -> (itemEntity, itemStack) -> {
         for (CollectiveItemEvents.Item_Expire callback : callbacks) {
            callback.onItemExpire(itemEntity, itemStack);
         }
      }
   );
   public static final Event<CollectiveItemEvents.Item_Fished> ON_ITEM_FISHED = EventFactory.createArrayBacked(
      CollectiveItemEvents.Item_Fished.class, callbacks -> (loot, hook) -> {
         for (CollectiveItemEvents.Item_Fished callback : callbacks) {
            callback.onItemFished(loot, hook);
         }
      }
   );
   public static final Event<CollectiveItemEvents.Item_Tossed> ON_ITEM_TOSSED = EventFactory.createArrayBacked(
      CollectiveItemEvents.Item_Tossed.class, callbacks -> (player, itemStack) -> {
         for (CollectiveItemEvents.Item_Tossed callback : callbacks) {
            callback.onItemTossed(player, itemStack);
         }
      }
   );
   public static final Event<CollectiveItemEvents.Item_Destroyed> ON_ITEM_DESTROYED = EventFactory.createArrayBacked(
      CollectiveItemEvents.Item_Destroyed.class, callbacks -> (player, itemStack, hand) -> {
         for (CollectiveItemEvents.Item_Destroyed callback : callbacks) {
            callback.onItemDestroyed(player, itemStack, hand);
         }
      }
   );
   public static final Event<CollectiveItemEvents.Item_Use_Finished> ON_ITEM_USE_FINISHED = EventFactory.createArrayBacked(
      CollectiveItemEvents.Item_Use_Finished.class, callbacks -> (player, usedItem, newItem, hand) -> {
         for (CollectiveItemEvents.Item_Use_Finished callback : callbacks) {
            class_1799 changedStack = callback.onItemUsedFinished(player, usedItem, newItem, hand);
            if (changedStack != null) {
               return changedStack;
            }
         }

         return null;
      }
   );

   private CollectiveItemEvents() {
   }

   @FunctionalInterface
   public interface Item_Destroyed {
      void onItemDestroyed(class_1657 var1, class_1799 var2, class_1268 var3);
   }

   @FunctionalInterface
   public interface Item_Expire {
      void onItemExpire(class_1542 var1, class_1799 var2);
   }

   @FunctionalInterface
   public interface Item_Fished {
      void onItemFished(List<class_1799> var1, class_1536 var2);
   }

   @FunctionalInterface
   public interface Item_Tossed {
      void onItemTossed(class_1657 var1, class_1799 var2);
   }

   @FunctionalInterface
   public interface Item_Use_Finished {
      class_1799 onItemUsedFinished(class_1657 var1, class_1799 var2, class_1799 var3, class_1268 var4);
   }
}
