package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import com.github.b4ndithelps.tennogamenolife.registry.sound.TenreincarnationSounds;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class FarCryUltimateSkill extends Skill {
   private static final double SKILL_CAST_COST = 300000.0;
   private static final double LEARN_COST = 5.0;
   private static final int CHARGE_TIME_TICKS = 600;
   private static final int STRIKE_DELAY_TICKS = 120;
   private static final int STRIKE_VFX_DURATION_TICKS = 180;
   private static final int COOLDOWN_TICKS = 1800;
   private static final double TARGET_RANGE = 64.0;
   private static final float EFFECT_RADIUS = 84.5F;
   private static final float TERRAIN_RADIUS = 32.5F;
   private static final float DAMAGE = 1300.0F;
   private static final float LARGE_EXPLOSION_VFX_RADIUS = 80.0F;
   private static final ResourceLocation FORBIDDEN_DIMENSION = ResourceLocation.fromNamespaceAndPath("tensura", "labyrinth");

   public FarCryUltimateSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/ultimate/far_cry.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 300000.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 5.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!DestructiveSkillSafety.blockIfDisabled(entity, "tennogamenolife.skill.far_cry")) {
         if (entity instanceof Player player) {
            if (this.isInForbiddenDimension(player)) {
               player.displayClientMessage(Component.translatable("skill.far_cry.forbidden_dimension"), true);
            } else if (!this.isOutOfEnergy(player, instance, mode, 1.0F)) {
               Vec3 targetPos = this.getTargetPosition(player, 64.0);
               CompoundTag tag = instance.getOrCreateTag();
               tag.putDouble("FarCryX", targetPos.x);
               tag.putDouble("FarCryY", targetPos.y);
               tag.putDouble("FarCryZ", targetPos.z);
               tag.putInt("FarCryChargeTicks", 0);
               tag.putBoolean("FarCryLocked", true);
               instance.markDirty();
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.getBoolean("FarCryLocked")) {
            return false;
         }

         if (this.isInForbiddenDimension(player)) {
            player.displayClientMessage(Component.translatable("skill.far_cry.forbidden_dimension"), true);
            this.removeFarCryCircle(player, tag, false);
            this.resetFarCryData(instance);
            return false;
         }

         Vec3 targetPos = new Vec3(tag.getDouble("FarCryX"), tag.getDouble("FarCryY"), tag.getDouble("FarCryZ"));
         DragonRoarChargeLock.apply(player, targetPos);
         int chargeTicks = Math.min(600, tag.getInt("FarCryChargeTicks") + 1);
         tag.putInt("FarCryChargeTicks", chargeTicks);
         instance.markDirty();
         float progress = Math.min(1.0F, chargeTicks / 600.0F);
         player.displayClientMessage(Component.translatable("skill.far_cry.charging", new Object[]{String.format("%.0f%%", progress * 100.0F)}), true);
         if (!player.level().isClientSide) {
            this.updateFarCryCircle(player, instance, chargeTicks);
         }

         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("FarCryLocked")) {
            if (tag.getInt("FarCryChargeTicks") < 600) {
               this.removeFarCryCircle(player, tag, false);
               this.resetFarCryData(instance);
            } else {
               Vec3 targetPos = new Vec3(tag.getDouble("FarCryX"), tag.getDouble("FarCryY"), tag.getDouble("FarCryZ"));
               if (player.level() instanceof ServerLevel serverLevel) {
                  this.removeFarCryCircle(player, tag, true);
                  this.startFarCryStrike(serverLevel, player, targetPos, instance, mode);
               }

               instance.setCoolDown(1800, mode);
               this.resetFarCryData(instance);
            }
         }
      }
   }

   private boolean isInForbiddenDimension(LivingEntity entity) {
      return FORBIDDEN_DIMENSION.equals(entity.level().dimension().location());
   }

   private Vec3 getTargetPosition(Player player, double maxDistance) {
      Vec3 eyePos = player.getEyePosition();
      Vec3 endPos = eyePos.add(player.getLookAngle().scale(maxDistance));
      ClipContext context = new ClipContext(eyePos, endPos, Block.OUTLINE, Fluid.NONE, player);
      BlockHitResult result = player.level().clip(context);
      Vec3 aimedPosition = result.getType() == Type.MISS ? endPos : result.getLocation();
      Vec3 groundSearchStart = aimedPosition.add(0.0, 1.0, 0.0);
      Vec3 groundSearchEnd = new Vec3(aimedPosition.x, player.level().getMinBuildHeight() - 1.0, aimedPosition.z);
      BlockHitResult groundResult = player.level().clip(new ClipContext(groundSearchStart, groundSearchEnd, Block.COLLIDER, Fluid.NONE, player));
      return groundResult.getType() == Type.MISS ? aimedPosition : groundResult.getLocation();
   }

   private void updateFarCryCircle(Player player, ManasSkillInstance instance, int chargeTicks) {
      CompoundTag tag = instance.getOrCreateTag();
      Vec3 lookVec = player.getLookAngle();
      Vec3 forwardVec = lookVec.scale(1.5);
      Vec3 circlePos = player.position().add(0.0, 1.6, 0.0).add(forwardVec.x, 0.0, forwardVec.z);
      if (chargeTicks == 1) {
         DragonRoarCircle circle = new DragonRoarCircle((EntityType<?>)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), player.level());
         circle.setPos(circlePos);
         circle.setSize(8.0F);
         circle.setLife(620);
         circle.setYRot(player.getYHeadRot());
         player.level().addFreshEntity(circle);
         tag.putInt("FarCryCircleID", circle.getId());
         instance.markDirty();
      } else {
         if (player.level().getEntity(tag.getInt("FarCryCircleID")) instanceof DragonRoarCircle circle) {
            circle.increaseLife(1);
            circle.setPos(circlePos);
            circle.setYRot(player.getYHeadRot());
         }
      }
   }

   private void removeFarCryCircle(Player player, CompoundTag tag, boolean discardImmediately) {
      if (!player.level().isClientSide) {
         if (player.level().getEntity(tag.getInt("FarCryCircleID")) instanceof DragonRoarCircle circle) {
            if (discardImmediately) {
               circle.discard();
            } else {
               circle.setLife(10);
            }
         }
      }
   }

   private void startFarCryStrike(ServerLevel level, Player caster, Vec3 targetPos, ManasSkillInstance instance, int mode) {
      DragonRoarCircle strikeVfx = new DragonRoarCircle((EntityType<?>)TenreincarnationEntities.DRAGON_ROAR_CIRCLE.get(), level);
      strikeVfx.setPos(targetPos);
      strikeVfx.setStrikeVfx(true);
      strikeVfx.setVfxSeed(level.random.nextInt());
      strikeVfx.setVfxDuration(180);
      strikeVfx.setVfxSecondPhaseDuration(60);
      strikeVfx.setVfxStartGameTime(level.getGameTime());
      strikeVfx.setVfxStartHeight((float)(caster.getY() + 200.0 - targetPos.y));
      strikeVfx.setLife(183);
      level.addFreshEntity(strikeVfx);
      DragonRoarStrikeSequence.startBeamImpactSequence(level, targetPos, () -> this.detonateFarCry(level, caster, targetPos, instance, mode));
   }

   private void detonateFarCry(ServerLevel level, Player caster, Vec3 center, ManasSkillInstance instance, int mode) {
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)TenreincarnationSounds.DRAGON_ROAR_EXPLOSION.get(), SoundSource.PLAYERS, 10.0F, 0.78F);
      SkyStrikeVfx.spawnExplosionExpansion(level, center, 80.0F);

      try {
         MagicExplosion visualExplosion = new MagicExplosion(level, caster);
         visualExplosion.setPos(center);
         visualExplosion.setDamage(0.0F);
         visualExplosion.setSecondaryDamage(0.0F);
         visualExplosion.setSize(80.0F);
         visualExplosion.setVisualOnly(true);
         visualExplosion.setSkill(caster, instance, this, mode, 1.0F);
         level.addFreshEntity(visualExplosion);
      } catch (Exception ignored) {
         level.sendParticles(ParticleTypes.EXPLOSION_EMITTER, center.x, center.y, center.z, 8, 4.0, 2.0, 4.0, 0.0);
      }

      this.damageTargets(level, caster, center, instance, mode);
      if (!DestructiveSkillSafety.shouldPreventTerrainDamage()) {
         float gameRuleLimit = level.getGameRules().getInt(TensuraGameRules.MAXIMUM_MAGIC_EXPLOSION);
         float terrainRadius = Math.min(32.5F, gameRuleLimit * 0.5F);
         TengekiProgressiveDestruction.start(level, center, terrainRadius, 84.5F, caster, instance);
      }
   }

   private void damageTargets(ServerLevel level, Player caster, Vec3 center, ManasSkillInstance instance, int mode) {
      double radiusSquared = 7140.25;
      DamageSource source = this.createSource(instance, caster, TensuraDamageTypes.MAGIC_GENERIC, mode);
      TensuraDamageSource tensuraSource = (TensuraDamageSource)source;
      tensuraSource.tensura$setDodgeBypass();
      tensuraSource.tensura$setBarrierBypassLevel(Float.MAX_VALUE);

      for (LivingEntity target : level.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(center, center).inflate(84.5),
         targetx -> targetx != caster && targetx.isAlive() && targetx.getBoundingBox().distanceToSqr(center) <= radiusSquared
      )) {
         target.invulnerableTime = 0;
         target.hurt(source, 1300.0F);
      }
   }

   private void resetFarCryData(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putDouble("FarCryX", 0.0);
      tag.putDouble("FarCryY", 0.0);
      tag.putDouble("FarCryZ", 0.0);
      tag.putInt("FarCryChargeTicks", 0);
      tag.putBoolean("FarCryLocked", false);
      tag.putInt("FarCryCircleID", 0);
      instance.markDirty();
   }
}
