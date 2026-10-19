package net.unusual.block_factorys_bosses.procedures;

import java.util.Comparator;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.monster.SoulKnightWitherSkeletonEntity;

public class SoulKnightWitherSkeletonOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if ((entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null) instanceof UnderworldKnightEntity
            && entity instanceof Mob _entity
            && null instanceof LivingEntity _ent) {
            _entity.setTarget(_ent);
         }

         if ((
               entity instanceof SoulKnightWitherSkeletonEntity _datEntI
                  ? (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_animtime)
                  : 0
            )
            > 0) {
            if ((
                  entity instanceof SoulKnightWitherSkeletonEntity _datEntI
                     ? (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_animtime)
                     : 0
               )
               == 10) {
               Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

               for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(3.5), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                  .toList()) {
                  if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null)) {
                     if (entity.isAlive()) {
                        entityiterator.hurt(
                           new DamageSource(world.holderOrThrow(DamageTypes.MOB_ATTACK), entity),
                           (float)(
                              entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE)
                                 ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getValue()
                                 : 0.0
                           )
                        );
                        if (!(entity instanceof LivingEntity _livEnt14 && _livEnt14.isBlocking())
                           && entityiterator instanceof LivingEntity _entity
                           && !_entity.level().isClientSide()) {
                           _entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));
                        }
                     }

                     entityiterator.setDeltaMovement(
                        new Vec3(
                           entityiterator.getDeltaMovement().x() + entity.getLookAngle().x * 0.2,
                           entityiterator.getDeltaMovement().y() + 0.1,
                           entityiterator.getDeltaMovement().z() + entity.getLookAngle().z * 0.2
                        )
                     );
                     break;
                  }
               }
            }

            if (entity instanceof SoulKnightWitherSkeletonEntity _datEntSetI) {
               _datEntSetI.getEntityData()
                  .set(
                     SoulKnightWitherSkeletonEntity.DATA_attack_animtime,
                     (
                           entity instanceof SoulKnightWitherSkeletonEntity _datEntI
                              ? (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_animtime)
                              : 0
                        )
                        - 1
                  );
            }
         } else if ((
               entity instanceof SoulKnightWitherSkeletonEntity _datEntI
                  ? (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_cooldown)
                  : 0
            )
            >= 30) {
            Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(2.75), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null)) {
                  if (entity instanceof SoulKnightWitherSkeletonEntity _datEntSetI) {
                     _datEntSetI.getEntityData().set(SoulKnightWitherSkeletonEntity.DATA_attack_cooldown, 0);
                  }

                  if (entity instanceof SoulKnightWitherSkeletonEntity _datEntSetI) {
                     _datEntSetI.getEntityData().set(SoulKnightWitherSkeletonEntity.DATA_attack_animtime, 20);
                  }
                  break;
               }
            }
         } else {
            Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());

            for (Entity entityiterator : world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
               .toList()) {
               if (entityiterator == (entity instanceof Mob _mobEnt ? _mobEnt.getTarget() : null)) {
                  if (entity instanceof SoulKnightWitherSkeletonEntity _datEntSetI) {
                     _datEntSetI.getEntityData()
                        .set(
                           SoulKnightWitherSkeletonEntity.DATA_attack_cooldown,
                           (
                                 entity instanceof SoulKnightWitherSkeletonEntity _datEntI
                                    ? (Integer)_datEntI.getEntityData().get(SoulKnightWitherSkeletonEntity.DATA_attack_cooldown)
                                    : 0
                              )
                              + 1
                        );
                  }
                  break;
               }
            }
         }

         if (Math.random() < 0.05 && world instanceof ServerLevel _level) {
            _level.sendParticles(ParticleTypes.SMOKE, entity.getX(), entity.getY() + 1.5, entity.getZ(), 1, 0.4, 0.4, 0.4, 0.0);
         }
      }
   }
}
