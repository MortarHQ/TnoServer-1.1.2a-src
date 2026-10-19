package com.github.wal_bos.moreskills.world.entity.avalon;

import com.github.wal_bos.moreskills.world.entity.AvalonNovariEntity;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.barrier.WaterJailEntity;
import io.github.manasmods.tensura.entity.magic.spike.MudSpikeEntity;
import io.github.manasmods.tensura.entity.projectile.magic.AcidBallProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.FireBallProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.IceLanceProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.StoneShotProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.ThunderSphereProjectile;
import io.github.manasmods.tensura.entity.projectile.magic.WindTornadoProjectile;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.magic.AspectualMagics;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class AvalonNovariMagic {
   private AvalonNovariMagic() {
   }

   public static void tick(AvalonNovariEntity boss, int t) {
      if (boss.level() instanceof ServerLevel level) {
         LivingEntity target = boss.resolveTarget();
         if (target == null) {
            boss.clearAttack();
            boss.setGlobalCooldown(10);
         } else {
            boss.getLookControl().setLookAt(target, 90.0F, 90.0F);
            int id = boss.getAttackId();
            if (t == 1) {
               level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), SoundSource.HOSTILE, 3.0F, 0.75F);
               TensuraParticleHelper.spawnServerParticles(
                  level,
                  TensuraParticleUtils.getColorlessWave(1.0F, boss.getBbWidth() * 4.0F, -0.4F, true),
                  boss.getX(),
                  boss.getY() + boss.getBbHeight() * 0.5,
                  boss.getZ()
               );
            }

            if (t == 18) {
               switch (id) {
                  case 1:
                     oblivionOrb(level, boss, target, false);
                     break;
                  case 2:
                     spatialCompression(level, boss);
                     break;
                  case 3:
                     erasureRune(level, boss, target);
                     break;
                  case 4:
                     fireStorm(level, boss, target);
                     break;
                  case 5:
                     blizzard(level, boss, target);
                     break;
                  case 6:
                     mentalCrush(level, boss, target);
                     break;
                  case 7:
                     mudhand(level, boss, target);
                     break;
                  case 8:
                     thunderRain(level, boss, target);
                     break;
                  case 9:
                     waterJail(level, boss, target);
                     break;
                  case 10:
                     oblivionOrb(level, boss, target, true);
                     break;
                  case 11:
                     fireBall(level, boss, target);
                     break;
                  case 12:
                     acidShell(level, boss, target);
                     break;
                  case 13:
                     iceBreaker(level, boss, target);
                     break;
                  case 14:
                     tornadoBlade(level, boss, target);
                     break;
                  case 15:
                     mudSpears(level, boss, target);
                     break;
                  case 16:
                     stoneShot(level, boss, target);
                     break;
                  case 17:
                     thunderOrb(level, boss, target);
                     break;
                  case 18:
                     reinforcedBarrier(level, boss);
                     break;
                  default:
                     oblivionOrb(level, boss, target, false);
               }
            }

            if (t >= 34) {
               boss.clearAttack();
               boss.setGlobalCooldown(boss.getPhase() == 2 ? 6 + boss.getRandom().nextInt(12) : 8);
            }
         }
      } else {
         boss.clearAttack();
      }
   }

   private static void fireBall(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.FIRE_BALL.get());
      FireBallProjectile ball = new FireBallProjectile(level, boss);
      ball.setSkill(instance);
      ball.setSize(3.0F);
      ball.setNoGravity(true);
      ball.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 8.0F);
      ball.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 18.0F);
      ball.setExplosionRadius(8.0F);
      ball.setHitRadius(8.0F);
      ball.setBurnTicks(200);
      ball.setImpactParticleCount(8);
      Vec3 pos = castPos(boss);
      ball.moveTo(pos.x, pos.y, pos.z, boss.getYRot(), boss.getXRot());
      ball.shootToward(target, 2.0F, 0.0F);
      level.addFreshEntity(ball);
      level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), SoundSource.HOSTILE, 4.0F, 0.7F);
   }

   private static void acidShell(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.ACID_SHELL.get());
      Vec3 base = castPos(boss);

      for (int i = 0; i < 11; i++) {
         AcidBallProjectile ball = new AcidBallProjectile(level, boss);
         ball.setSkill(instance);
         ball.setSize(1.2F);
         ball.setNoGravity(true);
         ball.setArmorHurt(900);
         ball.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5.0F);
         ball.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 9.0F);
         ball.setHitRadius(4.0F);
         ball.setMobEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CORROSION), 400, 2, true, false, true));
         ball.setBurnTicks(-1);
         double angle = (Math.PI * 2) * i / 11.0;
         Vec3 offset = new Vec3(Math.cos(angle) * 1.8, Math.sin(angle * 2.0) * 0.7, Math.sin(angle) * 1.8);
         ball.setPos(base.add(offset));
         ball.shootToward(target, 2.05F, 0.0F);
         level.addFreshEntity(ball);
      }

      level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_WATER.get(), SoundSource.HOSTILE, 4.0F, 0.65F);
   }

   private static void iceBreaker(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.ICE_BREAKER.get());
      IceLanceProjectile lance = new IceLanceProjectile(level, boss);
      lance.setSkill(instance);
      lance.setSize(5.0F);
      lance.setNoGravity(true);
      lance.setHitRadius(5.0F);
      lance.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 8.0F);
      lance.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 16.0F);
      lance.setIceBreaker(true);
      lance.setFrostBonusDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 12.0F);
      lance.setChillBonusDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 8.0F);
      Vec3 pos = castPos(boss);
      lance.moveTo(pos.x, pos.y, pos.z, boss.getYRot(), boss.getXRot());
      lance.shootToward(target, 2.2F, 0.0F);
      level.addFreshEntity(lance);
      level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), SoundSource.HOSTILE, 4.0F, 0.65F);
   }

   private static void tornadoBlade(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.TORNADO_BLADE.get());
      WindTornadoProjectile projectile = new WindTornadoProjectile(level, boss);
      projectile.setSkill(instance);
      projectile.setSize(2.0F);
      projectile.setNoGravity(true);
      projectile.setBurstDelay(28);
      projectile.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 6.0F);
      projectile.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 12.0F);
      projectile.setHitRadius(8.0F);
      projectile.setPullForce(0.18F);
      projectile.setOnExplodeBlades(18);
      projectile.setBladeDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 6.0F);
      Vec3 pos = castPos(boss);
      projectile.moveTo(pos.x, pos.y, pos.z, boss.getYRot(), boss.getXRot());
      projectile.shootToward(target, Vec3.ZERO, 1.45F, 0.0F);
      level.addFreshEntity(projectile);
      level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_WIND.get(), SoundSource.HOSTILE, 4.0F, 0.65F);
   }

   private static void waterJail(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.WATER_JAIL.get());
      WaterJailEntity jail = new WaterJailEntity(level, boss);
      jail.setSkill(instance);
      jail.setLife(240);
      jail.setHealth(boss.getMaxHealth() / 2.0F);
      jail.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5.0F);
      jail.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 10.0F);
      jail.setTickEachHit(25);
      jail.setSize(4.0F);
      jail.setPos(target.getX(), target.getY() - 1.5, target.getZ());
      level.addFreshEntity(jail);
      jail.triggerAnim("controller", "start");
      level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_WATER.get(), SoundSource.HOSTILE, 4.0F, 0.7F);
   }

   private static void mudSpears(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.MUD_SPEARS.get());
      Vec3 center = target.position();
      int amount = 14;

      for (int i = 0; i < amount; i++) {
         double angle = (Math.PI * 2) * i / amount;
         MudSpikeEntity spike = new MudSpikeEntity(level, boss);
         spike.setSkill(instance);
         spike.setPos(center.x + Math.cos(angle) * 4.5, center.y, center.z + Math.sin(angle) * 4.5);
         spike.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5.0F);
         spike.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 7.0F);
         spike.setContactDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 5.0F);
         spike.setLife(300);
         spike.setYaw(-30.0F);
         spike.setPitch((float)Math.toDegrees(Math.atan2(center.z - spike.getZ(), center.x - spike.getX())));
         spike.setSize(1.4F);
         level.addFreshEntity(spike);
         spike.triggerAnim("controller", "start");
      }

      level.playSound(null, target.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_EARTH.get(), SoundSource.HOSTILE, 4.0F, 0.6F);
   }

   private static void stoneShot(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.STONE_SHOT.get());
      Vec3 center = boss.position().add(0.0, boss.getBbHeight() * 0.5, 0.0);

      for (int i = 0; i < 18; i++) {
         double angle = (Math.PI * 2) * i / 18.0;
         Vec3 dir = new Vec3(Math.cos(angle), 0.12, Math.sin(angle)).normalize();
         StoneShotProjectile projectile = new StoneShotProjectile(level, boss);
         projectile.setSkill(instance);
         projectile.setNoGravity(true);
         projectile.setPiercingEntity(true);
         projectile.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 4.0F);
         projectile.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 8.0F);
         projectile.setPos(center.add(dir.scale(3.0)));
         projectile.shoot(dir.x, dir.y, dir.z, 1.8F, 0.0F);
         level.addFreshEntity(projectile);
      }

      level.playSound(null, boss.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 4.0F, 0.85F);
   }

   private static void thunderOrb(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      ManasSkillInstance instance = magic(boss, (ManasSkill)AspectualMagics.THUNDER_ORB.get());
      ThunderSphereProjectile orb = new ThunderSphereProjectile(level, boss);
      orb.setSkill(instance);
      orb.setDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 8.0F);
      orb.setSecondaryDamage((float)boss.getAttributeValue(Attributes.ATTACK_DAMAGE) * 14.0F);
      orb.setStrikeRadius(14.0F);
      orb.setMpCost(0.0);
      orb.setExplosionRadius(6.0F);
      orb.setPos(boss.getEyePosition().add(0.0, boss.getBbHeight() * 0.8, 0.0));
      orb.setOwnerOffset(new Vec3(0.0, boss.getBbHeight() * 0.8, 0.0));
      orb.setLookDistance(42.0F);
      orb.setDelayTick(20);
      orb.setDelaySizeChange(0.08F);
      orb.setNoGravity(true);
      level.addFreshEntity(orb);
      level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get(), SoundSource.HOSTILE, 4.0F, 0.7F);
   }

   private static void reinforcedBarrier(ServerLevel level, AvalonNovariEntity boss) {
      boss.heal(boss.getMaxHealth() * 0.12F);
      boss.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_BARRIER), 260, 6, true, false, true), boss);
      boss.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PHYSICAL_BARRIER), 260, 6, true, false, true), boss);
      level.playSound(null, boss.blockPosition(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.HOSTILE, 4.0F, 0.75F);
   }

   private static void oblivionOrb(ServerLevel level, AvalonNovariEntity boss, LivingEntity target, boolean unstable) {
      Vec3 start = boss.getEyePosition();
      Vec3 end = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
      double radius = unstable ? 7.5 : 4.5;
      float damage = unstable ? 2400.0F : 950.0F;
      damageLine(level, boss, source(level, boss, TensuraDamageTypes.SPACE_ELEMENTAL), start, end, radius, damage);
      damageLine(level, boss, source(level, boss, TensuraDamageTypes.DARKNESS_ELEMENTAL), start, end, radius * 0.62, damage * 0.42F);
      level.sendParticles(ParticleTypes.REVERSE_PORTAL, end.x, end.y, end.z, unstable ? 180 : 90, radius, radius, radius, unstable ? 0.8 : 0.35);
      level.sendParticles(ParticleTypes.WHITE_ASH, end.x, end.y, end.z, unstable ? 220 : 100, radius, radius, radius, unstable ? 0.35 : 0.18);
      level.playSound(null, end.x, end.y, end.z, SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, unstable ? 5.0F : 3.5F, unstable ? 0.42F : 0.72F);
   }

   private static void spatialCompression(ServerLevel level, AvalonNovariEntity boss) {
      Vec3 center = boss.position().add(0.0, boss.getBbHeight() * 0.5, 0.0);
      boolean pull = boss.getHealth() > boss.getMaxHealth() * 0.5F;
      AABB box = boss.getBoundingBox().inflate(34.0);
      DamageSource source = source(level, boss, TensuraDamageTypes.SPACE_ELEMENTAL);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, box, boss::isValidEnemy)) {
         Vec3 dir = pull ? center.subtract(living.position()) : living.position().subtract(center);
         if (dir.lengthSqr() < 0.001) {
            dir = new Vec3(0.0, 1.0, 0.0);
         }

         living.setDeltaMovement(living.getDeltaMovement().add(dir.normalize().scale(pull ? 2.4 : 3.2)).add(0.0, pull ? 0.22 : 0.55, 0.0));
         living.hurtMarked = true;
         hurt(living, source, 850.0F);
      }

      TensuraParticleHelper.spawnServerParticles(level, TensuraParticleUtils.getPurpleWave(1.0F, 12.0F, -0.5F, true), center.x, center.y, center.z);
      level.playSound(null, boss.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 5.0F, 0.45F);
   }

   private static void erasureRune(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      Vec3 center = target.position();
      DamageSource source = source(level, boss, TensuraDamageTypes.SOUL_SCATTER);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(8.0, 4.0, 8.0), boss::isValidEnemy)) {
         if (getEp(living) > 0.0 && getEp(living) < getEp(boss)) {
            hurt(living, source, living.getMaxHealth() * 20.0F);
            hurtSpiritual(living, 999999.0F, source);
         } else {
            hurt(living, source, 1600.0F);
            hurtSpiritual(living, 2200.0F, source);
         }
      }

      BlockPos pos = BlockPos.containing(center);
      level.sendParticles(
         new BlockParticleOption(ParticleTypes.BLOCK, Blocks.CRYING_OBSIDIAN.defaultBlockState()),
         pos.getX() + 0.5,
         pos.getY() + 0.25,
         pos.getZ() + 0.5,
         160,
         7.0,
         0.15,
         7.0,
         0.25
      );
      level.playSound(null, pos, (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.HOSTILE, 4.0F, 0.28F);
   }

   private static void fireStorm(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      areaDamage(level, boss, target.position(), 13.0, TensuraDamageTypes.FIRE_ELEMENTAL, 1200.0F, 0.8);
      level.sendParticles(ParticleTypes.FLAME, target.getX(), target.getY() + 1.0, target.getZ(), 260, 11.0, 5.0, 11.0, 0.45);
      level.playSound(null, target.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.HOSTILE, 4.0F, 0.62F);
   }

   private static void blizzard(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      areaDamage(level, boss, target.position(), 15.0, TensuraDamageTypes.ICE_ELEMENTAL, 1000.0F, 0.35);
      level.sendParticles(ParticleTypes.SNOWFLAKE, target.getX(), target.getY() + 1.0, target.getZ(), 300, 12.0, 5.5, 12.0, 0.3);
      level.playSound(null, target.blockPosition(), SoundEvents.PLAYER_HURT_FREEZE, SoundSource.HOSTILE, 4.0F, 0.55F);
   }

   private static void mentalCrush(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      DamageSource source = source(level, boss, TensuraDamageTypes.INSANITY);
      hurt(target, source, 1350.0F);
      hurtSpiritual(target, 3200.0F, source);
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 120, 2, true, false, true));
      level.sendParticles(ParticleTypes.WITCH, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), 110, 1.0, 1.2, 1.0, 0.2);
      level.playSound(null, target.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.HOSTILE, 4.0F, 0.32F);
   }

   private static void mudhand(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      DamageSource source = source(level, boss, TensuraDamageTypes.EARTH_ELEMENTAL);
      hurt(target, source, 900.0F);
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 120, 3, true, false, true));
      level.sendParticles(
         new BlockParticleOption(ParticleTypes.BLOCK, Blocks.MUD.defaultBlockState()),
         target.getX(),
         target.getY() + 0.1,
         target.getZ(),
         180,
         3.0,
         0.35,
         3.0,
         0.22
      );
      level.playSound(null, target.blockPosition(), SoundEvents.MUD_BREAK, SoundSource.HOSTILE, 4.0F, 0.6F);
   }

   private static void thunderRain(ServerLevel level, AvalonNovariEntity boss, LivingEntity target) {
      for (int i = 0; i < 7; i++) {
         Vec3 pos = target.position().add((level.random.nextDouble() - 0.5) * 16.0, 0.0, (level.random.nextDouble() - 0.5) * 16.0);
         areaDamage(level, boss, pos, 4.0, TensuraDamageTypes.LIGHTNING_ELEMENTAL, 750.0F, 0.65);
         level.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x, pos.y + 2.0, pos.z, 70, 2.0, 4.0, 2.0, 0.45);
      }

      level.playSound(null, target.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 4.5F, 0.75F);
   }

   private static void areaDamage(
      ServerLevel level, AvalonNovariEntity boss, Vec3 center, double radius, ResourceKey<DamageType> type, float damage, double lift
   ) {
      DamageSource source = source(level, boss, type);

      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(center, center).inflate(radius), boss::isValidEnemy)) {
         double dist = living.position().distanceTo(center);
         if (!(dist > radius + living.getBbWidth())) {
            double close = 1.0 - Math.min(1.0, dist / radius);
            hurt(living, source, damage * (0.45F + (float)close));
            Vec3 push = living.position().subtract(center);
            if (push.lengthSqr() < 0.001) {
               push = new Vec3(0.0, 1.0, 0.0);
            }

            living.setDeltaMovement(living.getDeltaMovement().add(push.normalize().scale(0.35 + close * 0.5)).add(0.0, lift, 0.0));
            living.hurtMarked = true;
         }
      }
   }

   private static void damageLine(ServerLevel level, AvalonNovariEntity boss, DamageSource source, Vec3 start, Vec3 end, double radius, float damage) {
      for (LivingEntity living : level.getEntitiesOfClass(LivingEntity.class, new AABB(start, end).inflate(radius + 2.0), boss::isValidEnemy)) {
         Vec3 center = living.position().add(0.0, living.getBbHeight() * 0.5, 0.0);
         double distance = distanceToSegment(center, start, end);
         if (!(distance > radius + living.getBbWidth() * 0.5)) {
            double close = 1.0 - Math.min(1.0, distance / radius);
            hurt(living, source, damage * (0.6F + (float)close * 0.8F));
         }
      }
   }

   private static DamageSource source(ServerLevel level, AvalonNovariEntity boss, ResourceKey<DamageType> type) {
      return TensuraDamageTypes.getEntityDamageSource(level, type, boss);
   }

   private static void hurt(LivingEntity target, DamageSource source, float amount) {
      target.invulnerableTime = 0;
      target.hurt(source, amount);
      target.invulnerableTime = 0;
   }

   private static ManasSkillInstance magic(AvalonNovariEntity boss, ManasSkill skill) {
      try {
         SkillHelper.learnSkill(boss, skill);
      } catch (Throwable var3) {
      }

      return SkillUtils.getSkillOrNull(boss, skill);
   }

   private static Vec3 castPos(AvalonNovariEntity boss) {
      boss.lookAt(Anchor.EYES, boss.resolveTarget() == null ? boss.getEyePosition().add(boss.getLookAngle()) : boss.resolveTarget().getEyePosition());
      float angle = (float) (Math.PI / 180.0) * boss.getYRot();
      double xOffset = Math.sin(Math.PI + angle);
      double zOffset = Math.cos(angle);
      return new Vec3(boss.getX() + xOffset, boss.getEyeY(), boss.getZ() + zOffset);
   }

   private static double distanceToSegment(Vec3 point, Vec3 start, Vec3 end) {
      Vec3 line = end.subtract(start);
      double len = line.lengthSqr();
      if (len <= 1.0E-4) {
         return point.distanceTo(start);
      }

      double t = point.subtract(start).dot(line) / len;
      t = Math.max(0.0, Math.min(1.0, t));
      return point.distanceTo(start.add(line.scale(t)));
   }

   private static double getEp(LivingEntity entity) {
      double value = 0.0;
      AttributeInstance aura = entity.getAttribute(TensuraAttributes.MAX_AURA);
      AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
      if (aura != null) {
         value += aura.getValue();
      }

      if (magicule != null) {
         value += magicule.getValue();
      }

      value += entity.getAttributeValue(Attributes.MAX_HEALTH);
      return value + entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
   }

   private static void hurtSpiritual(LivingEntity target, float amount, DamageSource source) {
      try {
         IExistence ex = TensuraStorages.getExistenceFrom(target);
         ex.setSpiritualHealth(Math.max(0.0, ex.getSpiritualHealth() - amount));
         ex.markDirty();
         if (ex.getSpiritualHealth() <= 0.0 && target.isAlive()) {
            target.setHealth(0.0F);
            target.die(source);
         }
      } catch (Throwable var4) {
      }
   }
}
