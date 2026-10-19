package net.mcreator.rpgstylemorebosses.procedures;

import net.mcreator.rpgstylemorebosses.entity.ExoframeTickEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.mcreator.rpgstylemorebosses.network.RpgStyleMoreBossesModVariables;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class ExoframeTickOnInitialEntitySpawnProcedure {
   public static void execute(Entity entity) {
      if (entity != null) {
         if (entity instanceof ExoframeTickEntity) {
            ((ExoframeTickEntity)entity).setAnimation("spawn");
         }

         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 30, 1, false, false));
         }

         RpgStyleMoreBossesModVariables.PlayerVariables _vars = (RpgStyleMoreBossesModVariables.PlayerVariables)entity.getData(
            RpgStyleMoreBossesModVariables.PLAYER_VARIABLES
         );
         _vars.TickExplosion = false;
         _vars.syncPlayerVariables(entity);
      }
   }
}
