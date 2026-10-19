package dev.foxboss.entity;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

final class MilimAggroController {
   private static final int WINDOW_TICKS = 200;
   private static final int HIT_SPACING_TICKS = 10;
   private static final int REQUIRED_HITS = 3;
   private final MilimEntity milim;
   private final Map<UUID, ArrayDeque<Long>> attacks = new HashMap<>();
   private UUID fighting;

   MilimAggroController(MilimEntity milim) {
      this.milim = milim;
   }

   void tick() {
      if (this.enabled() && this.milim.hasCombatTarget()) {
         this.observe(this.milim.getTarget());
         if (this.milim.tickCount % 20 == 0) {
            long now = this.milim.level().getGameTime();
            this.attacks.values().forEach(hits -> expire((ArrayDeque<Long>)hits, now));
            this.attacks.values().removeIf(ArrayDeque::isEmpty);
         }
      } else {
         this.reset();
      }
   }

   void attacked(DamageSource source, float amount, LivingEntity previousTarget) {
      if (this.enabled() && amount > 0.0F && source.getEntity() instanceof LivingEntity attacker && attacker.isAlive() && this.milim.canAttack(attacker)) {
         if (previousTarget != null && previousTarget.isAlive() && this.milim.canAttack(previousTarget)) {
            this.observe(previousTarget);
            if (attacker != previousTarget) {
               long now = this.milim.level().getGameTime();
               ArrayDeque<Long> hits = this.attacks.computeIfAbsent(attacker.getUUID(), ignored -> new ArrayDeque<>());
               expire(hits, now);
               if (hits.isEmpty() || now - hits.getLast() >= 10L) {
                  hits.addLast(now);
                  if (hits.size() >= 3) {
                     this.reset();
                     this.milim.prioritizeAttacker(attacker);
                  }
               }
            }
         } else {
            this.reset();
            this.milim.prioritizeAttacker(attacker);
         }
      }
   }

   private boolean enabled() {
      return !this.milim.level().isClientSide
         && this.milim.isAlive()
         && !this.milim.isInvulnerable()
         && !this.milim.isNoAi()
         && !this.milim.isDemo()
         && !this.milim.isDebugAnimation()
         && !this.milim.isOrderedToSit();
   }

   private void observe(LivingEntity target) {
      if (!target.getUUID().equals(this.fighting)) {
         this.attacks.clear();
         this.fighting = target.getUUID();
      }
   }

   private static void expire(ArrayDeque<Long> hits, long now) {
      while (!hits.isEmpty() && now - hits.getFirst() > 200L) {
         hits.removeFirst();
      }
   }

   private void reset() {
      this.attacks.clear();
      this.fighting = null;
   }
}
