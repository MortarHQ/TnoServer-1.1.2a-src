package com.tensura_tno.ai;

import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.UUID;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;

@EventBusSubscriber(modid = "tensura_tno")
public final class SubordinateSkillAiEventHandler {
   private SubordinateSkillAiEventHandler() {
   }

   @SubscribeEvent(priority = EventPriority.LOWEST)
   public static void onEntityTick(Post event) {
      if (event.getEntity() instanceof Mob subordinate) {
         if (!subordinate.level().isClientSide() && SubordinateSkillAiController.shouldPoll(subordinate)) {
            if (!(subordinate instanceof FoxSpiritEntity) && !SpiritSummonEntityHelper.isSpiritSummon(subordinate)) {
               UUID ownerId = SubordinateSkillAiController.findOwnerId(subordinate);
               if (ownerId == null) {
                  SubordinateSkillAiController.cancel(subordinate);
               } else {
                  LivingEntity owner = SubordinateSkillAiController.resolveOwner(subordinate, ownerId);
                  boolean combatAllowed = owner != null
                     && owner.isAlive()
                     && owner.level() == subordinate.level()
                     && !subordinate.isVehicle()
                     && !SubordinateHelper.isOrderedToStay(subordinate);
                  LivingEntity target = SubordinateSkillAiController.resolveTarget(subordinate);
                  SubordinateSkillAiController.tick(subordinate, target, ownerId, combatAllowed);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onEntityLeave(EntityLeaveLevelEvent event) {
      if (event.getEntity() instanceof LivingEntity living) {
         SubordinateSkillAiController.terminate(living);
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      SubordinateSkillAiController.terminate(event.getEntity());
   }
}
