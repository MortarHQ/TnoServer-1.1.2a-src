package com.tensura_tno.event;

import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import com.tensura_tno.race.fox_spirit.SpiritSummonLifecycle;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Pre;

@EventBusSubscriber(modid = "tensura_tno")
public final class SpiritSummonProtectionEventHandler {
   private SpiritSummonProtectionEventHandler() {
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onTick(Pre event) {
      if (event.getEntity() instanceof Mob mob && !mob.level().isClientSide()) {
         if (SpiritSummonEntityHelper.isSpiritSummon(mob)) {
            if (mob.isDeadOrDying()) {
               mob.discard();
               event.setCanceled(true);
            } else if (SpiritSummonLifecycle.isSummoning(mob)) {
               if (SpiritSummonLifecycle.isAbandoned(mob)) {
                  mob.discard();
               }

               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onDamage(LivingIncomingDamageEvent event) {
      if (SpiritSummonLifecycle.isSummoning(event.getEntity())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onAttack(AttackEntityEvent event) {
      if (SpiritSummonLifecycle.isSummoning(event.getTarget())) {
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onInteract(EntityInteract event) {
      if (SpiritSummonLifecycle.isSummoning(event.getTarget())) {
         event.setCancellationResult(InteractionResult.FAIL);
         event.setCanceled(true);
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onInteractSpecific(EntityInteractSpecific event) {
      if (SpiritSummonLifecycle.isSummoning(event.getTarget())) {
         event.setCancellationResult(InteractionResult.FAIL);
         event.setCanceled(true);
      }
   }
}
