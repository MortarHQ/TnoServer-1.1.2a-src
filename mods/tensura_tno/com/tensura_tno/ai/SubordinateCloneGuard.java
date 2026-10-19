package com.tensura_tno.ai;

import com.tensura_tno.race.fox_spirit.SpiritSummonEntityHelper;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class SubordinateCloneGuard {
   private SubordinateCloneGuard() {
   }

   public static boolean isRestrictedSummoner(LivingEntity source) {
      if (source instanceof Player || source.level().isClientSide()) {
         return false;
      } else if (!(source instanceof CloneEntity) && !SubordinateSkillAiController.isCasting(source)) {
         return source instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null
            ? true
            : SubordinateSkillAiController.findOwnerId(source) != null || SpiritSummonEntityHelper.getSpiritSummonOwnerUUID(source) != null;
      } else {
         return true;
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onCloneJoin(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide() && event.getEntity() instanceof SubordinateCloneGuard.SpawnSource source && source.tensuraTno$isSubordinateClone()) {
         event.setCanceled(true);
      }
   }

   public interface SpawnSource {
      boolean tensuraTno$isSubordinateClone();
   }
}
