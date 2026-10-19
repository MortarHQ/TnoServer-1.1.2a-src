package com.tensura_tno.race.fox_spirit;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class SpiritSummonRetaliationEventHandler {
   private static final double RETALIATION_RANGE = 64.0;

   private SpiritSummonRetaliationEventHandler() {
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      if (!event.getEntity().level().isClientSide()) {
         if (event.getEntity() instanceof ServerPlayer player) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker) {
               if (isValidAttacker(player, attacker)) {
                  for (Mob summon : SpiritSummonEntityHelper.findActiveSpiritSummons(player)) {
                     orderRetaliation(summon, attacker);
                  }
               }
            }
         }
      }
   }

   private static boolean isValidAttacker(ServerPlayer player, LivingEntity attacker) {
      if (!attacker.isAlive()) {
         return false;
      } else if (attacker == player) {
         return false;
      } else if (attacker.level() != player.level()) {
         return false;
      } else if (player.isSpectator()) {
         return false;
      } else {
         return SpiritSummonEntityHelper.isOwnedBy(attacker, player) ? false : !player.isAlliedTo(attacker);
      }
   }

   private static void orderRetaliation(Mob summon, LivingEntity attacker) {
      if (summon.isAlive()) {
         if (summon.level() == attacker.level()) {
            if (!(summon.distanceToSqr(attacker) > 4096.0)) {
               if (TargetingConditions.DEFAULT.test(summon, attacker)) {
                  summon.setTarget(attacker);

                  try {
                     summon.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, attacker);
                     summon.getBrain().setMemory(MemoryModuleType.HURT_BY_ENTITY, attacker);
                  } catch (Exception var3) {
                  }
               }
            }
         }
      }
   }
}
