package com.foxablazeultimate.event;

import com.foxablazeultimate.config.SatanaelConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateItems;
import com.foxablazeultimate.registry.FoxAblazeUltimateMobEffects;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class WrathCrystalDropHandler {
   private WrathCrystalDropHandler() {
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (victim.level() instanceof ServerLevel) {
         LivingEntity killer = event.getSource().getEntity() instanceof LivingEntity living ? living : null;
         if (hasRampage(victim) || killer != null && hasRampage(killer)) {
            double chance = Math.max(0.0, Math.min(1.0, SatanaelConfig.get().Satanael.wrathCrystalDropChance));
            if (!(victim.getRandom().nextDouble() >= chance)) {
               victim.spawnAtLocation(new ItemStack((ItemLike)FoxAblazeUltimateItems.WRATH_CRYSTAL.get()));
            }
         }
      }
   }

   private static boolean hasRampage(LivingEntity entity) {
      return entity.hasEffect(FoxAblazeUltimateMobEffects.SATANAEL_RAMPAGE) || entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.RAMPAGE));
   }
}
