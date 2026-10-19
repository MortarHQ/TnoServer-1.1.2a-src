package com.github.b4ndithelps.tennogamenolife.ability.skill.ultimate;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.effect.ModEffects;
import com.github.b4ndithelps.tennogamenolife.registry.sound.TenreincarnationSounds;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GodlySmiteUltimateSkill extends Skill {
   private static final double MINIMUM_MAX_MAGICULE = 2000000.0;
   private static final double MINIMUM_CURRENT_MAGICULE_RATIO = 0.9;
   private static final double RELEASE_MAGICULE_COST_RATIO = 0.9;
   private static final double LEARN_COST = 10.0;
   private static final int COOLDOWN_TICKS = 3600;
   private static final float EFFECT_RADIUS = 96.0F;
   private static final float TERRAIN_RADIUS = 72.0F;
   private static final float CRATER_DEPTH = 36.0F;
   private static final ResourceLocation FORBIDDEN_DIMENSION = ResourceLocation.fromNamespaceAndPath("tensura", "labyrinth");

   public GodlySmiteUltimateSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", "textures/skill/ultimate/godly_smite.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return 0.0;
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 10.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!DestructiveSkillSafety.blockIfDisabled(entity, "tennogamenolife.skill.godly_smite")) {
         if (entity instanceof Player player) {
            if (this.isInForbiddenDimension(player)) {
               player.displayClientMessage(Component.translatable("skill.godly_smite.forbidden_dimension"), true);
            } else if (this.meetsMagiculeRequirements(player)) {
               CompoundTag tag = instance.getOrCreateTag();
               this.storePlayerPosition(tag, player);
               tag.putInt("GodlySmiteChargeTicks", 0);
               tag.putBoolean("GodlySmiteLocked", true);
               if (player.level() instanceof ServerLevel level) {
                  DragonRoarCircle vfx = GodlySmiteVfx.spawnCharge(level, player);
                  tag.putInt("GodlySmiteVfxId", vfx.getId());
                  level.playSound(
                     null, player.getX(), player.getY() + player.getBbHeight(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 4.0F, 0.55F
                  );
               }

               instance.markDirty();
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.getBoolean("GodlySmiteLocked")) {
            return false;
         }

         if (this.isInForbiddenDimension(player)) {
            player.displayClientMessage(Component.translatable("skill.godly_smite.forbidden_dimension"), true);
            this.cancelCharge(player, tag);
            this.resetData(instance);
            return false;
         }

         DragonRoarChargeLock.stopHorizontalMovement(player);
         this.storePlayerPosition(tag, player);
         int chargeTicks = Math.min(1200, tag.getInt("GodlySmiteChargeTicks") + 1);
         tag.putInt("GodlySmiteChargeTicks", chargeTicks);
         instance.markDirty();
         if (player.level() instanceof ServerLevel level) {
            this.maintainChargeVfx(level, player, tag);
         }

         if (chargeTicks == 1200) {
            player.displayClientMessage(Component.translatable("skill.godly_smite.charge_complete"), true);
         } else {
            float progress = chargeTicks / 1200.0F;
            player.displayClientMessage(Component.translatable("skill.godly_smite.charging", new Object[]{String.format("%.0f%%", progress * 100.0F)}), true);
         }

         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("GodlySmiteLocked")) {
            if (tag.getInt("GodlySmiteChargeTicks") < 1200) {
               player.displayClientMessage(Component.translatable("skill.godly_smite.not_ready"), true);
               this.cancelCharge(player, tag);
               this.resetData(instance);
            } else if (!this.meetsMagiculeRequirements(player)) {
               this.cancelCharge(player, tag);
               this.resetData(instance);
            } else {
               Vec3 target = GodlySmiteVfx.getReleaseImpactSurface(player);
               if (player.level() instanceof ServerLevel level) {
                  this.consumeReleaseMagicule(player);
                  this.beginStrike(level, player, target, tag, instance, mode);
               }

               instance.setCoolDown(3600, mode);
               this.resetData(instance);
            }
         }
      }
   }

   private void maintainChargeVfx(ServerLevel level, Player player, CompoundTag tag) {
      if (level.getEntity(tag.getInt("GodlySmiteVfxId")) instanceof DragonRoarCircle vfx && vfx.isGodlySmiteChargeVfx()) {
         vfx.increaseLife(1);
      } else {
         DragonRoarCircle replacement = GodlySmiteVfx.spawnCharge(level, player);
         tag.putInt("GodlySmiteVfxId", replacement.getId());
      }
   }

   private void beginStrike(ServerLevel level, Player caster, Vec3 target, CompoundTag tag, ManasSkillInstance instance, int mode) {
      Entity entity = level.getEntity(tag.getInt("GodlySmiteVfxId"));
      Vec3 origin = GodlySmiteVfx.getReleaseOrigin(caster);
      if (entity instanceof DragonRoarCircle vfx) {
         GodlySmiteVfx.beginStrike(level, vfx, target, origin);
      } else {
         GodlySmiteVfx.spawnStrike(level, target, origin);
      }

      level.playSound(null, target.x, target.y, target.z, SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 8.0F, 0.72F);
      DragonRoarStrikeSequence.start(level, 120, () -> this.detonate(level, caster, target, instance, mode));
   }

   private void detonate(ServerLevel level, Player caster, Vec3 center, ManasSkillInstance instance, int mode) {
      level.playSound(null, center.x, center.y, center.z, (SoundEvent)TenreincarnationSounds.DRAGON_ROAR_EXPLOSION.get(), SoundSource.PLAYERS, 12.0F, 0.55F);
      level.playSound(null, center.x, center.y, center.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 8.0F, 0.62F);
      GodlySmiteVfx.spawnImpactParticles(level, center);
      this.spawnVisualExplosion(level, caster, center, instance, mode);
      this.damageTargets(level, caster, center, instance, mode);
      this.applyReleaseWeaken(caster);
      if (!DestructiveSkillSafety.shouldPreventTerrainDamage()) {
         float gameRuleLimit = level.getGameRules().getInt(TensuraGameRules.MAXIMUM_MAGIC_EXPLOSION);
         float radius = Math.min(72.0F, gameRuleLimit);
         float depth = Math.min(36.0F, radius * 0.6F);
         TengekiProgressiveDestruction.startSmoothCrater(level, center, radius, depth, 96.0F, caster, instance);
      }
   }

   private void applyReleaseWeaken(Player caster) {
      caster.addEffect(new MobEffectInstance(ModEffects.WEAKEN, 12000, 0));
      caster.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 12000, 1));
   }

   private void consumeReleaseMagicule(Player caster) {
      double currentMagicule = TensuraStorages.getExistenceFrom(caster).getMagicule();
      EnergyHelper.isOutOfMagiculeConsuming(caster, currentMagicule * 0.9);
   }

   private boolean meetsMagiculeRequirements(Player player) {
      double maximumMagicule = EnergyHelper.getMaxMagicule(player);
      if (maximumMagicule < 2000000.0) {
         player.displayClientMessage(Component.translatable("skill.godly_smite.magicule_insufficient"), true);
         return false;
      } else {
         double currentMagicule = TensuraStorages.getExistenceFrom(player).getMagicule();
         double requiredMagicule = maximumMagicule * 0.9;
         if (currentMagicule < requiredMagicule) {
            player.displayClientMessage(Component.translatable("skill.godly_smite.magicule_insufficient"), true);
            return false;
         } else {
            return true;
         }
      }
   }

   private void spawnVisualExplosion(ServerLevel level, Player caster, Vec3 center, ManasSkillInstance instance, int mode) {
      try {
         MagicExplosion explosion = new MagicExplosion(level, caster);
         explosion.setPos(center);
         explosion.setDamage(0.0F);
         explosion.setSecondaryDamage(0.0F);
         explosion.setSize(48.0F);
         explosion.setVisualOnly(true);
         explosion.setTickEachHit(1000);
         explosion.setSkill(caster, instance, this, mode, 1.0F);
         level.addFreshEntity(explosion);
      } catch (Exception var7) {
      }
   }

   private void damageTargets(ServerLevel level, Player caster, Vec3 center, ManasSkillInstance instance, int mode) {
      double radiusSquared = 9216.0;
      DamageSource source = this.createSource(instance, caster, TensuraDamageTypes.MAGIC_GENERIC, mode);
      TensuraDamageSource tensuraSource = (TensuraDamageSource)source;
      tensuraSource.tensura$setDodgeBypass();
      tensuraSource.tensura$setBarrierBypassLevel(Float.MAX_VALUE);
      tensuraSource.tensura$setResistanceBypassLevel(Float.MAX_VALUE);

      for (LivingEntity target : level.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(center, center).inflate(96.0),
         targetx -> targetx != caster && targetx.isAlive() && targetx.getBoundingBox().distanceToSqr(center) <= radiusSquared
      )) {
         target.invulnerableTime = 0;
         target.hurt(source, Float.MAX_VALUE);
         if (target.isAlive()) {
            target.getCombatTracker().recordDamage(source, Math.max(1.0F, target.getHealth() + target.getAbsorptionAmount()));
            target.setAbsorptionAmount(0.0F);
            target.setHealth(0.0F);
            target.die(source);
         }
      }
   }

   private boolean isInForbiddenDimension(LivingEntity entity) {
      return FORBIDDEN_DIMENSION.equals(entity.level().dimension().location());
   }

   private void cancelCharge(Player player, CompoundTag tag) {
      if (!player.level().isClientSide) {
         if (player.level().getEntity(tag.getInt("GodlySmiteVfxId")) instanceof DragonRoarCircle vfx) {
            vfx.setLife(8);
         }
      }
   }

   private void resetData(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.putDouble("GodlySmiteX", 0.0);
      tag.putDouble("GodlySmiteY", 0.0);
      tag.putDouble("GodlySmiteZ", 0.0);
      tag.putInt("GodlySmiteChargeTicks", 0);
      tag.putBoolean("GodlySmiteLocked", false);
      tag.putInt("GodlySmiteVfxId", 0);
      instance.markDirty();
   }

   private void storePlayerPosition(CompoundTag tag, Player player) {
      tag.putDouble("GodlySmiteX", player.getX());
      tag.putDouble("GodlySmiteY", player.getY());
      tag.putDouble("GodlySmiteZ", player.getZ());
   }
}
