package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.AbnormalConditionNullification;
import io.github.manasmods.tensura.entity.projectile.magic.FireBoltProjectile;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SchwiExMachinaSkill extends Skill {
   public static final int MODE_AUTO_SHOOTING = 0;
   public static final int MODE_LOCK = 1;
   public static final int MODE_CONTINUOUS_FIRE = 2;
   public static final int MODE_STRUCTURAL_ANALYSIS = 3;
   public static final int MODE_FINAL = 4;
   private static final MoreSkillsConfigs.SchwiExMachina CONFIG = MoreSkillsConfigs.GRAND.schwiExMachina;
   private static final String TAG_SCHWI = "tensuramoreskills_schwi";
   private static final String TAG_STACK_TICKS = "CalcTick";
   private static final String TAG_STACKS = "CalcStacks";
   private static final String TAG_CD_CARRY = "CdCarry";
   private static final String TAG_COMBAT_TICKS = "CombatTicks";
   private static final String TAG_ALIVE_PREV = "AlivePrev";
   private static final String TAG_LOCK_UUID = "LockUUID";
   private static final String TAG_LOCK_TICKS = "LockTicks";
   private static final String TAG_AUTO_TICKS = "AutoTicks";
   private static final String TAG_AUTO_SHOT_CD = "AutoShotCd";
   private static final String TAG_FIRE_HELD_TICKS = "FireHeldTicks";
   private static final String TAG_FIRE_SHOT_CD = "FireShotCd";
   private static final String TAG_SCAN_UUID = "ScanUUID";
   private static final String TAG_SCAN_TICKS = "ScanTicks";
   private static final String TAG_FINAL_TICKS = "FinalTicks";
   private static final String TAG_OVERHEAT_TICKS = "OverheatTicks";
   private static final String TAG_FX_ROT = "FxRot";
   private static final String TAG_FX_TICK = "FxTick";
   private static final ResourceLocation ZERO_MOVESPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_zero_move_speed");
   private static final ResourceLocation ZERO_ATTACKSPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_zero_attack_speed");
   private static final ResourceLocation FINAL_MOVESPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_final_move_speed");
   private static final ResourceLocation FINAL_ATTACKSPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_final_attack_speed");
   private static final ResourceLocation FINAL_ARMOR_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_final_armor");
   private static final ResourceLocation FINAL_TOUGH_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_final_toughness");
   private static final ResourceLocation FINAL_ATK_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_final_attack");
   private static final ResourceLocation FINAL_KB_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "schwi_final_kb");

   public SchwiExMachinaSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/schwi_ex_machina.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)CONFIG.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)CONFIG.acquirementMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity, (Double)CONFIG.acquirementMagiculeCost.get(), (String)CONFIG.requiredSkillId.get(), (Boolean)CONFIG.requiredSkillMustBeMastered.get()
      );
   }

   public int getMaxMastery() {
      return (Integer)CONFIG.maxMastery.get();
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? this.getModes(instance) - 1 : mode - 1;
      } else {
         return mode >= this.getModes(instance) - 1 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "schwi_ex_machina.1";
         case 1 -> "schwi_ex_machina.2";
         case 2 -> "schwi_ex_machina.3";
         case 3 -> "schwi_ex_machina.4";
         case 4 -> "schwi_ex_machina.5";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> ((Integer)CONFIG.autoShootingCost.get()).intValue();
         case 1 -> ((Integer)CONFIG.lockCost.get()).intValue();
         case 2 -> ((Integer)CONFIG.continuousFireCost.get()).intValue();
         case 3 -> ((Integer)CONFIG.structuralAnalysisCost.get()).intValue();
         case 4 -> ((Integer)CONFIG.finalCost.get()).intValue();
         default -> 0.0;
      };
   }

   public boolean canBeToggled(@NotNull ManasSkillInstance instance, @NotNull LivingEntity living) {
      return true;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player p) {
         p.displayClientMessage(Component.translatable("tensuramoreskills.skill.schwi_ex_machina.toggle_on").withStyle(ChatFormatting.AQUA), true);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player p) {
         p.displayClientMessage(Component.translatable("tensuramoreskills.skill.schwi_ex_machina.toggle_off").withStyle(ChatFormatting.GRAY), true);
      }

      CompoundTag root = entity.getPersistentData().getCompound("tensuramoreskills_schwi");
      resetStacks(root);
      root.putInt("FireHeldTicks", 0);
      root.putInt("FireShotCd", 0);
      entity.getPersistentData().put("tensuramoreskills_schwi", root);
      applyZeroBuffs(entity, false);
      applyFinalBuffs(entity, false, false);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return entity.isAlive();
   }

   public void onTick(@NotNull ManasSkillInstance instance, @NotNull LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag var16 = entity.getPersistentData().getCompound("tensuramoreskills_schwi");
         boolean aliveNow = entity.isAlive() && !entity.isDeadOrDying() && entity.getHealth() > 0.0F;
         boolean alivePrev = var16.getBoolean("AlivePrev");
         if (alivePrev && !aliveNow) {
            resetStacks(var16);
         }

         var16.putBoolean("AlivePrev", aliveNow);
         int combat = var16.getInt("CombatTicks");
         if (combat > 0) {
            var16.putInt("CombatTicks", combat - 1);
         }

         int fxTick = var16.getInt("FxTick");
         boolean doBuffRefresh = fxTick % (Integer)CONFIG.buffRefreshTicks.get() == 0;
         if (instance.isToggled()) {
            if (doBuffRefresh) {
               addOrRefreshEffectIfLow(entity, MobEffects.DAMAGE_RESISTANCE, (Integer)CONFIG.zeroResistanceDurationTicks.get(), 0);
            }

            applyZeroBuffs(entity, true);
         } else {
            applyZeroBuffs(entity, false);
         }

         if (aliveNow) {
            boolean activeWindow = instance.isToggled() || var16.getInt("CombatTicks") > 0 || var16.getInt("FinalTicks") > 0;
            if (activeWindow) {
               buildStacks(instance, entity, var16);
               if (entity instanceof Player player) {
                  accelerateCooldownsSelfOnly(player, var16.getInt("CalcStacks"), var16, fxTick);
               }
            } else {
               resetStacks(var16);
            }
         } else {
            resetStacks(var16);
         }

         int lockTicks = var16.getInt("LockTicks");
         if (lockTicks > 0) {
            var16.putInt("LockTicks", --lockTicks);
            if (lockTicks == 0) {
               var16.remove("LockUUID");
            }
         }

         int scanTicks = var16.getInt("ScanTicks");
         if (scanTicks > 0) {
            var16.putInt("ScanTicks", --scanTicks);
            if (scanTicks == 0) {
               var16.remove("ScanUUID");
            }
         }

         int autoTicks = var16.getInt("AutoTicks");
         if (autoTicks > 0) {
            var16.putInt("AutoTicks", --autoTicks);
            int shotCd = Math.max(0, var16.getInt("AutoShotCd") - 1);
            if (shotCd == 0) {
               LivingEntity locked = resolveLockedTarget(entity, var16);
               if (locked != null && locked.isAlive() && locked != entity && locked.distanceTo(entity) <= (Double)CONFIG.targetRange.get()) {
                  shootAutoVolley(level, instance, entity, locked, 0);
                  level.playSound(null, entity.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.85F, 1.35F);
               }

               shotCd = (Integer)CONFIG.autoShotIntervalTicks.get();
            }

            var16.putInt("AutoShotCd", shotCd);
            if (autoTicks == 0) {
               var16.putInt("AutoShotCd", 0);
            }
         }

         int finalTicks = var16.getInt("FinalTicks");
         if (finalTicks > 0) {
            var16.putInt("FinalTicks", --finalTicks);
            applyFinalBuffs(entity, true, doBuffRefresh);
            int max = instance.isMastered(entity) ? (Integer)CONFIG.maxStacksMastered.get() : (Integer)CONFIG.maxStacksBase.get();
            var16.putInt("CalcStacks", max);
            var16.putInt("CalcTick", 0);
            if (finalTicks == 0) {
               var16.putInt("OverheatTicks", (Integer)CONFIG.overheatDurationTicks.get());
               applyFinalBuffs(entity, false, false);
               if (entity instanceof ServerPlayer sp) {
                  sp.displayClientMessage(Component.translatable("tensuramoreskills.skill.schwi_ex_machina.final_overheat").withStyle(ChatFormatting.RED), true);
               }
            }
         } else {
            applyFinalBuffs(entity, false, false);
         }

         int overheat = var16.getInt("OverheatTicks");
         if (overheat > 0) {
            var16.putInt("OverheatTicks", overheat - 1);
         }

         tickFx(level, entity, var16);
         entity.getPersistentData().put("tensuramoreskills_schwi", var16);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (!this.isInSlot(entity, instance)) {
         return false;
      } else if (!(entity.level() instanceof ServerLevel level)) {
         return true;
      } else {
         CompoundTag var15 = entity.getPersistentData().getCompound("tensuramoreskills_schwi");
         if (mode == 2) {
            if (heldTicks % (Integer)CONFIG.fireDrainCheckTicks.get() == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               return false;
            }

            int shotCd = Math.max(0, var15.getInt("FireShotCd") - 1);
            int interval = fireIntervalFromHeldTicks(heldTicks, var15.getInt("FinalTicks") > 0);
            if (shotCd == 0) {
               var15.putInt("FireHeldTicks", heldTicks);
               float seconds = heldTicks / 20.0F;
               float bonus = seconds * ((Double)CONFIG.fireRampPerSecond.get()).floatValue();
               float ramp = var15.getInt("FinalTicks") > 0 ? 1.0F + bonus : 1.0F + Math.min(((Double)CONFIG.fireMaxBonus.get()).floatValue(), bonus);
               LivingEntity locked = resolveLockedTarget(entity, var15);
               LivingEntity target = locked != null ? locked : getAimedTarget(entity, (Double)CONFIG.targetRange.get());
               if (target != null && target.isAlive() && target != entity) {
                  shootFireBoltAtTarget(
                     level,
                     instance,
                     entity,
                     target,
                     ((Double)CONFIG.fireProjectileSpeed.get()).floatValue(),
                     ((Double)CONFIG.fireBaseDamage.get()).floatValue() * ramp,
                     mode
                  );
                  level.playSound(null, entity.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.45F, 1.85F);
               } else {
                  Vec3 point = getAimedPoint(entity, (Double)CONFIG.targetRange.get());
                  shootFireBoltAtPoint(
                     level,
                     instance,
                     entity,
                     point,
                     ((Double)CONFIG.blindFireProjectileSpeed.get()).floatValue(),
                     ((Double)CONFIG.fireBaseDamage.get()).floatValue() * ramp,
                     mode
                  );
                  level.playSound(null, entity.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.35F, 2.0F);
               }

               shotCd = interval;
            }

            var15.putInt("FireShotCd", shotCd);
            entity.getPersistentData().put("tensuramoreskills_schwi", var15);
         }

         return true;
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (this.isInSlot(entity, instance)) {
            if (!instance.onCoolDown(mode)) {
               switch (mode) {
                  case 0:
                     this.castAutoShooting(instance, player, mode);
                     break;
                  case 1:
                     this.castLock(instance, player, mode);
                     break;
                  case 2:
                     player.displayClientMessage(
                        Component.translatable("tensuramoreskills.skill.schwi_ex_machina.barrage_on").withStyle(ChatFormatting.AQUA), true
                     );
                     player.level()
                        .playSound(null, player.blockPosition(), (SoundEvent)SoundEvents.CROSSBOW_QUICK_CHARGE_1.value(), SoundSource.PLAYERS, 0.85F, 1.2F);
                     instance.addMasteryPoint(player);
                     break;
                  case 3:
                     this.castStructuralAnalysisPressed(instance, player, mode);
                     break;
                  case 4:
                     this.castFinal(instance, player, mode);
               }
            }
         }
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(attacker, instance)) {
         return true;
      }

      CompoundTag root = attacker.getPersistentData().getCompound("tensuramoreskills_schwi");
      touchCombat(root);
      if (attacker.getHealth() / attacker.getMaxHealth() > 0.75F && attacker.getRandom().nextFloat() < (Double)CONFIG.predictCritChance.get()) {
         AttributeInstance critAttr = attacker.getAttribute(ManasCoreAttributes.CRITICAL_DAMAGE_MULTIPLIER);
         double critical = critAttr != null ? critAttr.getValue() : 1.5;
         amount.set((float)(((Float)amount.get()).floatValue() * critical));
         attacker.level().playSound(null, attacker.blockPosition(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      LivingEntity locked = resolveLockedTarget(attacker, root);
      if (locked != null && target == locked) {
         amount.set((float)(((Float)amount.get()).floatValue() * (1.0 + (Double)CONFIG.lockDamageBonus.get())));
      }

      LivingEntity scan = resolveScanTarget(attacker, root);
      if (scan != null && target == scan) {
         amount.set((float)(((Float)amount.get()).floatValue() * (1.0 + (Double)CONFIG.analysisDamageTakenBonus.get())));
      }

      int stacks = root.getInt("CalcStacks");
      if (stacks > 0) {
         amount.set((float)(((Float)amount.get()).floatValue() * (1.0 + (Double)CONFIG.damagePerStack.get() * stacks)));
      }

      attacker.getPersistentData().put("tensuramoreskills_schwi", root);
      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(entity, instance)) {
         return true;
      }

      if (!source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !source.is(DamageTypeTags.BYPASSES_ARMOR)) {
         Entity direct = source.getDirectEntity();
         if (direct != null && direct == source.getEntity() && entity.getRandom().nextFloat() < (Double)CONFIG.predictDodgeChance.get()) {
            entity.level().playSound(null, entity.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 2.0F, 1.2F);
            amount.set(0.0F);
            return false;
         } else {
            CompoundTag root = entity.getPersistentData().getCompound("tensuramoreskills_schwi");
            touchCombat(root);
            entity.getPersistentData().put("tensuramoreskills_schwi", root);
            return true;
         }
      } else {
         return true;
      }
   }

   public void onProjectileHit(
      ManasSkillInstance instance,
      LivingEntity entity,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result
   ) {
      if (this.isInSlot(entity, instance)) {
         if (entity.getRandom().nextFloat() < (Double)CONFIG.predictDodgeChance.get()) {
            entity.level().playSound(null, entity.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 2.0F, 1.2F);
            deflection.set(ProjectileDeflection.NONE);
            result.set(ProjectileHitResult.HIT_NO_DAMAGE);
         } else {
            CompoundTag root = entity.getPersistentData().getCompound("tensuramoreskills_schwi");
            touchCombat(root);
            entity.getPersistentData().put("tensuramoreskills_schwi", root);
         }
      }
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      MobEffectInstance effectInstance = (MobEffectInstance)effect.get();
      return effectInstance != null && instance.isToggled() ? !this.collectImmuneEffects(instance, entity).contains(effectInstance.getEffect()) : true;
   }

   private List<Holder<MobEffect>> collectImmuneEffects(ManasSkillInstance instance, LivingEntity entity) {
      List<Holder<MobEffect>> list = new ArrayList<>();
      list.addAll(new AbnormalConditionNullification().getImmuneEffects(instance, entity));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.CHILL));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.DISINTEGRATING));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.SILENCE));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.REST));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.CURSE));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL));
      return list;
   }

   private void castAutoShooting(ManasSkillInstance instance, ServerPlayer player, int mode) {
      if (!EnergyHelper.isOutOfEnergy(player, instance, mode)) {
         CompoundTag root = player.getPersistentData().getCompound("tensuramoreskills_schwi");
         int duration = instance.isMastered(player)
            ? (Integer)CONFIG.autoDurationTicks.get() + (Integer)CONFIG.autoMasteredBonusDurationTicks.get()
            : (Integer)CONFIG.autoDurationTicks.get();
         root.putInt("AutoTicks", duration);
         root.putInt("AutoShotCd", 0);
         player.getPersistentData().put("tensuramoreskills_schwi", root);
         instance.setCoolDown((Integer)CONFIG.autoCooldownTicks.get(), mode);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.schwi_ex_machina.auto_on").withStyle(ChatFormatting.AQUA), true);
         player.level().playSound(null, player.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.0F, 1.2F);
         instance.addMasteryPoint(player);
      }
   }

   private void castLock(ManasSkillInstance instance, ServerPlayer player, int mode) {
      LivingEntity target = getAimedTarget(player, (Double)CONFIG.targetRange.get());
      if (target != null && target != player && target.isAlive()) {
         if (!EnergyHelper.isOutOfEnergy(player, instance, mode)) {
            CompoundTag root = player.getPersistentData().getCompound("tensuramoreskills_schwi");
            root.putUUID("LockUUID", target.getUUID());
            root.putInt("LockTicks", (Integer)CONFIG.lockDurationTicks.get());
            player.getPersistentData().put("tensuramoreskills_schwi", root);
            target.addEffect(new MobEffectInstance(MobEffects.GLOWING, (Integer)CONFIG.lockGlowDurationTicks.get(), 0, false, false, true));
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                  (Integer)CONFIG.lockMovementInterferenceDurationTicks.get(),
                  (Integer)CONFIG.lockMovementInterferenceAmplifier.get(),
                  true,
                  false,
                  true
               )
            );
            instance.setCoolDown((Integer)CONFIG.lockCooldownTicks.get(), mode);
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.schwi_ex_machina.lock", new Object[]{target.getDisplayName()}).withStyle(ChatFormatting.YELLOW),
               true
            );
            player.level().playSound(null, player.blockPosition(), SoundEvents.ARROW_HIT_PLAYER, SoundSource.PLAYERS, 1.0F, 0.8F);
            instance.addMasteryPoint(player);
         }
      }
   }

   private void castStructuralAnalysisPressed(ManasSkillInstance instance, ServerPlayer player, int mode) {
      LivingEntity target = getAimedTarget(player, (Double)CONFIG.targetRange.get());
      if (target != null && target != player && target.isAlive()) {
         if (!EnergyHelper.isOutOfEnergy(player, instance, mode)) {
            CompoundTag root = player.getPersistentData().getCompound("tensuramoreskills_schwi");
            root.putUUID("ScanUUID", target.getUUID());
            root.putInt("ScanTicks", (Integer)CONFIG.structuralAnalysisDurationTicks.get());
            player.getPersistentData().put("tensuramoreskills_schwi", root);
            ServerLevel level = (ServerLevel)player.level();
            level.sendParticles(ParticleTypes.WAX_ON, target.getX(), target.getY() + target.getBbHeight() * 0.8, target.getZ(), 10, 0.35, 0.45, 0.35, 0.0);
            level.sendParticles(ParticleTypes.ENCHANT, target.getX(), target.getY() + target.getBbHeight() * 0.45, target.getZ(), 12, 0.35, 0.45, 0.35, 0.0);
            player.level().playSound(null, target.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 0.8F, 1.8F);
            instance.setCoolDown((Integer)CONFIG.scanCooldownTicks.get(), mode);
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.schwi_ex_machina.scan_start", new Object[]{target.getDisplayName()})
                  .withStyle(ChatFormatting.AQUA),
               true
            );
            instance.addMasteryPoint(player);
         }
      }
   }

   private void castFinal(ManasSkillInstance instance, ServerPlayer player, int mode) {
      if (!EnergyHelper.isOutOfEnergy(player, instance, mode)) {
         CompoundTag root = player.getPersistentData().getCompound("tensuramoreskills_schwi");
         root.putInt("FinalTicks", (Integer)CONFIG.finalDurationTicks.get());
         root.putInt("OverheatTicks", 0);
         player.getPersistentData().put("tensuramoreskills_schwi", root);
         applyFinalBuffs(player, true, true);
         instance.setCoolDown((Integer)CONFIG.finalCooldownTicks.get(), mode);
         player.displayClientMessage(Component.translatable("tensuramoreskills.skill.schwi_ex_machina.final_on").withStyle(ChatFormatting.DARK_AQUA), true);
         player.level().playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.2F, 1.6F);
         instance.addMasteryPoint(player);
      }
   }

   private static void touchCombat(CompoundTag root) {
      int prev = root.getInt("CombatTicks");
      root.putInt("CombatTicks", Math.max(prev, (Integer)CONFIG.combatWindowTicks.get()));
   }

   private static void buildStacks(ManasSkillInstance instance, LivingEntity entity, CompoundTag root) {
      int ticks = root.getInt("CalcTick") + 1;
      int stacks = root.getInt("CalcStacks");
      int max = instance.isMastered(entity) ? (Integer)CONFIG.maxStacksMastered.get() : (Integer)CONFIG.maxStacksBase.get();
      if (ticks >= (Integer)CONFIG.stackIntervalTicks.get()) {
         ticks = 0;
         if (stacks < max) {
            stacks++;
         }
      }

      root.putInt("CalcTick", ticks);
      root.putInt("CalcStacks", stacks);
   }

   private static void resetStacks(CompoundTag root) {
      root.putInt("CalcTick", 0);
      root.putInt("CalcStacks", 0);
      root.putDouble("CdCarry", 0.0);
   }

   private static LivingEntity resolveLockedTarget(LivingEntity owner, CompoundTag root) {
      if (root.getInt("LockTicks") > 0 && root.hasUUID("LockUUID")) {
         if (owner.level() instanceof ServerLevel server) {
            return server.getEntity(root.getUUID("LockUUID")) instanceof LivingEntity living ? living : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static LivingEntity resolveScanTarget(LivingEntity owner, CompoundTag root) {
      if (root.getInt("ScanTicks") > 0 && root.hasUUID("ScanUUID")) {
         if (owner.level() instanceof ServerLevel server) {
            return server.getEntity(root.getUUID("ScanUUID")) instanceof LivingEntity living ? living : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static void accelerateCooldownsSelfOnly(Player player, int stacks, CompoundTag root, int fxTick) {
      if (stacks > 0 && fxTick % (Integer)CONFIG.cooldownApplyIntervalTicks.get() == 0) {
         double carry = root.getDouble("CdCarry");
         carry += CONFIG.cooldownRatePerStack.get() * stacks * ((Integer)CONFIG.cooldownApplyIntervalTicks.get()).intValue();
         int extraDec = (int)Math.floor(carry);
         carry -= extraDec;
         root.putDouble("CdCarry", carry);
         if (extraDec > 0) {
            Skills storage = SkillAPI.getSkillsFrom(player);
            Collection<ManasSkillInstance> learned = storage.getLearnedSkills();
            if (learned != null && !learned.isEmpty()) {
               for (ManasSkillInstance inst : learned) {
                  if (inst != null && inst.onCoolDown(0)) {
                     int cd = inst.getCoolDown(0);
                     if (cd > 0) {
                        inst.setCoolDown(Math.max(0, cd - extraDec), 0);
                        inst.markDirty();
                     }
                  }
               }
            }
         }
      }
   }

   private static void addOrRefreshEffectIfLow(LivingEntity entity, Holder<MobEffect> effect, int duration, int amplifier) {
      MobEffectInstance current = entity.getEffect(effect);
      if (current == null || current.getAmplifier() != amplifier || current.getDuration() < duration / 2) {
         entity.addEffect(new MobEffectInstance(effect, duration, amplifier, false, false, false));
      }
   }

   private static LivingEntity getAimedTarget(LivingEntity shooter, double range) {
      Vec3 eye = shooter.getEyePosition();
      Vec3 look = shooter.getLookAngle();
      Vec3 end = eye.add(look.scale(range));
      AABB box = shooter.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0);
      EntityHitResult result = ProjectileUtil.getEntityHitResult(
         shooter.level(), shooter, eye, end, box, e -> e instanceof LivingEntity && e.isAlive() && e != shooter
      );
      return result != null && result.getEntity() instanceof LivingEntity living ? living : null;
   }

   private static Vec3 getAimedPoint(LivingEntity shooter, double range) {
      Vec3 eye = shooter.getEyePosition();
      Vec3 look = shooter.getLookAngle();
      Vec3 end = eye.add(look.scale(range));
      HitResult hit = shooter.level().clip(new ClipContext(eye, end, Block.COLLIDER, Fluid.NONE, shooter));
      return hit != null && hit.getType() != Type.MISS ? hit.getLocation() : end;
   }

   private static void shootAutoVolley(ServerLevel level, ManasSkillInstance instance, LivingEntity shooter, LivingEntity target, int mode) {
      Vec3 base = shooter.getEyePosition();
      Vec3 to = target.getEyePosition().subtract(base);
      if (!(to.lengthSqr() < 1.0E-4)) {
         Vec3 normal = to.normalize();
         Vec3 side = new Vec3(-normal.z, 0.0, normal.x);
         if (side.lengthSqr() < 1.0E-4) {
            side = new Vec3(1.0, 0.0, 0.0);
         }

         side = side.normalize();
         Vec3 up = new Vec3(0.0, 1.0, 0.0);
         Vec3[] offsets = new Vec3[]{side.scale(0.75).add(up.scale(0.15)), side.scale(-0.75).add(up.scale(0.15)), up.scale(0.65), up.scale(-0.25)};

         for (Vec3 offset : offsets) {
            Vec3 start = base.add(offset);
            shootFireBoltFromTo(
               level,
               instance,
               shooter,
               start,
               target.getEyePosition(),
               ((Double)CONFIG.autoProjectileSpeed.get()).floatValue(),
               ((Double)CONFIG.autoHitDamage.get()).floatValue(),
               mode
            );
         }
      }
   }

   private static void shootFireBoltAtTarget(
      ServerLevel level, ManasSkillInstance instance, LivingEntity shooter, LivingEntity target, float speed, float damage, int mode
   ) {
      shootFireBoltFromTo(level, instance, shooter, shooter.getEyePosition(), target.getEyePosition(), speed, damage, mode);
   }

   private static void shootFireBoltAtPoint(
      ServerLevel level, ManasSkillInstance instance, LivingEntity shooter, Vec3 point, float speed, float damage, int mode
   ) {
      shootFireBoltFromTo(level, instance, shooter, shooter.getEyePosition(), point, speed, damage, mode);
   }

   private static void shootFireBoltFromTo(
      ServerLevel level, ManasSkillInstance instance, LivingEntity shooter, Vec3 start, Vec3 end, float speed, float damage, int mode
   ) {
      FireBoltProjectile bolt = new FireBoltProjectile(level, shooter);
      bolt.setDamage(damage);
      bolt.setSecondaryDamage(damage * ((Double)CONFIG.projectileSecondaryDamageMultiplier.get()).floatValue());
      bolt.setBurnTicks((Integer)CONFIG.projectileBurnTicks.get());
      bolt.setNoGravity(true);
      bolt.setSkill(shooter, instance, (Skill)instance.getSkill(), mode);
      bolt.setPos(start.x, start.y, start.z);
      Vec3 to = end.subtract(start);
      if (to.lengthSqr() > 1.0E-4) {
         Vec3 direction = to.normalize();
         bolt.shoot(direction.x, direction.y, direction.z, speed, 0.0F);
      }

      level.addFreshEntity(bolt);
   }

   private static int fireIntervalFromHeldTicks(int heldTicks, boolean inFinal) {
      if (inFinal) {
         return (Integer)CONFIG.continuousFireFinalInterval.get();
      } else {
         int seconds = heldTicks / 20;
         if (seconds < 2) {
            return (Integer)CONFIG.continuousFireIntervalStage0.get();
         } else if (seconds < 4) {
            return (Integer)CONFIG.continuousFireIntervalStage1.get();
         } else if (seconds < 6) {
            return (Integer)CONFIG.continuousFireIntervalStage2.get();
         } else if (seconds < 8) {
            return (Integer)CONFIG.continuousFireIntervalStage3.get();
         } else {
            return seconds < 10 ? (Integer)CONFIG.continuousFireIntervalStage4.get() : (Integer)CONFIG.continuousFireIntervalStage5.get();
         }
      }
   }

   private static void applyZeroBuffs(LivingEntity entity, boolean enable) {
      AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
      if (movementSpeed != null) {
         AttributeModifier existing = movementSpeed.getModifier(ZERO_MOVESPEED_ID);
         if (enable) {
            if (existing == null) {
               movementSpeed.addOrUpdateTransientModifier(
                  new AttributeModifier(ZERO_MOVESPEED_ID, (Double)CONFIG.zeroMoveSpeedMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL)
               );
            }
         } else if (existing != null) {
            movementSpeed.removeModifier(ZERO_MOVESPEED_ID);
         }
      }

      if (attackSpeed != null) {
         AttributeModifier existing = attackSpeed.getModifier(ZERO_ATTACKSPEED_ID);
         if (enable) {
            if (existing == null) {
               attackSpeed.addOrUpdateTransientModifier(
                  new AttributeModifier(ZERO_ATTACKSPEED_ID, (Double)CONFIG.zeroAttackSpeedMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL)
               );
            }
         } else if (existing != null) {
            attackSpeed.removeModifier(ZERO_ATTACKSPEED_ID);
         }
      }
   }

   private static void applyFinalBuffs(LivingEntity entity, boolean enable, boolean doRefreshEffects) {
      AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      AttributeInstance attackSpeed = entity.getAttribute(Attributes.ATTACK_SPEED);
      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      AttributeInstance toughness = entity.getAttribute(Attributes.ARMOR_TOUGHNESS);
      AttributeInstance attackDamage = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      AttributeInstance knockbackResistance = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
      if (movementSpeed != null) {
         AttributeModifier existing = movementSpeed.getModifier(FINAL_MOVESPEED_ID);
         if (enable) {
            if (existing == null) {
               movementSpeed.addOrUpdateTransientModifier(
                  new AttributeModifier(FINAL_MOVESPEED_ID, (Double)CONFIG.finalMoveSpeedMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL)
               );
            }
         } else if (existing != null) {
            movementSpeed.removeModifier(FINAL_MOVESPEED_ID);
         }
      }

      if (attackSpeed != null) {
         AttributeModifier existing = attackSpeed.getModifier(FINAL_ATTACKSPEED_ID);
         if (enable) {
            if (existing == null) {
               attackSpeed.addOrUpdateTransientModifier(
                  new AttributeModifier(FINAL_ATTACKSPEED_ID, (Double)CONFIG.finalAttackSpeedMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL)
               );
            }
         } else if (existing != null) {
            attackSpeed.removeModifier(FINAL_ATTACKSPEED_ID);
         }
      }

      if (armor != null) {
         AttributeModifier existing = armor.getModifier(FINAL_ARMOR_ID);
         if (enable) {
            if (existing == null) {
               armor.addOrUpdateTransientModifier(new AttributeModifier(FINAL_ARMOR_ID, (Double)CONFIG.finalArmorBonus.get(), Operation.ADD_VALUE));
            }
         } else if (existing != null) {
            armor.removeModifier(FINAL_ARMOR_ID);
         }
      }

      if (toughness != null) {
         AttributeModifier existing = toughness.getModifier(FINAL_TOUGH_ID);
         if (enable) {
            if (existing == null) {
               toughness.addOrUpdateTransientModifier(new AttributeModifier(FINAL_TOUGH_ID, (Double)CONFIG.finalToughnessBonus.get(), Operation.ADD_VALUE));
            }
         } else if (existing != null) {
            toughness.removeModifier(FINAL_TOUGH_ID);
         }
      }

      if (attackDamage != null) {
         AttributeModifier existing = attackDamage.getModifier(FINAL_ATK_ID);
         if (enable) {
            if (existing == null) {
               attackDamage.addOrUpdateTransientModifier(
                  new AttributeModifier(FINAL_ATK_ID, (Double)CONFIG.finalAttackMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL)
               );
            }
         } else if (existing != null) {
            attackDamage.removeModifier(FINAL_ATK_ID);
         }
      }

      if (knockbackResistance != null) {
         AttributeModifier existing = knockbackResistance.getModifier(FINAL_KB_ID);
         if (enable) {
            if (existing == null) {
               knockbackResistance.addOrUpdateTransientModifier(
                  new AttributeModifier(FINAL_KB_ID, (Double)CONFIG.finalKnockbackResistanceBonus.get(), Operation.ADD_VALUE)
               );
            }
         } else if (existing != null) {
            knockbackResistance.removeModifier(FINAL_KB_ID);
         }
      }

      if (enable && doRefreshEffects) {
         addOrRefreshEffectIfLow(entity, MobEffects.DAMAGE_RESISTANCE, (Integer)CONFIG.finalShortBuffDurationTicks.get(), 1);
         addOrRefreshEffectIfLow(entity, MobEffects.DIG_SPEED, (Integer)CONFIG.finalShortBuffDurationTicks.get(), 3);
         addOrRefreshEffectIfLow(entity, MobEffects.JUMP, (Integer)CONFIG.finalShortBuffDurationTicks.get(), 2);
         addOrRefreshEffectIfLow(entity, MobEffects.NIGHT_VISION, (Integer)CONFIG.finalNightVisionDurationTicks.get(), 0);
      }
   }

   private static void tickFx(ServerLevel level, LivingEntity owner, CompoundTag root) {
      int tick = root.getInt("FxTick") + 1;
      root.putInt("FxTick", tick);
      float rotation = root.getFloat("FxRot") + 0.12F;
      if (rotation > (float) (Math.PI * 2)) {
         rotation -= (float) (Math.PI * 2);
      }

      root.putFloat("FxRot", rotation);
      int stacks = root.getInt("CalcStacks");
      if (stacks > 0 && tick % 4 == 0) {
         double radius = 0.6 + 0.02 * Math.min(stacks, 25);
         spawnOrbit(level, owner, rotation, radius, ParticleTypes.END_ROD);
         spawnOrbit(level, owner, rotation + (float) (Math.PI * 2.0 / 3.0), radius, ParticleTypes.ELECTRIC_SPARK);
         spawnOrbit(level, owner, rotation + (float) (Math.PI * 4.0 / 3.0), radius, ParticleTypes.ENCHANT);
      }

      if (root.getInt("AutoTicks") > 0 && tick % 10 == 0) {
         level.sendParticles(ParticleTypes.LAVA, owner.getX(), owner.getY() + owner.getBbHeight() * 0.65, owner.getZ(), 1, 0.15, 0.1, 0.15, 0.0);
      }

      if (root.getInt("FireHeldTicks") > 0 && tick % 2 == 0) {
         double radius = 1.35;
         spawnOrbit(level, owner, rotation * 1.8F, radius, ParticleTypes.FLAME);
         spawnOrbit(level, owner, rotation * 1.8F + (float) (Math.PI * 2.0 / 3.0), radius, ParticleTypes.FLAME);
         spawnOrbit(level, owner, rotation * 1.8F + (float) (Math.PI * 4.0 / 3.0), radius, ParticleTypes.FLAME);
      }

      if (root.getInt("LockTicks") > 0 && root.hasUUID("LockUUID")) {
         LivingEntity locked = resolveLockedTarget(owner, root);
         if (locked != null && locked.isAlive() && tick % 2 == 0) {
            level.sendParticles(ParticleTypes.GLOW, locked.getX(), locked.getY() + locked.getBbHeight() * 0.6, locked.getZ(), 2, 0.25, 0.25, 0.25, 0.0);
            level.sendParticles(ParticleTypes.END_ROD, locked.getX(), locked.getY() + locked.getBbHeight() * 0.15, locked.getZ(), 1, 0.18, 0.25, 0.18, 0.0);
         }
      }

      if (root.getInt("ScanTicks") > 0 && root.hasUUID("ScanUUID") && tick % 6 == 0) {
         LivingEntity scan = resolveScanTarget(owner, root);
         if (scan != null && scan.isAlive()) {
            level.sendParticles(ParticleTypes.WAX_ON, scan.getX(), scan.getY() + scan.getBbHeight() * 0.8, scan.getZ(), 2, 0.2, 0.35, 0.2, 0.0);
            level.sendParticles(ParticleTypes.ENCHANT, scan.getX(), scan.getY() + scan.getBbHeight() * 0.45, scan.getZ(), 2, 0.25, 0.35, 0.25, 0.0);
         }
      }

      if (root.getInt("FinalTicks") > 0 && tick % 2 == 0) {
         level.sendParticles(ParticleTypes.ELECTRIC_SPARK, owner.getX(), owner.getY() + owner.getBbHeight() * 0.7, owner.getZ(), 4, 0.25, 0.2, 0.25, 0.0);
         level.sendParticles(ParticleTypes.ENCHANT, owner.getX(), owner.getY() + owner.getBbHeight() * 0.6, owner.getZ(), 3, 0.25, 0.2, 0.25, 0.0);
      }
   }

   private static void spawnOrbit(ServerLevel level, LivingEntity owner, float angle, double radius, ParticleOptions particle) {
      double offsetX = Math.cos(angle) * radius;
      double offsetZ = Math.sin(angle) * radius;
      double y = owner.getY() + owner.getBbHeight() * 0.65;
      level.sendParticles(particle, owner.getX() + offsetX, y, owner.getZ() + offsetZ, 1, 0.0, 0.0, 0.0, 0.0);
   }
}
