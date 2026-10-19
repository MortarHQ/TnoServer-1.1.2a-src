package com.github.wal_bos.moreskills.ability.skill.unique;

import com.github.wal_bos.moreskills.menu.VoidArchiveForgeMenu;
import com.github.wal_bos.moreskills.menu.VoidArchiveLibraryMenu;
import com.github.wal_bos.moreskills.menu.VoidArchiveLoadoutMenu;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ISubAbilityModeHolder;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class VoidArchiveSkill extends Skill implements ISubAbilityModeHolder {
   private static final double OBTAIN_COST = 135000.0;
   private static final int MAX_MASTERY = 2000;
   public static final int MODE_LIBRARY = 0;
   public static final int MODE_FORGE = 1;
   public static final int MODE_LOADOUT = 2;
   private static final int SUB_MODE_OFFSET = 3;
   private static final int MAX_DAMAGE_RECORDS_NORMAL = 10;
   private static final int MAX_DAMAGE_RECORDS_MASTERED = 16;
   private static final int MAX_EFFECT_RECORDS_NORMAL = 8;
   private static final int MAX_EFFECT_RECORDS_MASTERED = 12;
   private static final int MAX_ECHOES_NORMAL = 3;
   private static final int MAX_ECHOES_MASTERED = 6;
   private static final int MAX_CREATED_MOVES_NORMAL = 16;
   private static final int MAX_CREATED_MOVES_MASTERED = 24;
   private static final int MAX_EQUIPPED_MOVES_NORMAL = 5;
   private static final int MAX_EQUIPPED_MOVES_MASTERED = 8;
   private static final int MAX_MELEE_INFUSIONS_NORMAL = 6;
   private static final int MAX_MELEE_INFUSIONS_MASTERED = 10;
   private static final int ECHO_COPY_COOLDOWN = 100;
   private static final int FORGE_MENU_COOLDOWN = 6;
   private static final int MOVE_USE_COOLDOWN = 80;
   private static final double ECHO_COPY_CHANCE = 0.24;
   private static final double ECHO_COPY_CHANCE_MASTERED = 0.42;
   private static final double ECHO_MASTERY_DIVISOR = 2500.0;
   private static final double MAX_MAGICULE_TAX_FACTOR_DAMAGE = 0.01;
   private static final double MAX_MAGICULE_TAX_FACTOR_EFFECT = 0.0085;
   private static final double MAX_MAGICULE_TAX_FACTOR_ECHO = 0.025;
   private static final double MAX_MAGICULE_TAX_FACTOR_CREATED_MOVE = 0.02;
   private static final double MAX_MAGICULE_TAX_FACTOR_MELEE = 0.015;
   private static final int EFFECT_DURATION_CAP = 600;
   private static final int EFFECT_AMPLIFIER_CAP = 5;
   private static final String TAG_DAMAGE_RECORDS = "DamageRecords";
   private static final String TAG_EFFECT_RECORDS = "EffectRecords";
   private static final String TAG_ACTIVE_DAMAGE = "ActiveDamageIndex";
   private static final String TAG_ACTIVE_EFFECT = "ActiveEffectIndex";
   public static final ResourceLocation SKILL_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "void_archive");

   public VoidArchiveSkill() {
      super(SkillType.UNIQUE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return 135000.0;
   }

   public int getMaxMastery() {
      return 2000;
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/unique/void_archive.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public int getSubModeOffset(ManasSkillInstance instance) {
      return 3;
   }

   public int getModes(ManasSkillInstance instance) {
      int echoes = ISubAbilityModeHolder.getSubSlots(instance).size();
      int equippedCreated = this.getEquippedMoves(instance).size();
      return 3 + echoes + equippedCreated;
   }

   private int clampMode(ManasSkillInstance instance, int mode) {
      int total = Math.max(1, this.getModes(instance));
      if (mode < 0) {
         return 0;
      } else {
         return mode >= total ? total - 1 : mode;
      }
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int total = Math.max(1, this.getModes(instance));
      int current = this.clampMode(instance, mode);
      return reverse ? (current == 0 ? total - 1 : current - 1) : (current == total - 1 ? 0 : current + 1);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      int local = this.clampMode(instance, mode);
      if (local == 0) {
         return "void_archive.library";
      }

      if (local == 1) {
         return "void_archive.forge";
      }

      if (local == 2) {
         return "void_archive.loadout";
      }

      List<AbilitySlot> echoSlots = ISubAbilityModeHolder.getSubSlots(instance);
      int index = local - 3;
      if (index >= 0 && index < echoSlots.size()) {
         return "void_archive.echo";
      }

      int createdIndex = index - echoSlots.size();
      return createdIndex >= 0 && createdIndex < this.getEquippedMoves(instance).size() ? "void_archive.created_move" : super.getModeId(instance, local);
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      int local = this.clampMode(instance, mode);
      if (local == 0) {
         return Component.translatable("tensuramoreskills.skill.mode.void_archive.library").withStyle(ChatFormatting.DARK_PURPLE);
      }

      if (local == 1) {
         return Component.translatable("tensuramoreskills.skill.mode.void_archive.forge").withStyle(ChatFormatting.AQUA);
      }

      if (local == 2) {
         return Component.translatable("tensuramoreskills.skill.mode.void_archive.loadout").withStyle(ChatFormatting.GOLD);
      }

      List<AbilitySlot> echoSlots = ISubAbilityModeHolder.getSubSlots(instance);
      int index = local - this.getSubModeOffset(instance);
      if (index >= 0 && index < echoSlots.size()) {
         AbilitySlot slot = echoSlots.get(index);
         ManasSkill skill = slot.getSkill();
         if (skill instanceof TensuraSkill tensuraSkill) {
            ManasSkillInstance subInstance = (ManasSkillInstance)instance.getSubInstances().get(skill);
            if (subInstance != null) {
               return (Component)(Objects.equals(tensuraSkill.getModeId(subInstance, slot.getMode()), "default")
                  ? skill.getName()
                  : tensuraSkill.getModeName(subInstance, slot.getMode()));
            } else {
               return skill == null ? Component.translatable("tensuramoreskills.skill.mode.void_archive.echo").withStyle(ChatFormatting.GRAY) : skill.getName();
            }
         } else {
            return skill == null ? Component.translatable("tensuramoreskills.skill.mode.void_archive.echo").withStyle(ChatFormatting.GRAY) : skill.getName();
         }
      } else {
         int createdIndex = index - echoSlots.size();
         List<VoidArchiveForgeData.CreatedMove> equippedMoves = this.getEquippedMoves(instance);
         return (Component)(createdIndex >= 0 && createdIndex < equippedMoves.size()
            ? Component.literal(equippedMoves.get(createdIndex).name()).withStyle(ChatFormatting.LIGHT_PURPLE)
            : super.getModeName(instance, local));
      }
   }

   public boolean shouldShowCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      int local = this.clampMode(instance, mode);
      return local < 3 ? instance.onCoolDown(local) : super.shouldShowCoolDown(instance, entity, local);
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      int local = this.clampMode(instance, mode);
      if (local < 3) {
         return false;
      } else {
         List<AbilitySlot> echoSlots = ISubAbilityModeHolder.getSubSlots(instance);
         int index = local - this.getSubModeOffset(instance);
         if (index >= 0 && index < echoSlots.size()) {
            AbilitySlot slot = echoSlots.get(index);
            ManasSkillInstance subInstance = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
            return subInstance != null && subInstance.canScroll(entity, slot.getMode());
         } else {
            return false;
         }
      }
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      int local = this.clampMode(instance, mode);
      if (local < 3) {
         return true;
      } else {
         List<AbilitySlot> echoSlots = ISubAbilityModeHolder.getSubSlots(instance);
         int index = local - this.getSubModeOffset(instance);
         if (index >= 0 && index < echoSlots.size()) {
            AbilitySlot slot = echoSlots.get(index);
            ManasSkillInstance subInstance = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
            return subInstance != null && subInstance.canIgnoreCoolDown(entity, slot.getMode());
         } else {
            return false;
         }
      }
   }

   private void activateMode(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      int local = this.clampMode(instance, mode);
      if (entity instanceof ServerPlayer sp) {
         if (local == 0) {
            sp.openMenu(
               new SimpleMenuProvider(
                  (containerId, inventory, player) -> VoidArchiveLibraryMenu.server(containerId, inventory, sp),
                  Component.translatable("menu.tensuramoreskills.void_archive_library")
               ),
               buf -> VoidArchiveLibraryMenu.writeExtraData(sp, buf)
            );
         } else if (local == 1) {
            if (instance.onCoolDown(1)) {
               sp.displayClientMessage(Component.literal("[VoidArchive] Forge is on cooldown").withStyle(ChatFormatting.RED), true);
            } else {
               sp.openMenu(
                  new SimpleMenuProvider(
                     (containerId, inventory, player) -> VoidArchiveForgeMenu.server(containerId, inventory, sp),
                     Component.translatable("menu.tensuramoreskills.void_archive_forge")
                  ),
                  buf -> VoidArchiveForgeMenu.writeExtraData(sp, buf)
               );
               instance.setCoolDown(6, 1);
               instance.markDirty();
            }
         } else if (local == 2) {
            if (entity.isShiftKeyDown()) {
               if (entity.level() instanceof ServerLevel serverLevel) {
                  if (instance.onCoolDown(2)) {
                     sp.displayClientMessage(Component.literal("[VoidArchive] Loadout move on cooldown").withStyle(ChatFormatting.RED), true);
                  } else {
                     VoidArchiveForgeData.CreatedMove move = this.getActiveCreatedMove(instance);
                     if (move == null) {
                        sp.displayClientMessage(Component.literal("[VoidArchive] No active forged move selected").withStyle(ChatFormatting.RED), true);
                     } else {
                        float moveDamage = 12.0F + (float)(instance.getMastery() / 120.0);
                        boolean ok = VoidArchiveForgeData.executeMove(serverLevel, entity, instance, move, moveDamage);
                        if (ok) {
                           instance.setCoolDown(80, 2);
                           instance.addMasteryPoint(entity, 2.0);
                           instance.markDirty();
                           sp.displayClientMessage(Component.literal("[VoidArchive] Cast " + move.name()).withStyle(ChatFormatting.GREEN), true);
                        } else {
                           sp.displayClientMessage(Component.literal("[VoidArchive] Move failed").withStyle(ChatFormatting.RED), true);
                        }
                     }
                  }
               }
            } else {
               sp.openMenu(
                  new SimpleMenuProvider(
                     (containerId, inventory, player) -> VoidArchiveLoadoutMenu.server(containerId, inventory, sp),
                     Component.translatable("menu.tensuramoreskills.void_archive_loadout")
                  ),
                  buf -> VoidArchiveLoadoutMenu.writeExtraData(sp, buf)
               );
            }
         } else {
            List<AbilitySlot> echoSlots = ISubAbilityModeHolder.getSubSlots(instance);
            int index = local - 3;
            if (index >= 0 && index < echoSlots.size()) {
               AbilitySlot slot = echoSlots.get(index);
               ManasSkill skill = slot.getSkill();
               if (skill != null) {
                  ManasSkillInstance subInstance = (ManasSkillInstance)instance.getSubInstances().get(skill);
                  if (subInstance != null) {
                     subInstance.onPressed(entity, keyNumber, slot.getMode());
                     instance.setCoolDown(Math.max(subInstance.getCoolDown(slot.getMode()), instance.getCoolDown(local)), local);
                     subInstance.setCoolDown(0, slot.getMode());
                     instance.markDirty();
                  } else {
                     sp.displayClientMessage(Component.literal("[VoidArchive] Echo instance missing").withStyle(ChatFormatting.RED), true);
                  }
               } else {
                  sp.displayClientMessage(Component.literal("[VoidArchive] Echo skill missing").withStyle(ChatFormatting.RED), true);
               }
            } else {
               int createdIndex = index - echoSlots.size();
               List<VoidArchiveForgeData.CreatedMove> equippedMoves = this.getEquippedMoves(instance);
               if (createdIndex >= 0 && createdIndex < equippedMoves.size()) {
                  if (entity.level() instanceof ServerLevel serverLevel) {
                     if (instance.onCoolDown(local)) {
                        sp.displayClientMessage(Component.literal("[VoidArchive] Created move on cooldown").withStyle(ChatFormatting.RED), true);
                     } else {
                        VoidArchiveForgeData.CreatedMove move = equippedMoves.get(createdIndex);
                        float moveDamage = 12.0F + (float)(instance.getMastery() / 120.0);
                        boolean ok = VoidArchiveForgeData.executeMove(serverLevel, entity, instance, move, moveDamage);
                        if (ok) {
                           instance.setCoolDown(80, local);
                           instance.addMasteryPoint(entity, 2.0);
                           instance.markDirty();
                           sp.displayClientMessage(Component.literal("[VoidArchive] Cast " + move.name()).withStyle(ChatFormatting.LIGHT_PURPLE), true);
                        } else {
                           sp.displayClientMessage(Component.literal("[VoidArchive] Equipped move failed").withStyle(ChatFormatting.RED), true);
                        }
                     }
                  }
               } else {
                  sp.displayClientMessage(Component.literal("[VoidArchive] Nothing mapped to this mode").withStyle(ChatFormatting.RED), true);
               }
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      int local = this.clampMode(instance, mode);
      this.activateMode(instance, entity, keyNumber, local);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      int local = this.clampMode(instance, mode);
      if (local >= 3) {
         List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
         int index = local - this.getSubModeOffset(instance);
         if (index >= 0 && index < slots.size()) {
            AbilitySlot slot = slots.get(index);
            ManasSkillInstance subInstance = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
            if (subInstance != null) {
               subInstance.onRelease(entity, heldTicks, keyNumber, slot.getMode());
               subInstance.removeAttributeModifiers(entity, slot.getMode());
               instance.setCoolDown(Math.max(subInstance.getCoolDown(slot.getMode()), instance.getCoolDown(local)), local);
               subInstance.setCoolDown(0, slot.getMode());
               instance.markDirty();
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      int local = this.clampMode(instance, mode);
      if (local < 3) {
         return false;
      } else {
         List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
         int index = local - this.getSubModeOffset(instance);
         if (index >= 0 && index < slots.size()) {
            AbilitySlot slot = slots.get(index);
            ManasSkillInstance subInstance = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
            return subInstance != null && subInstance.onHeld(entity, heldTicks, slot.getMode());
         } else {
            return false;
         }
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      int local = this.clampMode(instance, mode);
      if (local >= 3) {
         List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
         int index = local - this.getSubModeOffset(instance);
         if (index >= 0 && index < slots.size()) {
            AbilitySlot slot = slots.get(index);
            ManasSkillInstance subInstance = (ManasSkillInstance)instance.getSubInstances().get(slot.getSkill());
            if (subInstance != null) {
               subInstance.onScroll(entity, delta, slot.getMode());
            }
         }
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      this.forwardBeingDamaged(instance, entity, source, amount);
      if (instance.isToggled() && this.isInSlot(entity, instance, 0) && source.getEntity() != entity && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.recordDamageProfile(instance, entity, source);
         if (source.getEntity() instanceof LivingEntity attacker
            && !(attacker instanceof Player creative && creative.getAbilities().invulnerable)
            && !attacker.getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)
            && !instance.onCoolDown(0)) {
            VoidArchiveSkill.ArchiveCandidate candidate = this.findArchiveCandidate(attacker);
            if (candidate != null) {
               this.archiveEcho(instance, entity, candidate.skill(), candidate.mode(), this.getEchoCopyChance(instance, entity));
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      this.forwardEffectAdded(instance, entity, source, effect);
      if (instance.isToggled() && this.isInSlot(entity, instance, 0) && source != null && source != entity) {
         MobEffectInstance effectInstance = (MobEffectInstance)effect.get();
         if (effectInstance == null) {
            return true;
         }

         if (!instance.isMastered(entity) && ((MobEffect)effectInstance.getEffect().value()).isBeneficial()) {
            return true;
         }

         this.recordEffectProfile(instance, entity, effectInstance);
         if (source instanceof LivingEntity attacker
            && !(attacker instanceof Player creative && creative.getAbilities().invulnerable)
            && !attacker.getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)
            && !instance.onCoolDown(0)) {
            VoidArchiveSkill.ArchiveCandidate candidate = this.findArchiveCandidate(attacker);
            if (candidate != null) {
               this.archiveEcho(instance, entity, candidate.skill(), candidate.mode(), this.getEchoCopyChance(instance, entity));
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.forwardDamageEntity(instance, entity, target, source, amount)) {
         return false;
      }

      if (instance.isToggled() && this.isInSlot(entity, instance, 0)) {
         this.recordDamageProfile(instance, entity, source);
      }

      if (entity.level() instanceof ServerLevel serverLevel) {
         VoidArchiveForgeData.MeleeInfusion infusion = this.getActiveMeleeInfusion(instance);
         if (infusion != null) {
            VoidArchiveForgeData.applyMeleeInfusionHit(serverLevel, entity, target, infusion, (Float)amount.get());
         }
      }

      return true;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.forwardTouchEntity(instance, entity, target, source, amount)) {
         return false;
      }

      if (instance.isToggled() && this.isInSlot(entity, instance, 0)) {
         this.recordDamageProfile(instance, entity, source);
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!this.forwardTakenDamage(instance, entity, source, amount)) {
         return false;
      }

      if (instance.isToggled() && this.isInSlot(entity, instance, 0) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.recordDamageProfile(instance, entity, source);
      }

      return true;
   }

   public void onProjectileHit(
      ManasSkillInstance instance,
      LivingEntity entity,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result
   ) {
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      Map<ManasSkill, ManasSkillInstance> subInstances = instance.getSubInstances();

      for (int i = 0; i < slots.size(); i++) {
         AbilitySlot slot = slots.get(i);
         if (slot.getSkill() != null && this.isInSlot(entity, instance, i + this.getSubModeOffset(instance))) {
            ManasSkillInstance subInstance = subInstances.get(slot.getSkill());
            if (subInstance != null) {
               subInstance.onProjectileHit(entity, hitResult, projectile, deflection, result);
            }
         }
      }

      if (instance.isToggled() && this.isInSlot(entity, instance, 0) && hitResult.getEntity() instanceof LivingEntity target) {
         DamageSource source = projectile.damageSources().mobProjectile(projectile, entity);
         this.recordDamageProfile(instance, entity, source);
         if (!target.getActiveEffects().isEmpty()) {
            MobEffectInstance first = (MobEffectInstance)target.getActiveEffects().iterator().next();
            this.recordEffectProfile(instance, entity, first);
         }
      }
   }

   public boolean createMoveFromSelection(
      ManasSkillInstance instance,
      LivingEntity entity,
      String name,
      VoidArchiveForgeData.MoveShape shape,
      VoidArchiveForgeData.MoveParticleStyle particleStyle,
      @Nullable ResourceLocation customParticleId,
      String secondaryDamage,
      int range,
      float radius,
      float power,
      float speed,
      int burstCount,
      float barrierBypass,
      float resistanceBypass,
      float magiculeSteal,
      int selfBuffDuration,
      boolean multiHit,
      boolean healCaster,
      boolean pullTarget,
      boolean launchTarget,
      boolean followAim,
      boolean guardBreak,
      boolean ignite,
      boolean shockSlow,
      boolean makeInvulnerable,
      boolean giveGuarded,
      boolean trapTargets,
      VoidArchiveForgeData.MoveBlockAction blockAction,
      VoidArchiveForgeData.MoveCastMode castMode,
      boolean projectileCast,
      boolean selfCast,
      boolean aoeCast
   ) {
      VoidArchiveSkill.DamageRecord damage = this.getSelectedDamageRecord(instance);
      VoidArchiveSkill.EffectRecord effect = this.getSelectedEffectRecord(instance);
      String primary = damage != null ? damage.id() : "tensura:magic";
      ResourceLocation effectId = effect != null ? effect.effectId() : null;
      VoidArchiveForgeData.CreatedMove move = VoidArchiveForgeData.buildMove(
         name,
         primary,
         secondaryDamage,
         effectId,
         effect == null ? 0 : Math.min(5, effect.amplifier()),
         effect == null ? 0 : Math.min(600, effect.duration()),
         shape,
         particleStyle,
         customParticleId,
         range,
         radius,
         power,
         speed,
         burstCount,
         barrierBypass,
         resistanceBypass,
         magiculeSteal,
         selfBuffDuration,
         multiHit,
         healCaster,
         pullTarget,
         launchTarget,
         followAim,
         guardBreak,
         ignite,
         shockSlow,
         makeInvulnerable,
         giveGuarded,
         trapTargets,
         VoidArchiveForgeData.MoveEntityAction.NONE,
         null,
         1,
         blockAction,
         castMode,
         projectileCast,
         selfCast,
         aoeCast
      );
      return this.createMove(instance, entity, move);
   }

   public boolean createSummonMove(
      ManasSkillInstance instance,
      LivingEntity entity,
      String moveName,
      int recordedEntityIndex,
      boolean revive,
      int summonCount,
      VoidArchiveForgeData.MoveCastMode castMode
   ) {
      List<VoidArchiveForgeData.RecordedEntity> recorded = this.getRecordedEntities(instance);
      if (recordedEntityIndex >= 0 && recordedEntityIndex < recorded.size()) {
         VoidArchiveForgeData.RecordedEntity data = recorded.get(recordedEntityIndex);
         VoidArchiveForgeData.CreatedMove move = VoidArchiveForgeData.buildMove(
            moveName,
            "tensura:magic",
            "",
            null,
            0,
            0,
            VoidArchiveForgeData.MoveShape.SUMMON,
            VoidArchiveForgeData.MoveParticleStyle.SOUL,
            null,
            6,
            3.0F,
            1.0F,
            1.0F,
            1,
            0.0F,
            0.0F,
            0.0F,
            80,
            false,
            false,
            false,
            false,
            true,
            false,
            false,
            false,
            false,
            true,
            false,
            revive ? VoidArchiveForgeData.MoveEntityAction.REVIVE_RECORDED : VoidArchiveForgeData.MoveEntityAction.SUMMON_RECORDED,
            data.entityId(),
            Math.max(1, Math.min(16, summonCount)),
            VoidArchiveForgeData.MoveBlockAction.NONE,
            castMode,
            false,
            castMode == VoidArchiveForgeData.MoveCastMode.SELF,
            true
         );
         return this.createMove(instance, entity, move);
      } else {
         return false;
      }
   }

   public boolean createMeleeInfusion(
      ManasSkillInstance instance,
      LivingEntity entity,
      String name,
      float powerMultiplier,
      int durationTicks,
      boolean applyDamage,
      boolean applyEffect,
      boolean lifeSteal
   ) {
      if (instance != null && entity != null) {
         VoidArchiveSkill.DamageRecord damage = this.getSelectedDamageRecord(instance);
         VoidArchiveSkill.EffectRecord effect = this.getSelectedEffectRecord(instance);
         String safeName = name != null && !name.isBlank() ? name : "Void Coat";
         VoidArchiveForgeData.MeleeInfusion infusion = VoidArchiveForgeData.buildMeleeInfusion(
            safeName,
            damage != null ? damage.id() : "tensura:magic",
            effect != null ? effect.effectId() : null,
            effect == null ? 0 : Math.min(5, effect.amplifier()),
            effect == null ? 0 : Math.min(600, effect.duration()),
            Math.max(0.2F, powerMultiplier),
            Math.max(40, durationTicks),
            applyDamage,
            applyEffect,
            lifeSteal
         );
         int max = instance.isMastered(entity) ? 10 : 6;
         boolean added = VoidArchiveForgeData.addMeleeInfusion(instance.getOrCreateTag(), infusion, max);
         if (!added) {
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.literal("[VoidArchive] Failed to add melee infusion").withStyle(ChatFormatting.RED), true);
            }

            return false;
         } else {
            instance.markDirty();
            this.applyMagiculeTax(entity, EnergyHelper.getMaxMagicule(entity) * 0.015);
            if (entity instanceof ServerPlayer sp) {
               Skills skills = SkillAPI.getSkillsFrom(sp);
               if (skills != null) {
                  skills.markDirty();
               }

               sp.displayClientMessage(Component.literal("[VoidArchive] Forged melee infusion: " + infusion.name()).withStyle(ChatFormatting.AQUA), true);
            } else if (entity instanceof Player player) {
               player.displayClientMessage(Component.literal("[VoidArchive] Forged melee infusion: " + infusion.name()).withStyle(ChatFormatting.AQUA), true);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean createMove(ManasSkillInstance instance, LivingEntity entity, VoidArchiveForgeData.CreatedMove move) {
      int max = instance.isMastered(entity) ? 24 : 16;
      boolean added = VoidArchiveForgeData.addCreatedMove(instance.getOrCreateTag(), move, max);
      if (!added) {
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.literal("[VoidArchive] Failed to add created move").withStyle(ChatFormatting.RED), true);
         }

         return false;
      } else {
         int newIndex = this.getCreatedMoves(instance).size() - 1;
         this.setActiveCreatedMove(instance, newIndex);
         this.equipCreatedMove(instance, newIndex, entity);
         this.applyMagiculeTax(entity, EnergyHelper.getMaxMagicule(entity) * 0.02);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.void_archive.created_move", new Object[]{move.name()}).withStyle(ChatFormatting.AQUA), true
            );
            player.displayClientMessage(Component.literal("[VoidArchive] Active move set to " + move.name()).withStyle(ChatFormatting.GREEN), true);
         }

         return true;
      }
   }

   public List<VoidArchiveForgeData.CreatedMove> getCreatedMoves(ManasSkillInstance instance) {
      return VoidArchiveForgeData.getCreatedMoves(instance.getOrCreateTag());
   }

   public List<VoidArchiveForgeData.CreatedMove> getEquippedMoves(ManasSkillInstance instance) {
      return VoidArchiveForgeData.getEquippedMoves(instance.getOrCreateTag());
   }

   public List<Integer> getEquippedMoveIndices(ManasSkillInstance instance) {
      return VoidArchiveForgeData.getEquippedMoveIndices(instance.getOrCreateTag());
   }

   public int getSelectedCreatedMoveIndex(ManasSkillInstance instance) {
      return VoidArchiveForgeData.getActiveMoveIndex(instance.getOrCreateTag());
   }

   public void setActiveCreatedMove(ManasSkillInstance instance, int index) {
      VoidArchiveForgeData.setActiveMove(instance.getOrCreateTag(), index);
   }

   public boolean equipCreatedMove(ManasSkillInstance instance, int index, LivingEntity entity) {
      return VoidArchiveForgeData.equipMove(instance.getOrCreateTag(), index, instance.isMastered(entity) ? 8 : 5);
   }

   public void unequipCreatedMove(ManasSkillInstance instance, int index) {
      VoidArchiveForgeData.unequipMove(instance.getOrCreateTag(), index);
   }

   public void removeCreatedMove(ManasSkillInstance instance, int index) {
      VoidArchiveForgeData.removeCreatedMove(instance.getOrCreateTag(), index);
   }

   public boolean isCreatedMoveEquipped(ManasSkillInstance instance, int index) {
      return VoidArchiveForgeData.isMoveEquipped(instance.getOrCreateTag(), index);
   }

   public List<VoidArchiveForgeData.RecordedEntity> getRecordedEntities(ManasSkillInstance instance) {
      return VoidArchiveForgeData.getRecordedEntities(instance.getOrCreateTag());
   }

   public void recordKilledEntity(ManasSkillInstance instance, LivingEntity victim) {
      if (victim != null && victim.getType() != EntityType.PLAYER) {
         VoidArchiveForgeData.addRecordedKill(instance.getOrCreateTag(), victim.getType());
      }
   }

   public List<VoidArchiveSkill.DamageRecord> getDamageRecords(ManasSkillInstance instance) {
      List<VoidArchiveSkill.DamageRecord> out = new ArrayList<>();

      for (Tag entryTag : instance.getOrCreateTag().getList("DamageRecords", 10)) {
         CompoundTag entry = (CompoundTag)entryTag;
         out.add(new VoidArchiveSkill.DamageRecord(entry.getString("Id"), entry.getInt("Count")));
      }

      return out;
   }

   public List<VoidArchiveSkill.EffectRecord> getEffectRecords(ManasSkillInstance instance) {
      List<VoidArchiveSkill.EffectRecord> out = new ArrayList<>();

      for (Tag entryTag : instance.getOrCreateTag().getList("EffectRecords", 10)) {
         CompoundTag entry = (CompoundTag)entryTag;
         out.add(
            new VoidArchiveSkill.EffectRecord(
               ResourceLocation.parse(entry.getString("Effect")), entry.getInt("Amplifier"), entry.getInt("Duration"), entry.getInt("Stacks")
            )
         );
      }

      return out;
   }

   public void removeDamageRecord(ManasSkillInstance instance, int index) {
      ListTag list = instance.getOrCreateTag().getList("DamageRecords", 10);
      if (index >= 0 && index < list.size()) {
         list.remove(index);
         instance.getOrCreateTag().put("DamageRecords", list);
         this.normalizeSelections(instance);
      }
   }

   public void removeEffectRecord(ManasSkillInstance instance, int index) {
      ListTag list = instance.getOrCreateTag().getList("EffectRecords", 10);
      if (index >= 0 && index < list.size()) {
         list.remove(index);
         instance.getOrCreateTag().put("EffectRecords", list);
         this.normalizeSelections(instance);
      }
   }

   public VoidArchiveSkill.DamageRecord getSelectedDamageRecord(ManasSkillInstance instance) {
      List<VoidArchiveSkill.DamageRecord> records = this.getDamageRecords(instance);
      if (records.isEmpty()) {
         return null;
      }

      int idx = Math.min(instance.getOrCreateTag().getInt("ActiveDamageIndex"), records.size() - 1);
      return records.get(idx);
   }

   public VoidArchiveSkill.EffectRecord getSelectedEffectRecord(ManasSkillInstance instance) {
      List<VoidArchiveSkill.EffectRecord> records = this.getEffectRecords(instance);
      if (records.isEmpty()) {
         return null;
      }

      int idx = Math.min(instance.getOrCreateTag().getInt("ActiveEffectIndex"), records.size() - 1);
      return records.get(idx);
   }

   public void setActiveDamage(ManasSkillInstance instance, int index) {
      if (index >= 0 && index < this.getDamageRecords(instance).size()) {
         instance.getOrCreateTag().putInt("ActiveDamageIndex", index);
      }
   }

   public void setActiveEffect(ManasSkillInstance instance, int index) {
      if (index >= 0 && index < this.getEffectRecords(instance).size()) {
         instance.getOrCreateTag().putInt("ActiveEffectIndex", index);
      }
   }

   @Nullable
   private VoidArchiveForgeData.CreatedMove getActiveCreatedMove(ManasSkillInstance instance) {
      List<VoidArchiveForgeData.CreatedMove> moves = this.getCreatedMoves(instance);
      if (moves.isEmpty()) {
         return null;
      }

      int idx = Math.min(this.getSelectedCreatedMoveIndex(instance), moves.size() - 1);
      return idx >= 0 && idx < moves.size() ? moves.get(idx) : null;
   }

   @Nullable
   private VoidArchiveForgeData.MeleeInfusion getActiveMeleeInfusion(ManasSkillInstance instance) {
      List<VoidArchiveForgeData.MeleeInfusion> infusions = VoidArchiveForgeData.getMeleeInfusions(instance.getOrCreateTag());
      if (infusions.isEmpty()) {
         return null;
      }

      int idx = Math.min(instance.getOrCreateTag().getInt("ActiveMeleeInfusionIndex"), infusions.size() - 1);
      return idx >= 0 && idx < infusions.size() ? infusions.get(idx) : null;
   }

   public static Component getDamageDisplay(String id) {
      String key = "tensuramoreskills.void_archive.damage." + id.replace(':', '.');
      Component translated = Component.translatable(key);
      if (!translated.getString().equals(key)) {
         return translated;
      } else if (id.contains(":")) {
         String[] split = id.split(":", 2);
         String path = split[1];
         String pretty = path.replace('_', ' ');
         return Component.literal(pretty.isEmpty() ? id : Character.toUpperCase(pretty.charAt(0)) + pretty.substring(1));
      } else {
         return Component.literal(id);
      }
   }

   public static Component getEffectDisplay(VoidArchiveSkill.EffectRecord effect) {
      MobEffect mobEffect = (MobEffect)BuiltInRegistries.MOB_EFFECT.get(effect.effectId());
      return (Component)(mobEffect == null ? Component.literal(effect.effectId().toString()) : mobEffect.getDisplayName());
   }

   private void recordDamageProfile(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      String id = this.getDamageProfileId(source, entity);
      this.addDamageRecord(instance, entity, id);
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.void_archive.recorded_damage", new Object[]{getDamageDisplay(id)})
               .withStyle(ChatFormatting.DARK_PURPLE),
            true
         );
      }
   }

   private void recordEffectProfile(ManasSkillInstance instance, LivingEntity entity, MobEffectInstance effect) {
      ResourceLocation effectId = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)effect.getEffect().value());
      if (effectId != null) {
         this.addEffectRecord(instance, entity, effectId, Math.min(effect.getAmplifier(), 5), Math.min(effect.getDuration(), 600));
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable(
                     "tensuramoreskills.skill.void_archive.recorded_effect", new Object[]{((MobEffect)effect.getEffect().value()).getDisplayName()}
                  )
                  .withStyle(ChatFormatting.AQUA),
               true
            );
         }
      }
   }

   private String getDamageProfileId(DamageSource source, LivingEntity target) {
      if (target != null && TensuraDamageHelper.isSeveranceDamage(source, target, false)) {
         return "tensura:sever";
      }

      if (TensuraDamageHelper.isSpatialDamage(source)) {
         return "tensura:space";
      }

      if (TensuraDamageHelper.isDarkDamage(source)) {
         return "tensura:dark";
      }

      if (TensuraDamageHelper.isLightDamage(source)) {
         return "tensura:light";
      }

      if (TensuraDamageHelper.isFireDamage(source)) {
         return "minecraft:fire";
      }

      if (TensuraDamageHelper.isWaterDamage(source)) {
         return "tensura:water";
      }

      if (TensuraDamageHelper.isWindDamage(source)) {
         return "tensura:wind";
      }

      if (TensuraDamageHelper.isEarthDamage(source)) {
         return "tensura:earth";
      }

      if (TensuraDamageHelper.isLightningDamage(source)) {
         return "tensura:lightning";
      }

      if (TensuraDamageHelper.isEnergyDrain(source)) {
         return "tensura:drain";
      }

      if (TensuraDamageHelper.isTensuraMagic(source)) {
         return "tensura:magic";
      }

      if (TensuraDamageHelper.isPhysicalAttack(source)) {
         return "minecraft:physical";
      }

      ResourceLocation raw = source.typeHolder()
         .unwrapKey()
         .<ResourceLocation>map(ResourceKey::location)
         .orElse(ResourceLocation.withDefaultNamespace("generic"));
      return raw.toString();
   }

   private void addDamageRecord(ManasSkillInstance instance, LivingEntity entity, String id) {
      CompoundTag tag = instance.getOrCreateTag();
      ListTag list = tag.getList("DamageRecords", 10);

      for (Tag entryTag : list) {
         CompoundTag entry = (CompoundTag)entryTag;
         if (entry.getString("Id").equals(id)) {
            entry.putInt("Count", Math.min(99, entry.getInt("Count") + 1));
            tag.put("DamageRecords", list);
            this.normalizeSelections(instance);
            this.applyMagiculeTax(entity, EnergyHelper.getMaxMagicule(entity) * 0.01);
            return;
         }
      }

      CompoundTag add = new CompoundTag();
      add.putString("Id", id);
      add.putInt("Count", 1);
      list.add(add);
      int max = instance.isMastered(entity) ? 16 : 10;

      while (list.size() > max) {
         list.remove(0);
      }

      tag.put("DamageRecords", list);
      this.normalizeSelections(instance);
      this.applyMagiculeTax(entity, EnergyHelper.getMaxMagicule(entity) * 0.01);
   }

   private void addEffectRecord(ManasSkillInstance instance, LivingEntity entity, ResourceLocation effectId, int amplifier, int duration) {
      CompoundTag tag = instance.getOrCreateTag();
      ListTag list = tag.getList("EffectRecords", 10);

      for (Tag entryTag : list) {
         CompoundTag entry = (CompoundTag)entryTag;
         if (entry.getString("Effect").equals(effectId.toString())) {
            entry.putInt("Amplifier", Math.max(entry.getInt("Amplifier"), amplifier));
            entry.putInt("Duration", Math.max(entry.getInt("Duration"), duration));
            entry.putInt("Stacks", Math.min(99, entry.getInt("Stacks") + 1));
            tag.put("EffectRecords", list);
            this.normalizeSelections(instance);
            this.applyMagiculeTax(entity, EnergyHelper.getMaxMagicule(entity) * 0.0085);
            return;
         }
      }

      CompoundTag add = new CompoundTag();
      add.putString("Effect", effectId.toString());
      add.putInt("Amplifier", amplifier);
      add.putInt("Duration", duration);
      add.putInt("Stacks", 1);
      list.add(add);
      int max = instance.isMastered(entity) ? 12 : 8;

      while (list.size() > max) {
         list.remove(0);
      }

      tag.put("EffectRecords", list);
      this.normalizeSelections(instance);
      this.applyMagiculeTax(entity, EnergyHelper.getMaxMagicule(entity) * 0.0085);
   }

   private void normalizeSelections(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      int damageSize = this.getDamageRecords(instance).size();
      int effectSize = this.getEffectRecords(instance).size();
      int createdSize = this.getCreatedMoves(instance).size();
      tag.putInt("ActiveDamageIndex", damageSize <= 0 ? 0 : Math.min(tag.getInt("ActiveDamageIndex"), damageSize - 1));
      tag.putInt("ActiveEffectIndex", effectSize <= 0 ? 0 : Math.min(tag.getInt("ActiveEffectIndex"), effectSize - 1));
      tag.putInt("ActiveCreatedMoveIndex", createdSize <= 0 ? 0 : Math.min(tag.getInt("ActiveCreatedMoveIndex"), createdSize - 1));
   }

   private void applyMagiculeTax(LivingEntity entity, double amount) {
      if (!(amount <= 0.0)) {
         AttributeInstance attr = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
         if (attr != null) {
            double newBase = Math.max(1.0, attr.getBaseValue() - amount);
            double actualTax = attr.getBaseValue() - newBase;
            if (!(actualTax <= 0.0)) {
               attr.setBaseValue(newBase);
               IExistence existence = TensuraStorages.getExistenceFrom(entity);
               existence.setMagicule(Math.min(existence.getMagicule(), EnergyHelper.getMaxMagicule(entity)));
               existence.markDirty();
            }
         }
      }
   }

   private VoidArchiveSkill.ArchiveCandidate findArchiveCandidate(LivingEntity attacker) {
      IAbility ability = TensuraStorages.getAbilityFrom(attacker);
      if (ability == null) {
         return null;
      }

      int preset = ability.getActivePreset();
      int i = 0;

      while (true) {
         AbilitySlot slot;
         try {
            slot = ability.getAbilitySlot(preset, i);
         } catch (IndexOutOfBoundsException ignored) {
            return null;
         }

         if (slot != null && slot.getSkill() != null) {
            ManasSkill skill = slot.getSkill();
            if (skill != this) {
               ManasSkillInstance copy = skill.createDefaultInstance();
               if (!copy.isTemporarySkill() && !copy.isSubInstance() && (!copy.is(TensuraSkillTags.MAGIC) || copy.is(TensuraSkillTags.COPIABLE_MAGIC))) {
                  ResourceLocation skillId = skill.getRegistryName();
                  if ((skillId == null || !skillId.getPath().contains("spirit")) && (!(skill instanceof Skill typed) || typed.getType() != SkillType.ULTIMATE)) {
                     return new VoidArchiveSkill.ArchiveCandidate(skill, slot.getMode());
                  }
               }
            }
         }

         i++;
      }
   }

   private void archiveEcho(ManasSkillInstance instance, LivingEntity entity, ManasSkill skill, int mode, double chance) {
      if (skill != null) {
         if (!SkillUtils.hasSkill(entity, skill) && !this.hasArchived(instance, skill, mode)) {
            this.pruneEchoesIfNeeded(instance, entity);
            if (!this.learnSubSkill(instance, entity, skill, mode, chance)) {
               if (entity instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("tensuramoreskills.skill.void_archive.fail", new Object[]{skill.getName()}).withStyle(ChatFormatting.RED), true
                  );
               }
            } else {
               double acquire = skill instanceof TensuraSkill t ? t.getAcquiringMagiculeCost(instance) : 2500.0;
               int points = Math.max(1, (int)(acquire / 2500.0));
               this.addMasteryPoint(instance, entity, points);
               this.applyMagiculeTax(entity, acquire * 0.025);
               instance.setCoolDown(100, 0);
               if (entity instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("tensuramoreskills.skill.void_archive.archived_echo", new Object[]{skill.getName()}).withStyle(ChatFormatting.GOLD),
                     true
                  );
               }

               entity.level()
                  .playSound(
                     null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.25F, 1.0F
                  );
            }
         }
      }
   }

   private void pruneEchoesIfNeeded(ManasSkillInstance instance, LivingEntity entity) {
      int max = instance.isMastered(entity) ? 6 : 3;
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      if (slots.size() >= max && entity instanceof ServerPlayer sp) {
         AbilitySlot first = slots.get(0);
         if (first.getSkill() != null) {
            this.removeSubSkill(instance, sp, first.getSkill(), first.getMode());
         }
      }
   }

   private boolean hasArchived(ManasSkillInstance instance, ManasSkill skill, int mode) {
      for (AbilitySlot slot : ISubAbilityModeHolder.getSubSlots(instance)) {
         if (slot.getSkill() == skill && slot.getMode() == mode) {
            return true;
         }
      }

      return false;
   }

   private double getEchoCopyChance(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity) ? 0.42 : 0.24;
   }

   private void forwardBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      Map<ManasSkill, ManasSkillInstance> subInstances = instance.getSubInstances();

      for (int i = 0; i < slots.size(); i++) {
         AbilitySlot slot = slots.get(i);
         if (slot.getSkill() != null && this.isInSlot(entity, instance, i + this.getSubModeOffset(instance))) {
            ManasSkillInstance subInstance = subInstances.get(slot.getSkill());
            if (subInstance != null) {
               subInstance.onBeingDamaged(entity, source, amount);
            }
         }
      }
   }

   private void forwardEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      Map<ManasSkill, ManasSkillInstance> subInstances = instance.getSubInstances();

      for (int i = 0; i < slots.size(); i++) {
         AbilitySlot slot = slots.get(i);
         if (slot.getSkill() != null && this.isInSlot(entity, instance, i + this.getSubModeOffset(instance))) {
            ManasSkillInstance subInstance = subInstances.get(slot.getSkill());
            if (subInstance != null) {
               subInstance.onEffectAdded(entity, source, effect);
            }
         }
      }
   }

   private boolean forwardDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      Map<ManasSkill, ManasSkillInstance> subInstances = instance.getSubInstances();

      for (int i = 0; i < slots.size(); i++) {
         AbilitySlot slot = slots.get(i);
         if (slot.getSkill() != null && this.isInSlot(entity, instance, i + this.getSubModeOffset(instance))) {
            ManasSkillInstance subInstance = subInstances.get(slot.getSkill());
            if (subInstance != null && !subInstance.onDamageEntity(entity, target, source, amount)) {
               return false;
            }
         }
      }

      return true;
   }

   private boolean forwardTouchEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      Map<ManasSkill, ManasSkillInstance> subInstances = instance.getSubInstances();

      for (int i = 0; i < slots.size(); i++) {
         AbilitySlot slot = slots.get(i);
         if (slot.getSkill() != null && this.isInSlot(entity, instance, i + this.getSubModeOffset(instance))) {
            ManasSkillInstance subInstance = subInstances.get(slot.getSkill());
            if (subInstance != null && !subInstance.onTouchEntity(entity, target, source, amount)) {
               return false;
            }
         }
      }

      return true;
   }

   private boolean forwardTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      List<AbilitySlot> slots = ISubAbilityModeHolder.getSubSlots(instance);
      Map<ManasSkill, ManasSkillInstance> subInstances = instance.getSubInstances();

      for (int i = 0; i < slots.size(); i++) {
         AbilitySlot slot = slots.get(i);
         if (slot.getSkill() != null && this.isInSlot(entity, instance, i + this.getSubModeOffset(instance))) {
            ManasSkillInstance subInstance = subInstances.get(slot.getSkill());
            if (subInstance != null && !subInstance.onTakenDamage(entity, source, amount)) {
               return false;
            }
         }
      }

      return true;
   }

   private record ArchiveCandidate(ManasSkill skill, int mode) {
   }

   public record DamageRecord(String id, int count) {
   }

   public record EffectRecord(ResourceLocation effectId, int amplifier, int duration, int stacks) {
   }
}
