package com.tno_tensura_extras.event;

import com.tno_tensura_extras.entity.DiabloEntity;
import com.tno_tensura_extras.entity.DiabloTargeting;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.Projectile;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.Nullable;

public final class DiabloCombatEvents {
   private DiabloCombatEvents() {
   }

   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (!event.getEntity().level().isClientSide()) {
         LivingEntity attacker = findAttacker(event.getSource().getEntity());
         if (attacker == null) {
            attacker = findAttacker(event.getSource().getDirectEntity());
         }

         if (attacker != null) {
            LivingEntity victim = event.getEntity();
            if (attacker instanceof DiabloEntity diablo && !diablo.isAllowedCombatTarget(victim)) {
               event.setCanceled(true);
            } else if (!event.isCanceled() && !(event.getOriginalAmount() <= 0.0F)) {
               if (DiabloTargeting.isProtectedRimuru(victim)) {
                  for (DiabloEntity guardian : victim.level().getEntitiesOfClass(DiabloEntity.class, victim.getBoundingBox().inflate(64.0))) {
                     guardian.respondToThreat(attacker, true);
                  }
               }
            }
         }
      }
   }

   @Nullable
   public static LivingEntity findAttacker(@Nullable Entity source) {
      for (int depth = 0; source != null && depth < 8; depth++) {
         if (source instanceof LivingEntity living) {
            return living;
         }

         if (source instanceof Projectile projectile) {
            source = projectile.getOwner();
         } else if (source instanceof PrimedTnt tnt) {
            source = tnt.getOwner();
         } else {
            if (!(source instanceof OwnableEntity ownable)) {
               return null;
            }

            source = ownable.getOwner();
         }
      }

      return null;
   }
}
