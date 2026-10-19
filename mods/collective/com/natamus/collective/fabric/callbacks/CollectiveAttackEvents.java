package com.natamus.collective.fabric.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1271;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;

public class CollectiveAttackEvents {
   public static final Event<CollectiveAttackEvents.On_Arrow_Nock> ON_ARROW_NOCK = EventFactory.createArrayBacked(
      CollectiveAttackEvents.On_Arrow_Nock.class, callbacks -> (item, level, player, hand, hasAmmo) -> {
         for (CollectiveAttackEvents.On_Arrow_Nock callback : callbacks) {
            class_1271<class_1799> resultHolder = callback.onArrowNock(item, level, player, hand, hasAmmo);
            if (!resultHolder.method_5467().equals(class_1269.field_5811)) {
               return resultHolder;
            }
         }

         return class_1271.method_22430(item);
      }
   );

   private CollectiveAttackEvents() {
   }

   @FunctionalInterface
   public interface On_Arrow_Nock {
      class_1271<class_1799> onArrowNock(class_1799 var1, class_1937 var2, class_1657 var3, class_1268 var4, boolean var5);
   }
}
