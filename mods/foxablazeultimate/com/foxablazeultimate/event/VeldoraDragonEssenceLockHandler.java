package com.foxablazeultimate.event;

import com.foxablazeultimate.ability.skill.veldora.VeldoraSkill;
import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.item.CrystalLockState;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class VeldoraDragonEssenceLockHandler {
   private VeldoraDragonEssenceLockHandler() {
   }

   @SubscribeEvent
   public static void onFinishItemUse(Finish event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (event.getItem().getItem() == TensuraMobDropItems.DRAGON_ESSENCE.get()) {
            if (SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.VELDORA.get())) {
               ResourceLocation veldoraId = ((VeldoraSkill)FoxAblazeUltimateSkills.VELDORA.get()).getRegistryName();
               if (!FoxAblazeUltimateConfig.disableCrystalSkillLocking(veldoraId)) {
                  if (veldoraId != null && !CrystalLockState.isCrystalLocked(player, veldoraId)) {
                     int eaten = CrystalLockState.incrementEaten(player, veldoraId);
                     if (eaten >= 10 && CrystalLockState.addCrystalLock(player, veldoraId)) {
                        WisdomCrystalSyncHandler.syncTo(player);
                     }
                  }
               }
            }
         }
      }
   }
}
