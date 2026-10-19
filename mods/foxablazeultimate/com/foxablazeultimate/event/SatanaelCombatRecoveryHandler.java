package com.foxablazeultimate.event;

import com.foxablazeultimate.config.SatanaelConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateMobEffects;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public final class SatanaelCombatRecoveryHandler {
   private SatanaelCombatRecoveryHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide() && player.isAlive() && player.tickCount % 20 == 0 && player.hasEffect(FoxAblazeUltimateMobEffects.SATANAEL_COMBAT)) {
         double maximumMagicule = player.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
         double recovery = maximumMagicule * Math.max(0.0, SatanaelConfig.get().Satanael.combatMagiculeRecoveryFraction);
         double limitedMaximum = player.getAttributeValue(TensuraAttributes.LIMITED_SPIRITUAL_MAX_MAGICULE);
         if (limitedMaximum > 0.0) {
            maximumMagicule = Math.min(maximumMagicule, limitedMaximum);
         }

         if (!(recovery <= 0.0) && !(maximumMagicule <= 0.0)) {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            double currentMagicule = existence.getMagicule();
            if (!(currentMagicule >= maximumMagicule)) {
               existence.setMagicule(Math.min(maximumMagicule, currentMagicule + recovery));
               existence.markDirty();
            }
         }
      }
   }
}
