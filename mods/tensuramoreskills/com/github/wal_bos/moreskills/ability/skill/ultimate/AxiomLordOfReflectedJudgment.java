package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AxiomLordOfReflectedJudgment extends Skill {
   public static final int MODE_RIPPLE_SCRIPT = 0;
   public static final int MODE_MIRRORFIELD = 1;
   public static final int MODE_TRUTH = 2;
   public static final int MODE_PLANETFALL = 3;
   private static final MoreSkillsConfigs.AxiomConfig CONFIG = MoreSkillsConfigs.GRAND.axiomLordOfReflectedJudgment;
   private static final String TAG_DATA = "tms_axiom";
   private static final String TAG_MEMORIES = "mem";
   private static final String TAG_SCRIPT_TARGET = "scriptTarget";
   private static final String TAG_SCRIPT_END = "scriptEnd";
   private static final String TAG_MIRRORFIELD_END = "mirrorfieldEnd";
   private static final String TAG_COUNTER_CD = "counterCd";
   private static final String TAG_PLANETFALL_END = "planetfallEnd";
   private static final String TAG_INVERSION_USED = "inversion_used";
   private static final String E_KIND = "k";
   private static final String E_TIME = "tm";
   private static final String E_TARGET = "t";
   private static final String E_SOURCE = "s";
   private static final String E_AMOUNT = "a";
   private static final int K_DMG = 1;
   private static final int K_KILL = 3;

   public AxiomLordOfReflectedJudgment() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/axiom.png");
   }

   public int getMaxMastery() {
      return (Integer)CONFIG.maxMastery.get();
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

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 3 : mode - 1;
      } else {
         return mode == 3 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "axiom_lord_of_reflected_judgment.1";
         case 1 -> "axiom_lord_of_reflected_judgment.2";
         case 2 -> "axiom_lord_of_reflected_judgment.3";
         case 3 -> "axiom_lord_of_reflected_judgment.4";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.rippleScriptCost.get();
         case 1 -> CONFIG.mirrorfieldCost.get();
         case 2 -> CONFIG.truthCost.get();
         case 3 -> CONFIG.planetfallCost.get();
         default -> 0.0;
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer sp) {
         sp.displayClientMessage(Component.translatable("tensuramoreskills.skill.axiom.toggle_on").withStyle(ChatFormatting.DARK_AQUA), true);
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag root = getRoot(instance);
      root.putBoolean("inversion_used", false);
      saveRoot(instance, root);
      if (entity instanceof ServerPlayer sp) {
         sp.displayClientMessage(Component.translatable("tensuramoreskills.skill.axiom.toggle_on").withStyle(ChatFormatting.DARK_AQUA), true);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer sp) {
         sp.displayClientMessage(Component.translatable("tensuramoreskills.skill.axiom.toggle_off").withStyle(ChatFormatting.GRAY), true);
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag root = getRoot(instance);
      long now = entity.level() instanceof ServerLevel sl ? sl.getGameTime() : 0L;
      return instance.isToggled() || root.getLong("mirrorfieldEnd") > now || root.getLong("planetfallEnd") > now || root.getLong("scriptEnd") > now;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer caster && entity.level() instanceof ServerLevel level) {
         CompoundTag var9 = getRoot(instance);
         long now = level.getGameTime();
         if (var9.getLong("scriptEnd") <= now) {
            var9.remove("scriptTarget");
            var9.remove("scriptEnd");
         }

         if (var9.getLong("planetfallEnd") > now && now % 30L == 0L) {
            int amp = (Integer)CONFIG.planetfallAmplifier.get();
            caster.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_BARRIER), 60, amp, false, false, true), caster);
            caster.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.REINFORCEMENT), 60, amp, false, false, true), caster);
            caster.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION), 60, amp, false, false, true), caster);
         }

         if (var9.getLong("mirrorfieldEnd") > now) {
            tickMirrorfield(level, caster, instance, var9);
         }

         saveRoot(instance, var9);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer caster && caster.level() instanceof ServerLevel level) {
         switch (mode) {
            case 0:
               if (EnergyHelper.isOutOfEnergy(caster, instance, mode)) {
                  return;
               }

               LivingEntity target = getLookedAtLiving(caster, (Double)CONFIG.rippleScriptRange.get());
               if (target == null) {
                  caster.displayClientMessage(Component.translatable("tensuramoreskills.skill.axiom.no_target").withStyle(ChatFormatting.RED), true);
                  return;
               }

               markScript(
                  level,
                  caster,
                  target,
                  (instance.isMastered(caster) ? (Integer)CONFIG.rippleScriptDurationTicksMastered.get() : (Integer)CONFIG.rippleScriptDurationTicks.get())
                     .intValue(),
                  instance
               );
               caster.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.axiom.prophecy_mark", new Object[]{target.getDisplayName()}).withStyle(ChatFormatting.AQUA),
                  true
               );
               instance.setCoolDown((Integer)CONFIG.rippleScriptCooldownTicks.get(), mode);
            case 1:
            default:
               break;
            case 2:
               if (EnergyHelper.isOutOfEnergy(caster, instance, mode)) {
                  return;
               }

               replayTruth(level, caster, instance, false);
               caster.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.axiom.truth").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}), true
               );
               level.playSound(null, caster.blockPosition(), (SoundEvent)SoundEvents.TRIDENT_THUNDER.value(), SoundSource.PLAYERS, 1.0F, 0.85F);
               instance.setCoolDown((Integer)CONFIG.truthCooldownTicks.get(), mode);
               break;
            case 3:
               if (EnergyHelper.isOutOfEnergy(caster, instance, mode)) {
                  return;
               }

               planetfall(level, caster, instance);
               instance.setCoolDown(
                  instance.isMastered(caster) ? (Integer)CONFIG.planetfallCooldownTicksMastered.get() : (Integer)CONFIG.planetfallCooldownTicks.get(), mode
               );
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (!(entity instanceof ServerPlayer caster && caster.level() instanceof ServerLevel level)) {
         return false;
      } else {
         if (mode != 1) {
            return true;
         }

         if (heldTicks % (Integer)CONFIG.mirrorfieldRefreshTicks.get() == 0) {
            if (EnergyHelper.isOutOfEnergy(caster, instance, mode)) {
               return false;
            }

            CompoundTag root = getRoot(instance);
            root.putLong("mirrorfieldEnd", level.getGameTime() + ((Integer)CONFIG.mirrorfieldActiveTicks.get()).intValue());
            saveRoot(instance, root);
            tickMirrorfield(level, caster, instance, root);
         }

         return true;
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!(entity instanceof ServerPlayer owner && owner.level() instanceof ServerLevel level)) {
         return true;
      } else if (!instance.isToggled()) {
         return true;
      } else {
         String msg = source.getMsgId();
         if (msg.startsWith("axiom_")) {
            return true;
         } else {
            float incoming = (Float)amount.get();
            if (incoming <= 0.0F) {
               return true;
            } else {
               record(instance, 1, owner.getUUID(), getSourceUUID(source, owner), incoming, level.getGameTime(), owner);
               amount.set(incoming * (1.0F - ((Double)CONFIG.passiveDamageReduction.get()).floatValue()));
               if (!(Boolean)CONFIG.counterIgnoresBypassInvulnerability.get() && source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                  return true;
               } else {
                  CompoundTag root = getRoot(instance);
                  long now = level.getGameTime();
                  long cdEnd = root.getLong("counterCd");
                  if (cdEnd > now) {
                     saveRoot(instance, root);
                     return true;
                  } else {
                     LivingEntity attacker = resolveAttacker(source, owner);
                     if (attacker != null && attacker.isAlive() && attacker != owner) {
                        root.putLong(
                           "counterCd",
                           now
                              + (instance.isMastered(owner) ? (Integer)CONFIG.counterCooldownTicksMastered.get() : (Integer)CONFIG.counterCooldownTicks.get())
                                 .intValue()
                        );
                        saveRoot(instance, root);
                        float reflect = incoming
                           * (
                              instance.isMastered(owner)
                                 ? ((Double)CONFIG.counterMultiplierMastered.get()).floatValue()
                                 : ((Double)CONFIG.counterMultiplier.get()).floatValue()
                           );
                        DamageSource counter = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, owner);
                        attacker.invulnerableTime = 0;
                        attacker.hurt(counter, reflect);
                        attacker.invulnerableTime = 0;
                        TensuraParticleHelper.spawnServerParticles(
                           level,
                           (ParticleOptions)TensuraParticleTypes.SOLAR_FLASH.get(),
                           owner.getX(),
                           owner.getY() + owner.getBbHeight() * 0.65,
                           owner.getZ(),
                           1,
                           0.0,
                           0.0,
                           0.0,
                           0.0,
                           true
                        );
                        TensuraParticleHelper.spawnServerParticles(
                           level,
                           (ParticleOptions)TensuraParticleTypes.PURPLE_LIGHTNING_SPARK.get(),
                           attacker.getX(),
                           attacker.getY() + attacker.getBbHeight() * 0.6,
                           attacker.getZ(),
                           28,
                           0.55,
                           0.4,
                           0.55,
                           0.05,
                           true
                        );
                        amount.set(0.0F);
                        return false;
                     } else {
                        saveRoot(instance, root);
                        return true;
                     }
                  }
               }
            }
         }
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!(entity instanceof ServerPlayer owner && owner.level() instanceof ServerLevel level)) {
         return true;
      } else {
         if (!instance.isToggled()) {
            return true;
         }

         float dealt = (Float)amount.get();
         if (dealt <= 0.0F) {
            return true;
         }

         record(instance, 1, target.getUUID(), owner.getUUID(), dealt, level.getGameTime(), owner);
         CompoundTag root = getRoot(instance);
         long end = root.getLong("scriptEnd");
         if (end > level.getGameTime() && root.hasUUID("scriptTarget") && root.getUUID("scriptTarget").equals(target.getUUID())) {
            float bonus = dealt
               * (
                  instance.isMastered(owner)
                     ? ((Double)CONFIG.rippleScriptBonusMultiplierMastered.get()).floatValue()
                     : ((Double)CONFIG.rippleScriptBonusMultiplier.get()).floatValue()
               );
            record(instance, 1, target.getUUID(), owner.getUUID(), bonus, level.getGameTime(), owner);
            amount.set(dealt + bonus);
            TensuraParticleHelper.spawnServerParticles(
               level,
               (ParticleOptions)TensuraParticleTypes.SOUL.get(),
               target.getX(),
               target.getY() + target.getBbHeight() * 0.6,
               target.getZ(),
               8,
               0.35,
               0.35,
               0.35,
               0.01,
               true
            );
         }

         saveRoot(instance, root);
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
      if ((Boolean)CONFIG.projectileReflectionEnabled.get()) {
         if (entity instanceof ServerPlayer owner && owner.level() instanceof ServerLevel level) {
            if (instance.isToggled()) {
               CompoundTag root = getRoot(instance);
               long now = level.getGameTime();
               if (root.getLong("counterCd") <= now) {
                  root.putLong(
                     "counterCd",
                     now
                        + (instance.isMastered(owner) ? (Integer)CONFIG.counterCooldownTicksMastered.get() : (Integer)CONFIG.counterCooldownTicks.get())
                           .intValue()
                  );
                  saveRoot(instance, root);
                  Vec3 v = projectile.getDeltaMovement();
                  double speed = (Double)CONFIG.projectileReflectSpeed.get();
                  Vec3 rev = v.lengthSqr() < 1.0E-7 ? owner.getLookAngle().scale(speed) : v.normalize().scale(speed).reverse();
                  projectile.setDeltaMovement(rev);
                  projectile.setOwner(owner);
                  if (projectile instanceof AbstractArrow arrow) {
                     arrow.setBaseDamage(
                        arrow.getBaseDamage()
                           * instance.isMastered(owner)
                              ? (Double)CONFIG.projectileArrowDamageMultiplierMastered.get()
                              : (Double)CONFIG.projectileArrowDamageMultiplier.get()
                     );
                  }

                  result.set(ProjectileHitResult.PASS);
                  TensuraParticleHelper.spawnServerParticles(
                     level,
                     (ParticleOptions)TensuraParticleTypes.SOLAR_FLASH.get(),
                     owner.getX(),
                     owner.getY() + owner.getBbHeight() * 0.65,
                     owner.getZ(),
                     1,
                     0.0,
                     0.0,
                     0.0,
                     0.0,
                     true
                  );
                  TensuraParticleHelper.spawnServerParticles(
                     level,
                     (ParticleOptions)TensuraParticleTypes.LIGHTNING_EFFECT.get(),
                     projectile.getX(),
                     projectile.getY() + 0.2,
                     projectile.getZ(),
                     16,
                     0.25,
                     0.25,
                     0.25,
                     0.02,
                     true
                  );
               }
            }
         }
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      if (!(entity instanceof ServerPlayer owner && owner.level() instanceof ServerLevel level)) {
         return true;
      } else {
         if (!instance.isToggled()) {
            return true;
         }

         if (source.getEntity() instanceof LivingEntity living && living != owner) {
            record(instance, 3, owner.getUUID(), living.getUUID(), owner.getMaxHealth(), level.getGameTime(), owner);
         }

         CompoundTag root = getRoot(instance);
         if (instance.isMastered(owner) && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !root.getBoolean("inversion_used")) {
            root.putBoolean("inversion_used", true);
            saveRoot(instance, root);
            entity.setHealth(
               Math.max(
                  ((Double)CONFIG.inversionMinimumHealth.get()).floatValue(), entity.getMaxHealth() * ((Double)CONFIG.inversionHealthRatio.get()).floatValue()
               )
            );
            owner.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_BARRIER),
                  (Integer)CONFIG.inversionBarrierTicks.get(),
                  (Integer)CONFIG.inversionBarrierAmplifier.get(),
                  false,
                  false,
                  true
               ),
               owner
            );
            owner.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
                  (Integer)CONFIG.inversionRegenTicks.get(),
                  (Integer)CONFIG.inversionRegenAmplifier.get(),
                  false,
                  false,
                  true
               ),
               owner
            );
            replayTruth(level, owner, instance, true);
            owner.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.axiom.inversion")
                  .withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD}),
               false
            );
            return false;
         } else {
            return true;
         }
      }
   }

   private static void tickMirrorfield(ServerLevel level, ServerPlayer owner, ManasSkillInstance instance, CompoundTag root) {
      long now = level.getGameTime();
      AABB box = owner.getBoundingBox().inflate((Double)CONFIG.mirrorfieldRadius.get());
      TensuraParticleHelper.addServerParticlesAroundSelf(owner, (ParticleOptions)TensuraParticleTypes.LIGHTNING_EFFECT.get(), 1.25);
      TensuraParticleHelper.spawnServerParticles(
         level,
         (ParticleOptions)TensuraParticleTypes.SOUL.get(),
         owner.getX(),
         owner.getY() + owner.getBbHeight() * 0.6,
         owner.getZ(),
         14,
         0.9,
         0.45,
         0.9,
         0.01,
         true
      );
      MagicCircle.castMagicCircle(
         "axiom_mirrorfield", 2.2F, 12, MagicCircleVariant.LIGHT, true, owner, root, 1.2F, 0.0F, Vec3.ZERO, instance, 1, Pair.of(0.0, 0.0)
      );

      for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, t -> t.isAlive() && t != owner && !t.isSpectator())) {
         DamageSource ds = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, owner);
         e.hurt(ds, ((Double)CONFIG.mirrorfieldDamage.get()).floatValue());
         float bonus = instance.isMastered(owner)
            ? ((Double)CONFIG.mirrorfieldMemoryDamageMastered.get()).floatValue()
            : ((Double)CONFIG.mirrorfieldMemoryDamage.get()).floatValue();
         record(instance, 1, e.getUUID(), owner.getUUID(), bonus, now, owner);
         e.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               (Integer)CONFIG.mirrorfieldMovementInterferenceTicks.get(),
               instance.isMastered(owner)
                  ? (Integer)CONFIG.mirrorfieldMovementInterferenceAmplifierMastered.get()
                  : (Integer)CONFIG.mirrorfieldMovementInterferenceAmplifier.get(),
               false,
               false,
               true
            ),
            owner
         );
         TensuraParticleHelper.spawnServerParticles(
            level,
            (ParticleOptions)TensuraParticleTypes.PURPLE_LIGHTNING_SPARK.get(),
            e.getX(),
            e.getY() + e.getBbHeight() * 0.6,
            e.getZ(),
            4,
            0.25,
            0.25,
            0.25,
            0.01,
            true
         );
      }
   }

   private static void planetfall(ServerLevel level, ServerPlayer caster, ManasSkillInstance instance) {
      boolean mastered = instance.isMastered(caster);
      double r = mastered ? (Double)CONFIG.planetfallRadiusMastered.get() : (Double)CONFIG.planetfallRadius.get();
      float base = mastered ? ((Double)CONFIG.planetfallBaseDamageMastered.get()).floatValue() : ((Double)CONFIG.planetfallBaseDamage.get()).floatValue();
      float hpPct = mastered ? ((Double)CONFIG.planetfallHpPercentMastered.get()).floatValue() : ((Double)CONFIG.planetfallHpPercent.get()).floatValue();
      float maxDmg = mastered ? ((Double)CONFIG.planetfallMaxDamageMastered.get()).floatValue() : ((Double)CONFIG.planetfallMaxDamage.get()).floatValue();
      int amp = (Integer)CONFIG.planetfallAmplifier.get();
      CompoundTag root = getRoot(instance);
      root.putLong("planetfallEnd", level.getGameTime() + ((Integer)CONFIG.planetfallBuffTicks.get()).intValue());
      saveRoot(instance, root);
      caster.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_BARRIER), (Integer)CONFIG.planetfallBuffTicks.get(), amp, false, false, true
         ),
         caster
      );
      caster.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.REINFORCEMENT), (Integer)CONFIG.planetfallBuffTicks.get(), amp, false, false, true
         ),
         caster
      );
      caster.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION), (Integer)CONFIG.planetfallBuffTicks.get(), amp, false, false, true
         ),
         caster
      );
      TensuraParticleHelper.spawnServerParticles(
         level,
         (ParticleOptions)TensuraParticleTypes.SOLAR_FLASH.get(),
         caster.getX(),
         caster.getY() + caster.getBbHeight() * 0.75,
         caster.getZ(),
         2,
         0.0,
         0.0,
         0.0,
         0.0,
         true
      );
      TensuraParticleHelper.spawnServerParticles(
         level,
         (ParticleOptions)TensuraParticleTypes.WEATHER_MANIPULATION.get(),
         caster.getX(),
         caster.getY() + 1.0,
         caster.getZ(),
         1,
         0.0,
         0.0,
         0.0,
         0.0,
         true
      );
      TensuraParticleHelper.spawnServerParticles(
         level,
         (ParticleOptions)TensuraParticleTypes.PURPLE_LIGHTNING_SPARK.get(),
         caster.getX(),
         caster.getY() + caster.getBbHeight() * 0.45,
         caster.getZ(),
         mastered ? 260 : 190,
         2.8,
         1.4,
         2.8,
         0.09,
         true
      );
      MagicCircle.castMagicCircle(
         "axiom_planetfall",
         mastered ? 7.5F : 6.0F,
         40,
         MagicCircleVariant.LIGHT,
         true,
         caster,
         root,
         0.0F,
         0.0F,
         new Vec3(0.0, 0.35, 0.0),
         instance,
         3,
         Pair.of(0.0, 0.0)
      );
      double cx = caster.getX();
      double cy = caster.getY() + 0.2;
      double cz = caster.getZ();
      if ((Boolean)CONFIG.planetfallExplosions.get()) {
         level.explode(
            null,
            cx,
            cy,
            cz,
            mastered
               ? ((Double)CONFIG.planetfallCenterExplosionPowerMastered.get()).floatValue()
               : ((Double)CONFIG.planetfallCenterExplosionPower.get()).floatValue(),
            false,
            ExplosionInteraction.NONE
         );
      }

      double[] rings = mastered ? new double[]{10.0, 18.0, 28.0, 40.0, 54.0} : new double[]{10.0, 18.0, 28.0, 40.0};

      for (double ring : rings) {
         int points = mastered ? 12 : 10;

         for (int i = 0; i < points; i++) {
            double ang = (Math.PI * 2) * ((double)i / points);
            double x = cx + Math.cos(ang) * ring;
            double z = cz + Math.sin(ang) * ring;
            TensuraParticleHelper.spawnServerParticles(
               level, (ParticleOptions)TensuraParticleTypes.LIGHTNING_EFFECT.get(), x, cy + 0.6, z, 8, 0.9, 0.4, 0.9, 0.02, true
            );
            TensuraParticleHelper.spawnServerParticles(
               level, (ParticleOptions)TensuraParticleTypes.SOUL.get(), x, cy + 0.6, z, mastered ? 30 : 22, 0.65, 0.45, 0.65, 0.06, true
            );
            if ((Boolean)CONFIG.planetfallExplosions.get()) {
               level.explode(
                  null,
                  x,
                  cy,
                  z,
                  mastered
                     ? ((Double)CONFIG.planetfallRingExplosionPowerMastered.get()).floatValue()
                     : ((Double)CONFIG.planetfallRingExplosionPower.get()).floatValue(),
                  false,
                  ExplosionInteraction.NONE
               );
            }
         }
      }

      AABB box = caster.getBoundingBox().inflate(r);

      for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, t -> t.isAlive() && t != caster && !t.isSpectator())) {
         double dist = Math.sqrt(caster.distanceToSqr(e));
         float falloff = (float)Mth.clamp(1.0 - dist / r, 0.15, 1.0);
         float dmg = base + e.getMaxHealth() * hpPct;
         dmg *= falloff;
         dmg = Mth.clamp(dmg, ((Double)CONFIG.planetfallMinDamage.get()).floatValue(), maxDmg);
         DamageSource ds = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, caster);
         e.hurt(ds, dmg);
         e.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS),
               mastered ? (Integer)CONFIG.planetfallBlindnessTicksMastered.get() : (Integer)CONFIG.planetfallBlindnessTicks.get(),
               0,
               false,
               false,
               true
            ),
            caster
         );
         e.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               mastered ? (Integer)CONFIG.planetfallMovementTicksMastered.get() : (Integer)CONFIG.planetfallMovementTicks.get(),
               mastered ? (Integer)CONFIG.planetfallMovementAmplifierMastered.get() : (Integer)CONFIG.planetfallMovementAmplifier.get(),
               false,
               false,
               true
            ),
            caster
         );
         e.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY),
               mastered ? (Integer)CONFIG.planetfallFragilityTicksMastered.get() : (Integer)CONFIG.planetfallFragilityTicks.get(),
               mastered ? (Integer)CONFIG.planetfallFragilityAmplifierMastered.get() : (Integer)CONFIG.planetfallFragilityAmplifier.get(),
               false,
               false,
               true
            ),
            caster
         );
         Vec3 away = e.position().subtract(caster.position());
         Vec3 kb = away.lengthSqr() < 1.0E-4
            ? Vec3.ZERO
            : away.normalize().scale(mastered ? (Double)CONFIG.planetfallKnockbackMastered.get() : (Double)CONFIG.planetfallKnockback.get());
         e.setDeltaMovement(
            e.getDeltaMovement()
               .add(kb.x, mastered ? (Double)CONFIG.planetfallVerticalKnockbackMastered.get() : (Double)CONFIG.planetfallVerticalKnockback.get(), kb.z)
         );
         e.hurtMarked = true;
         TensuraParticleHelper.spawnServerParticles(
            level,
            (ParticleOptions)TensuraParticleTypes.WEATHER_MANIPULATION.get(),
            e.getX(),
            e.getY() + e.getBbHeight() * 0.55,
            e.getZ(),
            1,
            0.0,
            0.0,
            0.0,
            0.0,
            true
         );
      }
   }

   private static void markScript(ServerLevel level, ServerPlayer owner, LivingEntity target, long durationTicks, ManasSkillInstance instance) {
      CompoundTag root = getRoot(instance);
      root.putUUID("scriptTarget", target.getUUID());
      root.putLong("scriptEnd", level.getGameTime() + durationTicks);
      saveRoot(instance, root);
      TensuraParticleHelper.spawnServerParticles(
         level,
         (ParticleOptions)TensuraParticleTypes.SOUL.get(),
         target.getX(),
         target.getY() + target.getBbHeight() * 0.7,
         target.getZ(),
         12,
         0.45,
         0.45,
         0.45,
         0.01,
         true
      );
      TensuraParticleHelper.spawnServerParticles(
         level,
         (ParticleOptions)TensuraParticleTypes.LIGHTNING_EFFECT.get(),
         target.getX(),
         target.getY() + target.getBbHeight() * 0.45,
         target.getZ(),
         18,
         0.35,
         0.35,
         0.35,
         0.03,
         true
      );
   }

   private static void replayTruth(ServerLevel level, ServerPlayer caster, ManasSkillInstance instance, boolean fromInversion) {
      CompoundTag root = getRoot(instance);
      ListTag list = root.contains("mem", 9) ? root.getList("mem", 10) : new ListTag();
      if (list.isEmpty()) {
         caster.displayClientMessage(Component.translatable("tensuramoreskills.skill.axiom.no_reflections").withStyle(ChatFormatting.RED), true);
         saveRoot(instance, root);
      } else {
         int max = instance.isMastered(caster) ? (Integer)CONFIG.truthConsumeMemoriesMastered.get() : (Integer)CONFIG.truthConsumeMemories.get();
         float dmgPool = 0.0F;
         int consumed = 0;

         for (int i = list.size() - 1; i >= 0 && consumed < max; i--) {
            CompoundTag c = list.getCompound(i);
            int kind = c.getInt("k");
            float amt = c.getFloat("a");
            list.remove(i);
            consumed++;
            if (!(amt <= 0.0F) && (kind == 1 || kind == 3)) {
               dmgPool += amt;
            }
         }

         float burst = dmgPool * (fromInversion ? ((Double)CONFIG.truthInversionMultiplier.get()).floatValue() : 1.0F);
         if (burst > 0.0F) {
            double rad = instance.isMastered(caster) ? (Double)CONFIG.truthRadiusMastered.get() : (Double)CONFIG.truthRadius.get();
            AABB box = caster.getBoundingBox().inflate(rad);
            TensuraParticleHelper.spawnServerParticles(
               level,
               (ParticleOptions)TensuraParticleTypes.SOLAR_FLASH.get(),
               caster.getX(),
               caster.getY() + caster.getBbHeight() * 0.7,
               caster.getZ(),
               1,
               0.0,
               0.0,
               0.0,
               0.0,
               true
            );
            TensuraParticleHelper.addServerParticlesAroundSelf(caster, (ParticleOptions)TensuraParticleTypes.SOUL.get(), 1.5);

            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, box, t -> t.isAlive() && t != caster && !t.isSpectator())) {
               double dist = Math.sqrt(caster.distanceToSqr(e));
               float falloff = (float)Mth.clamp(1.0 - dist / rad, (Double)CONFIG.truthFalloffMinimum.get(), 1.0);
               float dmg = burst * falloff;
               dmg = Mth.clamp(
                  dmg,
                  ((Double)CONFIG.truthMinDamage.get()).floatValue(),
                  instance.isMastered(caster) ? ((Double)CONFIG.truthMaxDamageMastered.get()).floatValue() : ((Double)CONFIG.truthMaxDamage.get()).floatValue()
               );
               DamageSource ds = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, caster);
               e.hurt(ds, dmg);
               e.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS), (Integer)CONFIG.truthBlindnessTicks.get(), 0, false, false, true
                  ),
                  caster
               );
               TensuraParticleHelper.spawnServerParticles(
                  level, (ParticleOptions)TensuraParticleTypes.SOUL.get(), e.getX(), e.getY() + e.getBbHeight() * 0.6, e.getZ(), 18, 0.4, 0.4, 0.4, 0.035, true
               );
               TensuraParticleHelper.spawnServerParticles(
                  level,
                  (ParticleOptions)TensuraParticleTypes.LIGHTNING_EFFECT.get(),
                  e.getX(),
                  e.getY() + e.getBbHeight() * 0.55,
                  e.getZ(),
                  4,
                  0.25,
                  0.25,
                  0.25,
                  0.01,
                  true
               );
            }
         }

         root.put("mem", list);
         saveRoot(instance, root);
      }
   }

   private static void record(ManasSkillInstance instance, int kind, UUID target, UUID source, float amount, long time, LivingEntity owner) {
      if (!(amount <= 0.0F)) {
         CompoundTag root = getRoot(instance);
         ListTag list = root.contains("mem", 9) ? root.getList("mem", 10) : new ListTag();
         int cap = instance.isMastered(owner) ? (Integer)CONFIG.maxMemoryMastered.get() : (Integer)CONFIG.maxMemory.get();
         CompoundTag add = new CompoundTag();
         add.putInt("k", kind);
         add.putLong("tm", time);
         add.putUUID("t", target);
         add.putUUID("s", source);
         add.putFloat("a", Mth.clamp(amount, 0.0F, 2.0E9F));
         list.add(add);

         while (list.size() > cap) {
            list.remove(0);
         }

         root.put("mem", list);
         saveRoot(instance, root);
      }
   }

   private static UUID getSourceUUID(DamageSource src, ServerPlayer fallback) {
      Entity d = src.getDirectEntity();
      if (d != null) {
         return d.getUUID();
      }

      Entity e = src.getEntity();
      return e != null ? e.getUUID() : fallback.getUUID();
   }

   @Nullable
   private static LivingEntity resolveAttacker(DamageSource src, LivingEntity victim) {
      if (src.getEntity() instanceof LivingEntity le && le != victim) {
         return le;
      } else {
         Entity d = src.getDirectEntity();
         if (d instanceof LivingEntity le2 && le2 != victim) {
            return le2;
         } else if (d instanceof Projectile p && p.getOwner() instanceof LivingEntity le3 && le3 != victim) {
            return le3;
         } else {
            LivingEntity lastMob = victim.getLastHurtByMob();
            return lastMob != null && lastMob.isAlive() && lastMob != victim ? lastMob : null;
         }
      }
   }

   @Nullable
   private static LivingEntity getLookedAtLiving(LivingEntity searcher, double range) {
      Vec3 eyePos = searcher.getEyePosition(1.0F);
      Vec3 look = searcher.getViewVector(1.0F);
      Vec3 endPos = eyePos.add(look.scale(range));
      AABB searchBox = searcher.getBoundingBox().expandTowards(look.scale(range)).inflate(1.5);
      LivingEntity closest = null;
      double best = range;

      for (LivingEntity e : searcher.level().getEntitiesOfClass(LivingEntity.class, searchBox, t -> t.isAlive() && t != searcher && !t.isSpectator())) {
         AABB bb = e.getBoundingBox().inflate(e.getPickRadius() + 0.5);
         Optional<Vec3> hit = bb.clip(eyePos, endPos);
         if (bb.contains(eyePos)) {
            closest = e;
            best = 0.0;
         } else if (hit.isPresent()) {
            double dist = eyePos.distanceTo(hit.get());
            if (dist < best && searcher.hasLineOfSight(e)) {
               best = dist;
               closest = e;
            }
         }
      }

      return closest;
   }

   private static CompoundTag getRoot(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.contains("tms_axiom", 10) ? tag.getCompound("tms_axiom") : new CompoundTag();
   }

   private static void saveRoot(ManasSkillInstance instance, CompoundTag root) {
      CompoundTag tag = instance.getOrCreateTag();
      tag.put("tms_axiom", root);
      instance.markDirty();
   }
}
