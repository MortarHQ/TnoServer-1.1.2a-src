package com.cerbon.bosses_of_mass_destruction.entity.ai;

import com.cerbon.bosses_of_mass_destruction.entity.damage.DamageMemory;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TargetSwitcher {
   private final Mob entity;
   private final DamageMemory damageMemory;
   private final int ageToRemember = 600;

   public TargetSwitcher(Mob entity, DamageMemory damageMemory) {
      this.entity = entity;
      this.damageMemory = damageMemory;
   }

   public void trySwitchTarget() {
      Entity newTarget = this.damageMemory
         .getDamageHistory()
         .stream()
         .filter(this::filterTargetableEntities)
         .filter(h -> h.ageWhenDamaged() + 600 >= this.entity.tickCount)
         .filter(damageHistory -> damageHistory.source().getEntity() != null)
         .collect(Collectors.groupingBy(h -> h.source().getEntity()))
         .entrySet()
         .stream()
         .max(Comparator.comparingDouble(e -> e.getValue().stream().mapToDouble(DamageMemory.DamageHistory::amount).sum()))
         .map(Entry::getKey)
         .orElse(null);
      if (newTarget != null && !newTarget.equals(this.entity.getTarget()) && this.entity.getRandom().nextInt(2) == 0) {
         this.entity.setTarget((LivingEntity)newTarget);
      }
   }

   private boolean filterTargetableEntities(DamageMemory.DamageHistory damageHistory) {
      Entity attacker = damageHistory.source().getEntity();
      if (attacker instanceof LivingEntity) {
         boolean canSee = this.entity.getSensing().hasLineOfSight(attacker);
         double followRange = this.entity.getAttributeValue(Attributes.FOLLOW_RANGE);
         boolean inRange = this.entity.distanceToSqr(attacker) < followRange * followRange;
         return canSee && inRange && this.entity.canAttack((LivingEntity)attacker);
      } else {
         return false;
      }
   }
}
