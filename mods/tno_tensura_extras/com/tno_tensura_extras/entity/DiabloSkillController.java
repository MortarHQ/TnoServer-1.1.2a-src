package com.tno_tensura_extras.entity;

import com.tno_tensura_extras.effect.DespairTimeEffect;
import com.tno_tensura_extras.registry.ExtrasMobEffects;
import com.tno_tensura_extras.registry.ExtrasSkills;
import com.tno_tensura_extras.skill.unique.SeducerSkill;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.common.ThoughtCommunicationSkill;
import io.github.manasmods.tensura.ability.skill.extra.LawManipulationSkill;
import io.github.manasmods.tensura.ability.skill.intrinsic.CharmSkill;
import io.github.manasmods.tensura.ability.skill.unique.SeekerSkill;
import io.github.manasmods.tensura.ability.subclass.ISpatialMovement;
import io.github.manasmods.tensura.config.ability.skill.ExtraSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.IntrinsicSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.ExtraSkillConfig.SpatialMotion;
import io.github.manasmods.tensura.config.ability.skill.IntrinsicSkillConfig.Charm;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import io.github.manasmods.tensura.entity.magic.barrier.DisintegrationEntity;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

final class DiabloSkillController {
   private static final List<String> SKILLS = List.of(
      "seeker",
      "thought_acceleration",
      "chant_annulment",
      "law_manipulation",
      "charm",
      "demon_lord_haki",
      "universal_perception",
      "spatial_motion",
      "multilayer_barrier",
      "thought_communication",
      "magic_resistance",
      "abnormal_condition_nullification",
      "pain_nullification",
      "physical_attack_nullification",
      "spiritual_attack_nullification",
      "infinite_regeneration"
   );
   private static final List<String> PASSIVES = List.of(
      "seeker",
      "thought_acceleration",
      "chant_annulment",
      "law_manipulation",
      "universal_perception",
      "magic_resistance",
      "abnormal_condition_nullification",
      "pain_nullification",
      "physical_attack_nullification",
      "spiritual_attack_nullification"
   );
   private static final ResourceLocation BARRIER = id("multilayer_barrier");
   private final DiabloEntity diablo;
   private boolean initialized;
   private boolean combatSkillsMastered;
   private boolean magicLoadoutInitialized;
   private boolean seducerInitialized;
   private int despairCooldown;
   private long nextSeducer;
   private int combatTicks;
   private int disintegrationCooldown;
   private long nextCharm;
   private long nextHaki;
   private long nextAnalysis;
   private long nextTakeover;
   private long nextCommand;
   private long nextBlink;
   @Nullable
   private TensuraProjectile analyzing;
   @Nullable
   private UUID disintegrationId;
   @Nullable
   private DisintegrationEntity disintegration;
   @Nullable
   private Vec3 retreatDestination;
   @Nullable
   private UUID retreatTargetId;
   private int retreatTicks;
   private boolean controllingFlight;
   private boolean flyingBeforeCast;
   private int unresolvedSpellTicks;

   DiabloSkillController(DiabloEntity diablo) {
      this.diablo = diablo;
   }

   void tick() {
      if (this.disintegrationCooldown > 0) {
         this.disintegrationCooldown--;
      }

      if (this.despairCooldown > 0) {
         this.despairCooldown--;
      }

      if (this.diablo.isAlive() && !this.diablo.isNoAi() && !this.diablo.isOrderedToSit() && !this.diablo.isSleeping() && !this.diablo.isPassenger()) {
         if (!this.initialized) {
            this.initializeSkills();
         }

         if (!this.combatSkillsMastered) {
            this.prepareCombatSkills();
         }

         if (!this.magicLoadoutInitialized) {
            this.initializeMagics();
         }

         if (!this.seducerInitialized) {
            this.initializeSeducer();
         }

         if (this.diablo.tickCount % 20 == 0) {
            for (String name : PASSIVES) {
               this.toggle(name, true);
            }

            this.toggle((ManasSkillInstance)SkillAPI.getSkillsFrom(this.diablo).getSkill((ManasSkill)ExtrasSkills.SEDUCER.get()).orElse(null), true);
            boolean injured = this.diablo.getHealth() < this.diablo.getMaxHealth()
               || TensuraStorages.getExistenceFrom(this.diablo).getSpiritualHealth() < this.diablo.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
            this.toggle("infinite_regeneration", injured);
            if (this.diablo.getActiveEffects().stream().anyMatch(effect -> LawManipulationSkill.getCleansingEffect().test(effect.getEffect()))) {
               this.press("law_manipulation", 0);
            }
         }

         LivingEntity target = this.diablo.getTarget();
         if (target != null && this.hostile(target)) {
            this.combatTicks++;
            if (!this.controllingFlight || this.diablo.isFlying() && this.diablo.canCastMagics(this.diablo)) {
               this.updateDisintegration();
               this.updateHeldSkills();
               if (this.retreatDestination != null) {
                  this.updateDisintegrationRetreat(target);
               } else if (this.isChanneling()) {
                  this.stopWalking();
                  this.diablo.lookAt(Anchor.EYES, target.getEyePosition());
               } else {
                  this.finishCastingFlight();
                  if (this.analyzing != null) {
                     this.diablo.lookAt(Anchor.EYES, this.analyzing.getBoundingBox().getCenter());
                  }

                  if (!this.diablo.hasCommittedCombatAction()) {
                     if (this.diablo.tickCount % 10 == 0) {
                        long now = this.diablo.level().getGameTime();
                        AttributeInstance barrier = this.diablo.getAttribute(TensuraAttributes.MULTILAYER_BARRIER);
                        if (barrier != null && !barrier.hasModifier(BARRIER)) {
                           this.press("multilayer_barrier", 0);
                        }

                        if (!this.diablo.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT))) {
                           this.press("demon_lord_haki", 1);
                        }

                        if (now >= this.nextCommand && this.ready("thought_communication", 0) != null) {
                           ThoughtCommunicationSkill.attackCommand(this.diablo, this.diablo, target, 32.0);
                           this.nextCommand = now + 100L;
                        }

                        if (!this.trySeducer(target, now)) {
                           if (!this.tryDespairTime(target)) {
                              if (!this.tryCharm(target, now)) {
                                 if (!this.tryDisintegration(target)) {
                                    if (this.analyzing == null
                                       && now >= this.nextBlink
                                       && this.combatTicks >= 60
                                       && !this.diablo.isWithinMeleeAttackRange(target)
                                       && (this.diablo.isCombatPathBlocked() || this.diablo.distanceToSqr(target) > 256.0)) {
                                       this.nextBlink = now + 100L;
                                       this.blinkTowards(target);
                                    }

                                    if (now >= this.nextHaki
                                       && this.combatTicks >= 20
                                       && this.diablo.distanceToSqr(target) <= 64.0
                                       && this.hold("demon_lord_haki", 0)) {
                                       this.nextHaki = now + 200L;
                                    }

                                    if (this.analyzing == null && !this.diablo.isWithinMeleeAttackRange(target)) {
                                       this.reactToProjectiles(now);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               this.cancelDisintegration();
            }
         } else {
            if (target != null) {
               SubordinateHelper.removeTarget(this.diablo);
            }

            this.combatTicks = 0;
            this.stop();
         }
      } else {
         this.stop();
      }
   }

   private void initializeSkills() {
      SkillStorage storage = SkillAPI.getSkillsFrom(this.diablo);

      for (String name : SKILLS) {
         ResourceLocation key = id(name);
         if (storage.getSkill(key).isEmpty()) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(key);
            if (skill != null) {
               ManasSkillInstance instance = skill.createDefaultInstance();
               storage.learnSkill(instance, null);
            }
         }
      }

      this.initialized = true;

      for (String name : PASSIVES) {
         this.toggle(name, true);
      }
   }

   private void prepareCombatSkills() {
      for (String name : List.of("law_manipulation", "seeker", "chant_annulment")) {
         ManasSkillInstance instance = this.skill(name);
         if (instance != null && !instance.isMastered(this.diablo)) {
            boolean toggled = instance.isToggled();
            if (toggled) {
               this.toggle(name, false);
            }

            instance.setMastery(instance.getMaxMastery());
            if (toggled) {
               this.toggle(name, true);
            }
         }
      }

      SkillAPI.getSkillsFrom(this.diablo).markDirty();
      this.combatSkillsMastered = true;
   }

   private void initializeMagics() {
      SkillStorage storage = SkillAPI.getSkillsFrom(this.diablo);

      for (String name : List.of(
         "fire_ball", "ice_breaker", "tornado_blade", "acid_shell", "mud_spears", "thunder_orb", "water_jail", "stone_shot", "reinforced_barrier"
      )) {
         ResourceLocation key = id(name);
         if (storage.getSkill(key).isEmpty()) {
            ManasSkill ability = (ManasSkill)SkillAPI.getSkillRegistry().get(key);
            if (ability != null) {
               storage.learnSkill(ability.createDefaultInstance(), null);
            }
         }
      }

      this.magicLoadoutInitialized = true;
   }

   private void initializeSeducer() {
      SkillStorage storage = SkillAPI.getSkillsFrom(this.diablo);
      ManasSkillInstance instance = (ManasSkillInstance)storage.getSkill((ManasSkill)ExtrasSkills.SEDUCER.get()).orElse(null);
      if (instance == null) {
         ManasSkillInstance var3 = ((SeducerSkill)ExtrasSkills.SEDUCER.get()).createDefaultInstance();
         storage.learnSkill(var3, null);
         instance = (ManasSkillInstance)storage.getSkill((ManasSkill)ExtrasSkills.SEDUCER.get()).orElse(null);
      }

      if (instance != null) {
         instance.setMastery(instance.getMaxMastery());
         this.toggle(instance, true);
      }

      storage.markDirty();
      this.seducerInitialized = true;
   }

   private boolean trySeducer(LivingEntity target, long now) {
      if (now >= this.nextSeducer && !(this.diablo.distanceToSqr(target) > 144.0) && this.diablo.hasLineOfSight(target)) {
         ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(this.diablo).getSkill((ManasSkill)ExtrasSkills.SEDUCER.get()).orElse(null);
         if (instance != null && instance.canInteractSkill(this.diablo)) {
            this.nextSeducer = now + 40L;
            SeducerSkill seducer = (SeducerSkill)ExtrasSkills.SEDUCER.get();
            boolean used = seducer.tryCombatMode(instance, this.diablo, target, 2) || seducer.tryCombatMode(instance, this.diablo, target, 1);
            if (used) {
               this.diablo.playCombatAnimation("attack");
            }

            return used;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean tryDespairTime(LivingEntity target) {
      ManasSkillInstance seducer = (ManasSkillInstance)SkillAPI.getSkillsFrom(this.diablo).getSkill((ManasSkill)ExtrasSkills.SEDUCER.get()).orElse(null);
      if (!(
         target instanceof ServerPlayer player
            && this.combatTicks >= 100
            && this.despairCooldown <= 0
            && seducer != null
            && seducer.canInteractSkill(this.diablo)
            && !(this.diablo.distanceToSqr(player) > 576.0)
            && this.diablo.hasLineOfSight(player)
            && !player.hasEffect(ExtrasMobEffects.DESPAIR_TIME)
      )) {
         return false;
      } else {
         if (!player.addEffect(new MobEffectInstance(ExtrasMobEffects.DESPAIR_TIME, 2400, 0, false, false, true), this.diablo)) {
            return false;
         }

         DespairTimeEffect.interruptMagic(player);
         this.despairCooldown = 6000;
         this.diablo.playCombatAnimation("attack");
         return true;
      }
   }

   private void toggle(String name, boolean enabled) {
      this.toggle(this.skill(name), enabled);
   }

   private void toggle(@Nullable ManasSkillInstance instance, boolean enabled) {
      if (instance != null && instance.isToggled() != enabled) {
         if (!enabled || instance.canBeToggled(this.diablo) && instance.canInteractSkill(this.diablo)) {
            instance.setToggled(enabled);
            if (enabled) {
               instance.onToggleOn(this.diablo);
            } else {
               instance.onToggleOff(this.diablo);
            }

            SkillAPI.getSkillsFrom(this.diablo).markDirty();
         }
      }
   }

   @Nullable
   private ManasSkillInstance skill(String name) {
      return (ManasSkillInstance)SkillAPI.getSkillsFrom(this.diablo).getSkill(id(name)).orElse(null);
   }

   @Nullable
   private ManasSkillInstance ready(String name, int mode) {
      ManasSkillInstance instance = this.skill(name);
      return instance != null && mode >= 0 && mode < instance.getModes() && instance.canInteractSkill(this.diablo) && !instance.onCoolDown(mode)
         ? instance
         : null;
   }

   private boolean hold(String name, int mode) {
      ManasSkillInstance instance = this.ready(name, mode);
      return instance != null && !TickingSkill.isTickingSkill(this.diablo, instance.getSkill(), mode)
         ? SkillAPI.getSkillsFrom(this.diablo).startHoldSkill(instance, 0, mode)
         : false;
   }

   private boolean press(String name, int mode) {
      if (!this.hold(name, mode)) {
         return false;
      }

      this.release(name, mode);
      return true;
   }

   private void release(String name, int mode) {
      ManasSkillInstance instance = this.skill(name);
      if (instance != null) {
         SkillStorage storage = SkillAPI.getSkillsFrom(this.diablo);
         storage.handleSkillRelease(instance, 0, mode, false);
         storage.markActiveTick();
         storage.markDirty();
      }
   }

   private void updateHeldSkills() {
      SkillStorage storage = SkillAPI.getSkillsFrom(this.diablo);

      for (TickingSkill held : List.copyOf(storage.getHeldSkills())) {
         String name = held.getSkill().getRegistryName().getPath();
         if (name.equals("demon_lord_haki") && held.getMode() == 0 && held.getDuration() >= 40) {
            this.release(name, 0);
         }

         if (name.equals("seeker")
            && (
               this.analyzing == null
                  || !this.analyzing.isAlive()
                  || !this.hostileProjectile(this.analyzing)
                  || held.getDuration() >= 100
                  || this.diablo.isWithinMeleeAttackRange(this.diablo.getTarget())
            )) {
            this.release(name, 0);
            this.analyzing = null;
         }
      }

      ManasSkillInstance seeker = this.skill("seeker");
      if (seeker == null || !TickingSkill.isTickingSkill(this.diablo, seeker.getSkill(), 0)) {
         this.analyzing = null;
      }
   }

   private boolean tryCharm(LivingEntity target, long now) {
      if (now >= this.nextCharm && this.ready("charm", 0) != null) {
         Charm config = ((IntrinsicSkillConfig)ConfigRegistry.getConfig(IntrinsicSkillConfig.class)).Charm;
         if (!(this.diablo.distanceToSqr(target) > config.range * config.range)
            && this.diablo.getSensing().hasLineOfSight(target)
            && CharmSkill.canMindControl(target, this.diablo.level())
            && !(EnergyHelper.getMaxEP(target) > EnergyHelper.getMaxEP(this.diablo) * config.neutralMultiplier)) {
            this.diablo.lookAt(Anchor.EYES, target.getEyePosition());
            if (ObjectSelectionHelper.getTargetingEntity(this.diablo, config.range, false) != target) {
               return false;
            } else {
               this.nextCharm = now + Math.max(100L, config.cooldown * 20L);
               this.press("charm", 0);
               if (!this.hostile(target)) {
                  SubordinateHelper.removeTarget(this.diablo);
                  this.stopWalking();
                  return true;
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void reactToProjectiles(long now) {
      if (now >= this.nextTakeover || now >= this.nextAnalysis) {
         double range = Math.min(32.0, Math.max(SeekerSkill.CONFIG.analyzeRange, LawManipulationSkill.CONFIG.takeoverRange));
         TensuraProjectile projectile = this.diablo
            .level()
            .getEntitiesOfClass(TensuraProjectile.class, this.diablo.getBoundingBox().inflate(range), this::hostileProjectile)
            .stream()
            .min(Comparator.comparingDouble(this.diablo::distanceToSqr))
            .orElse(null);
         if (projectile != null) {
            this.diablo.lookAt(Anchor.EYES, projectile.getBoundingBox().getCenter());
            ManasSkillInstance copied = projectile.getSkill();
            if (now >= this.nextAnalysis
               && copied != null
               && copied.getMastery() >= 0.0
               && copied.is(TensuraSkillTags.COPIABLE_MAGIC)
               && SkillAPI.getSkillsFrom(this.diablo).getSkill(copied.getSkill()).isEmpty()
               && ObjectSelectionHelper.getTargetingEntity(TensuraProjectile.class, this.diablo, SeekerSkill.CONFIG.analyzeRange, 0.5, false, true, false)
                  == projectile
               && this.hold("seeker", 0)) {
               this.analyzing = projectile;
               this.nextAnalysis = now + 120L;
            } else {
               ManasSkillInstance law = this.ready("law_manipulation", 1);
               if (now >= this.nextTakeover
                  && law != null
                  && law.isMastered(this.diablo)
                  && ObjectSelectionHelper.getTargetingEntity(
                        TensuraProjectile.class, this.diablo, LawManipulationSkill.CONFIG.takeoverRange, 0.5, true, false, false
                     )
                     == projectile) {
                  this.press("law_manipulation", 1);
                  this.nextTakeover = now + 60L;
               }
            }
         }
      }
   }

   private boolean hostileProjectile(TensuraProjectile projectile) {
      return projectile.isAlive()
         && projectile.getOwner() instanceof LivingEntity owner
         && this.hostile(owner)
         && projectile.getSkill() != null
         && projectile.getSkill().is(TensuraSkillTags.MAGIC)
         && this.diablo.hasLineOfSight(projectile);
   }

   private boolean hostile(LivingEntity target) {
      return this.diablo.isAllowedCombatTarget(target);
   }

   private void blinkTowards(LivingEntity target) {
      Vec3 away = this.diablo.position().subtract(target.position()).multiply(1.0, 0.0, 1.0).normalize();
      if (away.lengthSqr() < 0.01) {
         away = new Vec3(1.0, 0.0, 0.0);
      }

      this.blinkAround(
         target, away, new double[]{0.0, Math.PI / 4, Math.PI / 2, Math.PI * 3.0 / 4.0, Math.PI, Math.PI * 5.0 / 4.0, Math.PI * 3.0 / 2.0, Math.PI * 7.0 / 4.0}
      );
   }

   boolean blinkBehind(LivingEntity target) {
      if (this.diablo.isAlive() && !this.isChanneling() && !this.isAnalyzing() && this.hostile(target)) {
         Vec3 behind = Vec3.directionFromRotation(0.0F, target.getYRot()).scale(-1.0);
         return this.blinkAround(target, behind, new double[]{0.0, Math.PI / 6, -Math.PI / 6, Math.PI / 3, -Math.PI / 3});
      } else {
         return false;
      }
   }

   private boolean blinkAround(LivingEntity target, Vec3 direction, double[] angles) {
      ManasSkillInstance instance = this.ready("spatial_motion", 0);
      if (instance != null && !SkillUtils.shouldCancelTeleportation(this.diablo)) {
         SpatialMotion config = ((ExtraSkillConfig)ConfigRegistry.getConfig(ExtraSkillConfig.class)).SpatialMotion;
         double maxRange = instance.isMastered(this.diablo) ? config.blinkRangeMastered : config.blinkRange;
         double separation = (this.diablo.getBbWidth() + target.getBbWidth()) * 0.5 + 1.2;

         for (double angle : angles) {
            Vec3 point = target.position().add(direction.yRot((float)angle).scale(separation));
            if (this.diablo.level().hasChunkAt(BlockPos.containing(point))) {
               point = ObjectSelectionHelper.getNearestGround(point.add(0.0, 2.0, 0.0), this.diablo.level(), 5.0, this.diablo);
               BlockPos pos = BlockPos.containing(point);
               AABB bounds = this.diablo.getBoundingBox().move(point.subtract(this.diablo.position()));
               double distance = this.diablo.position().distanceTo(point);
               if (!(distance > maxRange)
                  && !(distance < 2.0)
                  && this.diablo.level().hasChunkAt(pos)
                  && !(bounds.minY < this.diablo.level().getMinBuildHeight())
                  && !(bounds.maxY > this.diablo.level().getMaxBuildHeight())
                  && this.diablo.level().getWorldBorder().isWithinBounds(bounds)
                  && this.diablo.level().noCollision(this.diablo, bounds)
                  && !this.diablo.level().containsAnyLiquid(bounds)
                  && this.diablo.level().getBlockState(pos.below()).blocksMotion()
                  && !this.diablo.level().getBlockState(pos).is(TensuraBlockTags.LABYRINTH_BLOCKS)
                  && !this.diablo.level().getBlockState(pos.below()).is(TensuraBlockTags.LABYRINTH_BLOCKS)) {
                  if (EnergyHelper.isOutOfEnergy(this.diablo, instance, 0, (float)distance)) {
                     return false;
                  }

                  ISpatialMovement.warp(this.diablo, point.x, point.y, point.z);
                  instance.setCoolDown(instance.isMastered(this.diablo) ? config.blinkCooldownMastered : config.blinkCooldown, 0);
                  SkillAPI.getSkillsFrom(this.diablo).markActiveTick();
                  SkillAPI.getSkillsFrom(this.diablo).markDirty();
                  this.stopWalking();
                  this.diablo.lookAt(Anchor.EYES, target.getEyePosition());
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean tryDisintegration(LivingEntity target) {
      if (this.disintegrationId == null
         && this.disintegrationCooldown <= 0
         && this.combatTicks >= 200
         && !(this.diablo.distanceToSqr(target) > 900.0)
         && this.diablo.getSensing().hasLineOfSight(target)) {
         boolean powerfulTarget = target.getMaxHealth() >= 200.0F || EnergyHelper.getMaxEP(target) >= EnergyHelper.getMaxEP(this.diablo) * 0.5;
         if (!powerfulTarget || !this.diablo.canCastMagics(this.diablo)) {
            return false;
         }

         if (this.diablo.canFly() && !this.diablo.shouldStopFlying(this.diablo)) {
            Vec3 destination = this.findRetreatPosition(target);
            if (destination == null) {
               this.disintegrationCooldown = 100;
               return false;
            } else {
               this.release("seeker", 0);
               this.analyzing = null;
               this.retreatDestination = destination;
               this.retreatTargetId = target.getUUID();
               this.retreatTicks = 0;
               this.flyingBeforeCast = this.diablo.isFlying();
               this.controllingFlight = true;
               this.stopWalking();
               this.diablo.setDeltaMovement(Vec3.ZERO);
               this.diablo.setCombatFlying(true);
               this.diablo.stopTriggeredAnim("bodyController", "attack");
               this.diablo.lookAt(Anchor.EYES, target.getEyePosition());
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   private Vec3 findRetreatPosition(LivingEntity target) {
      Vec3 away = this.diablo.position().subtract(target.position()).multiply(1.0, 0.0, 1.0).normalize();
      if (away.lengthSqr() < 0.01) {
         away = this.diablo.getLookAngle().multiply(-1.0, 0.0, -1.0).normalize();
         if (away.lengthSqr() < 0.01) {
            away = new Vec3(0.0, 0.0, -1.0);
         }
      }

      double height = Math.min(16.0, Math.max(8.0, target.getBbHeight() + 4.0));
      Vec3 offset = away.scale(6.0).add(0.0, height, 0.0);
      int samples = (int)Math.ceil(offset.length() * 4.0);

      for (double scale : new double[]{1.0, 0.85, 0.65}) {
         boolean clear = true;

         for (int step = 1; step <= samples; step++) {
            Vec3 movement = offset.scale(scale * step / samples);
            AABB bounds = this.diablo.getBoundingBox().move(movement);
            if (bounds.minY < this.diablo.level().getMinBuildHeight()
               || bounds.maxY > this.diablo.level().getMaxBuildHeight()
               || !this.diablo.level().hasChunkAt(BlockPos.containing(this.diablo.position().add(movement)))
               || !this.diablo.level().getWorldBorder().isWithinBounds(bounds)
               || !this.diablo.level().noCollision(this.diablo, bounds)
               || this.diablo.level().containsAnyLiquid(bounds)) {
               clear = false;
               break;
            }
         }

         if (clear) {
            return this.diablo.position().add(offset.scale(scale));
         }
      }

      return null;
   }

   private void updateDisintegrationRetreat(LivingEntity target) {
      if (target.getUUID().equals(this.retreatTargetId)
         && this.diablo.isFlying()
         && this.diablo.canCastMagics(this.diablo)
         && !(this.diablo.distanceToSqr(target) > 1600.0)) {
         this.stopWalking();
         this.diablo.lookAt(Anchor.EYES, target.getEyePosition());
         this.retreatTicks++;
         if (this.retreatTicks >= 20 && this.diablo.position().distanceToSqr(this.retreatDestination) <= 0.09) {
            this.retreatDestination = null;
            this.retreatTargetId = null;
            if (!this.diablo.getSensing().hasLineOfSight(target) || !this.summonDisintegration(target)) {
               this.cancelDisintegration();
            }
         } else if (this.retreatTicks >= 80) {
            this.cancelDisintegration();
         }
      } else {
         this.cancelDisintegration();
      }
   }

   private boolean summonDisintegration(LivingEntity target) {
      DisintegrationEntity spell = new DisintegrationEntity(this.diablo.level(), this.diablo);
      spell.setSize(3.0F);
      spell.setHeight(50.0F);
      spell.setLife(310);
      spell.setDamage(1000.0F);
      spell.setPos(ObjectSelectionHelper.getNearestGround(target.position(), this.diablo.level(), 10.0, target));
      if (!this.diablo.level().addFreshEntity(spell)) {
         return false;
      }

      this.disintegration = spell;
      this.disintegrationId = spell.getUUID();
      this.disintegrationCooldown = 1000;
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 40, 9, false, false, false));
      target.teleportTo(spell.getX(), spell.getY(), spell.getZ());
      target.setDeltaMovement(Vec3.ZERO);
      target.hurtMarked = true;
      this.diablo.triggerAnim("bodyController", "attack");
      this.stopWalking();
      return true;
   }

   private void updateDisintegration() {
      if (this.disintegrationId != null) {
         if (this.disintegration == null && this.diablo.level() instanceof ServerLevel level) {
            if (level.getEntity(this.disintegrationId) instanceof DisintegrationEntity spell && spell.getOwner() == this.diablo) {
               this.disintegration = spell;
               this.unresolvedSpellTicks = 0;
            } else if (++this.unresolvedSpellTicks >= 40) {
               this.disintegrationId = null;
            }
         }

         if (this.disintegration != null
            && (
               !this.disintegration.isAlive()
                  || this.disintegration.getAge() > 20 && this.disintegration.getAge() < 215 && this.disintegration.getAffectedEntities().isEmpty()
            )) {
            this.cancelDisintegration();
         }
      }
   }

   boolean isChanneling() {
      return this.retreatDestination != null || this.disintegration != null && this.disintegration.isAlive() && this.disintegration.getAge() < 250;
   }

   boolean isControllingFlight() {
      return this.controllingFlight;
   }

   Vec3 getCastingMotion() {
      if (this.retreatDestination == null) {
         return Vec3.ZERO;
      }

      Vec3 remaining = this.retreatDestination.subtract(this.diablo.position());
      double distance = remaining.length();
      return distance < 0.02 ? Vec3.ZERO : remaining.scale(Math.min(0.45, distance * 0.25) / distance);
   }

   private void finishCastingFlight() {
      if (this.controllingFlight) {
         this.controllingFlight = false;
         this.diablo.setDeltaMovement(Vec3.ZERO);
         this.diablo.setCombatFlying(this.flyingBeforeCast);
      }
   }

   boolean isAnalyzing() {
      return this.analyzing != null && this.analyzing.isAlive();
   }

   void updateLook() {
      if (this.isAnalyzing()) {
         this.diablo.lookAt(Anchor.EYES, this.analyzing.getBoundingBox().getCenter());
      }
   }

   private void stopWalking() {
      this.diablo.getNavigation().stop();
      this.diablo.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
      this.diablo.getBrain().eraseMemory(MemoryModuleType.PATH);
   }

   private void cancelDisintegration() {
      if (this.controllingFlight) {
         this.disintegrationCooldown = Math.max(this.disintegrationCooldown, 100);
      }

      this.retreatDestination = null;
      this.retreatTargetId = null;
      this.retreatTicks = 0;
      this.finishCastingFlight();
      if (this.disintegration != null && !this.disintegration.isRemoved()) {
         this.disintegration.discard();
      }

      this.disintegration = null;
      this.disintegrationId = null;
   }

   void stop() {
      for (String name : List.of("seeker", "demon_lord_haki")) {
         ManasSkillInstance instance = this.skill(name);
         if (instance != null && TickingSkill.isTickingSkill(this.diablo, instance.getSkill(), 0)) {
            this.release(name, 0);
         }
      }

      this.analyzing = null;
      this.cancelDisintegration();
   }

   void save(CompoundTag tag) {
      tag.putBoolean("DiabloSkillsInitialized", this.initialized);
      tag.putBoolean("DiabloCombatSkillsMastered", this.combatSkillsMastered);
      tag.putBoolean("DiabloMagicLoadoutInitialized", this.magicLoadoutInitialized);
      tag.putBoolean("DiabloSeducerInitialized", this.seducerInitialized);
      tag.putInt("DiabloDespairCooldown", this.despairCooldown);
      tag.putBoolean("DiabloDisintegrationFlight", this.controllingFlight);
      tag.putBoolean("DiabloFlyingBeforeCast", this.flyingBeforeCast);
      tag.putInt("DiabloDisintegrationCooldown", this.disintegrationCooldown);
      if (this.disintegrationId != null) {
         tag.putUUID("DiabloDisintegration", this.disintegrationId);
      }
   }

   void load(CompoundTag tag) {
      this.initialized = tag.getBoolean("DiabloSkillsInitialized");
      this.combatSkillsMastered = tag.getBoolean("DiabloCombatSkillsMastered");
      this.magicLoadoutInitialized = tag.getBoolean("DiabloMagicLoadoutInitialized");
      this.seducerInitialized = tag.getBoolean("DiabloSeducerInitialized");
      this.despairCooldown = Math.max(0, tag.getInt("DiabloDespairCooldown"));
      this.disintegrationCooldown = Math.max(0, tag.getInt("DiabloDisintegrationCooldown"));
      this.disintegrationId = tag.hasUUID("DiabloDisintegration") ? tag.getUUID("DiabloDisintegration") : null;
      if (tag.getBoolean("DiabloDisintegrationFlight")) {
         this.diablo.setCombatFlying(tag.getBoolean("DiabloFlyingBeforeCast"));
      }
   }

   private static ResourceLocation id(String name) {
      return ResourceLocation.fromNamespaceAndPath("tensura", name);
   }
}
