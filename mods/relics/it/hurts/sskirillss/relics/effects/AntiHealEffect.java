package it.hurts.sskirillss.relics.effects;

import it.hurts.sskirillss.relics.init.EffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

public class AntiHealEffect extends MobEffect {
   public AntiHealEffect() {
      super(MobEffectCategory.HARMFUL, 6829738);
   }

   @EventBusSubscriber(modid = "relics")
   public static class Events {
      @SubscribeEvent
      public static void onLivingHeal(LivingHealEvent event) {
         LivingEntity entity = event.getEntity();
         Level level = entity.level();
         AABB box = entity.getBoundingBox();
         BlockPos min = BlockPos.containing(Math.floor(box.minX) - 1.0, Math.floor(box.minY), Math.floor(box.minZ) - 1.0);
         BlockPos max = BlockPos.containing(Math.floor(box.maxX) + 1.0, Math.floor(entity.getEyeY()) + 1.0, Math.floor(box.maxZ) + 1.0);
         if (level.hasChunksAt(min, max)) {
            if (entity.hasEffect(EffectRegistry.ANTI_HEAL)) {
               event.setCanceled(true);
            }
         }
      }
   }
}
