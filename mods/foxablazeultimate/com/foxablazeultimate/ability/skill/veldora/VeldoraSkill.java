package com.foxablazeultimate.ability.skill.veldora;

import com.foxablazeultimate.config.VeldoraConfig;
import com.foxablazeultimate.entity.VeldoraBlackMistProjectile;
import com.foxablazeultimate.registry.FoxAblazeUltimateEntityTypes;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.entity.magic.lightning.BlackLightningBolt;
import io.github.manasmods.tensura.entity.projectile.magic.WindBladeProjectile;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class VeldoraSkill extends Skill {
   private static final String DOMAIN_TICKS_TAG = "VeldoraDomainTicks";
   private static final String DOMAIN_CLOCK_TAG = "VeldoraDomainClock";
   private static final String DOMAIN_START_TIME_TAG = "VeldoraDomainStartTime";
   private static final String DOMAIN_END_TIME_TAG = "VeldoraDomainEndTime";
   private static final String DOMAIN_WEATHER_LEVEL_TAG = "VeldoraDomainWeatherLevel";
   private static final String DOMAIN_WEATHER_CHANGED_TAG = "VeldoraDomainWeatherChanged";
   private static final String DOMAIN_WEATHER_WAS_RAINING_TAG = "VeldoraDomainWeatherWasRaining";
   private static final String DOMAIN_WEATHER_WAS_THUNDERING_TAG = "VeldoraDomainWeatherWasThundering";
   private static final String SLASH_CASTING_TAG = "VeldoraSlashCasting";
   private static final String SLASH_MIST_TAG = "VeldoraSlashMist";
   private static final String SLASH_TARGET_X_TAG = "VeldoraSlashTargetX";
   private static final String SLASH_TARGET_Y_TAG = "VeldoraSlashTargetY";
   private static final String SLASH_TARGET_Z_TAG = "VeldoraSlashTargetZ";
   private static final String SLASH_TRIGGERED_TAG = "VeldoraSlashTriggered";
   private static final String SLASH_ANIMATION_START_TAG = "VeldoraSlashAnimationStart";
   private static final String SLASH_RAISE_TICKS_TAG = "VeldoraSlashRaiseTicks";
   private static final String SLASH_SWING_TICKS_TAG = "VeldoraSlashSwingTicks";
   private static final int MODE_COUNT = 4;
   private static final int MODE_BREATH = 0;
   private static final int MODE_LIGHTNING = 1;
   private static final int MODE_DOMAIN = 2;
   private static final int MODE_SLASH = 3;

   private static VeldoraConfig.VeldoraSettings cfg() {
      return VeldoraConfig.get().Veldora;
   }

   public VeldoraSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/veldora.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.AQUA);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 400000.0;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   private static int sanitizeMode(int mode) {
      return Math.floorMod(mode, 4);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safe = sanitizeMode(mode);
      if (reverse) {
         return safe == 0 ? 3 : safe - 1;
      } else {
         return safe == 3 ? 0 : safe + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (sanitizeMode(mode)) {
         case 0 -> "veldora.breath";
         case 1 -> "veldora.lightning";
         case 2 -> "veldora.domain";
         case 3 -> "veldora.slash";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      VeldoraConfig.VeldoraSettings c = cfg();
      boolean mastered = instance.isMastered(entity);

      return switch (sanitizeMode(mode)) {
         case 0 -> mastered ? c.magiculeCostBreathMastered : c.magiculeCostBreath;
         case 1 -> mastered ? c.magiculeCostLightningMastered : c.magiculeCostLightning;
         case 2 -> mastered ? c.magiculeCostDomainMastered : c.magiculeCostDomain;
         case 3 -> mastered ? c.magiculeCostSlashMastered : c.magiculeCostSlash;
         default -> 0.0;
      };
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         applyPassiveAttributes(entity, instance);
         if (entity instanceof ServerPlayer player) {
            this.registerTrulyUnique(player);
         }
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      if (!instance.isTemporarySkill()) {
         applyPassiveAttributes(entity, instance);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      this.finishSlash(instance, entity, 3, true);
      removePassiveAttributes(entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      this.stopSlashState(instance, entity);
      this.stopDomainState(instance, entity);
      removePassiveAttributes(entity);
      if (entity instanceof ServerPlayer player) {
         this.unregisterTrulyUnique(player);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      this.stopSlashState(instance, owner);
      this.stopDomainState(instance, owner);
      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource damageSource, Changeable<Float> amount) {
      if (!this.isInSlot(owner, instance)) {
         return true;
      }

      VeldoraConfig.VeldoraSettings c = cfg();
      if (TensuraDamageHelper.isWindDamage(damageSource)) {
         amount.set(reduceDamage((Float)amount.get(), c.windDamageReduction));
      } else if (TensuraDamageHelper.isLightningDamage(damageSource)) {
         amount.set(reduceDamage((Float)amount.get(), c.thunderDamageReduction));
      }

      if ((Float)amount.get() > 0.0F && isSlashAnimationActive(instance)) {
         this.finishSlash(instance, owner, 3, true);
      }

      if (owner.getRandom().nextFloat() < c.counterAttackChance
         && damageSource.getEntity() instanceof LivingEntity attacker
         && attacker.distanceTo(owner) <= 3.0) {
         Vec3 knockback = attacker.position().subtract(owner.position()).normalize().scale(c.counterKnockback);
         attacker.push(knockback.x, 0.3, knockback.z);
      }

      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      return tag != null && hasDomainState(tag);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!(entity instanceof ServerPlayer)) {
         this.tickDomain(instance, entity);
      }
   }

   public int getMaxHeldTime(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.getBoolean("VeldoraSlashCasting")) {
         int animationTicks = getSlashRaiseTicks(tag) + getSlashSwingTicks(tag) + 1;
         return Math.max(animationTicks, Math.max(1, cfg().slashMaxHoldTicks));
      } else {
         return super.getMaxHeldTime(instance, entity);
      }
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return sanitizeMode(mode) == 3 ? true : super.shouldTriggerReleaseOnHeldInterrupt(instance, entity, keyNumber, mode);
   }

   public void tickDomain(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getTag();
         if (tag != null) {
            if (this.tickDomainInternal(instance, entity, tag, cfg())) {
               instance.markDirty();
            }
         }
      }
   }

   private boolean tickDomainInternal(ManasSkillInstance instance, LivingEntity entity, CompoundTag tag, VeldoraConfig.VeldoraSettings c) {
      boolean dirty = false;
      long now = getDomainClock(entity, tag);
      long endTime = tag.getLong("VeldoraDomainEndTime");
      if (endTime <= 0L && tag.getInt("VeldoraDomainTicks") > 0) {
         endTime = now + tag.getInt("VeldoraDomainTicks");
         tag.putLong("VeldoraDomainStartTime", now);
         tag.putLong("VeldoraDomainEndTime", endTime);
         dirty = true;
      }

      if (endTime <= 0L) {
         if (tag.getBoolean("VeldoraDomainWeatherChanged")) {
            this.restoreDomainWeather(entity, tag);
            removeDomainTags(tag);
            return true;
         } else {
            return dirty;
         }
      } else {
         long remaining = endTime - now;
         if (remaining > 0L && entity.isAlive()) {
            long startTime = tag.getLong("VeldoraDomainStartTime");
            if (startTime <= 0L || startTime > now) {
               startTime = Math.max(0L, endTime - getDomainDurationTicks(c, instance, entity));
               tag.putLong("VeldoraDomainStartTime", startTime);
               dirty = true;
            }

            dirty |= this.maintainDomainThunderstorm(entity, tag, c);
            long clock = Math.max(0L, now - startTime);
            double domainRadius = getDomainRadius(c, instance, entity);
            int visualInterval = Math.max(1, c.domainBarrierVisualInterval);
            if (clock > 0L && clock % visualInterval == 0L) {
               this.spawnDomainBarrierVisual(entity, domainRadius, c.domainBarrierShellPoints, (int)Math.min(2147483647L, clock));
            }

            int interval = Math.max(1, c.domainTickInterval);
            if (clock > 0L && clock % interval == 0L) {
               this.doDomainPulse(instance, entity, 2, shouldShootDomainWindBlade(c, clock));
               instance.addMasteryPoint(entity);
               dirty = true;
            }

            int lightningInterval = Math.max(1, c.domainVanillaLightningIntervalTicks);
            if (clock > 0L && clock % lightningInterval == 0L) {
               this.doDomainVanillaLightningPulse(instance, entity, c);
            }

            int remainingTicks = (int)Math.min(2147483647L, remaining);
            if (tag.getInt("VeldoraDomainTicks") != remainingTicks && clock % 20L == 0L) {
               tag.putInt("VeldoraDomainTicks", remainingTicks);
               tag.putInt("VeldoraDomainClock", (int)Math.min(2147483647L, clock));
               dirty = true;
            }

            return dirty;
         } else {
            this.restoreDomainWeather(entity, tag);
            removeDomainTags(tag);
            return true;
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (sanitizeMode(mode)) {
         case 0:
            this.onBreathPressed(instance, entity, mode);
            break;
         case 1:
            this.onLightning(instance, entity, mode);
            break;
         case 2:
            this.onDomain(instance, entity, mode);
            break;
         case 3:
            this.onSlashPressed(instance, entity, mode);
      }
   }

   private void onBreathPressed(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!instance.onCoolDown(mode)) {
         instance.getOrCreateTag().putInt("BreathEntity", 0);
         instance.markDirty();
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      int safeMode = sanitizeMode(mode);
      if (safeMode == 3) {
         return this.onSlashHeld(instance, entity, heldTicks, safeMode);
      }

      if (safeMode != 0) {
         return false;
      }

      if (instance.onCoolDown(mode)) {
         return false;
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      VeldoraConfig.VeldoraSettings c = cfg();
      float damage = instance.isMastered(entity) ? (float)c.breathDamageMastered : (float)c.breathDamage;
      BreathEntity breath = BreathEntity.spawnBreathEntity((EntityType)MiscEntityTypes.THUNDER_BREATH.get(), entity, instance, damage, this, mode);
      if (breath != null) {
         breath.setLength(c.breathRange);
      }

      this.applyBreathWindPressure(entity, c.breathRange);
      this.spawnBreathStormParticles(entity, c.breathRange, heldTicks);
      if (heldTicks % 2 == 0) {
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BREATH_THUNDER.get(), SoundSource.PLAYERS, 0.75F, 1.0F);
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      int safeMode = sanitizeMode(mode);
      if (safeMode == 3) {
         this.finishSlash(instance, entity, safeMode, true);
      } else if (safeMode == 0) {
         CompoundTag tag = instance.getTag();
         boolean hadBreath = tag != null && tag.getInt("BreathEntity") != 0;
         if (tag != null) {
            tag.putInt("BreathEntity", 0);
         }

         if ((heldTicks > 0 || hadBreath) && !instance.onCoolDown(safeMode)) {
            instance.setCoolDown(Math.max(1, cfg().breathCooldown), safeMode);
         }
      }
   }

   private void applyBreathWindPressure(LivingEntity entity, double range) {
      if (!entity.level().isClientSide()) {
         Vec3 origin = entity.getEyePosition();
         Vec3 look = entity.getLookAngle().normalize();
         double safeRange = Math.max(1.0, range);

         for (LivingEntity target : entity.level()
            .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(safeRange), t -> !t.is(entity) && t.isAlive() && !t.isAlliedTo(entity))) {
            Vec3 toTarget = target.getEyePosition().subtract(origin);
            double forward = toTarget.dot(look);
            if (!(forward <= 0.0) && !(forward > safeRange)) {
               double coneRadius = 1.0 + forward * 0.22;
               Vec3 closest = origin.add(look.scale(forward));
               if (!(target.getEyePosition().distanceTo(closest) > coneRadius)) {
                  target.push(look.x * 0.25, 0.08, look.z * 0.25);
               }
            }
         }
      }
   }

   private void spawnBreathStormParticles(LivingEntity entity, double range, int heldTicks) {
      if (!entity.level().isClientSide() && heldTicks % 2 == 0) {
         Vec3 look = entity.getLookAngle().normalize();
         Vec3 side = safeSide(look);
         Vec3 up = safeNormalize(side.cross(look), new Vec3(0.0, 1.0, 0.0));
         Vec3 start = entity.getEyePosition().add(look.scale(1.2));
         double safeRange = Math.max(1.0, range);
         int points = Math.max(4, Math.min(10, (int)Math.ceil(safeRange / 2.0)));

         for (int i = 0; i < points; i++) {
            double distance = 0.75 + i * (safeRange / points);
            double spread = 0.12 + distance * 0.05;
            double sideOffset = (entity.getRandom().nextDouble() - 0.5) * spread;
            double upOffset = (entity.getRandom().nextDouble() - 0.5) * spread;
            Vec3 pos = start.add(look.scale(distance)).add(side.scale(sideOffset)).add(up.scale(upOffset));
            TensuraParticleHelper.spawnServerParticles(
               entity.level(), (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), pos.x, pos.y, pos.z, 2, 0.08, 0.08, 0.08, 0.05, true
            );
            TensuraParticleHelper.spawnServerParticles(
               entity.level(), (ParticleOptions)TensuraParticleTypes.LIGHTNING_EFFECT.get(), pos.x, pos.y, pos.z, 1, 0.04, 0.04, 0.04, 0.03, true
            );
            if (i % 2 == 0) {
               TensuraParticleHelper.spawnServerParticles(
                  entity.level(), TensuraParticleUtils.getGreenGust(), pos.x, pos.y, pos.z, 1, 0.05, 0.05, 0.05, 0.02, true
               );
            }
         }
      }
   }

   private void onLightning(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!instance.onCoolDown(mode)) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            VeldoraConfig.VeldoraSettings c = cfg();
            LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, c.lightningRange, false);
            Vec3 pos;
            if (target != null) {
               pos = target.position();
            } else {
               BlockHitResult hit = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, c.lightningRange);
               pos = hit.getLocation();
            }

            float damage = (float)(instance.isMastered(entity) ? c.lightningDamageMastered : c.lightningDamage);
            this.spawnBlackLightning(instance, entity, mode, pos, damage, damage * 0.35F, c.lightningRadius, c.lightningAdditionalVisual);
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get(), SoundSource.PLAYERS, 1.0F, 1.0F
               );
            instance.addMasteryPoint(entity);
            instance.setCoolDown(getLightningCooldown(c, instance, entity), mode);
            entity.swing(InteractionHand.MAIN_HAND, true);
         }
      }
   }

   private void onDomain(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!instance.onCoolDown(mode)) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            VeldoraConfig.VeldoraSettings c = cfg();
            CompoundTag tag = instance.getOrCreateTag();
            int domainDurationTicks = getDomainDurationTicks(c, instance, entity);
            long now = getDomainClock(entity, tag);
            tag.putInt("VeldoraDomainTicks", domainDurationTicks);
            tag.putInt("VeldoraDomainClock", 0);
            tag.putLong("VeldoraDomainStartTime", now);
            tag.putLong("VeldoraDomainEndTime", now + domainDurationTicks);
            instance.markDirty();
            double domainRadius = getDomainRadius(c, instance, entity);
            this.startDomainThunderstorm(entity, tag, c, domainDurationTicks);
            this.spawnDomainOpeningVisual(entity, domainRadius);
            this.spawnDomainBarrierVisual(entity, domainRadius, c.domainBarrierShellPoints, 0);
            this.doDomainOpeningBlackLightning(instance, entity, mode, c);
            this.doDomainPulse(instance, entity, mode, false);
            entity.level()
               .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_WIND.get(), SoundSource.PLAYERS, 1.5F, 0.6F);
            instance.addMasteryPoint(entity);
            instance.setCoolDown(getDomainCooldown(c, instance, entity), mode);
         }
      }
   }

   private void onSlashPressed(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!instance.onCoolDown(mode)) {
         if (!isHoldingSword(entity)) {
            if (!entity.level().isClientSide() && entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("foxablazeultimate.skill.veldora.slash.requires_sword"), true);
            }
         } else {
            VeldoraConfig.VeldoraSettings c = cfg();
            CompoundTag tag = instance.getOrCreateTag();
            this.stopSlashMist(entity, tag);
            clearSlashTags(tag);
            tag.putBoolean("VeldoraSlashCasting", true);
            tag.putBoolean("VeldoraSlashTriggered", false);
            tag.putInt("VeldoraSlashMist", 0);
            tag.putLong("VeldoraSlashAnimationStart", entity.level().getGameTime());
            tag.putInt("VeldoraSlashRaiseTicks", Math.max(1, c.slashRaiseTicks));
            tag.putInt("VeldoraSlashSwingTicks", Math.max(1, c.slashSwingTicks));
            Vec3 target = getSlashTarget(entity, c);
            tag.putDouble("VeldoraSlashTargetX", target.x);
            tag.putDouble("VeldoraSlashTargetY", target.y);
            tag.putDouble("VeldoraSlashTargetZ", target.z);
            instance.markDirty();
            this.lockSlashCaster(entity, tag);
         }
      }
   }

   private boolean onSlashHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (instance.onCoolDown(mode)) {
         this.finishSlash(instance, entity, mode, false);
         return false;
      }

      CompoundTag tag = instance.getTag();
      if (tag == null || !tag.getBoolean("VeldoraSlashCasting")) {
         return false;
      }

      if (entity.level().isClientSide()) {
         return true;
      }

      if (!isHoldingSword(entity)) {
         this.finishSlash(instance, entity, mode, true);
         return false;
      }

      this.lockSlashCaster(entity, tag);
      int strikeTick = getSlashRaiseTicks(tag) + getSlashSwingTicks(tag);
      boolean triggered = tag.getBoolean("VeldoraSlashTriggered");
      if (!triggered) {
         if (heldTicks < strikeTick) {
            return true;
         }

         if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            this.finishSlash(instance, entity, mode, false);
            return false;
         }

         tag.putBoolean("VeldoraSlashTriggered", true);
         instance.markDirty();
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get(), SoundSource.PLAYERS, 1.0F, 0.75F);
         instance.addMasteryPoint(entity);
      }

      int activeTicks = Math.max(0, heldTicks - strikeTick);
      if (activeTicks > 0 && activeTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         this.finishSlash(instance, entity, mode, true);
         return false;
      }

      VeldoraConfig.VeldoraSettings c = cfg();
      this.spawnOrUpdateSlashMist(instance, entity, mode, c);
      if (activeTicks > 0 && activeTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0) {
         instance.addMasteryPoint(entity);
      }

      if (activeTicks > 0 && activeTicks % 6 == 0) {
         entity.level()
            .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.PREDATION.get(), SoundSource.PLAYERS, 0.75F, 0.65F);
      }

      return true;
   }

   private void spawnOrUpdateSlashMist(ManasSkillInstance instance, LivingEntity entity, int mode, VeldoraConfig.VeldoraSettings c) {
      if (!entity.level().isClientSide()) {
         CompoundTag tag = instance.getOrCreateTag();
         VeldoraBlackMistProjectile mist = null;
         if ((tag.getInt("VeldoraSlashMist") == 0 ? null : entity.level().getEntity(tag.getInt("VeldoraSlashMist"))) instanceof VeldoraBlackMistProjectile existing
            && existing.isAlive()) {
            mist = existing;
         }

         boolean mastered = instance.isMastered(entity);
         float damage = (float)(mastered ? c.slashDamageMastered : c.slashDamage);
         float range = Math.max(c.slashRange, c.slashMistRange);
         if (mist == null) {
            mist = (VeldoraBlackMistProjectile)((EntityType)FoxAblazeUltimateEntityTypes.VELDORA_BLACK_MIST.get()).create(entity.level());
            if (mist == null) {
               return;
            }

            mist.setOwner(entity);
            mist.setFollowingOwner(true);
            mist.setLife(Math.max(35, c.slashMistLifeTicks));
            mist.setRange(Math.min(3.0F, range));
            mist.setSize(1.5F);
            mist.setPos(entity.getEyePosition().add(entity.getLookAngle().normalize().scale(0.5)));
            mist.setSkill(instance);
            mist.setMode(mode);
            mist.setApCost(0.0);
            mist.setMpCost(0.0);
            entity.level().addFreshEntity(mist);
            mist.triggerAnim("controller", "start");
            tag.putInt("VeldoraSlashMist", mist.getId());
            instance.markDirty();
         } else {
            mist.setAge(0);
         }

         mist.setFollowingOwner(true);
         mist.setAttackingRange(range);
         mist.setDamage(damage);
         mist.setSecondaryDamage(damage * Math.max(0.0F, c.slashMistSecondaryDamageMultiplier));
         mist.setKnockForce(c.slashMistKnockback);
         mist.setDamageIntervalTicks(c.slashMistDamageIntervalTicks);
      }
   }

   private void finishSlash(ManasSkillInstance instance, LivingEntity entity, int mode, boolean applyCooldown) {
      CompoundTag tag = instance.getTag();
      if (tag != null) {
         boolean hadSlash = hasSlashState(tag);
         boolean hadMist = tag.getInt("VeldoraSlashMist") != 0;
         this.stopSlashMist(entity, tag);
         clearSlashTags(tag);
         entity.setDeltaMovement(Vec3.ZERO);
         if (hadMist && applyCooldown && !instance.onCoolDown(mode)) {
            instance.setCoolDown(getCooldown(cfg().slashCooldown, instance, entity), mode);
         }

         if (hadSlash) {
            instance.markDirty();
         }
      }
   }

   public void stopSlashState(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      if (tag != null && hasSlashState(tag)) {
         this.stopSlashMist(entity, tag);
         clearSlashTags(tag);
         instance.markDirty();
      }
   }

   public void validateSlashState(ManasSkillInstance instance, LivingEntity entity, boolean slashHeld) {
      if (isSlashAnimationActive(instance)) {
         if (!slashHeld || !entity.isAlive() || !isHoldingSword(entity)) {
            this.finishSlash(instance, entity, 3, true);
         }
      }
   }

   public static boolean isSlashMode(int mode) {
      return sanitizeMode(mode) == 3;
   }

   public static boolean isSlashAnimationActive(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag != null && tag.getBoolean("VeldoraSlashCasting");
   }

   public static long getSlashAnimationStartTime(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag == null ? 0L : tag.getLong("VeldoraSlashAnimationStart");
   }

   public static int getSlashAnimationRaiseTicks(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag == null ? 1 : getSlashRaiseTicks(tag);
   }

   public static int getSlashAnimationSwingTicks(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag == null ? 1 : getSlashSwingTicks(tag);
   }

   public static Vec3 getSlashLockedTarget(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag != null && tag.getBoolean("VeldoraSlashCasting") && hasSlashTarget(tag)
         ? new Vec3(tag.getDouble("VeldoraSlashTargetX"), tag.getDouble("VeldoraSlashTargetY"), tag.getDouble("VeldoraSlashTargetZ"))
         : null;
   }

   private void stopSlashMist(LivingEntity entity, CompoundTag tag) {
      if (!entity.level().isClientSide()) {
         int mistId = tag.getInt("VeldoraSlashMist");
         if (mistId != 0) {
            if (entity.level().getEntity(mistId) instanceof VeldoraBlackMistProjectile mist && mist.isAlive()) {
               mist.setFollowingOwner(false);
               mist.setRemoveIn(25);
            }
         }
      }
   }

   private void lockSlashCaster(LivingEntity entity, CompoundTag tag) {
      if (entity instanceof Player player) {
         if (!hasSlashTarget(tag)) {
            return;
         }

         VeldoraSlashViewLock.apply(
            player, new Vec3(tag.getDouble("VeldoraSlashTargetX"), tag.getDouble("VeldoraSlashTargetY"), tag.getDouble("VeldoraSlashTargetZ"))
         );
      } else {
         entity.setDeltaMovement(Vec3.ZERO);
      }
   }

   private static Vec3 getSlashTarget(LivingEntity entity, VeldoraConfig.VeldoraSettings c) {
      double distance = Math.max(1.0, Math.max(c.slashRange, c.slashMistRange));
      return entity.getEyePosition().add(entity.getLookAngle().normalize().scale(distance));
   }

   private static boolean hasSlashState(CompoundTag tag) {
      return tag.getBoolean("VeldoraSlashCasting") || tag.getInt("VeldoraSlashMist") != 0;
   }

   private static boolean hasSlashTarget(CompoundTag tag) {
      return tag.contains("VeldoraSlashTargetX") && tag.contains("VeldoraSlashTargetY") && tag.contains("VeldoraSlashTargetZ");
   }

   private static boolean isHoldingSword(LivingEntity entity) {
      ItemStack stack = entity.getMainHandItem();
      return stack.is(ItemTags.SWORDS) || stack.getItem() instanceof SwordItem;
   }

   private static int getSlashRaiseTicks(CompoundTag tag) {
      return Math.max(1, tag.getInt("VeldoraSlashRaiseTicks"));
   }

   private static int getSlashSwingTicks(CompoundTag tag) {
      return Math.max(1, tag.getInt("VeldoraSlashSwingTicks"));
   }

   private static void clearSlashTags(CompoundTag tag) {
      tag.remove("VeldoraSlashCasting");
      tag.remove("VeldoraSlashTriggered");
      tag.remove("VeldoraSlashMist");
      tag.remove("VeldoraSlashAnimationStart");
      tag.remove("VeldoraSlashRaiseTicks");
      tag.remove("VeldoraSlashSwingTicks");
      tag.remove("VeldoraSlashTargetX");
      tag.remove("VeldoraSlashTargetY");
      tag.remove("VeldoraSlashTargetZ");
   }

   private void shootWindBlade(ManasSkillInstance instance, LivingEntity entity, int mode, float damage, Vec3 offset) {
      WindBladeProjectile blade = new WindBladeProjectile(entity.level(), entity);
      blade.setSpeed(2.0F);
      blade.setDamage(damage);
      blade.setSecondaryDamage(damage * 0.3F);
      blade.setKnockForce(3.0F);
      blade.setSkill(entity, instance, this, mode);
      blade.setNoGravity(true);
      blade.setPos(entity.getEyePosition().add(offset).add(entity.getLookAngle().normalize()));
      blade.shootFromRot(entity.getLookAngle());
      entity.level().addFreshEntity(blade);
   }

   private void doDomainPulse(ManasSkillInstance instance, LivingEntity entity, int mode, boolean shootWindBlades) {
      if (!entity.level().isClientSide()) {
         VeldoraConfig.VeldoraSettings c = cfg();
         double radius = getDomainRadius(c, instance, entity);
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getGreenGust(),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5F,
            entity.getZ(),
            24,
            radius * 0.45,
            0.4,
            radius * 0.45,
            0.15,
            true
         );
         List<LivingEntity> targets = entity.level()
            .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), t -> !t.is(entity) && t.isAlive() && !t.isAlliedTo(entity));
         int effectDuration = Math.max(10, c.domainTickInterval + 10);
         int effectLevel = Math.max(0, c.domainMovementInterferenceLevel);

         for (LivingEntity target : targets) {
            pushAway(entity, target, c.domainKnockback);
            TensuraMobEffect.addEffect(
               target,
               new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), effectDuration, effectLevel, false, false, true),
               entity,
               this,
               mode
            );
            if (shootWindBlades) {
               this.shootDomainWindBlade(instance, entity, target, mode, (float)c.domainDamage);
            }
         }
      }
   }

   private void doDomainOpeningBlackLightning(ManasSkillInstance instance, LivingEntity entity, int mode, VeldoraConfig.VeldoraSettings c) {
      if (!entity.level().isClientSide()) {
         double radius = getDomainLightningTargetRadius(c);
         if (!(radius <= 0.0)) {
            float damage = (float)c.domainOpeningBlackLightningDamage;
            float secondaryDamage = (float)c.domainOpeningBlackLightningSecondaryDamage;

            for (LivingEntity target : entity.level()
               .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), t -> isDomainHostileTarget(entity, t))) {
               this.spawnBlackLightning(
                  instance,
                  entity,
                  mode,
                  target.position(),
                  damage,
                  secondaryDamage,
                  c.domainOpeningBlackLightningImpactRadius,
                  c.domainOpeningBlackLightningAdditionalVisual
               );
            }
         }
      }
   }

   private void startDomainThunderstorm(LivingEntity entity, CompoundTag tag, VeldoraConfig.VeldoraSettings c, int durationTicks) {
      if (c.domainThunderstormWeather && entity.level() instanceof ServerLevel serverLevel) {
         boolean var9 = serverLevel.isRaining();
         boolean wasThundering = serverLevel.isThundering();
         tag.putString("VeldoraDomainWeatherLevel", serverLevel.dimension().location().toString());
         tag.putBoolean("VeldoraDomainWeatherWasRaining", var9);
         tag.putBoolean("VeldoraDomainWeatherWasThundering", wasThundering);
         boolean changed = !wasThundering;
         tag.putBoolean("VeldoraDomainWeatherChanged", changed);
         if (changed) {
            serverLevel.setWeatherParameters(0, Math.max(20, durationTicks), true, true);
         }
      }
   }

   private boolean maintainDomainThunderstorm(LivingEntity entity, CompoundTag tag, VeldoraConfig.VeldoraSettings c) {
      if (!c.domainThunderstormWeather) {
         if (tag.getBoolean("VeldoraDomainWeatherChanged")) {
            this.restoreDomainWeather(entity, tag);
            return true;
         } else {
            return false;
         }
      } else {
         ServerLevel serverLevel = resolveDomainWeatherLevel(entity, tag);
         if (serverLevel == null) {
            return false;
         } else if (!serverLevel.isThundering()) {
            tag.putBoolean("VeldoraDomainWeatherChanged", true);
            int remaining = (int)Math.min(2147483647L, Math.max(20L, tag.getLong("VeldoraDomainEndTime") - getDomainClock(entity, tag)));
            serverLevel.setWeatherParameters(0, remaining, true, true);
            return true;
         } else {
            return false;
         }
      }
   }

   private void restoreDomainWeather(LivingEntity entity, CompoundTag tag) {
      ServerLevel serverLevel = resolveDomainWeatherLevel(entity, tag);
      if (tag.getBoolean("VeldoraDomainWeatherChanged") && serverLevel != null) {
         boolean wasRaining = tag.getBoolean("VeldoraDomainWeatherWasRaining");
         boolean wasThundering = tag.getBoolean("VeldoraDomainWeatherWasThundering");
         boolean restoreRaining = wasRaining || wasThundering;
         int clearTime = restoreRaining ? 0 : 12000;
         int rainTime = restoreRaining ? 12000 : 0;
         serverLevel.setWeatherParameters(clearTime, rainTime, restoreRaining, wasThundering);
      }

      tag.remove("VeldoraDomainWeatherChanged");
      tag.remove("VeldoraDomainWeatherWasRaining");
      tag.remove("VeldoraDomainWeatherWasThundering");
      tag.remove("VeldoraDomainWeatherLevel");
   }

   public void stopDomainState(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      if (tag != null) {
         boolean dirty = false;
         if (hasDomainState(tag)) {
            this.restoreDomainWeather(entity, tag);
            removeDomainTags(tag);
            dirty = true;
         }

         if (dirty) {
            instance.markDirty();
         }
      }
   }

   private static boolean hasDomainState(CompoundTag tag) {
      return tag.getInt("VeldoraDomainTicks") > 0 || tag.getLong("VeldoraDomainEndTime") > 0L || tag.getBoolean("VeldoraDomainWeatherChanged");
   }

   private static void removeDomainTags(CompoundTag tag) {
      tag.remove("VeldoraDomainTicks");
      tag.remove("VeldoraDomainClock");
      tag.remove("VeldoraDomainStartTime");
      tag.remove("VeldoraDomainEndTime");
      tag.remove("VeldoraDomainWeatherChanged");
      tag.remove("VeldoraDomainWeatherWasRaining");
      tag.remove("VeldoraDomainWeatherWasThundering");
      tag.remove("VeldoraDomainWeatherLevel");
   }

   private static long getDomainClock(LivingEntity entity, CompoundTag tag) {
      ServerLevel level = resolveDomainWeatherLevel(entity, tag);
      return level == null ? entity.level().getGameTime() : level.getGameTime();
   }

   private static ServerLevel resolveDomainWeatherLevel(LivingEntity entity, CompoundTag tag) {
      MinecraftServer server = entity.getServer();
      if (server != null && tag.contains("VeldoraDomainWeatherLevel")) {
         ResourceLocation levelId = ResourceLocation.tryParse(tag.getString("VeldoraDomainWeatherLevel"));
         if (levelId != null) {
            ServerLevel level = server.getLevel(ResourceKey.create(Registries.DIMENSION, levelId));
            if (level != null) {
               return level;
            }
         }
      }

      return entity.level() instanceof ServerLevel serverLevel ? serverLevel : null;
   }

   private void doDomainVanillaLightningPulse(ManasSkillInstance instance, LivingEntity entity, VeldoraConfig.VeldoraSettings c) {
      if (!entity.level().isClientSide()) {
         double radius = getDomainLightningTargetRadius(c);
         float chance = clampChance(c.domainVanillaLightningChance);
         if (!(radius <= 0.0) && !(chance <= 0.0F)) {
            if (!(entity.getRandom().nextFloat() >= chance)) {
               List<LivingEntity> targets = entity.level()
                  .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), t -> isDomainVanillaLightningTarget(entity, t));
               if (!targets.isEmpty()) {
                  LivingEntity target = targets.get(entity.getRandom().nextInt(targets.size()));
                  this.spawnVanillaLightning(entity, target.position());
               }
            }
         }
      }
   }

   private static boolean isDomainHostileTarget(LivingEntity owner, LivingEntity target) {
      if (target.is(owner) || !target.isAlive() || target.isAlliedTo(owner)) {
         return false;
      } else if (target instanceof Player) {
         return false;
      } else {
         return target instanceof Enemy ? true : target instanceof Mob mob && mob.getTarget() == owner;
      }
   }

   private static boolean isDomainVanillaLightningTarget(LivingEntity owner, LivingEntity target) {
      return !target.is(owner) && target.isAlive() ? !(target instanceof Player) : false;
   }

   private void spawnVanillaLightning(LivingEntity owner, Vec3 pos) {
      if (!owner.level().isClientSide()) {
         LightningBolt bolt = (LightningBolt)EntityType.LIGHTNING_BOLT.create(owner.level());
         if (bolt != null) {
            if (owner instanceof ServerPlayer sp) {
               bolt.setCause(sp);
            }

            bolt.setPos(pos.x, pos.y, pos.z);
            owner.level().addFreshEntity(bolt);
         }
      }
   }

   private void spawnDomainOpeningVisual(LivingEntity entity, double radius) {
      if (!entity.level().isClientSide()) {
         double safeRadius = Math.max(1.0, radius);
         TensuraParticleHelper.spawnServerParticles(
            entity.level(), TensuraParticleUtils.getLightGreenWave(0.55F, (float)safeRadius, -0.25F, false), entity.getX(), entity.getY() + 0.15, entity.getZ()
         );
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getColorlessWave(0.35F, (float)safeRadius, -0.35F, true),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5F,
            entity.getZ()
         );
      }
   }

   private void spawnDomainBarrierVisual(LivingEntity entity, double radius, int shellPoints, int clock) {
      if (!entity.level().isClientSide()) {
         double safeRadius = Math.max(1.0, radius);
         int points = Math.max(24, Math.min(160, shellPoints));
         double baseY = entity.getY() + 0.2;
         double rotation = clock * 0.08;

         for (int i = 0; i < points; i++) {
            double t = (i + 0.5) / points;
            double theta = t * Math.PI * 0.5;
            double ringRadius = safeRadius * Math.cos(theta);
            double y = baseY + safeRadius * Math.sin(theta);
            double angle = i * 2.399963229728653 + rotation;
            double x = entity.getX() + Math.cos(angle) * ringRadius;
            double z = entity.getZ() + Math.sin(angle) * ringRadius;
            TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getGreenGust(), x, y, z, 1, 0.02, 0.02, 0.02, 0.0, true);
         }

         int ringPoints = Math.max(24, points / 2);

         for (int i = 0; i < ringPoints; i++) {
            double angle = (Math.PI * 2) * i / ringPoints - rotation;
            double x = entity.getX() + Math.cos(angle) * safeRadius;
            double z = entity.getZ() + Math.sin(angle) * safeRadius;
            TensuraParticleHelper.spawnServerParticles(entity.level(), TensuraParticleUtils.getGreenGust(), x, baseY, z, 1, 0.01, 0.01, 0.01, 0.0, true);
         }
      }
   }

   private void shootDomainWindBlade(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, int mode, float damage) {
      WindBladeProjectile blade = new WindBladeProjectile(entity.level(), entity);
      blade.setSpeed(1.6F);
      blade.setDamage(damage);
      blade.setKnockForce(1.5F);
      blade.setSkill(entity, instance, this, mode);
      blade.setNoGravity(true);
      blade.setPos(entity.getEyePosition());
      Vec3 dir = safeNormalize(target.getEyePosition().subtract(blade.position()), entity.getLookAngle());
      blade.shootFromRot(dir);
      entity.level().addFreshEntity(blade);
   }

   private void spawnBlackLightning(
      ManasSkillInstance instance, LivingEntity entity, int mode, Vec3 pos, float damage, float secondaryDamage, float radius, int additionalVisual
   ) {
      if (!entity.level().isClientSide()) {
         BlackLightningBolt bolt = new BlackLightningBolt(entity.level(), entity);
         bolt.setCause(entity instanceof ServerPlayer sp ? sp : null);
         bolt.setSkill(entity, instance, this, mode);
         bolt.setMode(mode);
         bolt.setTensuraDamage(damage);
         bolt.setSecondaryDamage(Math.max(0.0F, secondaryDamage));
         bolt.setRadius(Math.max(0.5F, radius));
         bolt.setAdditionalVisual(Math.max(0, additionalVisual));
         bolt.setShouldPlaceFire(false);
         bolt.setPos(pos);
         entity.level().addFreshEntity(bolt);
      }
   }

   private static void pushAway(LivingEntity source, LivingEntity target, double strength) {
      Vec3 dir = safeNormalize(target.position().subtract(source.position()), source.getLookAngle());
      Vec3 knockback = dir.scale(strength);
      target.push(knockback.x, 0.2, knockback.z);
   }

   private static Vec3 safeNormalize(Vec3 value, Vec3 fallback) {
      return value.lengthSqr() < 1.0E-4 ? fallback.normalize() : value.normalize();
   }

   private static Vec3 safeSide(Vec3 look) {
      Vec3 side = look.cross(new Vec3(0.0, 1.0, 0.0));
      return side.lengthSqr() < 1.0E-4 ? new Vec3(1.0, 0.0, 0.0) : side.normalize();
   }

   private static int getCooldown(double baseSeconds, ManasSkillInstance instance, LivingEntity entity) {
      double seconds = instance.isMastered(entity) ? baseSeconds * cfg().masteredCooldownMultiplier : baseSeconds;
      return Math.max(1, (int)Math.round(seconds));
   }

   private static int getLightningCooldown(VeldoraConfig.VeldoraSettings c, ManasSkillInstance instance, LivingEntity entity) {
      return Math.max(1, instance.isMastered(entity) ? c.lightningCooldownMastered : c.lightningCooldown);
   }

   private static int getDomainCooldown(VeldoraConfig.VeldoraSettings c, ManasSkillInstance instance, LivingEntity entity) {
      return Math.max(1, instance.isMastered(entity) ? c.domainCooldownMastered : c.domainCooldown);
   }

   private static int getDomainDurationTicks(VeldoraConfig.VeldoraSettings c, ManasSkillInstance instance, LivingEntity entity) {
      int seconds = instance.isMastered(entity) ? c.domainDurationMasteredSeconds : c.domainDurationSeconds;
      return Math.max(1, seconds) * 20;
   }

   private static double getDomainRadius(VeldoraConfig.VeldoraSettings c, ManasSkillInstance instance, LivingEntity entity) {
      return Math.max(0.0, instance.isMastered(entity) ? c.domainRadiusMastered : c.domainRadius);
   }

   private static double getDomainLightningTargetRadius(VeldoraConfig.VeldoraSettings c) {
      return Math.max(0.0, c.domainLightningTargetRadius);
   }

   private static boolean shouldShootDomainWindBlade(VeldoraConfig.VeldoraSettings c, long clock) {
      int interval = c.domainWindBladeIntervalTicks;
      return interval > 0 && clock > 0L && clock % Math.max(1, interval) == 0L;
   }

   private static float clampChance(float chance) {
      return Math.max(0.0F, Math.min(1.0F, chance));
   }

   private void registerTrulyUnique(ServerPlayer player) {
      if (isTrulyUniqueEnabled(player)) {
         ServerLevel overworld = overworldOf(player);
         if (overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            ResourceLocation id = this.getRegistryName();
            if (id != null) {
               UUID owner = unique.getOwner(id);
               if (owner == null || owner.equals(player.getUUID())) {
                  unique.addSkill(id, player.getUUID());
                  unique.markDirty();
               }
            }
         }
      }
   }

   private void unregisterTrulyUnique(ServerPlayer player) {
      ServerLevel overworld = overworldOf(player);
      if (overworld != null) {
         ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
         ResourceLocation id = this.getRegistryName();
         if (id != null) {
            UUID owner = unique.getOwner(id);
            if (owner != null && owner.equals(player.getUUID())) {
               unique.removeSkill(id);
               unique.markDirty();
            }
         }
      }
   }

   private static boolean isTrulyUniqueEnabled(ServerPlayer player) {
      return player.level().getGameRules().getBoolean(FoxAblazeGameRules.VELDORA_TRULY_UNIQUE);
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   private static void applyPassiveAttributes(LivingEntity entity, ManasSkillInstance instance) {
      VeldoraConfig.VeldoraSettings c = cfg();
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.WIND_BOOST, elementalBoostMultiplier(c.windDamageBoost));
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.LIGHTNING_BOOST, elementalBoostMultiplier(c.thunderDamageBoost));
   }

   private static void removePassiveAttributes(LivingEntity entity) {
      VeldoraConfig.VeldoraSettings c = cfg();
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.WIND_BOOST, elementalBoostMultiplier(c.windDamageBoost));
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.LIGHTNING_BOOST, elementalBoostMultiplier(c.thunderDamageBoost));
      AttributeHelper.removeDominationDegradation(entity, TensuraAttributes.WIND_RESIST_DEGRADATION);
      AttributeHelper.removeDominationDegradation(entity, TensuraAttributes.LIGHTNING_RESIST_DEGRADATION);
   }

   private static double elementalBoostMultiplier(double bonus) {
      return Math.max(0.0, 1.0 + bonus);
   }

   private static float reduceDamage(float damage, double reduction) {
      double clamped = Math.max(0.0, Math.min(1.0, reduction));
      return (float)(damage * (1.0 - clamped));
   }
}
