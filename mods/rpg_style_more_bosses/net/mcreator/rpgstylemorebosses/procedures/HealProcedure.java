package net.mcreator.rpgstylemorebosses.procedures;

import java.util.Comparator;
import net.mcreator.rpgstylemorebosses.RpgStyleMoreBossesMod;
import net.mcreator.rpgstylemorebosses.entity.KriStrarzEntity;
import net.mcreator.rpgstylemorebosses.entity.TheExoframeEntity;
import net.mcreator.rpgstylemorebosses.init.RpgStyleMoreBossesModMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HealProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
            _entity.addEffect(new MobEffectInstance(RpgStyleMoreBossesModMobEffects.STUN, 100, 5, false, false));
         }

         if (entity instanceof TheExoframeEntity) {
            ((TheExoframeEntity)entity).setAnimation("heal");
         }

         RpgStyleMoreBossesMod.queueServerWork(15, () -> {
            if (entity instanceof LivingEntity _entityxx && !_entityxx.level().isClientSide()) {
               _entityxx.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 70, 1, false, false));
            }

            if (entity instanceof LivingEntity _entityx && !_entityx.level().isClientSide()) {
               _entityx.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 70, 5, false, false));
            }
         });
         RpgStyleMoreBossesMod.queueServerWork(
            82,
            () -> {
               if (entity instanceof LivingEntity _entityxx && !_entityxx.level().isClientSide()) {
                  _entityxx.addEffect(new MobEffectInstance(MobEffects.HEAL, 0, 4, false, false));
               }

               Vec3 _center = new Vec3(entity.getX() + entity.getLookAngle().x * 1.0, entity.getY(), entity.getZ() + entity.getLookAngle().z * 1.0);

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator instanceof KriStrarzEntity) {
                     if (entity instanceof LivingEntity _entityx && !_entityx.level().isClientSide()) {
                        _entityx.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 60, 2, false, false));
                     }

                     if (world instanceof ServerLevel _level) {
                        _level.sendParticles(ParticleTypes.CRIT, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), 20, 1.0, 1.5, 1.0, 0.0);
                     }
                  }
               }
            }
         );
      }
   }
}
