package com.foxablazeultimate.ability.skill.velgrynd;

import com.foxablazeultimate.config.VelgryndConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateDataComponents;
import com.foxablazeultimate.registry.FoxAblazeUltimateMobEffects;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ICloning;
import io.github.manasmods.tensura.ability.subclass.ISpatialMovement;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.human.CloneEntity.CopySkill;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class VelgryndSkill extends Skill implements ICloning {
   public static final int MODE_LIBERATION = 0;
   public static final int MODE_EXCITATION = 1;
   public static final int MODE_DIMENSION_JUMP = 2;
   public static final int MODE_ACCELERATION = 3;
   private static final int MODE_COUNT = 4;
   private static final int LIBERATION_SUB_SPLIT = 0;
   private static final int LIBERATION_SUB_FUSION = 1;
   private static final int AVATAR_SLOTS = 5;
   private static final int JUMP_SUB_TELEPORT = 0;
   private static final String AVATARS_TAG = "VelgryndAvatars";
   private static final String AVATAR_UUID_TAG = "UUID";
   private static final String AVATAR_TIME_TAG = "Time";
   private static final String LIBERATION_SUB_TAG = "VelgryndLiberationSub";
   private static final String JUMP_SUB_TAG = "VelgryndJumpSub";
   private static final String CHARGING_TAG = "VelgryndCharging";
   private static final String GRANTED_SKILLS_TAG = "VelgryndGrantedSkills";
   private static final String CONSUMED_SKILLS_TAG = "VelgryndConsumedSkills";
   private static final DustParticleOptions CHARGE_DUST = new DustParticleOptions(new Vector3f(1.0F, 0.12F, 0.05F), 1.0F);

   private static VelgryndConfig.VelgryndSettings cfg() {
      return VelgryndConfig.settings();
   }

   public VelgryndSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/velgrynd.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.RED);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return acquisitionMagiculeCost();
   }

   public static double acquisitionMagiculeCost() {
      double cost = cfg().acquisitionMaximumMagiculeCost;
      return Double.isFinite(cost) ? Math.max(0.0, cost) : 3000000.0;
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
         case 0 -> "velgrynd.liberation";
         case 1 -> "velgrynd.excitation";
         case 2 -> "velgrynd.dimension_jump";
         case 3 -> "velgrynd.acceleration";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      VelgryndConfig.VelgryndSettings c = cfg();

      return switch (sanitizeMode(mode)) {
         case 0 -> c.magiculeCostLiberation;
         case 1 -> c.magiculeCostExcitation;
         case 2 -> c.magiculeCostDimensionJump;
         case 3 -> c.magiculeCostAcceleration;
         default -> 0.0;
      };
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      int safe = sanitizeMode(mode);
      return safe == 0 || safe == 2;
   }

   public boolean shouldShowCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return instance.onCoolDown(sanitizeMode(mode));
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         applyPassiveAttributes(entity);
         if (entity instanceof ServerPlayer) {
            grantFlameSkills(instance, entity);
         }
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      if (!instance.isTemporarySkill()) {
         applyPassiveAttributes(entity);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      removePassiveAttributes(entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      removePassiveAttributes(entity);
      if (entity.level() instanceof ServerLevel level && !(entity instanceof CloneEntity)) {
         this.recallAvatars(instance, entity, level);
         revokeFlameSkills(instance, entity);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      clearCharging(instance);
      return true;
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (!instance.isToggled()) {
         return true;
      } else if (source.is(DamageTypeTags.IS_FIRE) && source.getEntity() == null) {
         entity.clearFire();
         return false;
      } else {
         return !TensuraDamageHelper.isFireDamage(source) || ((TensuraDamageSource)source).tensura$getResistanceBypassLevel() >= 2.0F;
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      }

      VelgryndConfig.VelgryndSettings c = cfg();
      float value = (Float)amount.get();
      if (TensuraDamageHelper.isFireDamage(source)) {
         value = reduceDamage(value, c.flameDamageReduction);
      } else if (TensuraDamageHelper.isNaturalEffects(source)) {
         value = reduceDamage(value, c.elementalDamageReduction);
      }

      if (TensuraDamageHelper.isPhysicalAttack(source)) {
         value = reduceDamage(value, c.physicalDamageReduction);
      }

      amount.set(value);
      return true;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      }

      if (source.getDirectEntity() == attacker && TensuraDamageHelper.isPhysicalAttack(source)) {
         int seconds = cfg().attackBurnSeconds;
         if (seconds > 0) {
            target.igniteForSeconds(seconds);
            TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.FLAME, 1.0);
         }

         return true;
      } else {
         return true;
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (owner.hasEffect(FoxAblazeUltimateMobEffects.VELGRYND_EXCITATION) && TensuraDamageHelper.isHeat(source)) {
         amount.set((Float)amount.get() * (float)Math.max(0.0, 1.0 + cfg().excitationHeatDamageBonus));
      }

      return true;
   }

   public void onSubordinateDeath(ManasSkillInstance instance, LivingEntity owner, LivingEntity subordinate, DamageSource source) {
      super.onSubordinateDeath(instance, owner, subordinate, source);
      if (subordinate instanceof CloneEntity clone) {
         int slot = slotOf(instance, clone.getUUID());
         if (slot > 0) {
            clearAvatar(instance.getOrCreateTag(), slot);
            instance.markDirty();
         }
      }
   }

   public void onCloneTick(CloneEntity clone, LivingEntity owner) {
      if (!clone.level().isClientSide()) {
         ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(owner).getSkill(this).orElse(null);
         if (instance != null) {
            if (clone.tickCount % 20 == 0 && slotOf(instance, clone.getUUID()) <= 0) {
               spawnAvatarVanishVisual(clone);
               clone.discard();
            } else {
               syncExcitation(owner, clone);
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         switch (sanitizeMode(mode)) {
            case 0:
               this.onLiberation(instance, entity, level, mode);
               break;
            case 1:
               this.onExcitation(instance, entity, level, mode);
               break;
            case 2:
               this.onDimensionJump(instance, entity, level, mode);
               break;
            case 3:
               this.onAccelerationPressed(instance, entity, mode);
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (sanitizeMode(mode) != 3) {
         return false;
      }

      CompoundTag tag = instance.getTag();
      if (tag != null && tag.getBoolean("VelgryndCharging")) {
         if (entity.level() instanceof ServerLevel level) {
            if (!isHoldingWeapon(entity)) {
               clearCharging(instance);
               return false;
            }

            VelgryndConfig.VelgryndSettings c = cfg();
            int chargeTicks = Math.max(1, c.accelerationChargeTicks);
            spawnChargeParticles(level, entity, heldTicks, chargeTicks);
            if (heldTicks > 0 && heldTicks % 20 == 0) {
               level.playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.SPIRIT_FLAME_AMBIENT.get(),
                  SoundSource.PLAYERS,
                  0.6F,
                  0.8F + 0.6F * Math.min(1.0F, (float)heldTicks / chargeTicks)
               );
            }

            if (heldTicks < chargeTicks) {
               return true;
            }

            clearCharging(instance);
            if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               return false;
            }

            entity.getMainHandItem().set((DataComponentType)FoxAblazeUltimateDataComponents.VELGRYND_CHARGE.get(), new VelgryndChargeData(entity.getUUID()));
            instance.addMasteryPoint(entity);
            instance.setCoolDown(Math.max(1, c.accelerationCooldown), mode);
            Vec3 hand = getHandPosition(entity);
            level.sendParticles(ParticleTypes.FLAME, hand.x, hand.y, hand.z, 20, 0.15, 0.15, 0.15, 0.05);
            level.sendParticles(CHARGE_DUST, hand.x, hand.y, hand.z, 20, 0.2, 0.2, 0.2, 0.0);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), SoundSource.PLAYERS, 1.0F, 0.8F);
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("foxablazeultimate.skill.velgrynd.acceleration.charged").withStyle(ChatFormatting.RED), true);
            }

            return false;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (sanitizeMode(mode) == 3) {
         clearCharging(instance);
      }
   }

   public int getMaxHeldTime(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      return tag != null && tag.getBoolean("VelgryndCharging") ? Math.max(1, cfg().accelerationChargeTicks) + 20 : super.getMaxHeldTime(instance, entity);
   }

   private void onLiberation(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int mode) {
      if (entity.isShiftKeyDown()) {
         int next = readLiberationSub(instance) == 0 ? 1 : 0;
         instance.getOrCreateTag().putInt("VelgryndLiberationSub", next);
         instance.markDirty();
         announceSubMode(entity, liberationSubLabel(next));
      } else if (readLiberationSub(instance) == 1) {
         this.recallAvatars(instance, entity, level);
      } else {
         VelgryndConfig.VelgryndSettings c = cfg();
         CloneEntity target = (CloneEntity)ObjectSelectionHelper.getTargetingEntity(CloneEntity.class, entity, c.avatarTargetRange, 0.2, false);
         if (target != null && slotOf(instance, target.getUUID()) > 0) {
            this.switchControl(instance, entity, level, target);
         } else if (!instance.onCoolDown(mode)) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, 3.0);
               int slot = pickAvatarSlot(instance, level);
               this.spawnAvatar(instance, entity, level, result.getLocation(), slot);
               level.playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_SPLIT.get(), SoundSource.PLAYERS, 1.0F, 1.0F
               );
               instance.addMasteryPoint(entity);
               instance.setCoolDown(Math.max(1, c.liberationCooldown), mode);
            }
         }
      }
   }

   private void switchControl(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, CloneEntity target) {
      if (SkillUtils.shouldCancelTeleportation(entity)) {
         sendFailure(entity, "tensura.skill.spatial_blockade");
      } else {
         int slot = slotOf(instance, target.getUUID());
         Vec3 origin = entity.position();
         float ownerHealth = entity.getHealth();
         float targetHealth = target.getHealth();
         CloneEntity fresh = this.spawnAvatar(instance, entity, level, origin, slot);
         fresh.setHealth(Math.max(1.0F, Math.min(ownerHealth, fresh.getMaxHealth())));
         CloneEntity.copyEffects(entity, fresh);
         teleportEntity(entity, level, target.getX(), target.getY(), target.getZ(), target.getYRot(), target.getXRot());
         entity.setHealth(Math.max(1.0F, Math.min(targetHealth, entity.getMaxHealth())));
         CloneEntity.copyEffects(target, entity);
         target.discard();
         level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_SPLIT.get(), SoundSource.PLAYERS, 1.0F, 1.2F);
         TensuraParticleHelper.addServerAuraParticles(
            entity, TensuraParticleUtils.getCrimsonAura(0.5F, (float)entity.getAttributeValue(Attributes.SCALE) * 4.0F, -0.3F), 10, 0.01
         );
      }
   }

   private CloneEntity spawnAvatar(ManasSkillInstance instance, LivingEntity owner, ServerLevel level, Vec3 position, int slot) {
      CloneEntity clone = new CloneEntity((EntityType)HumanEntityTypes.CLONE.get(), level);
      if (owner instanceof Player player) {
         clone.tame(player);
      }

      clone.setSkill(instance);
      clone.copyStatsAndSkills(owner, CopySkill.ALL, true);
      CloneEntity.copyStatusEffect(owner, clone, CopySkill.ALL, false);
      clone.setHealth(clone.getMaxHealth());
      IExistence ownerExistence = TensuraStorages.getExistenceFrom(owner);
      double maxMagicule = EnergyHelper.getBaseMaxMagicule(owner);
      EnergyHelper.setMaxMagicule(clone, maxMagicule);
      EnergyHelper.setMaxAura(clone, EnergyHelper.getBaseMaxAura(owner));
      IExistence existence = TensuraStorages.getExistenceFrom(clone);
      existence.setMagicule(Math.min(ownerExistence.getMagicule(), maxMagicule));
      existence.setAura(Math.min(ownerExistence.getAura(), EnergyHelper.getBaseMaxAura(owner)));
      existence.markDirty();

      for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
         ItemStack copy = owner.getItemBySlot(equipmentSlot).copy();
         copy.remove((DataComponentType)FoxAblazeUltimateDataComponents.VELGRYND_CHARGE.get());
         clone.addFakeItem(equipmentSlot, copy);
      }

      clone.setPos(position);
      CloneEntity.copyRotation(owner, clone);
      level.addFreshEntity(clone);
      CompoundTag tag = instance.getOrCreateTag();
      setAvatar(tag, slot, clone.getUUID(), level.getGameTime());
      instance.markDirty();
      TensuraParticleHelper.addServerAuraParticles(
         clone, TensuraParticleUtils.getCrimsonAura(0.5F, (float)clone.getAttributeValue(Attributes.SCALE) * 4.0F, -0.3F), 10, 0.01
      );
      level.sendParticles(ParticleTypes.FLAME, clone.getX(), clone.getY() + clone.getBbHeight() * 0.5, clone.getZ(), 24, 0.3, 0.6, 0.3, 0.02);
      return clone;
   }

   private static int pickAvatarSlot(ManasSkillInstance instance, ServerLevel level) {
      CompoundTag tag = instance.getOrCreateTag();
      int max = maxAvatars();
      int oldestSlot = 1;
      long oldestTime = Long.MAX_VALUE;

      for (int slot = 1; slot <= max; slot++) {
         UUID id = getAvatarId(tag, slot);
         if (id == null) {
            return slot;
         }

         CloneEntity avatar = findAvatar(level, id);
         if (avatar == null || !avatar.isAlive()) {
            clearAvatar(tag, slot);
            return slot;
         }

         long time = getAvatarTime(tag, slot);
         if (time < oldestTime) {
            oldestTime = time;
            oldestSlot = slot;
         }
      }

      UUID oldestId = getAvatarId(tag, oldestSlot);
      CloneEntity oldest = oldestId == null ? null : findAvatar(level, oldestId);
      if (oldest != null) {
         spawnAvatarVanishVisual(oldest);
         oldest.discard();
      }

      clearAvatar(tag, oldestSlot);
      return oldestSlot;
   }

   private void recallAvatars(ManasSkillInstance instance, LivingEntity entity, ServerLevel level) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.contains("VelgryndAvatars")) {
         boolean recalled = false;

         for (int slot = 1; slot <= 5; slot++) {
            UUID id = getAvatarId(tag, slot);
            if (id != null) {
               CloneEntity avatar = findAvatar(level, id);
               if (avatar != null && avatar.isAlive()) {
                  spawnAvatarVanishVisual(avatar);
                  avatar.discard();
                  recalled = true;
               }
            }
         }

         tag.remove("VelgryndAvatars");
         tag.putInt("VelgryndJumpSub", 0);
         instance.markDirty();
         if (recalled) {
            level.playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F
            );
            TensuraParticleHelper.addServerAuraParticles(
               entity, TensuraParticleUtils.getCrimsonAura(0.5F, (float)entity.getAttributeValue(Attributes.SCALE) * 4.0F, -0.3F), 10, 0.01
            );
         }
      }
   }

   private void onExcitation(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int mode) {
      if (!instance.onCoolDown(mode)) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            VelgryndConfig.VelgryndSettings c = cfg();
            int duration = Math.max(1, c.excitationDurationSeconds) * 20;
            applyExcitation(entity, duration);
            CompoundTag tag = instance.getTag();
            if (tag != null) {
               for (int slot = 1; slot <= 5; slot++) {
                  UUID id = getAvatarId(tag, slot);
                  if (id != null) {
                     CloneEntity avatar = findAvatar(level, id);
                     if (avatar != null && avatar.isAlive()) {
                        applyExcitation(avatar, duration);
                     }
                  }
               }
            }

            TensuraParticleHelper.spawnServerParticles(
               level,
               TensuraParticleUtils.getRedWave(0.8F, entity.getBbWidth() * 4.0F, -0.2F, true),
               entity.getX(),
               entity.getY() + entity.getBbHeight() * 0.5,
               entity.getZ()
            );
            level.sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 30, 0.4, 0.8, 0.4, 0.04);
            level.playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.9F
            );
            instance.addMasteryPoint(entity);
            instance.setCoolDown(Math.max(1, c.excitationCooldown), mode);
         }
      }
   }

   private static void applyExcitation(LivingEntity entity, int duration) {
      entity.addEffect(new MobEffectInstance(FoxAblazeUltimateMobEffects.VELGRYND_EXCITATION, duration, 0, false, true, true));
   }

   private static void syncExcitation(LivingEntity owner, CloneEntity clone) {
      Holder<MobEffect> excitation = FoxAblazeUltimateMobEffects.VELGRYND_EXCITATION;
      MobEffectInstance ownerEffect = owner.getEffect(excitation);
      MobEffectInstance cloneEffect = clone.getEffect(excitation);
      if (ownerEffect == null) {
         if (cloneEffect != null) {
            clone.removeEffect(excitation);
         }
      } else {
         if (cloneEffect == null || cloneEffect.getDuration() < ownerEffect.getDuration() - 20) {
            clone.addEffect(new MobEffectInstance(ownerEffect));
         }
      }
   }

   private void onDimensionJump(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int mode) {
      if (entity.isShiftKeyDown()) {
         this.cycleJumpSub(instance, entity);
      } else if (!instance.onCoolDown(mode)) {
         if (SkillUtils.shouldCancelTeleportation(entity)) {
            sendFailure(entity, "tensura.skill.spatial_blockade");
         } else {
            int sub = readJumpSub(instance);
            boolean jumped;
            if (sub == 0) {
               jumped = this.teleportToSight(instance, entity, level, mode);
            } else {
               int slot = sub > 5 ? sub - 5 : sub;
               CloneEntity avatar = this.resolveAvatar(instance, entity, level, slot);
               if (avatar == null) {
                  return;
               }

               jumped = sub > 5 ? this.swapWithAvatar(instance, entity, level, avatar, mode) : this.arriveAtAvatar(instance, entity, avatar, mode);
            }

            if (jumped) {
               instance.addMasteryPoint(entity);
               instance.setCoolDown(Math.max(1, cfg().dimensionJumpCooldown), mode);
            }
         }
      }
   }

   private boolean teleportToSight(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int mode) {
      BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, cfg().dimensionJumpRange);
      BlockPos pos = result.getBlockPos().relative(result.getDirection());
      if (level.getBlockState(pos).is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)) {
         level.playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F
         );
         return false;
      }

      if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      spawnJumpVisual(level, entity);
      ISpatialMovement.warp(entity, pos.getCenter().x(), pos.getY(), pos.getCenter().z());
      spawnJumpVisual(level, entity);
      return true;
   }

   private boolean arriveAtAvatar(ManasSkillInstance instance, LivingEntity entity, CloneEntity avatar, int mode) {
      if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      if (entity.level() instanceof ServerLevel from) {
         spawnJumpVisual(from, entity);
      }

      ISpatialMovement.warp(entity, entity, avatar.getX(), avatar.getY(), avatar.getZ(), avatar.level().dimension());
      if (entity.level() instanceof ServerLevel to) {
         spawnJumpVisual(to, entity);
      }

      return true;
   }

   private boolean swapWithAvatar(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, CloneEntity avatar, int mode) {
      if (avatar.level() != level) {
         sendFailure(entity, "tensura.ability.activation_failed");
         return false;
      }

      if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      Vec3 origin = entity.position();
      float yRot = entity.getYRot();
      float xRot = entity.getXRot();
      spawnJumpVisual(level, entity);
      spawnJumpVisual(level, avatar);
      teleportEntity(entity, level, avatar.getX(), avatar.getY(), avatar.getZ(), avatar.getYRot(), avatar.getXRot());
      teleportEntity(avatar, level, origin.x, origin.y, origin.z, yRot, xRot);
      avatar.getNavigation().stop();
      spawnJumpVisual(level, entity);
      spawnJumpVisual(level, avatar);
      return true;
   }

   private void cycleJumpSub(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      List<Integer> available = new ArrayList<>();
      available.add(0);

      for (int slot = 1; slot <= 5; slot++) {
         if (getAvatarId(tag, slot) != null) {
            available.add(slot);
         }
      }

      for (int slot = 1; slot <= 5; slot++) {
         if (getAvatarId(tag, slot) != null) {
            available.add(5 + slot);
         }
      }

      int current = readJumpSub(instance);
      int index = available.indexOf(current);
      int next = available.get(index < 0 ? 0 : (index + 1) % available.size());
      tag.putInt("VelgryndJumpSub", next);
      instance.markDirty();
      announceSubMode(entity, jumpSubLabel(next));
   }

   private CloneEntity resolveAvatar(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int slot) {
      CompoundTag tag = instance.getOrCreateTag();
      UUID id = getAvatarId(tag, slot);
      CloneEntity avatar = id == null ? null : findAvatar(level, id);
      if (avatar != null && avatar.isAlive()) {
         return avatar;
      }

      clearAvatar(tag, slot);
      tag.putInt("VelgryndJumpSub", 0);
      instance.markDirty();
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("foxablazeultimate.skill.velgrynd.avatar_missing", new Object[]{slot}).withStyle(ChatFormatting.RED), true
         );
      }

      return null;
   }

   private void onAccelerationPressed(ManasSkillInstance instance, LivingEntity entity, int mode) {
      clearCharging(instance);
      if (!instance.onCoolDown(mode)) {
         if (!isHoldingWeapon(entity)) {
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("foxablazeultimate.skill.velgrynd.acceleration.requires_weapon").withStyle(ChatFormatting.RED), true
               );
            }
         } else if (!isChargedBy(entity.getMainHandItem(), entity)) {
            instance.getOrCreateTag().putBoolean("VelgryndCharging", true);
            instance.markDirty();
            entity.level()
               .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), SoundSource.PLAYERS, 0.8F, 0.6F);
         }
      }
   }

   public void releaseCharge(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target) {
      attacker.getMainHandItem().remove((DataComponentType)FoxAblazeUltimateDataComponents.VELGRYND_CHARGE.get());
      breakBarriers(target);
      DamageSource source = ((TensuraDamageSource)this.createSource(instance, attacker, TensuraDamageTypes.FIRE_ELEMENTAL, 3))
         .tensura$setBarrierBypassLevel(1.0F);
      target.invulnerableTime = 0;
      target.hurt(source, (float)cfg().accelerationDamage);
      if (target.level() instanceof ServerLevel level) {
         double y = target.getY() + target.getBbHeight() * 0.5;
         level.sendParticles(ParticleTypes.FLAME, target.getX(), y, target.getZ(), 40, 0.4, 0.6, 0.4, 0.08);
         level.sendParticles(CHARGE_DUST, target.getX(), y, target.getZ(), 30, 0.5, 0.6, 0.5, 0.0);
         TensuraParticleHelper.spawnServerParticles(
            level, TensuraParticleUtils.getRedWave(0.8F, target.getBbWidth() * 3.0F, -0.2F, true), target.getX(), y, target.getZ()
         );
         level.playSound(null, target.getX(), target.getY(), target.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), SoundSource.PLAYERS, 1.2F, 0.7F);
      }
   }

   private static void breakBarriers(LivingEntity target) {
      boolean broken = false;
      AttributeInstance multilayer = target.getAttribute(TensuraAttributes.MULTILAYER_BARRIER);
      if (multilayer != null && multilayer.getValue() > 0.0) {
         multilayer.removeModifiers();
         broken = true;
      }

      for (Holder<MobEffect> barrier : List.of(
         TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_BARRIER), TensuraMobEffects.getReference(TensuraMobEffects.PHYSICAL_BARRIER)
      )) {
         if (target.hasEffect(barrier)) {
            target.removeEffect(barrier);
            broken = true;
         }
      }

      if (broken) {
         target.level().playSound(null, target.blockPosition(), (SoundEvent)TensuraSoundEvents.BARRIER_BREAK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   public static boolean isChargedBy(ItemStack stack, Entity entity) {
      VelgryndChargeData data = (VelgryndChargeData)stack.get((DataComponentType)FoxAblazeUltimateDataComponents.VELGRYND_CHARGE.get());
      return data != null && data.owner().equals(entity.getUUID());
   }

   private static boolean isHoldingWeapon(LivingEntity entity) {
      ItemStack stack = entity.getMainHandItem();
      if (stack.isEmpty()) {
         return false;
      } else if (stack.is(ItemTags.SWORDS) || stack.is(ItemTags.AXES)) {
         return true;
      } else {
         return !(stack.getItem() instanceof SwordItem)
               && !(stack.getItem() instanceof AxeItem)
               && !(stack.getItem() instanceof TridentItem)
               && !(stack.getItem() instanceof MaceItem)
            ? stack.getAttributeModifiers()
               .modifiers()
               .stream()
               .anyMatch(entry -> entry.attribute().value() == Attributes.ATTACK_DAMAGE.value() && entry.modifier().amount() > 0.0)
            : true;
      }
   }

   private static void clearCharging(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.contains("VelgryndCharging")) {
         tag.remove("VelgryndCharging");
         instance.markDirty();
      }
   }

   private static void spawnChargeParticles(ServerLevel level, LivingEntity entity, int heldTicks, int chargeTicks) {
      Vec3 hand = getHandPosition(entity);
      float progress = Math.min(1.0F, (float)heldTicks / chargeTicks);
      int streams = 6 + (int)(progress * 6.0F);
      double radius = 2.6;
      int period = 16;

      for (int i = 0; i < streams; i++) {
         double t = (i + 0.5) / streams;
         double polar = Math.acos(1.0 - 2.0 * t);
         double azimuth = i * 2.399963229728653 + heldTicks * 0.05;
         Vec3 dir = new Vec3(Math.sin(polar) * Math.cos(azimuth), Math.cos(polar), Math.sin(polar) * Math.sin(azimuth));
         double phase = (heldTicks + i * ((double)period / streams)) % period / period;
         Vec3 pos = hand.add(dir.scale(radius * (1.0 - phase)));
         level.sendParticles(CHARGE_DUST, pos.x, pos.y, pos.z, 1, 0.0, 0.0, 0.0, 0.0);
      }

      if (heldTicks % 4 == 0) {
         level.sendParticles(ParticleTypes.FLAME, hand.x, hand.y, hand.z, 1 + (int)(progress * 3.0F), 0.05, 0.05, 0.05, 0.01);
      }
   }

   private static Vec3 getHandPosition(LivingEntity entity) {
      float yaw = entity.yBodyRot * (float) (Math.PI / 180.0);
      Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
      Vec3 right = new Vec3(-Mth.cos(yaw), 0.0, -Mth.sin(yaw));
      if (entity.getMainArm() == HumanoidArm.LEFT) {
         right = right.scale(-1.0);
      }

      return entity.position().add(0.0, entity.getBbHeight() * 0.55, 0.0).add(right.scale(entity.getBbWidth() * 0.65)).add(forward.scale(0.35));
   }

   private static int maxAvatars() {
      return Mth.clamp(cfg().maxAvatars, 1, 5);
   }

   private static UUID getAvatarId(CompoundTag tag, int slot) {
      if (!tag.contains("VelgryndAvatars", 10)) {
         return null;
      }

      CompoundTag avatars = tag.getCompound("VelgryndAvatars");
      String key = Integer.toString(slot);
      if (!avatars.contains(key, 10)) {
         return null;
      }

      CompoundTag entry = avatars.getCompound(key);
      return entry.hasUUID("UUID") ? entry.getUUID("UUID") : null;
   }

   private static long getAvatarTime(CompoundTag tag, int slot) {
      return tag.getCompound("VelgryndAvatars").getCompound(Integer.toString(slot)).getLong("Time");
   }

   private static void setAvatar(CompoundTag tag, int slot, UUID id, long time) {
      CompoundTag avatars = tag.getCompound("VelgryndAvatars");
      CompoundTag entry = new CompoundTag();
      entry.putUUID("UUID", id);
      entry.putLong("Time", time);
      avatars.put(Integer.toString(slot), entry);
      tag.put("VelgryndAvatars", avatars);
   }

   private static void clearAvatar(CompoundTag tag, int slot) {
      if (tag.contains("VelgryndAvatars", 10)) {
         CompoundTag avatars = tag.getCompound("VelgryndAvatars");
         avatars.remove(Integer.toString(slot));
         if (avatars.isEmpty()) {
            tag.remove("VelgryndAvatars");
         }
      }
   }

   private static int slotOf(ManasSkillInstance instance, UUID id) {
      CompoundTag tag = instance.getTag();
      if (tag == null) {
         return 0;
      }

      for (int slot = 1; slot <= 5; slot++) {
         if (id.equals(getAvatarId(tag, slot))) {
            return slot;
         }
      }

      return 0;
   }

   private static CloneEntity findAvatar(ServerLevel level, UUID id) {
      return ObjectSelectionHelper.getEntityFromUUID(level, id, e -> e instanceof CloneEntity) instanceof CloneEntity clone ? clone : null;
   }

   private static int readLiberationSub(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      return tag != null && tag.getInt("VelgryndLiberationSub") == 1 ? 1 : 0;
   }

   private static int readJumpSub(ManasSkillInstance instance) {
      CompoundTag tag = instance.getTag();
      if (tag == null) {
         return 0;
      } else {
         int sub = tag.getInt("VelgryndJumpSub");
         if (sub > 0 && sub <= 10) {
            int slot = sub > 5 ? sub - 5 : sub;
            return getAvatarId(tag, slot) == null ? 0 : sub;
         } else {
            return 0;
         }
      }
   }

   private static Component liberationSubLabel(int sub) {
      return Component.translatable(sub == 1 ? "foxablazeultimate.skill.velgrynd.submode.fusion" : "foxablazeultimate.skill.velgrynd.submode.split");
   }

   private static Component jumpSubLabel(int sub) {
      if (sub == 0) {
         return Component.translatable("foxablazeultimate.skill.mode.velgrynd.dimension_jump");
      } else {
         return sub > 5
            ? Component.translatable("foxablazeultimate.skill.velgrynd.submode.swap", new Object[]{sub - 5})
            : Component.translatable("foxablazeultimate.skill.velgrynd.submode.arrive", new Object[]{sub});
      }
   }

   private static void announceSubMode(LivingEntity entity, Component label) {
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("foxablazeultimate.skill.velgrynd.submode.switched", new Object[]{label}).withStyle(ChatFormatting.GOLD), true
         );
      }
   }

   private static void teleportEntity(LivingEntity entity, ServerLevel level, double x, double y, double z, float yRot, float xRot) {
      entity.unRide();
      entity.resetFallDistance();
      if (entity instanceof ServerPlayer player) {
         player.teleportTo(level, x, y, z, yRot, xRot);
         player.hurtMarked = true;
      } else {
         entity.teleportTo(x, y, z);
         entity.setYRot(yRot);
         entity.setXRot(xRot);
         entity.setYHeadRot(yRot);
      }
   }

   private static void spawnJumpVisual(ServerLevel level, LivingEntity entity) {
      level.sendParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 30, 0.3, 0.6, 0.3, 0.3);
      level.sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 12, 0.3, 0.6, 0.3, 0.02);
      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   private static void spawnAvatarVanishVisual(CloneEntity avatar) {
      if (avatar.level() instanceof ServerLevel level) {
         level.sendParticles(ParticleTypes.FLAME, avatar.getX(), avatar.getY() + avatar.getBbHeight() * 0.5, avatar.getZ(), 24, 0.3, 0.6, 0.3, 0.03);
         TensuraParticleHelper.addServerAuraParticles(
            avatar, TensuraParticleUtils.getCrimsonAura(0.5F, (float)avatar.getAttributeValue(Attributes.SCALE) * 4.0F, -0.3F), 10, 0.01
         );
      }
   }

   private static void sendFailure(LivingEntity entity, String key) {
      if (entity instanceof Player player) {
         player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.RED), true);
      }

      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
   }

   public static void markConsumedSkills(ManasSkillInstance instance, Collection<ResourceLocation> skills) {
      ListTag consumed = new ListTag();
      skills.forEach(id -> consumed.add(StringTag.valueOf(id.toString())));
      instance.getOrCreateTag().put("VelgryndConsumedSkills", consumed);
   }

   private static void grantFlameSkills(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      ListTag granted = tag.getList("VelgryndGrantedSkills", 8);
      ListTag consumed = tag.getList("VelgryndConsumedSkills", 8);
      boolean changed = false;

      for (ManasSkill skill : List.of((ManasSkill)ExtraSkills.FLAME_MANIPULATION.get(), (ManasSkill)ExtraSkills.FLAME_DOMINATION.get())) {
         if (!SkillUtils.hasSkill(entity, skill)) {
            ResourceLocation id = skill.getRegistryName();
            if (id != null && !consumed.contains(StringTag.valueOf(id.toString()))) {
               boolean learned;
               if (tag.contains("VelgryndConsumedSkills")) {
                  TensuraSkillInstance gift = new TensuraSkillInstance(skill);
                  gift.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                  gift.getOrCreateTag().putBoolean("GiftedInstance", true);
                  learned = SkillHelper.learnSkill(entity, gift, -1, null);
               } else {
                  learned = SkillHelper.learnSkill(entity, skill);
               }

               if (learned) {
                  granted.add(StringTag.valueOf(id.toString()));
                  changed = true;
               }
            }
         }
      }

      if (changed) {
         tag.put("VelgryndGrantedSkills", granted);
         instance.markDirty();
      }
   }

   private static void revokeFlameSkills(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getTag();
      if (tag != null && tag.contains("VelgryndGrantedSkills", 9)) {
         ListTag granted = tag.getList("VelgryndGrantedSkills", 8);

         for (int i = 0; i < granted.size(); i++) {
            ResourceLocation id = ResourceLocation.tryParse(granted.getString(i));
            if (id != null) {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
               if (skill != null && SkillUtils.hasSkill(entity, skill)) {
                  SkillAPI.getSkillsFrom(entity).forgetSkill(skill);
               }
            }
         }

         tag.remove("VelgryndGrantedSkills");
      }
   }

   private static void applyPassiveAttributes(LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.FLAME_BOOST, elementalBoostMultiplier(cfg().flameDamageBoost));
   }

   private static void removePassiveAttributes(LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.FLAME_BOOST, elementalBoostMultiplier(cfg().flameDamageBoost));
   }

   private static double elementalBoostMultiplier(double bonus) {
      return Math.max(0.0, 1.0 + bonus);
   }

   private static float reduceDamage(float damage, double reduction) {
      double clamped = Math.max(0.0, Math.min(1.0, reduction));
      return (float)(damage * (1.0 - clamped));
   }
}
