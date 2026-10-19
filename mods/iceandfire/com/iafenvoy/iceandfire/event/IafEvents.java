package com.iafenvoy.iceandfire.event;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.uranus.event.Event;
import net.minecraft.world.entity.LivingEntity;

public final class IafEvents {
   public static final Event<IafEvents.GriefBreakBlock> ON_GRIEF_BREAK_BLOCK = new Event(callbacks -> (griefer, targetX, targetY, targetZ) -> {
      for (IafEvents.GriefBreakBlock callback : callbacks) {
         if (callback.onBreakBlock(griefer, targetX, targetY, targetZ)) {
            return true;
         }
      }

      return false;
   });
   public static final Event<IafEvents.DragonFireDamageWorld> ON_DRAGON_DAMAGE_BLOCK = new Event(callbacks -> (dragonBase, targetX, targetY, targetZ) -> {
      for (IafEvents.DragonFireDamageWorld callback : callbacks) {
         if (callback.onDamageBlock(dragonBase, targetX, targetY, targetZ)) {
            return true;
         }
      }

      return false;
   });
   public static final Event<IafEvents.DragonFire> ON_DRAGON_FIRE_BLOCK = new Event(callbacks -> (dragonBase, targetX, targetY, targetZ) -> {
      for (IafEvents.DragonFire callback : callbacks) {
         if (callback.onFireBlock(dragonBase, targetX, targetY, targetZ)) {
            return true;
         }
      }

      return false;
   });

   @FunctionalInterface
   public interface DragonFire {
      boolean onFireBlock(DragonBaseEntity var1, double var2, double var4, double var6);
   }

   @FunctionalInterface
   public interface DragonFireDamageWorld {
      boolean onDamageBlock(DragonBaseEntity var1, double var2, double var4, double var6);
   }

   @FunctionalInterface
   public interface GriefBreakBlock {
      boolean onBreakBlock(LivingEntity var1, double var2, double var4, double var6);
   }
}
