package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;

public class TheExoframeOnInitialEntitySpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         RpgStyleMoreBossesModVariables.MapVariables.get(world).BC = 0.0;
         RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
         RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeDieAnimPlay = false;
         RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 30, 1, false, false));
         }

         RpgStyleMoreBossesModVariables.MapVariables.get(world).ExoframeAbilityUse = false;
         RpgStyleMoreBossesModVariables.MapVariables.get(world).syncData(world);
      }
   }
}
