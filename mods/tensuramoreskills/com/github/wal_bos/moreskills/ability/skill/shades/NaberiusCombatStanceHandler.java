package com.github.wal_bos.moreskills.ability.skill.shades;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.NaberiusManager;
import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.race.RaceHelper;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.ExistenceStorage;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NaberiusCombatStanceHandler {
   public static final ResourceLocation COMBAT_HP_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_hp");
   public static final ResourceLocation COMBAT_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_damage");
   public static final ResourceLocation COMBAT_ARMOR_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_armor");
   public static final ResourceLocation COMBAT_SPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_speed");
   public static final ResourceLocation COMBAT_ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_attack_speed");
   public static final ResourceLocation COMBAT_REGEN_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_regen");
   public static final ResourceLocation COMBAT_COST_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_cost");
   public static final ResourceLocation COMBAT_DEGRADE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_degrade");
   public static final ResourceLocation COMBAT_DODGE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_dodge");
   public static final ResourceLocation COMBAT_PRESENCE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_presence");
   public static final ResourceLocation COMBAT_DODGE_NEGATE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_combat_dodge_negate");

   private static MoreSkillsConfigs.NaberiusConfig cfg() {
      return MoreSkillsConfigs.GRAND.naberius;
   }

   public static boolean isCombatStance(ServerPlayer player) {
      return NaberiusSkill.root(player).getInt("stance") == 1;
   }

   public static void switchStance(ServerPlayer player) {
      CompoundTag root = NaberiusSkill.root(player);
      int current = root.getInt("stance");
      int next = current == 1 ? 0 : 1;
      root.putInt("stance", next);
      clearCombatAttributes(player);
      if (next == 1) {
         applyCombatAttributes(player);
         if (player.level() instanceof ServerLevel level) {
            forceWarPassivePulse(null, player, level, true);
            lifeDeityOvergrowth(level, player, (Integer)cfg().combatSwitchOvergrowthRadius.get(), (Integer)cfg().combatSwitchOvergrowthAttempts.get());
            spawnCombatRelease(level, player, (Integer)cfg().combatSwitchReleaseCharge.get());
         }

         player.displayClientMessage(Component.literal("Naberius stance: Crimson Eden Warform").withStyle(ChatFormatting.RED), true);
         player.level().playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.2F, 0.75F);
      } else {
         player.displayClientMessage(Component.literal("Naberius stance: Grave Archive").withStyle(ChatFormatting.DARK_GREEN), true);
         player.level().playSound(null, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 0.7F);
      }
   }

   public static void tick(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance, ServerLevel level, CompoundTag root) {
      if (!isCombatStance(player)) {
         clearCombatAttributes(player);
      } else {
         if (player.tickCount % (Integer)cfg().combatAttributeRefreshTicks.get() == 0) {
            applyCombatAttributes(player);
         }

         player.addEffect(
            new MobEffectInstance(
               ModMobEffects.getReference(ModMobEffects.NABERIUS_WILL),
               (Integer)cfg().combatPassiveEffectDurationTicks.get(),
               (Integer)cfg().combatPassiveEffectAmplifier.get(),
               false,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               ModMobEffects.getReference(ModMobEffects.NABERIUS_VISION),
               (Integer)cfg().combatPassiveEffectDurationTicks.get(),
               (Integer)cfg().combatPassiveEffectAmplifier.get(),
               false,
               false,
               true
            )
         );
         if (player.tickCount % (Integer)cfg().combatForceWarPulseIntervalTicks.get() == 0) {
            forceWarPassivePulse(skill, player, level, false);
         }

         if (player.tickCount % (Integer)cfg().combatOvergrowthIntervalTicks.get() == 0) {
            lifeDeityOvergrowth(level, player, (Integer)cfg().combatPassiveOvergrowthRadius.get(), (Integer)cfg().combatPassiveOvergrowthAttempts.get());
         }

         if (player.tickCount % (Integer)cfg().combatRecoveryIntervalTicks.get() == 0) {
            passiveCombatRecovery(skill, player, level);
         }

         if (player.tickCount % (Integer)cfg().combatPressureIntervalTicks.get() == 0) {
            passiveCombatPressure(player, level);
         }

         if (player.tickCount % (Integer)cfg().combatAuraIntervalTicks.get() == 0) {
            skill.spawnNaberiusAura(level, player);
         }
      }
   }

   public static void onPressed(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance, int mode) {
      switch (mode) {
         case 0:
         case 1:
            genesisPulse(skill, player, instance);
            break;
         case 2:
            spiritWarStep(skill, player, instance);
            break;
         case 3:
            samsaraBloom(skill, player, instance);
            break;
         case 4:
            player.displayClientMessage(Component.literal("Hold to cast Eden of the Life Deity.").withStyle(ChatFormatting.RED), true);
            break;
         case 5:
            if (player.isShiftKeyDown()) {
               bestowOneLife(skill, player, instance);
            } else {
               deathlessLifeSiphon(skill, player, instance);
            }
            break;
         case 6:
            forceEvolution(skill, player, instance);
            break;
         case 7:
            createFruitOfLife(skill, player, instance);
      }
   }

   public static boolean onHeld(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance, int heldTicks, int mode) {
      if (mode != 4) {
         return false;
      }

      if (player.level() instanceof ServerLevel level) {
         if (heldTicks % (Integer)cfg().combatDomainHoldCheckIntervalTicks.get() == 0) {
            int charge = Math.max(1, heldTicks / 20);
            double cost = (Double)cfg().combatDomainHoldBaseCost.get() + charge * (Double)cfg().combatDomainHoldCostPerSecond.get();
            if (!skill.canAfford(player, cost)) {
               return false;
            }

            spawnCombatCharge(level, player, charge);
            if (heldTicks % (Integer)cfg().combatDomainHoldPulseIntervalTicks.get() == 0) {
               player.heal(
                  Math.max(
                     ((Double)cfg().combatDomainHoldMinHeal.get()).floatValue(),
                     player.getMaxHealth() * ((Double)cfg().combatDomainHoldHealMaxHealthRatio.get()).floatValue()
                  )
               );
               restoreExistence(
                  player,
                  (Double)cfg().combatDomainHoldMagiculeRestorePerCharge.get() * charge,
                  (Double)cfg().combatDomainHoldAuraRestorePerCharge.get() * charge,
                  (Double)cfg().combatDomainHoldSpiritualRestorePerCharge.get() * charge
               );
               lifeDeityOvergrowth(
                  level,
                  player,
                  Math.min(
                     (Integer)cfg().combatDomainHoldOvergrowthMaxRadius.get(),
                     (Integer)cfg().combatDomainHoldOvergrowthBaseRadius.get() + charge / (Integer)cfg().combatDomainHoldOvergrowthChargeDivisor.get()
                  ),
                  (Integer)cfg().combatDomainHoldOvergrowthBaseAttempts.get() + charge * (Integer)cfg().combatDomainHoldOvergrowthAttemptsPerCharge.get()
               );
               applyAllyRegenPulse(
                  skill,
                  player,
                  level,
                  (Double)cfg().combatDomainHoldAllyRadiusBase.get() + charge * (Double)cfg().combatDomainHoldAllyRadiusPerCharge.get(),
                  (Integer)cfg().combatDomainHoldAllyDurationBase.get() + charge * (Integer)cfg().combatDomainHoldAllyDurationPerCharge.get(),
                  Math.min((Integer)cfg().combatDomainHoldAllyMaxAmplifier.get(), charge / (Integer)cfg().combatDomainHoldAllyAmplifierDivisor.get())
               );
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static void onRelease(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance, int heldTicks, int mode) {
      if (mode == 4) {
         if (player.level() instanceof ServerLevel level) {
            if (heldTicks >= (Integer)cfg().combatDomainMinimumHeldTicks.get()) {
               int charge = Math.max(1, heldTicks / 20);
               double cost = (Double)cfg().combatDomainReleaseBaseCost.get()
                  + charge * (Double)cfg().combatDomainReleaseCostPerSecond.get()
                  + charge * charge * (Double)cfg().combatDomainReleaseCostPerSecondSquared.get();
               if (!skill.spendMagicule(player, cost)) {
                  player.displayClientMessage(Component.literal("Eden of the Life Deity needs " + (long)cost + " MP.").withStyle(ChatFormatting.RED), true);
               } else {
                  AABB box = player.getBoundingBox()
                     .inflate((Double)cfg().combatDomainRadiusBase.get() + charge * (Double)cfg().combatDomainRadiusPerCharge.get());
                  int hit = 0;
                  int blessed = 0;

                  for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, LivingEntity::isAlive)) {
                     if (target == player || isAlly(player, target)) {
                        applyInstantRegen(
                           skill,
                           player,
                           target,
                           (Integer)cfg().combatDomainAllyRegenDurationBase.get() + charge * (Integer)cfg().combatDomainAllyRegenDurationPerCharge.get(),
                           Math.min(
                              (Integer)cfg().combatDomainAllyRegenMaxAmplifier.get(),
                              (Integer)cfg().combatDomainAllyRegenBaseAmplifier.get() + charge / (Integer)cfg().combatDomainAllyRegenAmplifierDivisor.get()
                           )
                        );
                        target.addEffect(
                           new MobEffectInstance(
                              TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
                              (Integer)cfg().combatDomainAllyHakiDurationBase.get() + charge * (Integer)cfg().combatDomainAllyHakiDurationPerCharge.get(),
                              Math.min(
                                 (Integer)cfg().combatDomainAllyHakiMaxAmplifier.get(), charge / (Integer)cfg().combatDomainAllyHakiAmplifierDivisor.get()
                              ),
                              false,
                              false,
                              true
                           )
                        );
                        restoreExistenceTo(
                           target,
                           (Double)cfg().combatDomainAllyMagiculeRestoreBase.get() + charge * (Double)cfg().combatDomainAllyMagiculeRestorePerCharge.get(),
                           (Double)cfg().combatDomainAllyAuraRestoreBase.get() + charge * (Double)cfg().combatDomainAllyAuraRestorePerCharge.get(),
                           (Double)cfg().combatDomainAllySpiritualRestoreBase.get() + charge * (Double)cfg().combatDomainAllySpiritualRestorePerCharge.get()
                        );
                        blessed++;
                     } else if (canHit(player, target)) {
                        target.hurt(
                           player.damageSources().magic(),
                           (float)((Double)cfg().combatDomainEnemyDamageBase.get() + charge * (Double)cfg().combatDomainEnemyDamagePerCharge.get())
                        );
                        target.addEffect(
                           new MobEffectInstance(
                              ModMobEffects.getReference(ModMobEffects.ORGAN_DECAY),
                              (Integer)cfg().combatDomainEnemyEffectDurationBase.get() + charge * (Integer)cfg().combatDomainEnemyEffectDurationPerCharge.get(),
                              Math.min(
                                 (Integer)cfg().combatDomainEnemyOrganDecayMaxAmplifier.get(),
                                 (Integer)cfg().combatDomainEnemyOrganDecayBaseAmplifier.get()
                                    + charge / (Integer)cfg().combatDomainEnemyOrganDecayAmplifierDivisor.get()
                              ),
                              false,
                              true,
                              true
                           )
                        );
                        target.addEffect(
                           new MobEffectInstance(
                              ModMobEffects.getReference(ModMobEffects.LIFE_SEIZURE),
                              (Integer)cfg().combatDomainEnemyEffectDurationBase.get() + charge * (Integer)cfg().combatDomainEnemyEffectDurationPerCharge.get(),
                              Math.min(
                                 (Integer)cfg().combatDomainEnemyLifeSeizureMaxAmplifier.get(),
                                 (Integer)cfg().combatDomainEnemyLifeSeizureBaseAmplifier.get()
                                    + charge / (Integer)cfg().combatDomainEnemyLifeSeizureAmplifierDivisor.get()
                              ),
                              false,
                              true,
                              true
                           )
                        );
                        stealExistence(
                           player,
                           target,
                           (Double)cfg().combatDomainEnemyMagiculeStealBase.get() + charge * (Double)cfg().combatDomainEnemyMagiculeStealPerCharge.get(),
                           (Double)cfg().combatDomainEnemyAuraStealBase.get() + charge * (Double)cfg().combatDomainEnemyAuraStealPerCharge.get(),
                           (Double)cfg().combatDomainEnemySpiritualStealBase.get() + charge * (Double)cfg().combatDomainEnemySpiritualStealPerCharge.get()
                        );
                        pullToPlayer(player, target, (Double)cfg().combatDomainPullBase.get() + charge * (Double)cfg().combatDomainPullPerCharge.get());
                        hit++;
                     }
                  }

                  player.heal(
                     Math.max(
                        player.getMaxHealth() * ((Double)cfg().combatDomainSelfHealMaxHealthRatio.get()).floatValue(),
                        hit * ((Double)cfg().combatDomainSelfHealPerHit.get()).floatValue()
                           + blessed * ((Double)cfg().combatDomainSelfHealPerBlessed.get()).floatValue()
                     )
                  );
                  restoreExistence(
                     player,
                     (Double)cfg().combatDomainSelfMagiculeRestoreBase.get() + hit * (Double)cfg().combatDomainSelfMagiculeRestorePerHit.get(),
                     (Double)cfg().combatDomainSelfAuraRestoreBase.get() + hit * (Double)cfg().combatDomainSelfAuraRestorePerHit.get(),
                     (Double)cfg().combatDomainSelfSpiritualRestoreBase.get() + hit * (Double)cfg().combatDomainSelfSpiritualRestorePerHit.get()
                  );
                  player.swing(InteractionHand.MAIN_HAND, true);
                  lifeDeityOvergrowth(
                     level,
                     player,
                     (Integer)cfg().combatDomainReleaseOvergrowthRadiusBase.get() + charge,
                     (Integer)cfg().combatDomainReleaseOvergrowthAttemptsBase.get()
                        + charge * (Integer)cfg().combatDomainReleaseOvergrowthAttemptsPerCharge.get()
                  );
                  spawnCombatRelease(level, player, charge + (Integer)cfg().combatDomainReleaseVisualChargeBonus.get());
                  level.playSound(null, player.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 2.0F, 0.75F);
                  level.playSound(null, player.blockPosition(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 1.7F, 1.1F);
                  instance.setCoolDown((Integer)cfg().combatDomainCooldownTicks.get(), 4);
                  player.displayClientMessage(
                     Component.literal("Eden blesses " + blessed + " allies and consumes " + hit + " enemies.").withStyle(ChatFormatting.RED), true
                  );
               }
            }
         }
      }
   }

   private static void genesisPulse(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance) {
      if (player.level() instanceof ServerLevel level) {
         if (!skill.spendMagicule(player, (Double)cfg().genesisPulseCost.get())) {
            player.displayClientMessage(
               Component.literal("Genesis Pulse needs " + ((Double)cfg().genesisPulseCost.get()).longValue() + " MP.").withStyle(ChatFormatting.RED), true
            );
         } else {
            player.heal(
               Math.max(
                  ((Double)cfg().genesisPulseMinHeal.get()).floatValue(),
                  player.getMaxHealth() * ((Double)cfg().genesisPulseHealMaxHealthRatio.get()).floatValue()
               )
            );
            restoreExistence(
               player,
               (Double)cfg().genesisPulseMagiculeRestore.get(),
               (Double)cfg().genesisPulseAuraRestore.get(),
               (Double)cfg().genesisPulseSpiritualRestore.get()
            );
            int blessed = applyAllyRegenPulse(
               skill,
               player,
               level,
               (Double)cfg().genesisPulseAllyRadius.get(),
               (Integer)cfg().genesisPulseAllyRegenDurationTicks.get(),
               (Integer)cfg().genesisPulseAllyRegenAmplifier.get()
            );
            player.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
                  (Integer)cfg().genesisPulseHakiDurationTicks.get(),
                  (Integer)cfg().genesisPulseHakiAmplifier.get(),
                  false,
                  false,
                  true
               )
            );
            applyInstantRegen(
               skill, player, player, (Integer)cfg().genesisPulseSelfRegenDurationTicks.get(), (Integer)cfg().genesisPulseSelfRegenAmplifier.get()
            );
            cleanseCombatDebuffs(player);
            lifeDeityOvergrowth(level, player, (Integer)cfg().genesisPulseOvergrowthRadius.get(), (Integer)cfg().genesisPulseOvergrowthAttempts.get());
            spawnCombatBurst(level, player, (Double)cfg().genesisPulseBurstRadius.get());
            level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.1F, 0.8F);
            instance.setCoolDown((Integer)cfg().genesisPulseCooldownTicks.get(), 1);
            player.displayClientMessage(Component.literal("Genesis Pulse restores " + blessed + " allied life signs.").withStyle(ChatFormatting.RED), true);
         }
      }
   }

   private static void spiritWarStep(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance) {
      if (player.level() instanceof ServerLevel level) {
         if (!skill.spendMagicule(player, (Double)cfg().spiritWarStepCost.get())) {
            player.displayClientMessage(
               Component.literal("Spirit War Step needs " + ((Double)cfg().spiritWarStepCost.get()).longValue() + " MP.").withStyle(ChatFormatting.RED), true
            );
         } else {
            Vec3 look = player.getLookAngle().normalize();
            player.setDeltaMovement(
               player.getDeltaMovement()
                  .add(
                     look.x * (Double)cfg().spiritWarStepForwardVelocity.get(),
                     (Double)cfg().spiritWarStepUpVelocity.get(),
                     look.z * (Double)cfg().spiritWarStepForwardVelocity.get()
                  )
            );
            player.hurtMarked = true;
            AABB box = player.getBoundingBox()
               .move(look.scale((Double)cfg().spiritWarStepHitboxForwardOffset.get()))
               .inflate(
                  (Double)cfg().spiritWarStepHitboxHorizontalInflate.get(),
                  (Double)cfg().spiritWarStepHitboxVerticalInflate.get(),
                  (Double)cfg().spiritWarStepHitboxHorizontalInflate.get()
               );
            int hit = 0;

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> canHit(player, e))) {
               target.hurt(player.damageSources().magic(), ((Double)cfg().spiritWarStepDamage.get()).floatValue());
               target.addEffect(
                  new MobEffectInstance(
                     ModMobEffects.getReference(ModMobEffects.LIFE_SEIZURE),
                     (Integer)cfg().spiritWarStepLifeSeizureDurationTicks.get(),
                     (Integer)cfg().spiritWarStepLifeSeizureAmplifier.get(),
                     false,
                     true,
                     true
                  )
               );
               stealExistence(
                  player,
                  target,
                  (Double)cfg().spiritWarStepMagiculeSteal.get(),
                  (Double)cfg().spiritWarStepAuraSteal.get(),
                  (Double)cfg().spiritWarStepSpiritualSteal.get()
               );
               knockAway(player, target, (Double)cfg().spiritWarStepKnockbackStrength.get());
               hit++;
            }

            if (hit > 0) {
               player.heal(
                  Math.max(((Double)cfg().spiritWarStepMinHealOnHit.get()).floatValue(), hit * ((Double)cfg().spiritWarStepHealPerHit.get()).floatValue())
               );
               restoreExistence(
                  player,
                  hit * (Double)cfg().spiritWarStepMagiculeRestorePerHit.get(),
                  hit * (Double)cfg().spiritWarStepAuraRestorePerHit.get(),
                  hit * (Double)cfg().spiritWarStepSpiritualRestorePerHit.get()
               );
            }

            spawnCombatBurst(level, player, (Double)cfg().spiritWarStepBurstRadius.get());
            level.playSound(null, player.blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.6F, 0.55F);
            instance.setCoolDown((Integer)cfg().spiritWarStepCooldownTicks.get(), 2);
            player.displayClientMessage(Component.literal("Spirit War Step cuts through soul-force.").withStyle(ChatFormatting.RED), true);
         }
      }
   }

   private static void samsaraBloom(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance) {
      if (player.level() instanceof ServerLevel level) {
         if (!skill.spendMagicule(player, (Double)cfg().samsaraBloomCost.get())) {
            player.displayClientMessage(
               Component.literal("Samsara Bloom needs " + ((Double)cfg().samsaraBloomCost.get()).longValue() + " MP.").withStyle(ChatFormatting.RED), true
            );
         } else {
            AABB box = player.getBoundingBox().inflate((Double)cfg().samsaraBloomRadius.get());
            int hit = 0;
            int rooted = 0;

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> canHit(player, e))) {
               float missing = Math.max(0.0F, target.getMaxHealth() - target.getHealth());
               target.hurt(
                  player.damageSources().magic(),
                  ((Double)cfg().samsaraBloomDamageBase.get()).floatValue() + missing * ((Double)cfg().samsaraBloomMissingHealthDamageRatio.get()).floatValue()
               );
               target.addEffect(
                  new MobEffectInstance(
                     ModMobEffects.getReference(ModMobEffects.ORGAN_DECAY),
                     (Integer)cfg().samsaraBloomEffectDurationTicks.get(),
                     (Integer)cfg().samsaraBloomOrganDecayAmplifier.get(),
                     false,
                     true,
                     true
                  )
               );
               target.addEffect(
                  new MobEffectInstance(
                     ModMobEffects.getReference(ModMobEffects.LIFE_SEIZURE),
                     (Integer)cfg().samsaraBloomEffectDurationTicks.get(),
                     (Integer)cfg().samsaraBloomLifeSeizureAmplifier.get(),
                     false,
                     true,
                     true
                  )
               );
               stealExistence(
                  player,
                  target,
                  (Double)cfg().samsaraBloomMagiculeSteal.get(),
                  (Double)cfg().samsaraBloomAuraSteal.get(),
                  (Double)cfg().samsaraBloomSpiritualSteal.get()
               );
               pullToPlayer(player, target, (Double)cfg().samsaraBloomPullStrength.get());
               if (target.onGround()) {
                  BlockPos p = target.blockPosition();

                  for (int i = 0; i < cfg().samsaraBloomRootAttemptsPerTarget.get(); i++) {
                     BlockPos vine = p.offset(level.random.nextInt(5) - 2, 0, level.random.nextInt(5) - 2);
                     if (level.isLoaded(vine) && level.isEmptyBlock(vine) && level.getBlockState(vine.below()).isSolidRender(level, vine.below())) {
                        level.setBlock(vine, Blocks.OAK_SAPLING.defaultBlockState(), 3);
                        rooted++;
                     }
                  }
               }

               hit++;
            }

            player.heal(Math.max(((Double)cfg().samsaraBloomMinHeal.get()).floatValue(), hit * ((Double)cfg().samsaraBloomHealPerHit.get()).floatValue()));
            restoreExistence(
               player,
               hit * (Double)cfg().samsaraBloomMagiculeRestorePerHit.get(),
               hit * (Double)cfg().samsaraBloomAuraRestorePerHit.get(),
               hit * (Double)cfg().samsaraBloomSpiritualRestorePerHit.get()
            );
            lifeDeityOvergrowth(level, player, (Integer)cfg().samsaraBloomOvergrowthRadius.get(), (Integer)cfg().samsaraBloomOvergrowthAttempts.get());
            spawnCombatRelease(level, player, (Integer)cfg().samsaraBloomReleaseCharge.get());
            level.playSound(null, player.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.4F, 0.7F);
            instance.setCoolDown((Integer)cfg().samsaraBloomCooldownTicks.get(), 3);
            player.displayClientMessage(
               Component.literal("Samsara Bloom roots " + rooted + " vessels and harvests " + hit + " souls.").withStyle(ChatFormatting.RED), true
            );
         }
      }
   }

   private static void deathlessLifeSiphon(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().deathlessLifeSiphonRange.get(), false);
      if (target == null) {
         player.displayClientMessage(Component.literal("No life can be severed.").withStyle(ChatFormatting.RED), true);
      } else if (!skill.spendMagicule(player, (Double)cfg().deathlessLifeSiphonCost.get())) {
         player.displayClientMessage(
            Component.literal("Deathless Life Siphon needs " + ((Double)cfg().deathlessLifeSiphonCost.get()).longValue() + " MP.")
               .withStyle(ChatFormatting.RED),
            true
         );
      } else if (player.level() instanceof ServerLevel level) {
         NaberiusManager.markLifeBypass(target, player, (Integer)cfg().deathlessLifeSiphonBypassTicks.get());
         float var8 = Math.min(
            target.getMaxHealth() * ((Double)cfg().deathlessLifeSiphonMaxHealthDamageCapRatio.get()).floatValue(),
            ((Double)cfg().deathlessLifeSiphonDamageBase.get()).floatValue()
               + target.getMaxHealth() * ((Double)cfg().deathlessLifeSiphonDamageMaxHealthRatio.get()).floatValue()
         );
         target.hurt(player.damageSources().magic(), var8);
         target.addEffect(
            new MobEffectInstance(
               ModMobEffects.getReference(ModMobEffects.LIFE_SEIZURE),
               (Integer)cfg().deathlessLifeSiphonEffectDurationTicks.get(),
               (Integer)cfg().deathlessLifeSiphonLifeSeizureAmplifier.get(),
               false,
               true,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               ModMobEffects.getReference(ModMobEffects.ORGAN_DECAY),
               (Integer)cfg().deathlessLifeSiphonEffectDurationTicks.get(),
               (Integer)cfg().deathlessLifeSiphonOrganDecayAmplifier.get(),
               false,
               true,
               true
            )
         );
         double stolen = stealExistence(
            player,
            target,
            (Double)cfg().deathlessLifeSiphonMagiculeSteal.get(),
            (Double)cfg().deathlessLifeSiphonAuraSteal.get(),
            (Double)cfg().deathlessLifeSiphonSpiritualSteal.get()
         );
         if (target.isAlive()
            && target.getHealth()
               <= Math.max(
                  ((Double)cfg().deathlessLifeSiphonExecuteMinHealth.get()).floatValue(),
                  target.getMaxHealth() * ((Double)cfg().deathlessLifeSiphonExecuteMaxHealthRatio.get()).floatValue()
               )) {
            target.setLastHurtByMob(player);
            target.hurt(player.damageSources().genericKill(), Float.MAX_VALUE);
         }

         player.heal(
            Math.max(
               ((Double)cfg().deathlessLifeSiphonMinHeal.get()).floatValue(), var8 * ((Double)cfg().deathlessLifeSiphonHealDamageRatio.get()).floatValue()
            )
         );
         restoreExistence(player, stolen, stolen, stolen * (Double)cfg().deathlessLifeSiphonSpiritualRestoreStolenRatio.get());
         spawnDrainLine(level, target, player);
         spawnTargetBurst(level, target, (Double)cfg().deathlessLifeSiphonTargetBurstRadius.get());
         level.playSound(null, target.blockPosition(), SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 1.2F, 0.55F);
         instance.setCoolDown((Integer)cfg().deathlessLifeSiphonCooldownTicks.get(), 5);
         player.displayClientMessage(Component.literal("Deathless Life Siphon cuts through extra lives.").withStyle(ChatFormatting.RED), true);
      }
   }

   private static void bestowOneLife(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().bestowOneLifeRange.get(), false);
      if (target == null) {
         target = player;
      }

      if (target != player && !isAlly(player, target)) {
         player.displayClientMessage(Component.literal("Only allies can receive a bestowed life.").withStyle(ChatFormatting.RED), true);
      } else if (target.getPersistentData().getBoolean("tms_naberius_bestowed_life")) {
         player.displayClientMessage(Component.literal("That soul already carries a bestowed life.").withStyle(ChatFormatting.RED), true);
      } else if (!skill.spendMagicule(player, (Double)cfg().bestowOneLifeCost.get())) {
         player.displayClientMessage(
            Component.literal("Bestow Life needs " + ((Double)cfg().bestowOneLifeCost.get()).longValue() + " MP.").withStyle(ChatFormatting.RED), true
         );
      } else {
         target.getPersistentData().putBoolean("tms_naberius_bestowed_life", true);
         target.getPersistentData().putUUID("tms_naberius_bestowed_life_owner", player.getUUID());
         applyInstantRegen(skill, player, target, (Integer)cfg().bestowOneLifeRegenDurationTicks.get(), (Integer)cfg().bestowOneLifeRegenAmplifier.get());
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
               (Integer)cfg().bestowOneLifeHakiDurationTicks.get(),
               (Integer)cfg().bestowOneLifeHakiAmplifier.get(),
               false,
               false,
               true
            )
         );
         restoreExistenceTo(
            target,
            (Double)cfg().bestowOneLifeMagiculeRestore.get(),
            (Double)cfg().bestowOneLifeAuraRestore.get(),
            (Double)cfg().bestowOneLifeSpiritualRestore.get()
         );
         if (player.level() instanceof ServerLevel level) {
            spawnCombatBurst(level, target, (Double)cfg().bestowOneLifeBurstRadius.get());
            level.playSound(null, target.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.2F, 0.8F);
         }

         instance.setCoolDown((Integer)cfg().bestowOneLifeCooldownTicks.get(), 5);
         player.displayClientMessage(Component.literal("Naberius grants one forbidden life.").withStyle(ChatFormatting.RED), true);
      }
   }

   private static void forceEvolution(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().forceEvolutionRange.get(), false);
      if (target == null) {
         player.displayClientMessage(Component.literal("No soul can be forced to evolve.").withStyle(ChatFormatting.RED), true);
      } else {
         if (target instanceof ServerPlayer targetPlayer) {
            if (player.isShiftKeyDown()) {
               forcePlayerAwakening(skill, player, targetPlayer, instance);
            } else {
               forcePlayerRaceEvolution(skill, player, targetPlayer, instance);
            }
         } else if (player.isShiftKeyDown()) {
            forceSubordinateAwakening(skill, player, target, instance);
         } else {
            forceSubordinateEvolution(skill, player, target, instance);
         }
      }
   }

   private static void forcePlayerRaceEvolution(NaberiusSkill skill, ServerPlayer player, ServerPlayer target, ManasSkillInstance instance) {
      int soulCost = (Integer)cfg().forcePlayerRaceEvolutionSoulCost.get();
      if (getSoulPoints(player) < soulCost) {
         player.displayClientMessage(Component.literal("Race Evolution needs " + soulCost + " soul points.").withStyle(ChatFormatting.RED), true);
      } else if (!skill.spendMagicule(player, (Double)cfg().forcePlayerRaceEvolutionCost.get())) {
         player.displayClientMessage(
            Component.literal("Race Evolution needs " + ((Double)cfg().forcePlayerRaceEvolutionCost.get()).longValue() + " MP.").withStyle(ChatFormatting.RED),
            true
         );
      } else {
         Object raceData = RaceAPI.getRaceFrom(target);
         Object race = getCurrentRace(raceData);
         if (race == null) {
            restoreExistence(player, (Double)cfg().forcePlayerRaceEvolutionCost.get(), 0.0, 0.0);
            player.displayClientMessage(Component.literal("That player has no race data.").withStyle(ChatFormatting.RED), true);
         } else {
            Object chosen = getReadyEvolution(target, race);
            if (chosen == null) {
               restoreExistence(player, (Double)cfg().forcePlayerRaceEvolutionCost.get(), 0.0, 0.0);
               player.displayClientMessage(Component.literal("That player has no ready race evolution.").withStyle(ChatFormatting.RED), true);
            } else if (!invokeRaceEvolution(target, chosen)) {
               restoreExistence(player, (Double)cfg().forcePlayerRaceEvolutionCost.get(), 0.0, 0.0);
               player.displayClientMessage(Component.literal("Race evolution method failed.").withStyle(ChatFormatting.RED), true);
            } else {
               setSoulPoints(player, getSoulPoints(player) - soulCost);
               markDirty(raceData);
               markDirty(TensuraStorages.getPlayerDataFrom(target));
               if (player.level() instanceof ServerLevel level) {
                  spawnCombatRelease(level, target, (Integer)cfg().forcePlayerRaceEvolutionReleaseCharge.get());
                  lifeDeityOvergrowth(
                     level,
                     target,
                     (Integer)cfg().forcePlayerRaceEvolutionOvergrowthRadius.get(),
                     (Integer)cfg().forcePlayerRaceEvolutionOvergrowthAttempts.get()
                  );
                  level.playSound(null, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.8F, 0.75F);
               }

               instance.setCoolDown((Integer)cfg().forcePlayerRaceEvolutionCooldownTicks.get(), 6);
               player.displayClientMessage(
                  Component.literal("Naberius forces " + target.getName().getString() + " to evolve.").withStyle(ChatFormatting.RED), true
               );
            }
         }
      }
   }

   private static void forcePlayerAwakening(NaberiusSkill skill, ServerPlayer player, ServerPlayer target, ManasSkillInstance instance) {
      int soulCost = (Integer)cfg().forcePlayerAwakeningSoulCost.get();
      if (getSoulPoints(player) < soulCost) {
         player.displayClientMessage(Component.literal("Force Awakening needs " + soulCost + " soul points.").withStyle(ChatFormatting.RED), true);
      } else if (!skill.spendMagicule(player, (Double)cfg().forcePlayerAwakeningCost.get())) {
         player.displayClientMessage(
            Component.literal("Force Awakening needs " + ((Double)cfg().forcePlayerAwakeningCost.get()).longValue() + " MP.").withStyle(ChatFormatting.RED),
            true
         );
      } else {
         boolean hero = RaceUtils.canAwaken(target, true);
         boolean demonLord = RaceUtils.canAwaken(target, false);
         if (!hero && !demonLord) {
            restoreExistence(player, (Double)cfg().forcePlayerAwakeningCost.get(), 0.0, 0.0);
            player.displayClientMessage(Component.literal("That player cannot awaken right now.").withStyle(ChatFormatting.RED), true);
         } else {
            setSoulPoints(player, getSoulPoints(player) - soulCost);
            if (hero) {
               RaceHelper.awakening(target, true);
            } else {
               ExistenceStorage.enterHarvestFestival(TensuraStorages.getExistenceFrom(target), target);
            }

            if (player.level() instanceof ServerLevel level) {
               spawnCombatRelease(level, target, (Integer)cfg().forcePlayerAwakeningReleaseCharge.get());
               lifeDeityOvergrowth(
                  level, target, (Integer)cfg().forcePlayerAwakeningOvergrowthRadius.get(), (Integer)cfg().forcePlayerAwakeningOvergrowthAttempts.get()
               );
               level.playSound(null, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.0F, 0.65F);
               level.playSound(null, target.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 2.0F, 0.9F);
            }

            instance.setCoolDown((Integer)cfg().forcePlayerAwakeningCooldownTicks.get(), 6);
            player.displayClientMessage(
               Component.literal(hero ? "Naberius forces True Hero awakening." : "Naberius forces True Demon Lord awakening.").withStyle(ChatFormatting.RED),
               true
            );
         }
      }
   }

   private static void forceSubordinateEvolution(NaberiusSkill skill, ServerPlayer player, LivingEntity target, ManasSkillInstance instance) {
      if (!isAlly(player, target)) {
         player.displayClientMessage(Component.literal("Only your allies or subordinates can be force-evolved.").withStyle(ChatFormatting.RED), true);
      } else {
         int stage = target.getPersistentData().getInt("tms_naberius_evolution_stage");
         int soulCost = (Integer)cfg().forceSubordinateEvolutionSoulCostBase.get() + stage * (Integer)cfg().forceSubordinateEvolutionSoulCostPerStage.get();
         if (getSoulPoints(player) < soulCost) {
            player.displayClientMessage(Component.literal("Subordinate Evolution needs " + soulCost + " soul points.").withStyle(ChatFormatting.RED), true);
         } else {
            double cost = (Double)cfg().forceSubordinateEvolutionCostBase.get() + stage * (Double)cfg().forceSubordinateEvolutionCostPerStage.get();
            if (!skill.spendMagicule(player, cost)) {
               player.displayClientMessage(Component.literal("Subordinate Evolution lacks MP.").withStyle(ChatFormatting.RED), true);
            } else {
               setSoulPoints(player, getSoulPoints(player) - soulCost);
               target.getPersistentData().putInt("tms_naberius_evolution_stage", stage + 1);
               target.getPersistentData().putBoolean("tms_naberius_forced_evolution", true);
               double hp = (Double)cfg().forceSubordinateEvolutionHpBase.get() + stage * (Double)cfg().forceSubordinateEvolutionHpPerStage.get();
               double dmg = (Double)cfg().forceSubordinateEvolutionDamageBase.get() + stage * (Double)cfg().forceSubordinateEvolutionDamagePerStage.get();
               double speed = (Double)cfg().forceSubordinateEvolutionSpeedBase.get() + stage * (Double)cfg().forceSubordinateEvolutionSpeedPerStage.get();
               double scale = Math.min(
                  (Double)cfg().forceSubordinateEvolutionScaleCap.get(),
                  (Double)cfg().forceSubordinateEvolutionScaleBase.get() + stage * (Double)cfg().forceSubordinateEvolutionScalePerStage.get()
               );
               int idStage = Math.min(stage, (Integer)cfg().forceSubordinateEvolutionMaxTrackedModifierStage.get());
               AttributeHelper.addPermanentAttribute(
                  target,
                  Attributes.MAX_HEALTH,
                  ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_subordinate_hp_" + idStage),
                  hp,
                  Operation.ADD_MULTIPLIED_TOTAL
               );
               AttributeHelper.addPermanentAttribute(
                  target,
                  Attributes.ATTACK_DAMAGE,
                  ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_subordinate_damage_" + idStage),
                  dmg,
                  Operation.ADD_MULTIPLIED_TOTAL
               );
               AttributeHelper.addPermanentAttribute(
                  target,
                  Attributes.MOVEMENT_SPEED,
                  ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_subordinate_speed_" + idStage),
                  speed,
                  Operation.ADD_MULTIPLIED_TOTAL
               );
               AttributeHelper.addPermanentAttribute(
                  target,
                  Attributes.SCALE,
                  ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_subordinate_scale_" + idStage),
                  scale,
                  Operation.ADD_VALUE
               );
               AttributeHelper.addPermanentAttribute(
                  target,
                  TensuraAttributes.RESISTANCE_DEGRADATION,
                  ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_subordinate_degrade_" + idStage),
                  (Double)cfg().forceSubordinateEvolutionDegradationBase.get() + stage * (Double)cfg().forceSubordinateEvolutionDegradationPerStage.get(),
                  Operation.ADD_VALUE
               );
               target.setHealth(target.getMaxHealth());
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
                     (Integer)cfg().forceSubordinateEvolutionHakiDurationTicks.get(),
                     Math.min(
                        (Integer)cfg().forceSubordinateEvolutionHakiMaxAmplifier.get(), (Integer)cfg().forceSubordinateEvolutionHakiBaseAmplifier.get() + stage
                     ),
                     false,
                     false,
                     true
                  )
               );
               applyInstantRegen(
                  skill,
                  player,
                  target,
                  (Integer)cfg().forceSubordinateEvolutionRegenDurationTicks.get(),
                  Math.min(
                     (Integer)cfg().forceSubordinateEvolutionRegenMaxAmplifier.get(), (Integer)cfg().forceSubordinateEvolutionRegenBaseAmplifier.get() + stage
                  )
               );
               restoreExistenceTo(
                  target,
                  (Double)cfg().forceSubordinateEvolutionMagiculeRestoreBase.get()
                     + stage * (Double)cfg().forceSubordinateEvolutionMagiculeRestorePerStage.get(),
                  (Double)cfg().forceSubordinateEvolutionAuraRestoreBase.get() + stage * (Double)cfg().forceSubordinateEvolutionAuraRestorePerStage.get(),
                  (Double)cfg().forceSubordinateEvolutionSpiritualRestoreBase.get()
                     + stage * (Double)cfg().forceSubordinateEvolutionSpiritualRestorePerStage.get()
               );
               if (player.level() instanceof ServerLevel level) {
                  spawnCombatRelease(level, target, (Integer)cfg().forceSubordinateEvolutionReleaseChargeBase.get() + stage);
                  lifeDeityOvergrowth(
                     level,
                     target,
                     (Integer)cfg().forceSubordinateEvolutionOvergrowthRadiusBase.get() + stage,
                     (Integer)cfg().forceSubordinateEvolutionOvergrowthAttemptsBase.get()
                        + stage * (Integer)cfg().forceSubordinateEvolutionOvergrowthAttemptsPerStage.get()
                  );
                  level.playSound(null, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.8F, 0.75F);
                  level.playSound(null, target.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.6F, 0.8F);
               }

               instance.setCoolDown((Integer)cfg().forceSubordinateEvolutionCooldownTicks.get(), 6);
               player.displayClientMessage(
                  Component.literal("Naberius evolves " + target.getName().getString() + " to Life-Deity Stage " + (stage + 1) + ".")
                     .withStyle(ChatFormatting.RED),
                  true
               );
            }
         }
      }
   }

   private static void forceSubordinateAwakening(NaberiusSkill skill, ServerPlayer player, LivingEntity target, ManasSkillInstance instance) {
      if (!isAlly(player, target)) {
         player.displayClientMessage(Component.literal("Only your allies or subordinates can be awakened.").withStyle(ChatFormatting.RED), true);
      } else if (target.getPersistentData().getBoolean("tms_naberius_false_awakening")) {
         player.displayClientMessage(Component.literal("That subordinate has already received false awakening.").withStyle(ChatFormatting.RED), true);
      } else {
         int soulCost = (Integer)cfg().forceSubordinateAwakeningSoulCost.get();
         if (getSoulPoints(player) < soulCost) {
            player.displayClientMessage(Component.literal("False Awakening needs " + soulCost + " soul points.").withStyle(ChatFormatting.RED), true);
         } else if (!skill.spendMagicule(player, (Double)cfg().forceSubordinateAwakeningCost.get())) {
            player.displayClientMessage(
               Component.literal("False Awakening needs " + ((Double)cfg().forceSubordinateAwakeningCost.get()).longValue() + " MP.")
                  .withStyle(ChatFormatting.RED),
               true
            );
         } else {
            setSoulPoints(player, getSoulPoints(player) - soulCost);
            boolean heroForm = player.getRandom().nextBoolean();
            target.getPersistentData().putBoolean("tms_naberius_false_awakening", true);
            target.getPersistentData().putBoolean(heroForm ? "tms_naberius_false_hero" : "tms_naberius_false_demon_lord", true);
            AttributeHelper.addPermanentAttribute(
               target,
               Attributes.MAX_HEALTH,
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_false_awakening_hp"),
               (Double)cfg().forceSubordinateAwakeningHpMultiplier.get(),
               Operation.ADD_MULTIPLIED_TOTAL
            );
            AttributeHelper.addPermanentAttribute(
               target,
               Attributes.ATTACK_DAMAGE,
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_false_awakening_damage"),
               (Double)cfg().forceSubordinateAwakeningDamageMultiplier.get(),
               Operation.ADD_MULTIPLIED_TOTAL
            );
            AttributeHelper.addPermanentAttribute(
               target,
               Attributes.ARMOR,
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_false_awakening_armor"),
               (Double)cfg().forceSubordinateAwakeningArmorBonus.get(),
               Operation.ADD_VALUE
            );
            AttributeHelper.addPermanentAttribute(
               target,
               Attributes.MOVEMENT_SPEED,
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_false_awakening_speed"),
               (Double)cfg().forceSubordinateAwakeningSpeedMultiplier.get(),
               Operation.ADD_MULTIPLIED_TOTAL
            );
            AttributeHelper.addPermanentAttribute(
               target,
               Attributes.SCALE,
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_false_awakening_scale"),
               (Double)cfg().forceSubordinateAwakeningScaleBonus.get(),
               Operation.ADD_VALUE
            );
            AttributeHelper.addPermanentAttribute(
               target,
               TensuraAttributes.RESISTANCE_DEGRADATION,
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_false_awakening_degrade"),
               (Double)cfg().forceSubordinateAwakeningDegradationBonus.get(),
               Operation.ADD_VALUE
            );
            AttributeHelper.addPermanentAttribute(
               target,
               TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION,
               ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_false_awakening_shp_regen"),
               (Double)cfg().forceSubordinateAwakeningSpiritualRegenBonus.get(),
               Operation.ADD_VALUE
            );
            target.setHealth(target.getMaxHealth());
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
                  (Integer)cfg().forceSubordinateAwakeningHakiDurationTicks.get(),
                  (Integer)cfg().forceSubordinateAwakeningHakiAmplifier.get(),
                  false,
                  false,
                  true
               )
            );
            applyInstantRegen(
               skill,
               player,
               target,
               (Integer)cfg().forceSubordinateAwakeningRegenDurationTicks.get(),
               (Integer)cfg().forceSubordinateAwakeningRegenAmplifier.get()
            );
            restoreExistenceTo(
               target,
               (Double)cfg().forceSubordinateAwakeningMagiculeRestore.get(),
               (Double)cfg().forceSubordinateAwakeningAuraRestore.get(),
               (Double)cfg().forceSubordinateAwakeningSpiritualRestore.get()
            );
            if (player.level() instanceof ServerLevel level) {
               spawnCombatRelease(level, target, (Integer)cfg().forceSubordinateAwakeningReleaseCharge.get());
               lifeDeityOvergrowth(
                  level,
                  target,
                  (Integer)cfg().forceSubordinateAwakeningOvergrowthRadius.get(),
                  (Integer)cfg().forceSubordinateAwakeningOvergrowthAttempts.get()
               );
               level.playSound(null, target.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.4F, 0.75F);
               level.playSound(null, target.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 2.0F, 0.65F);
               level.playSound(null, target.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 2.0F, 0.9F);
            }

            instance.setCoolDown((Integer)cfg().forceSubordinateAwakeningCooldownTicks.get(), 6);
            player.displayClientMessage(
               Component.literal(heroForm ? "Naberius creates a False True Hero." : "Naberius creates a False True Demon Lord.").withStyle(ChatFormatting.RED),
               true
            );
         }
      }
   }

   private static void createFruitOfLife(NaberiusSkill skill, ServerPlayer player, ManasSkillInstance instance) {
      if (!skill.spendMagicule(player, (Double)cfg().fruitOfLifeCost.get())) {
         player.displayClientMessage(
            Component.literal("Fruit of Life needs " + ((Double)cfg().fruitOfLifeCost.get()).longValue() + " MP.").withStyle(ChatFormatting.RED), true
         );
      } else {
         ItemStack fruit = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE);
         CompoundTag tag = new CompoundTag();
         tag.putBoolean("tms_fruit_of_life", true);
         fruit.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
         fruit.set(DataComponents.CUSTOM_NAME, Component.literal("Fruit of Life").withStyle(ChatFormatting.RED));
         fruit.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
         if (!player.getInventory().add(fruit)) {
            player.drop(fruit, false);
         }

         if (player.level() instanceof ServerLevel level) {
            lifeDeityOvergrowth(level, player, (Integer)cfg().fruitOfLifeOvergrowthRadius.get(), (Integer)cfg().fruitOfLifeOvergrowthAttempts.get());
            spawnCombatRelease(level, player, (Integer)cfg().fruitOfLifeReleaseCharge.get());
            level.playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.6F, 1.2F);
            level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS, 1.5F, 0.8F);
         }

         instance.setCoolDown((Integer)cfg().fruitOfLifeCooldownTicks.get(), 7);
         player.displayClientMessage(Component.literal("Naberius grows the Fruit of Life.").withStyle(ChatFormatting.RED), true);
      }
   }

   private static void passiveCombatRecovery(NaberiusSkill skill, ServerPlayer player, ServerLevel level) {
      float missing = Math.max(0.0F, player.getMaxHealth() - player.getHealth());
      float heal = Math.max(
         ((Double)cfg().combatRecoveryMinHeal.get()).floatValue(), missing * ((Double)cfg().combatRecoveryMissingHealthRatio.get()).floatValue()
      );
      if (player.getHealth() <= player.getMaxHealth() * ((Double)cfg().combatRecoveryLowHealthThreshold.get()).floatValue()) {
         heal *= ((Double)cfg().combatRecoveryLowHealthHealMultiplier.get()).floatValue();
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
               (Integer)cfg().combatRecoveryLowHealthHakiDurationTicks.get(),
               (Integer)cfg().combatRecoveryLowHealthHakiAmplifier.get(),
               false,
               false,
               true
            )
         );
      }

      player.heal(heal);
      restoreExistence(
         player,
         (Double)cfg().combatRecoveryMagiculeRestore.get(),
         (Double)cfg().combatRecoveryAuraRestore.get(),
         (Double)cfg().combatRecoverySpiritualRestore.get()
      );
   }

   private static void passiveCombatPressure(ServerPlayer player, ServerLevel level) {
      AABB box = player.getBoundingBox().inflate((Double)cfg().combatPressureRadius.get());

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> canHit(player, e))) {
         target.addEffect(
            new MobEffectInstance(
               ModMobEffects.getReference(ModMobEffects.ORGAN_DECAY),
               (Integer)cfg().combatPressureOrganDecayDurationTicks.get(),
               (Integer)cfg().combatPressureOrganDecayAmplifier.get(),
               false,
               true,
               true
            )
         );
      }
   }

   private static void forceWarPassivePulse(NaberiusSkill skill, ServerPlayer player, ServerLevel level, boolean free) {
      boolean paid = true;
      if (!free && player.tickCount % (Integer)cfg().combatSustainCostIntervalTicks.get() == 0) {
         double cost = (Double)cfg().combatSustainCost.get();
         if (skill != null) {
            paid = skill.spendMagicule(player, cost);
         }
      }

      if (!paid) {
         player.displayClientMessage(Component.literal("Crimson Eden needs MP to sustain regeneration.").withStyle(ChatFormatting.RED), true);
      } else {
         MobEffectInstance regeneration = new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION),
            (Integer)cfg().combatSustainRegenDurationTicks.get(),
            (Integer)cfg().combatSustainRegenAmplifier.get(),
            false,
            false,
            true
         );

         try {
            if (skill != null) {
               TensuraMobEffect.addEffect(player, regeneration, player, skill);
            }
         } catch (Throwable var7) {
         }

         player.addEffect(regeneration);
         if (player.tickCount % (Integer)cfg().combatSustainHealIntervalTicks.get() == 0) {
            player.heal(
               Math.max(
                  ((Double)cfg().combatSustainMinHeal.get()).floatValue(),
                  player.getMaxHealth() * ((Double)cfg().combatSustainHealMaxHealthRatio.get()).floatValue()
               )
            );
            restoreExistence(
               player,
               (Double)cfg().combatSustainMagiculeRestore.get(),
               (Double)cfg().combatSustainAuraRestore.get(),
               (Double)cfg().combatSustainSpiritualRestore.get()
            );
            level.sendParticles(
               ParticleTypes.TOTEM_OF_UNDYING,
               player.getX(),
               player.getY() + player.getBbHeight() * 0.65,
               player.getZ(),
               12,
               player.getBbWidth(),
               player.getBbHeight() * 0.35,
               player.getBbWidth(),
               0.08
            );
            level.sendParticles(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 0.4, player.getZ(), 18, 1.4, 0.35, 1.4, 0.05);
         }
      }
   }

   private static void applyInstantRegen(NaberiusSkill skill, LivingEntity source, LivingEntity target, int duration, int amplifier) {
      MobEffectInstance regeneration = new MobEffectInstance(
         TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), duration, amplifier, false, false, true
      );

      try {
         if (skill != null) {
            TensuraMobEffect.addEffect(target, regeneration, source, skill);
         }
      } catch (Throwable var7) {
      }

      target.addEffect(regeneration);
   }

   private static int applyAllyRegenPulse(NaberiusSkill skill, ServerPlayer player, ServerLevel level, double radius, int duration, int amplifier) {
      int blessed = 0;
      AABB box = player.getBoundingBox().inflate(radius);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && (e == player || isAlly(player, e)))) {
         applyInstantRegen(skill, player, target, duration, amplifier);
         target.addEffect(
            new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT), duration, Math.max(0, amplifier - 1), false, false, true)
         );
         restoreExistenceTo(
            target,
            (Double)cfg().allyRegenPulseMagiculeRestoreBase.get() + amplifier * (Double)cfg().allyRegenPulseMagiculeRestorePerAmplifier.get(),
            (Double)cfg().allyRegenPulseAuraRestoreBase.get() + amplifier * (Double)cfg().allyRegenPulseAuraRestorePerAmplifier.get(),
            (Double)cfg().allyRegenPulseSpiritualRestoreBase.get() + amplifier * (Double)cfg().allyRegenPulseSpiritualRestorePerAmplifier.get()
         );
         blessed++;
      }

      return blessed;
   }

   private static boolean isAlly(ServerPlayer player, LivingEntity target) {
      if (target == player) {
         return true;
      }

      if (SubordinateHelper.isSubordinate(player, target)) {
         return true;
      }

      if (!NaberiusSkill.isNaberiusSummon(target)) {
         if (target instanceof TamableAnimal tamable) {
            return player.getUUID().equals(tamable.getOwnerUUID());
         } else {
            try {
               IExistence existence = TensuraStorages.getExistenceFrom(target);
               if (player.getUUID().equals(existence.getSummoner())) {
                  return true;
               }

               if (player.getUUID().equals(existence.getTemporaryOwner())) {
                  return true;
               }

               if (player.getUUID().equals(existence.getPermanentOwner())) {
                  return true;
               }
            } catch (Throwable var3) {
            }

            return false;
         }
      } else {
         UUID owner = NaberiusSkill.getNaberiusOwnerUUID(target);
         return owner != null && owner.equals(player.getUUID());
      }
   }

   private static double stealExistence(ServerPlayer player, LivingEntity target, double minMagicule, double minAura, double minSpiritual) {
      double stolen = minMagicule;

      try {
         IExistence targetEx = TensuraStorages.getExistenceFrom(target);
         double magicule = Math.max(minMagicule, targetEx.getMagicule() * (Double)cfg().combatStealMagiculeRatio.get());
         double aura = Math.max(minAura, targetEx.getAura() * (Double)cfg().combatStealAuraRatio.get());
         double spiritual = Math.max(minSpiritual, targetEx.getSpiritualHealth() * (Double)cfg().combatStealSpiritualRatio.get());
         targetEx.setMagicule(Math.max(0.0, targetEx.getMagicule() - magicule));
         targetEx.setAura(Math.max(0.0, targetEx.getAura() - aura));
         targetEx.setSpiritualHealth(Math.max(0.0, targetEx.getSpiritualHealth() - spiritual));
         targetEx.markDirty();
         restoreExistence(player, magicule, aura, spiritual);
         stolen = magicule;
      } catch (Throwable ignored) {
         restoreExistence(player, minMagicule, minAura, minSpiritual);
      }

      return stolen;
   }

   private static void restoreExistenceTo(LivingEntity target, double magicule, double aura, double spiritual) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(target);
         existence.setMagicule(existence.getMagicule() + magicule);
         existence.setAura(existence.getAura() + aura);
         existence.setSpiritualHealth(existence.getSpiritualHealth() + spiritual);
         existence.markDirty();
      } catch (Throwable var8) {
      }
   }

   private static int getSoulPoints(ServerPlayer player) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);

         try {
            Method method = existence.getClass().getMethod("getSoulPoints");
            return method.invoke(existence) instanceof Number number ? number.intValue() : 0;
         } catch (Throwable var6) {
            Object data = TensuraStorages.getPlayerDataFrom(player);
            Method method = data.getClass().getMethod("getSoulPoints");
            return method.invoke(data) instanceof Number number ? number.intValue() : 0;
         }
      } catch (Throwable ignored) {
         return 0;
      }
   }

   private static void setSoulPoints(ServerPlayer player, int value) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);

         try {
            Method method = existence.getClass().getMethod("setSoulPoints", int.class);
            method.invoke(existence, Math.max(0, value));
            existence.markDirty();
            return;
         } catch (Throwable var5) {
            Object data = TensuraStorages.getPlayerDataFrom(player);
            Method method = data.getClass().getMethod("setSoulPoints", int.class);
            method.invoke(data, Math.max(0, value));
            markDirty(data);
         }
      } catch (Throwable var6) {
      }
   }

   private static Object getCurrentRace(Object raceData) {
      try {
         if (raceData.getClass().getMethod("getRace").invoke(raceData) instanceof Optional<?> optional && optional.isPresent()) {
            return optional.get();
         }
      } catch (Throwable var3) {
      }

      return null;
   }

   private static Object getReadyEvolution(ServerPlayer target, Object race) {
      try {
         Method nextMethod = race.getClass().getMethod("getNextEvolutions", LivingEntity.class);
         if (!(nextMethod.invoke(race, target) instanceof Iterable<?> iterable)) {
            return null;
         }

         Method progressMethod = race.getClass()
            .getMethod("getEvolutionProgress", LivingEntity.class, Class.forName("io.github.manasmods.manascore.race.api.ManasRace"));

         for (Object next : iterable) {
            if (progressMethod.invoke(race, target, next) instanceof Number number
               && number.doubleValue() >= ((Integer)cfg().forceRaceEvolutionRequiredProgress.get()).intValue()) {
               return next;
            }
         }
      } catch (Throwable var10) {
      }

      return null;
   }

   private static boolean invokeRaceEvolution(ServerPlayer target, Object chosen) {
      try {
         for (Method method : RaceHelper.class.getMethods()) {
            if (method.getName().equals("evolveRace") && method.getParameterCount() == 3) {
               Class<?>[] types = method.getParameterTypes();
               if (types[0].isAssignableFrom(target.getClass()) && types[1].isAssignableFrom(chosen.getClass())) {
                  method.invoke(null, target, chosen, true);
                  return true;
               }
            }
         }
      } catch (Throwable var7) {
      }

      return false;
   }

   private static void markDirty(Object object) {
      if (object != null) {
         try {
            Method method = object.getClass().getMethod("markDirty");
            method.invoke(object);
         } catch (Throwable var2) {
         }
      }
   }

   private static void lifeDeityOvergrowth(ServerLevel level, LivingEntity entity, int radius, int attempts) {
      BlockPos center = entity.blockPosition();

      for (int i = 0; i < attempts; i++) {
         BlockPos pos = center.offset(
            entity.getRandom().nextInt(radius * 2 + 1) - radius, entity.getRandom().nextInt(7) - 3, entity.getRandom().nextInt(radius * 2 + 1) - radius
         );
         if (level.isLoaded(pos)) {
            BlockState state = level.getBlockState(pos);
            BlockPos above = pos.above();
            if ((state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.ROOTED_DIRT) || state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM))
               && level.getBlockState(above).isAir()) {
               level.setBlock(pos, Blocks.GRASS_BLOCK.defaultBlockState(), 3);
            } else if (state.is(Blocks.GRASS_BLOCK) && level.isEmptyBlock(above)) {
               int roll = entity.getRandom().nextInt(16);
               if (roll == 0) {
                  level.setBlock(above, Blocks.OAK_SAPLING.defaultBlockState(), 3);
               } else if (roll == 1) {
                  level.setBlock(above, Blocks.BIRCH_SAPLING.defaultBlockState(), 3);
               } else if (roll == 2) {
                  level.setBlock(above, Blocks.AZALEA.defaultBlockState(), 3);
               } else if (roll <= 5) {
                  level.setBlock(above, Blocks.FERN.defaultBlockState(), 3);
               } else if (roll == 6) {
                  level.setBlock(above, Blocks.DANDELION.defaultBlockState(), 3);
               } else if (roll == 7) {
                  level.setBlock(above, Blocks.POPPY.defaultBlockState(), 3);
               } else {
                  level.setBlock(above, Blocks.SHORT_GRASS.defaultBlockState(), 3);
               }
            } else if ((state.is(Blocks.STONE) || state.is(Blocks.COBBLESTONE) || state.is(Blocks.MOSSY_COBBLESTONE))
               && level.getBlockState(above).isAir()
               && entity.getRandom().nextInt(6) == 0) {
               level.setBlock(pos, Blocks.MOSS_BLOCK.defaultBlockState(), 3);
            } else if (state.getBlock() instanceof BonemealableBlock bonemealable) {
               try {
                  if (bonemealable.isValidBonemealTarget(level, pos, state)) {
                     bonemealable.performBonemeal(level, level.random, pos, state);
                  }
               } catch (Throwable var11) {
               }
            }
         }
      }

      level.sendParticles(ParticleTypes.HAPPY_VILLAGER, entity.getX(), entity.getY() + 0.2, entity.getZ(), 40, radius * 0.75, 0.35, radius * 0.75, 0.05);
      level.sendParticles(ParticleTypes.COMPOSTER, entity.getX(), entity.getY() + 0.4, entity.getZ(), 55, radius * 0.75, 0.45, radius * 0.75, 0.07);
   }

   private static void applyCombatAttributes(ServerPlayer player) {
      clearCombatAttributes(player);
      AttributeHelper.addPermanentAttribute(
         player, Attributes.MAX_HEALTH, COMBAT_HP_ID, (Double)cfg().combatMaxHealthMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttribute(
         player, Attributes.ATTACK_DAMAGE, COMBAT_DAMAGE_ID, (Double)cfg().combatAttackDamageMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttribute(player, Attributes.ARMOR, COMBAT_ARMOR_ID, (Double)cfg().combatArmorBonus.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(
         player, Attributes.MOVEMENT_SPEED, COMBAT_SPEED_ID, (Double)cfg().combatMovementSpeedMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttribute(
         player, Attributes.ATTACK_SPEED, COMBAT_ATTACK_SPEED_ID, (Double)cfg().combatAttackSpeedMultiplier.get(), Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttribute(
         player, TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, COMBAT_REGEN_ID, (Double)cfg().combatSpiritualHealthRegenBonus.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         player, TensuraAttributes.MAGIC_COST_MULTIPLIER, COMBAT_COST_ID, (Double)cfg().combatMagicCostMultiplierBonus.get(), Operation.ADD_MULTIPLIED_TOTAL
      );
      AttributeHelper.addPermanentAttribute(
         player, TensuraAttributes.RESISTANCE_DEGRADATION, COMBAT_DEGRADE_ID, (Double)cfg().combatResistanceDegradationBonus.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         player, TensuraAttributes.DODGE_INVULNERABILITY, COMBAT_DODGE_ID, (Double)cfg().combatDodgeInvulnerabilityBonus.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         player, TensuraAttributes.PRESENCE_SENSE, COMBAT_PRESENCE_ID, (Double)cfg().combatPresenceSenseBonus.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         player, TensuraAttributes.DODGE_NEGATE_CHANCE, COMBAT_DODGE_NEGATE_ID, (Double)cfg().combatDodgeNegateChanceBonus.get(), Operation.ADD_VALUE
      );
   }

   private static void clearCombatAttributes(ServerPlayer player) {
      AttributeHelper.removeAttribute(player, Attributes.MAX_HEALTH, COMBAT_HP_ID);
      AttributeHelper.removeAttribute(player, Attributes.ATTACK_DAMAGE, COMBAT_DAMAGE_ID);
      AttributeHelper.removeAttribute(player, Attributes.ARMOR, COMBAT_ARMOR_ID);
      AttributeHelper.removeAttribute(player, Attributes.MOVEMENT_SPEED, COMBAT_SPEED_ID);
      AttributeHelper.removeAttribute(player, Attributes.ATTACK_SPEED, COMBAT_ATTACK_SPEED_ID);
      AttributeHelper.removeAttribute(player, TensuraAttributes.SPIRITUAL_HEALTH_REGENERATION, COMBAT_REGEN_ID);
      AttributeHelper.removeAttribute(player, TensuraAttributes.MAGIC_COST_MULTIPLIER, COMBAT_COST_ID);
      AttributeHelper.removeAttribute(player, TensuraAttributes.RESISTANCE_DEGRADATION, COMBAT_DEGRADE_ID);
      AttributeHelper.removeAttribute(player, TensuraAttributes.DODGE_INVULNERABILITY, COMBAT_DODGE_ID);
      AttributeHelper.removeAttribute(player, TensuraAttributes.PRESENCE_SENSE, COMBAT_PRESENCE_ID);
      AttributeHelper.removeAttribute(player, TensuraAttributes.DODGE_NEGATE_CHANCE, COMBAT_DODGE_NEGATE_ID);
      if (player.getHealth() > player.getMaxHealth()) {
         player.setHealth(player.getMaxHealth());
      }
   }

   private static boolean canHit(ServerPlayer player, LivingEntity target) {
      if (target == player) {
         return false;
      } else if (!target.isAlive()) {
         return false;
      } else if (target.isSpectator()) {
         return false;
      } else {
         return SubordinateHelper.isSubordinate(player, target) ? false : !NaberiusSkill.isNaberiusSummon(target);
      }
   }

   private static void cleanseCombatDebuffs(ServerPlayer player) {
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.HYPNOSIS));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.WEBBED));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE));
   }

   private static void restoreExistence(ServerPlayer player, double magicule, double aura, double spiritual) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         existence.setMagicule(existence.getMagicule() + magicule);
         existence.setAura(existence.getAura() + aura);
         existence.setSpiritualHealth(existence.getSpiritualHealth() + spiritual);
         existence.markDirty();
      } catch (Throwable var8) {
      }
   }

   private static void knockAway(ServerPlayer player, LivingEntity target, double strength) {
      Vec3 vec = target.position().subtract(player.position());
      if (vec.lengthSqr() > 0.001) {
         Vec3 push = vec.normalize().scale(strength);
         target.setDeltaMovement(target.getDeltaMovement().add(push.x, (Double)cfg().knockAwayVerticalVelocity.get(), push.z));
         target.hurtMarked = true;
      }
   }

   private static void pullToPlayer(ServerPlayer player, LivingEntity target, double strength) {
      Vec3 vec = player.position().subtract(target.position());
      if (vec.lengthSqr() > 0.001) {
         Vec3 pull = vec.normalize().scale(strength);
         target.setDeltaMovement(
            target.getDeltaMovement()
               .multiply((Double)cfg().pullHorizontalDamping.get(), (Double)cfg().pullVerticalDamping.get(), (Double)cfg().pullHorizontalDamping.get())
               .add(pull.x, (Double)cfg().pullVerticalVelocity.get(), pull.z)
         );
         target.hurtMarked = true;
      }
   }

   private static void spawnCombatBurst(ServerLevel level, LivingEntity entity, double radius) {
      level.sendParticles(
         ParticleTypes.COMPOSTER,
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.45,
         entity.getZ(),
         45,
         radius,
         entity.getBbHeight() * 0.45,
         radius,
         0.08
      );
      level.sendParticles(
         ParticleTypes.HAPPY_VILLAGER,
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.75,
         entity.getZ(),
         24,
         radius * 0.7,
         entity.getBbHeight() * 0.3,
         radius * 0.7,
         0.04
      );
      level.sendParticles(
         ParticleTypes.SOUL,
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.55,
         entity.getZ(),
         28,
         radius * 0.75,
         entity.getBbHeight() * 0.35,
         radius * 0.75,
         0.05
      );
      level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY() + 0.25, entity.getZ(), 18, radius * 0.65, 0.2, radius * 0.65, 0.04);
   }

   private static void spawnTargetBurst(ServerLevel level, LivingEntity target, double radius) {
      level.sendParticles(
         ParticleTypes.DAMAGE_INDICATOR,
         target.getX(),
         target.getY() + target.getBbHeight() * 0.6,
         target.getZ(),
         24,
         radius * 0.45,
         target.getBbHeight() * 0.35,
         radius * 0.45,
         0.08
      );
      level.sendParticles(
         ParticleTypes.COMPOSTER,
         target.getX(),
         target.getY() + target.getBbHeight() * 0.45,
         target.getZ(),
         30,
         radius * 0.55,
         target.getBbHeight() * 0.35,
         radius * 0.55,
         0.06
      );
      level.sendParticles(
         ParticleTypes.SOUL,
         target.getX(),
         target.getY() + target.getBbHeight() * 0.65,
         target.getZ(),
         22,
         radius * 0.5,
         target.getBbHeight() * 0.35,
         radius * 0.5,
         0.05
      );
   }

   private static void spawnCombatCharge(ServerLevel level, ServerPlayer player, int charge) {
      double radius = Math.min(
         (Double)cfg().combatChargeMaxRadius.get(), (Double)cfg().combatChargeRadiusBase.get() + charge * (Double)cfg().combatChargeRadiusPerCharge.get()
      );
      level.sendParticles(ParticleTypes.COMPOSTER, player.getX(), player.getY() + 0.3, player.getZ(), 12 + charge, radius, 0.2, radius, 0.035);
      level.sendParticles(
         ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.0, player.getZ(), 6 + charge / 2, radius * 0.75, 0.75, radius * 0.75, 0.025
      );
      level.sendParticles(ParticleTypes.SOUL, player.getX(), player.getY() + 1.0, player.getZ(), 8 + charge / 2, radius * 0.55, 0.75, radius * 0.55, 0.02);
      if (charge % (Integer)cfg().combatChargeSoundEveryCharge.get() == 0) {
         level.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.9F, 0.75F + Math.min(0.5F, charge * 0.025F));
      }
   }

   private static void spawnCombatRelease(ServerLevel level, LivingEntity entity, int charge) {
      double radius = Math.min(
         (Double)cfg().combatReleaseMaxRadius.get(), (Double)cfg().combatReleaseRadiusBase.get() + charge * (Double)cfg().combatReleaseRadiusPerCharge.get()
      );
      level.sendParticles(ParticleTypes.EXPLOSION, entity.getX(), entity.getY() + entity.getBbHeight() * 0.45, entity.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
      level.sendParticles(ParticleTypes.COMPOSTER, entity.getX(), entity.getY() + 0.25, entity.getZ(), 110, radius, 0.25, radius, 0.12);
      level.sendParticles(ParticleTypes.HAPPY_VILLAGER, entity.getX(), entity.getY() + 1.2, entity.getZ(), 65, radius * 0.75, 1.0, radius * 0.75, 0.08);
      level.sendParticles(ParticleTypes.SOUL, entity.getX(), entity.getY() + 1.0, entity.getZ(), 85, radius * 0.65, 1.0, radius * 0.65, 0.08);
      level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, entity.getX(), entity.getY() + 0.25, entity.getZ(), 55, radius * 0.8, 0.25, radius * 0.8, 0.06);
   }

   private static void spawnDrainLine(ServerLevel level, LivingEntity fromEntity, LivingEntity toEntity) {
      Vec3 from = fromEntity.position().add(0.0, fromEntity.getBbHeight() * 0.55, 0.0);
      Vec3 to = toEntity.position().add(0.0, toEntity.getBbHeight() * 0.55, 0.0);
      Vec3 diff = to.subtract(from);

      for (int i = 0; i < cfg().drainLineParticleSteps.get(); i++) {
         double t = i / Math.max(1.0, ((Integer)cfg().drainLineParticleSteps.get()).intValue() - 1.0);
         Vec3 p = from.add(diff.scale(t));
         level.sendParticles(ParticleTypes.COMPOSTER, p.x, p.y, p.z, 2, 0.08, 0.08, 0.08, 0.01);
         level.sendParticles(ParticleTypes.SOUL, p.x, p.y, p.z, 1, 0.05, 0.05, 0.05, 0.01);
      }
   }
}
