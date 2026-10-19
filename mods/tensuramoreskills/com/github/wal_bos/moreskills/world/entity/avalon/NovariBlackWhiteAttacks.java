package com.github.wal_bos.moreskills.world.entity.avalon;

import com.github.wal_bos.moreskills.net.msg.NovariBlackWhiteVfxPayload;
import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class NovariBlackWhiteAttacks {
   private NovariBlackWhiteAttacks() {
   }

   public static void tick(AvalonNovariEntity boss, int t) {
      if (boss.level() instanceof ServerLevel level) {
         LivingEntity target = boss.resolveTarget();
         if (target == null) {
            boss.clearAttack();
            boss.setGlobalCooldown(10);
         } else {
            boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
            switch (boss.getAttackId()) {
               case 30:
                  usurperBait(level, boss, target, t);
                  break;
               case 31:
                  decoyShift(level, boss, target, t);
                  break;
               case 32:
                  serratedReflection(level, boss, target, t);
                  break;
               case 33:
                  phenomenonTheft(level, boss, target, t);
                  break;
               case 34:
                  vacuumAura(level, boss, target, t);
                  break;
               case 35:
                  infiniteMaw(level, boss, target, t);
                  break;
               case 36:
                  oblivionWave(level, boss, target, t);
                  break;
               case 37:
                  unleashedHoard(level, boss, target, t);
                  break;
               default:
                  boss.clearAttack();
                  boss.setGlobalCooldown(8);
            }
         }
      } else {
         boss.clearAttack();
      }
   }

   private static void beginVfx(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int visual, int duration, float radius) {
      Vec3 pos = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      NovariBlackWhiteVfxPayload payload = new NovariBlackWhiteVfxPayload(
         boss.getId(), target.getId(), visual, pos.x, pos.y, pos.z, radius, duration, boss.getRandom().nextInt()
      );
      AABB area = boss.getBoundingBox().inflate(160.0);

      for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, area)) {
         NetworkManager.sendToPlayer(player, payload);
      }
   }

   private static void usurperBait(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         boss.setReflectTicks(60, boss.getPhase() >= 4 ? 1.05F : 1.0F);
         beginVfx(level, boss, target, 1, 60, 7.0F);
         level.playSound(null, boss.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.HOSTILE, 4.0F, 0.48F);
      } else {
         if (t >= 60) {
            boss.clearAttack();
            boss.setGlobalCooldown(12);
         }
      }
   }

   private static void decoyShift(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         beginVfx(level, boss, target, 2, 36, 9.0F);
         Vec3 center = target.position();
         double angle = boss.getRandom().nextDouble() * Math.PI * 2.0;
         double radius = 10.0 + boss.getRandom().nextDouble() * 12.0;
         boss.teleportTo(center.x + Math.cos(angle) * radius, center.y, center.z + Math.sin(angle) * radius);
         level.playSound(null, boss.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 4.0F, 1.55F);
      } else if (t == 14) {
         area(level, boss, boss.position(), 8.0, TensuraDamageTypes.LIGHT_ELEMENTAL, 1200.0F, 0.45, true);
         level.sendParticles(ParticleTypes.FLASH, boss.getX(), boss.getY() + 1.5, boss.getZ(), 4, 0.4, 0.4, 0.4, 0.0);
         level.playSound(null, boss.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 3.5F, 1.25F);
      } else {
         if (t >= 22) {
            boss.clearAttack();
            boss.setGlobalCooldown(8);
         }
      }
   }

   private static void serratedReflection(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         beginVfx(level, boss, target, 3, 42, 13.0F);
         level.playSound(null, boss.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.HOSTILE, 5.0F, 0.35F);
      } else if (t != 22) {
         if (t >= 38) {
            boss.clearAttack();
            boss.setGlobalCooldown(18);
         }
      } else {
         AABB box = boss.getBoundingBox().inflate(22.0);
         DamageSource source = source(level, boss, TensuraDamageTypes.SPACE_ELEMENTAL, true);

         for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
            int effects = living.getActiveEffects().size();
            float damage = 1600.0F + effects * 650.0F + living.getAbsorptionAmount() * 120.0F;
            living.removeAllEffects();
            living.setAbsorptionAmount(0.0F);
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;
            hurtSpiritual(living, boss, 1200.0F + effects * 350.0F, source);
         }

         level.sendParticles(ParticleTypes.WHITE_ASH, boss.getX(), boss.getY() + 1.5, boss.getZ(), 260, 18.0, 6.0, 18.0, 0.42);
      }
   }

   private static void phenomenonTheft(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         boss.setPhenomenonTheftTicks(600);
         beginVfx(level, boss, target, 4, 120, 18.0F);
         level.playSound(null, boss.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.HOSTILE, 5.0F, 0.38F);
      } else {
         if (t % 20 == 0 && t <= 100) {
            area(level, boss, target.position(), 12.0, TensuraDamageTypes.HOLY_DAMAGE, 900.0F, 0.18, true);
         }

         if (t >= 120) {
            boss.clearAttack();
            boss.setGlobalCooldown(28);
         }
      }
   }

   private static void vacuumAura(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         beginVfx(level, boss, target, 5, 100, 28.0F);
      }

      if (t % 2 == 0) {
         Vec3 center = boss.position().add(0.0, boss.getBbHeight() * 0.5, 0.0);

         for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, boss.getBoundingBox().inflate(38.0), boss::isValidEnemy)) {
            Vec3 dir = center.subtract(living.position().add(0.0, living.getBbHeight() * 0.5, 0.0));
            if (!(dir.lengthSqr() < 0.001)) {
               living.setDeltaMovement(living.getDeltaMovement().scale(0.82).add(dir.normalize().scale(0.45)).add(0.0, 0.035, 0.0));
               living.hurtMarked = true;
            }
         }
      }

      if (t % 16 == 0) {
         area(level, boss, boss.position(), 16.0, TensuraDamageTypes.GRAVITY_ELEMENTAL, 750.0F, 0.05, false);
      }

      if (t >= 100) {
         boss.clearAttack();
         boss.setGlobalCooldown(12);
      }
   }

   private static void infiniteMaw(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         beginVfx(level, boss, target, 6, 120, 18.0F);
         level.playSound(null, boss.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.HOSTILE, 5.0F, 0.25F);
      }

      if (t % 10 == 0) {
         AABB box = boss.getBoundingBox().inflate(15.0);
         DamageSource source = source(level, boss, TensuraDamageTypes.ENERGY_DRAIN, true);

         for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
            living.invulnerableTime = 0;
            living.hurt(source, 1400.0F);
            living.invulnerableTime = 0;
            boss.heal(1200.0F);
            drainExistence(living, boss, 1800.0);
         }
      }

      if (t >= 120) {
         boss.clearAttack();
         boss.setGlobalCooldown(18);
      }
   }

   private static void oblivionWave(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         beginVfx(level, boss, target, 7, 76, 42.0F);
         level.playSound(null, boss.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.HOSTILE, 6.0F, 0.24F);
      }

      if (t == 36) {
         Vec3 start = boss.position().add(0.0, boss.getBbHeight() * 0.55, 0.0);
         Vec3 dir = target.position().subtract(boss.position());
         if (dir.lengthSqr() < 0.001) {
            dir = boss.getLookAngle();
         }

         dir = dir.normalize();

         for (int i = 0; i < 9; i++) {
            Vec3 end = start.add(dir.scale(12.0 + i * 8.0));
            area(level, boss, end, 8.0 + i * 0.7, TensuraDamageTypes.SPACE_ELEMENTAL, 2400.0F + i * 180.0F, 0.25, true);
            level.sendParticles(i % 2 == 0 ? ParticleTypes.REVERSE_PORTAL : ParticleTypes.WHITE_ASH, end.x, end.y, end.z, 120, 7.0, 3.5, 7.0, 0.45);
         }

         level.playSound(null, target.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 7.0F, 0.32F);
      }

      if (t >= 76) {
         boss.clearAttack();
         boss.setGlobalCooldown(20);
      }
   }

   private static void unleashedHoard(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, int t) {
      if (t == 1) {
         beginVfx(level, boss, target, 8, 100, 52.0F);
         level.playSound(null, boss.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 7.0F, 0.42F);
      }

      if (t >= 10 && t <= 100 && t % 5 == 0) {
         int index = t / 5 % 6;
         switch (index) {
            case 0:
               area(level, boss, target.position(), 10.0, TensuraDamageTypes.SPACE_ELEMENTAL, 1800.0F, 0.18, true);
               break;
            case 1:
               area(level, boss, target.position(), 12.0, TensuraDamageTypes.DARKNESS_ELEMENTAL, 1700.0F, 0.08, true);
               break;
            case 2:
               area(level, boss, target.position(), 9.0, TensuraDamageTypes.HOLY_DAMAGE, 1900.0F, 0.25, true);
               break;
            case 3:
               area(level, boss, target.position(), 14.0, TensuraDamageTypes.GRAVITY_ELEMENTAL, 1500.0F, 0.02, true);
               break;
            case 4:
               area(level, boss, boss.position(), 18.0, TensuraDamageTypes.ENERGY_DRAIN, 1200.0F, 0.06, true);
               break;
            default:
               area(level, boss, target.position(), 16.0, TensuraDamageTypes.SOUL_SCATTER, 1600.0F, 0.12, true);
         }
      }

      if (t >= 105) {
         boss.clearAttack();
         boss.setGlobalCooldown(36);
      }
   }

   private static void area(
      ServerLevel level, AvalonNovariEntity boss, Vec3 center, double radius, ResourceKey<DamageType> type, float baseDamage, double lift, boolean spiritual
   ) {
      DamageSource source = source(level, boss, type, true);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(radius), boss::isValidEnemy)) {
         Vec3 c = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double dist = c.distanceTo(center);
         if (!(dist > radius + living.getBbWidth())) {
            double close = 1.0 - Math.min(1.0, dist / radius);
            float damage = baseDamage * (0.58F + (float)close * 0.92F);
            living.invulnerableTime = 0;
            living.hurt(source, damage);
            living.invulnerableTime = 0;
            if (spiritual) {
               hurtSpiritual(living, boss, damage * 0.8F, source);
            }

            Vec3 push = c.subtract(center);
            if (push.lengthSqr() < 0.001) {
               push = new Vec3(0.0, 1.0, 0.0);
            }

            living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.22 + close * 0.62)).add(0.0, lift, 0.0));
            living.hurtMarked = true;
         }
      }
   }

   private static DamageSource source(ServerLevel level, AvalonNovariEntity boss, ResourceKey<DamageType> type, boolean bypass) {
      return TensuraDamageTypes.getEntityDamageSource(level, type, boss);
   }

   private static void hurtSpiritual(LivingEntity target, AvalonNovariEntity boss, float amount, DamageSource source) {
      try {
         IExistence ex = TensuraStorages.getExistenceFrom(target);
         ex.setSpiritualHealth(Math.max(0.0, ex.getSpiritualHealth() - amount));
         ex.markDirty();
         if (ex.getSpiritualHealth() <= 0.0 && target.isAlive()) {
            target.setHealth(0.0F);
            target.die(source);
         }
      } catch (Throwable var5) {
      }
   }

   private static void drainExistence(LivingEntity target, AvalonNovariEntity boss, double amount) {
      try {
         IExistence targetEx = TensuraStorages.getExistenceFrom(target);
         IExistence bossEx = TensuraStorages.getExistenceFrom(boss);
         double aura = Math.min(targetEx.getAura(), amount);
         double magicule = Math.min(targetEx.getMagicule(), amount);
         targetEx.setAura(Math.max(0.0, targetEx.getAura() - aura));
         targetEx.setMagicule(Math.max(0.0, targetEx.getMagicule() - magicule));
         bossEx.setAura(bossEx.getAura() + aura);
         bossEx.setMagicule(bossEx.getMagicule() + magicule);
         targetEx.markDirty();
         bossEx.markDirty();
      } catch (Throwable var10) {
      }
   }
}
