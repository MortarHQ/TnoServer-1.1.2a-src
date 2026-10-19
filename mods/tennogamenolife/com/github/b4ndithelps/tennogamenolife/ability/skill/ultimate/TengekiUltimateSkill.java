package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.ability.skill.intrinsic.FourthProtectionManager;
import com.github.b4ndithelps.tennogamenolife.client.render.ChargeBarRenderer;
import com.github.b4ndithelps.tennogamenolife.registry.effect.ModEffects;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.ExplosionCircle;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class TengekiUltimateSkill extends Skill {
   private static final String LEARNING_VERSION_TAG = "TengekiLearningVersion";
   private static final int LEARNING_VERSION = 1;
   private final double skillCastCost = 100000.0;
   private final double learnCost = 5.0;
   private static final double MAX_RADIUS = 200.0;
   private static final double MIN_RADIUS = 5.0;
   private static final double TARGET_RANGE = 100.0;
   private static final float DAMAGE = 3000.0F;
   public static final long MIN_CHARGE_TIME = 5000L;
   public static final long MAX_CHARGE_TIME = 60000L;
   private boolean isCharging = false;
   private long chargeStartTime = 0L;
   private long lastConsumeTime = 0L;
   private Vec3 targetPos;
   private static final ResourceLocation FORBIDDEN_DIMENSION = ResourceLocation.fromNamespaceAndPath("tensura", "labyrinth");

   public TengekiUltimateSkill() {
      super(SkillType.ULTIMATE);
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return -100;
   }

   public double getDefaultLearningIncrement(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return 1.0;
   }

   public TensuraSkillInstance createLearningInstance(LivingEntity entity) {
      TensuraSkillInstance instance = super.createLearningInstance(entity);
      instance.getOrCreateTag().putInt("TengekiLearningVersion", 1);
      instance.markDirty();
      return instance;
   }

   public static boolean usesCurrentLearningRules(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag != null && tag.getInt("TengekiLearningVersion") >= 1;
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/ultimate/tengeki.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 100000.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 5.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!DestructiveSkillSafety.blockIfDisabled(entity, "tennogamenolife.skill.tengeki")) {
         if (this.isInForbiddenDimension(entity)) {
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("skill.tengeki.forbidden_dimension"), true);
            }
         } else if (!this.isOutOfEnergy(entity, instance, mode, 1.0F)) {
            this.isCharging = true;
            this.chargeStartTime = System.currentTimeMillis();
            this.lastConsumeTime = this.chargeStartTime;
            this.targetPos = this.findTargetPosition(entity);
            ChargeBarRenderer.startCharging();
            CompoundTag tag = instance.getOrCreateTag();
            tag.putDouble("circleX", this.targetPos.x);
            tag.putDouble("circleY", this.targetPos.y);
            tag.putDouble("circleZ", this.targetPos.z);
            tag.putInt("PowerScale", 0);
            instance.markDirty();
         }
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      if (this.isCharging && this.isInForbiddenDimension(entity)) {
         this.isCharging = false;
         ChargeBarRenderer.stopCharging();
         this.stopMagicCircles(entity, instance);
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("skill.tengeki.forbidden_dimension"), true);
         }

         return false;
      } else {
         return this.isCharging;
      }
   }

   private boolean isInForbiddenDimension(LivingEntity entity) {
      ResourceLocation dimensionLocation = entity.level().dimension().location();
      return FORBIDDEN_DIMENSION.equals(dimensionLocation);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (this.isCharging) {
         long chargeTime = System.currentTimeMillis() - this.chargeStartTime;
         if (chargeTime >= 5000L) {
            double chargeProgress = Math.min(1.0, chargeTime / 60000.0);
            double currentRadius = 5.0 + 195.0 * chargeProgress;
            this.triggerExplosion(instance, entity, currentRadius);
            instance.setCoolDown(3600, mode);
         }

         this.isCharging = false;
         ChargeBarRenderer.stopCharging();
         this.stopMagicCircles(entity, instance);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (!this.isCharging) {
         return false;
      }

      long currentTime = System.currentTimeMillis();
      long chargeTime = currentTime - this.chargeStartTime;
      CompoundTag tag = instance.getOrCreateTag();
      double chargeProgress = Math.min(1.0, chargeTime / 60000.0);
      int powerScale = (int)(chargeProgress * 100.0);
      tag.putInt("PowerScale", powerScale);
      instance.markDirty();
      double currentRadius = 5.0 + 195.0 * chargeProgress;
      float power = (float)(currentRadius / 200.0 * 10.0);
      if (this.targetPos != null) {
         ExplosionCircle.castMagicCircle(50.0F, power, 21, this.targetPos, entity, tag, instance, 0, Pair.of(0.0, 0.0));
         MagicCircle.castMagicCircle(
            "MagicCircleID2",
            entity.getBbWidth() * 4.0F,
            25,
            MagicCircleVariant.EXPLOSION,
            true,
            entity,
            tag,
            0.0F,
            0.0F,
            Vec3.ZERO,
            instance,
            0,
            Pair.of(0.0, 0.0)
         );
         MagicCircle.castMagicCircle(
            "MagicCircleID3", 1.25F, 25, MagicCircleVariant.EXPLOSION, true, entity, tag, 1.25F, 0.0F, Vec3.ZERO, instance, 0, Pair.of(0.0, 0.0)
         );
      }

      if (chargeTime >= 10000L && currentTime - this.lastConsumeTime >= 1000L && entity instanceof Player player) {
         if (this.isOutOfEnergy(player, instance, 0.0, 15000.0 / this.getMagiculeCost(player, instance, 0))) {
            DamageSource drainSource = TensuraDamageTypes.getDamageSource(player.level(), TensuraDamageTypes.ENERGY_DRAIN);
            player.hurt(drainSource, Float.MAX_VALUE);
            this.isCharging = false;
            ChargeBarRenderer.stopCharging();
            this.stopMagicCircles(entity, instance);
            return false;
         }

         this.lastConsumeTime = currentTime;
      }

      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
   }

   private void stopMagicCircles(LivingEntity entity, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putDouble("circleX", 0.0);
      tag.putDouble("circleY", 0.0);
      tag.putDouble("circleZ", 0.0);
      tag.putInt("PowerScale", 0);
      instance.markDirty();
   }

   private Vec3 findTargetPosition(LivingEntity entity) {
      if (entity instanceof Player player) {
         Level level = player.level();
         Vec3 startPos = player.getEyePosition(1.0F);
         Vec3 lookVec = player.getViewVector(1.0F);
         Vec3 endPos = startPos.add(lookVec.scale(100.0));
         BlockHitResult hitResult = level.clip(new ClipContext(startPos, endPos, Block.COLLIDER, Fluid.NONE, player));
         return hitResult.getType() != Type.MISS ? hitResult.getLocation() : endPos;
      } else {
         return null;
      }
   }

   private void triggerExplosion(ManasSkillInstance instance, LivingEntity entity, double radius) {
      if (this.targetPos != null) {
         Level level = entity.level();
         long chargeTime = System.currentTimeMillis() - this.chargeStartTime;
         float explosionPower = (float)radius;
         if (chargeTime >= 5000L) {
            explosionPower *= 1.5F;
         }

         if (chargeTime >= 20000L) {
            explosionPower *= 1.5F;
         }

         if (chargeTime >= 40000L) {
            explosionPower *= 1.5F;
         }

         if (chargeTime >= 59999L) {
            explosionPower *= 2.0F;
         }

         if (chargeTime >= 20000L && entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(ModEffects.WEAKEN, 12000, 0));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 12000, 1));
         }

         float limitedPower = Math.min(explosionPower, 130.0F);
         boolean preventTerrainDamage = DestructiveSkillSafety.shouldPreventTerrainDamage();
         float terrainRadius = limitedPower;
         if (level instanceof ServerLevel serverLevel) {
            terrainRadius = Math.min(terrainRadius, serverLevel.getGameRules().getInt(TensuraGameRules.MAXIMUM_MAGIC_EXPLOSION));
            terrainRadius = Math.min(terrainRadius, 50.0F);
         }

         try {
            MagicExplosion magicExplosion = new MagicExplosion(level, entity);
            magicExplosion.setPos(this.targetPos.x, this.targetPos.y, this.targetPos.z);
            magicExplosion.setDamage(0.0F);
            magicExplosion.setSecondaryDamage(0.0F);
            magicExplosion.setSize(limitedPower);
            magicExplosion.setVisualOnly(true);
            if (entity instanceof Player player) {
               magicExplosion.setSkill(player, instance, this, 0, 1.0F);
            }

            level.addFreshEntity(magicExplosion);
            level.playSound(null, this.targetPos.x, this.targetPos.y, this.targetPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 10.0F, 0.5F);
         } catch (Exception e) {
            System.err.println("Failed to create magic explosion effect: " + e.getMessage());
            level.playSound(null, this.targetPos.x, this.targetPos.y, this.targetPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);
            level.explode(null, this.targetPos.x, this.targetPos.y, this.targetPos.z, limitedPower, ExplosionInteraction.NONE);

            for (int i = 0; i < 50; i++) {
               double angle = level.random.nextDouble() * Math.PI * 2.0;
               double distance = level.random.nextDouble() * limitedPower;
               double x = this.targetPos.x + Math.cos(angle) * distance;
               double y = this.targetPos.y + level.random.nextDouble() * limitedPower;
               double z = this.targetPos.z + Math.sin(angle) * distance;
               level.addParticle(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 0.0, 0.0, 0.0);
            }
         }

         if (level instanceof ServerLevel serverLevel) {
            this.damageAffectedEntities(instance, entity, serverLevel, this.targetPos, limitedPower);
            TengekiWitherZone.start(serverLevel, this.targetPos, limitedPower);
            if (!preventTerrainDamage) {
               TengekiProgressiveDestruction.start(serverLevel, this.targetPos, terrainRadius, limitedPower, entity, instance);
            }
         }
      }
   }

   private void damageAffectedEntities(ManasSkillInstance instance, LivingEntity caster, ServerLevel level, Vec3 center, float radius) {
      double radiusSquared = radius * radius;
      DamageSource damageSource = this.createSource(instance, caster, TensuraDamageTypes.MAGIC_GENERIC, 0);
      TensuraDamageSource tensuraSource = (TensuraDamageSource)damageSource;
      tensuraSource.tensura$setDodgeBypass();
      tensuraSource.tensura$setBarrierBypassLevel(Float.MAX_VALUE);

      for (LivingEntity target : level.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(center, center).inflate(radius),
         targetx -> targetx != caster && targetx.isAlive() && targetx.getBoundingBox().distanceToSqr(center) <= radiusSquared
      )) {
         if (!FourthProtectionManager.isInsideBarrier(level, target.getBoundingBox().getCenter())) {
            target.invulnerableTime = 0;
            target.hurt(damageSource, 3000.0F);
         }
      }
   }
}
