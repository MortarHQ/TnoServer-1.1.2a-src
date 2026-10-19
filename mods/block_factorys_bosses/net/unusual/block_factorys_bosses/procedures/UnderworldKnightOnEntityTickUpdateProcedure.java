package net.unusual.block_factorys_bosses.procedures;

import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.entity.boss.knight.UnderworldKnightEntity;
import net.unusual.block_factorys_bosses.entity.projectile.SoulShockwaveEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.NotNull;

public class UnderworldKnightOnEntityTickUpdateProcedure extends AbstractBossOnEntityTickUpdateProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, @NotNull UnderworldKnightEntity boss) {
      if (!onSpawn(boss, world, 226)) {
         ;
      }
   }

   public static void attackCombo1(@NotNull UnderworldKnightEntity boss, LevelAccessor world) {
      double x = boss.getX();
      double y = boss.getY();
      double z = boss.getZ();
      int attackAnimtime = 158 - boss.getTimer();
      if (attackAnimtime >= 158) {
         boss.lookAtTarget();
      }

      if (attackAnimtime == 60 && boss.heavyCounter < (boss.getHealthRatio() > 0.5 ? 3 : 5)) {
         boss.placeMark(0.5F);
      }

      if (attackAnimtime == 122 || attackAnimtime == 76 || attackAnimtime == 58) {
         double distance = 1.0;
         if (boss.getTarget() != null) {
            boss.lookAtTarget();
            distance = boss.distanceTo(boss.getTarget()) * 0.08;
         }

         boss.push(boss.getLookAngle().x * distance, 0.3, boss.getLookAngle().z * distance);
         boss.meleeAttack(6.0, 2.5, 1.5);
      } else if (attackAnimtime == 108 || attackAnimtime == 94 || attackAnimtime == 62) {
         double distance = 1.0;
         if (boss.getTarget() != null) {
            boss.lookAtTarget();
            distance = boss.distanceTo(boss.getTarget()) * 0.08;
         }

         boss.push(boss.getLookAngle().x * distance, 0.3, boss.getLookAngle().z * distance);
         boss.meleeAttack(14.0, 2.5, 1.5);
      } else if (attackAnimtime <= 30) {
         if (attackAnimtime == 30) {
            boss.meleeAttack(8.0, 2.5, 3.0);
         }

         if (attackAnimtime == 29) {
            for (int index0 = 0; index0 < 26; index0++) {
               world.addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.SOUL_CLOUD.get(),
                  x + boss.getLookAngle().x * 4.0,
                  y + 0.1,
                  z + boss.getLookAngle().z * 4.0,
                  (Math.random() - 0.5) * 2.0,
                  Math.random() * 0.3,
                  (Math.random() - 0.5) * 2.0
               );
               world.addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.BLACK_FALLING_DUST.get(),
                  x + boss.getLookAngle().x * 4.0,
                  y + 0.1,
                  z + boss.getLookAngle().z * 4.0,
                  (Math.random() - 0.5) * 2.5,
                  Math.random() * 0.4,
                  (Math.random() - 0.5) * 2.5
               );
               if (Math.random() < 0.3) {
                  world.addParticle(
                     (ParticleOptions)BossesRiseParticleTypes.MAGICAL_DOT.get(),
                     x + boss.getLookAngle().x * 4.0,
                     y + 0.1,
                     z + boss.getLookAngle().z * 4.0,
                     (Math.random() - 0.5) * 2.5,
                     Math.random() * 0.4,
                     (Math.random() - 0.5) * 2.5
                  );
               }
            }
         }

         if (attackAnimtime < 29) {
            double nb = 0.0;
            double nb2 = 2.0 + (18 - attackAnimtime) * 0.1;

            for (int index1 = 0; index1 < Math.round(18.0 * nb2); index1++) {
               double new_x = boss.getLookAngle().x * 4.0 + Math.cos(nb) * nb2 * 2.0;
               double new_z = boss.getLookAngle().z * 4.0 + Math.sin(nb) * nb2 * 2.0;
               ItemStack stack = new ItemStack(world.getBlockState(BlockPos.containing(Math.floor(x + new_x), y - 1.0, Math.floor(z + new_z))).getBlock());
               boss.forEachNearbyEntity(
                  0.5, new Vec3(new_x, 0.0, new_z), entity -> entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.IN_WALL)), 12.0F)
               );
               if (stack.getItem() != ItemStack.EMPTY.getItem() && world instanceof ServerLevel _level) {
                  _level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), x + new_x, y + 0.1, z + new_z, 3, 0.0, 0.0, 0.0, 0.05);
               }

               nb += nb2;
            }
         }
      }

      if (attackAnimtime == 36) {
         boss.lookAtTarget();
      }
   }

   public static void attackJumpspin1(@NotNull UnderworldKnightEntity boss, LevelAccessor world) {
      double x = boss.getX();
      double y = boss.getY();
      double z = boss.getZ();
      int attackAnimtime = 78 - boss.getTimer();
      if (attackAnimtime >= 62) {
         boss.lookAtTarget();
      }

      if (attackAnimtime == 62) {
         boss.push(0.0, boss.isInWaterOrBubble() ? 2.1 : 1.2, 0.0);
      }

      if (attackAnimtime < 62 && attackAnimtime > 41 && boss.getTarget() != null) {
         boss.push((boss.getTarget().getX() - x) * 0.05, 0.0, (boss.getTarget().getZ() - z) * 0.05);
         if (world.getLevelData().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            for (int sx = -3; sx <= 3; sx++) {
               for (int sy = 3; sy <= 6; sy++) {
                  for (int sz = -3; sz <= 3; sz++) {
                     if (world.getBlockState(BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                              .getDestroySpeed(world, BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                           > 0.0F
                        && world.getBlockState(BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                              .getDestroySpeed(world, BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz))
                           < 50.0F) {
                        world.setBlock(BlockPos.containing(Math.floor(x) + sx, Math.floor(y) + sy, Math.floor(z) + sz), Blocks.AIR.defaultBlockState(), 3);
                     }
                  }
               }
            }
         }
      }

      if (attackAnimtime == 41) {
         boss.lookAt(Anchor.EYES, new Vec3(x, y - 10.0, z));
         if (world instanceof ServerLevel level) {
            SoulShockwaveEntity shockwave = new SoulShockwaveEntity((EntityType<? extends SoulShockwaveEntity>)BossesRiseEntities.SOUL_SHOCKWAVE.get(), level);
            shockwave.setOwner(boss);
            shockwave.setBaseDamage(17.5);
            shockwave.setSilent(true);
            shockwave.setPos(x, boss.getEyeY() - 0.1, z);
            shockwave.shoot(boss.getLookAngle().x, boss.getLookAngle().y, boss.getLookAngle().z, 1.0F, 0.0F);
            level.addFreshEntity(shockwave);
         }

         if (Math.random() < 0.2) {
            boss.playSound((SoundEvent)BossesRiseSounds.KNIGHT_ATTACK_VOICELINE.value());
         }
      }

      if (attackAnimtime == 19) {
         boss.push(0.0, -1.0, 0.0);
      }
   }
}
