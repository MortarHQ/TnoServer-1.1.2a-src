package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.AbnormalConditionNullification;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WitchOfVainglorySkill extends Skill {
   public static final int MODE_REALITY_CHANGE = 0;
   public static final int MODE_DELETED_TIMELINE = 1;
   public static final int MODE_FALSE_PERCEPTION = 2;
   public static final int MODE_NEVER_HERE = 3;
   private static final MoreSkillsConfigs.WitchOfVainglory CONFIG = MoreSkillsConfigs.GRAND.witchOfVainglory;
   private static final String TAG_ROOT = "WitchOfVainglory";
   private static final String TAG_REALITY_COAT_TICKS = "RealityCoatTicks";
   private static final String TAG_COUNTER_TICKS = "CounterTicks";
   private static final String TAG_FALSE_PERCEPTION_TICKS = "FalsePerceptionTicks";
   private static final String TAG_FALSE_PERCEPTION_OWNER_TICKS = "FalsePerceptionOwnerTicks";
   private static final String TAG_REVIVE_DAY = "VaingloryReviveDay";
   private static final String TAG_REVIVE_COUNT = "VaingloryReviveCount";

   public WitchOfVainglorySkill() {
      super(SkillType.ULTIMATE);
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/witch_of_vainglory.png");
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
         return mode <= 0 ? this.getModes(instance) - 1 : mode - 1;
      } else {
         return mode >= this.getModes(instance) - 1 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "witch_of_vainglory.1";
         case 1 -> "witch_of_vainglory.2";
         case 2 -> "witch_of_vainglory.3";
         case 3 -> "witch_of_vainglory.4";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> ((Integer)CONFIG.realityChangeCost.get()).intValue();
         case 1 -> ((Integer)CONFIG.deletedTimelineCost.get()).intValue();
         case 2 -> ((Integer)CONFIG.falsePerceptionCost.get()).intValue();
         case 3 -> ((Integer)CONFIG.neverHereCost.get()).intValue();
         default -> 0.0;
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag().getCompound("WitchOfVainglory");
      return instance.isToggled() || tag.getInt("RealityCoatTicks") > 0 || tag.getInt("CounterTicks") > 0 || tag.getInt("FalsePerceptionOwnerTicks") > 0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.witch_of_vainglory.reality_toggle_on").withStyle(ChatFormatting.DARK_PURPLE), true
         );
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.witch_of_vainglory.reality_toggle_off").withStyle(ChatFormatting.GRAY), true
         );
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag skillTag = instance.getOrCreateTag();
         CompoundTag root = skillTag.getCompound("WitchOfVainglory");
         if (instance.isToggled()) {
            AABB auraBox = entity.getBoundingBox().inflate((Double)CONFIG.realityAuraRadius.get());
            if (entity.tickCount % (Integer)CONFIG.auraEffectRefreshIntervalTicks.get() == 0) {
               for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, auraBox, e -> e.isAlive() && e != entity && !e.isAlliedTo(entity))) {
                  target.addEffect(
                     new MobEffectInstance(
                        TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
                        (Integer)CONFIG.auraEffectDurationTicks.get(),
                        0,
                        true,
                        false,
                        true
                     )
                  );
               }
            }

            if (entity.tickCount % 5 == 0) {
               level.sendParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 4, 0.2, 0.3, 0.2, 0.01);
            }
         }

         int coat = root.getInt("RealityCoatTicks");
         if (coat > 0) {
            root.putInt("RealityCoatTicks", --coat);
            if (entity.tickCount % (Integer)CONFIG.realityCoatEffectRefreshIntervalTicks.get() == 0
               || coat == (Integer)CONFIG.realityCoatDurationTicks.get() - 1) {
               this.applyRealityCoatEffects(entity);
            }

            if (entity.tickCount % 5 == 0) {
               level.sendParticles(ParticleTypes.ENCHANT, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 6, 0.25, 0.35, 0.25, 0.02);
            }

            if (coat == 0 && entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.witch_of_vainglory.reality_change_end").withStyle(ChatFormatting.DARK_PURPLE), true
               );
            }
         }

         int counter = root.getInt("CounterTicks");
         if (counter > 0) {
            root.putInt("CounterTicks", --counter);
            if (entity.tickCount % 4 == 0) {
               level.sendParticles(
                  ParticleTypes.ELECTRIC_SPARK, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 5, 0.2, 0.2, 0.2, 0.01
               );
            }
         }

         int ownerFalse = root.getInt("FalsePerceptionOwnerTicks");
         if (ownerFalse > 0) {
            root.putInt("FalsePerceptionOwnerTicks", --ownerFalse);
            AABB box = entity.getBoundingBox().inflate(((Integer)CONFIG.falsePerceptionRadius.get()).intValue());

            for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != entity && !e.isAlliedTo(entity))) {
               CompoundTag targetRoot = target.getPersistentData().getCompound("WitchOfVainglory");
               int ticks = targetRoot.getInt("FalsePerceptionTicks");
               if (ticks > 0) {
                  targetRoot.putInt("FalsePerceptionTicks", ticks - 1);
                  target.getPersistentData().put("WitchOfVainglory", targetRoot);
                  float yaw = target.getYRot() + ((Double)CONFIG.falsePerceptionYawRotation.get()).floatValue();
                  target.setYRot(yaw);
                  target.setYHeadRot(yaw);
                  target.setYBodyRot(yaw);
                  if (target.tickCount % 4 == 0) {
                     level.sendParticles(
                        ParticleTypes.SMOKE, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), 2, 0.15, 0.15, 0.15, 0.01
                     );
                  }
               }
            }
         }

         skillTag.put("WitchOfVainglory", root);
         instance.markDirty();
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            if (player.level() instanceof ServerLevel level) {
               switch (mode) {
                  case 0:
                     this.castRealityChange(instance, player, level, mode);
                     break;
                  case 1:
                     this.castCounter(instance, player, level, mode);
                     break;
                  case 2:
                     this.castFalsePerception(instance, player, level, mode);
                     break;
                  case 3:
                     this.castNeverHere(instance, player, level, mode);
               }
            }
         }
      }
   }

   private void applyRealityCoatEffects(LivingEntity entity) {
      entity.addEffect(
         new MobEffectInstance(
            MobEffects.DAMAGE_RESISTANCE,
            (Integer)CONFIG.realityCoatEffectDurationTicks.get(),
            (Integer)CONFIG.realityCoatResistanceAmplifier.get(),
            false,
            false,
            false
         )
      );
      entity.addEffect(
         new MobEffectInstance(
            MobEffects.DAMAGE_BOOST,
            (Integer)CONFIG.realityCoatEffectDurationTicks.get(),
            (Integer)CONFIG.realityCoatStrengthAmplifier.get(),
            false,
            false,
            false
         )
      );
      entity.addEffect(
         new MobEffectInstance(
            MobEffects.MOVEMENT_SPEED,
            (Integer)CONFIG.realityCoatEffectDurationTicks.get(),
            (Integer)CONFIG.realityCoatSpeedAmplifier.get(),
            false,
            false,
            false
         )
      );
      entity.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
            (Integer)CONFIG.realityCoatEffectDurationTicks.get(),
            (Integer)CONFIG.realityCoatRegenerationAmplifier.get(),
            true,
            false,
            true
         )
      );
   }

   private void castRealityChange(ManasSkillInstance instance, ServerPlayer player, ServerLevel level, int mode) {
      CompoundTag skillTag = instance.getOrCreateTag();
      CompoundTag root = skillTag.getCompound("WitchOfVainglory");
      root.putInt("RealityCoatTicks", (Integer)CONFIG.realityCoatDurationTicks.get());
      skillTag.put("WitchOfVainglory", root);
      this.applyRealityCoatEffects(player);

      for (LivingEntity target : level.getEntitiesOfClass(
         LivingEntity.class,
         player.getBoundingBox().inflate((Double)CONFIG.realityChangeBurstRadius.get()),
         e -> e.isAlive() && e != player && !e.isAlliedTo(player)
      )) {
         target.knockback(((Double)CONFIG.realityChangeKnockback.get()).floatValue(), target.getX() - player.getX(), target.getZ() - player.getZ());
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.CONFUSION),
               (Integer)CONFIG.realityChangeConfusionTicks.get(),
               (Integer)CONFIG.realityChangeConfusionAmplifier.get(),
               true,
               false,
               true
            )
         );
      }

      for (Entity projectile : level.getEntities(
         player, player.getBoundingBox().inflate((Double)CONFIG.realityChangeProjectileClearRadius.get()), e -> e instanceof Projectile
      )) {
         projectile.discard();
      }

      level.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + player.getBbHeight() * 0.5, player.getZ(), 36, 0.8, 0.8, 0.8, 0.06);
      level.playSound(null, player.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.2F, 0.9F);
      player.displayClientMessage(
         Component.translatable("tensuramoreskills.skill.witch_of_vainglory.reality_change")
            .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD}),
         true
      );
      instance.addMasteryPoint(player);
      instance.setCoolDown((Integer)CONFIG.realityChangeCooldownTicks.get(), mode);
      instance.markDirty();
   }

   private void castCounter(ManasSkillInstance instance, ServerPlayer player, ServerLevel level, int mode) {
      CompoundTag skillTag = instance.getOrCreateTag();
      CompoundTag root = skillTag.getCompound("WitchOfVainglory");
      if (root.getInt("CounterTicks") <= 0) {
         root.putInt("CounterTicks", (Integer)CONFIG.counterDurationTicks.get());
         skillTag.put("WitchOfVainglory", root);
         player.addEffect(
            new MobEffectInstance(
               MobEffects.DAMAGE_RESISTANCE, (Integer)CONFIG.counterDurationTicks.get(), (Integer)CONFIG.counterResistanceAmplifier.get(), false, false, false
            )
         );
         level.sendParticles(ParticleTypes.ELECTRIC_SPARK, player.getX(), player.getY() + 0.5, player.getZ(), 24, 0.5, 0.35, 0.5, 0.02);
         level.playSound(null, player.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.2F, 0.7F);
         player.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.witch_of_vainglory.deleted_timeline_start")
               .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC}),
            true
         );
         instance.setCoolDown((Integer)CONFIG.deletedTimelineReadyCooldownTicks.get(), mode);
         instance.markDirty();
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      CompoundTag root = instance.getOrCreateTag().getCompound("WitchOfVainglory");
      if (root.getInt("CounterTicks") <= 0) {
         return true;
      }

      if (source.getEntity() instanceof LivingEntity attacker) {
         float reflected = Math.max(
            ((Double)CONFIG.counterReflectMinimumDamage.get()).floatValue(), (Float)amount.get() * ((Double)CONFIG.counterReflectMultiplier.get()).floatValue()
         );
         DamageSource reflect = TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, entity);
         attacker.hurt(reflect, reflected);
         root.putInt("CounterTicks", 0);
         instance.getOrCreateTag().put("WitchOfVainglory", root);
         if (entity.level() instanceof ServerLevel level) {
            level.sendParticles(
               ParticleTypes.ELECTRIC_SPARK, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 20, 0.35, 0.35, 0.35, 0.04
            );
            level.playSound(null, entity.blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.PLAYERS, 1.0F, 1.1F);
         }

         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.skill.witch_of_vainglory.deleted_timeline_rewind")
                  .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD}),
               true
            );
         }

         amount.set(0.0F);
         instance.addMasteryPoint(entity);
         instance.setCoolDown((Integer)CONFIG.deletedTimelineTriggeredCooldownTicks.get(), 1);
         instance.markDirty();
         return false;
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
      CompoundTag root = instance.getOrCreateTag().getCompound("WitchOfVainglory");
      if (root.getInt("CounterTicks") > 0) {
         if (projectile.getOwner() != entity) {
            Vec3 reversed = projectile.getDeltaMovement().reverse();
            if (reversed.lengthSqr() < 1.0E-4) {
               reversed = entity.getLookAngle().scale((Double)CONFIG.projectileReverseFallbackSpeed.get());
            } else {
               reversed = reversed.normalize().scale(Math.max((Double)CONFIG.projectileReverseMinimumSpeed.get(), projectile.getDeltaMovement().length()));
            }

            projectile.setOwner(entity);
            projectile.setDeltaMovement(reversed);
            projectile.hurtMarked = true;
            deflection.set(ProjectileDeflection.NONE);
            result.set(ProjectileHitResult.PASS);
            root.putInt("CounterTicks", 0);
            instance.getOrCreateTag().put("WitchOfVainglory", root);
            if (entity.level() instanceof ServerLevel level) {
               level.sendParticles(
                  ParticleTypes.ELECTRIC_SPARK, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 20, 0.35, 0.35, 0.35, 0.04
               );
               level.playSound(null, entity.blockPosition(), SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1.0F, 1.0F);
            }

            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.witch_of_vainglory.deleted_timeline_rewind")
                     .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.BOLD}),
                  true
               );
            }

            instance.addMasteryPoint(entity);
            instance.setCoolDown((Integer)CONFIG.deletedTimelineTriggeredCooldownTicks.get(), 1);
            instance.markDirty();
         }
      }
   }

   private void castFalsePerception(ManasSkillInstance instance, ServerPlayer player, ServerLevel level, int mode) {
      AABB box = player.getBoundingBox().inflate(((Integer)CONFIG.falsePerceptionRadius.get()).intValue());
      CompoundTag skillTag = instance.getOrCreateTag();
      CompoundTag root = skillTag.getCompound("WitchOfVainglory");
      root.putInt("FalsePerceptionOwnerTicks", (Integer)CONFIG.falsePerceptionDurationTicks.get());
      skillTag.put("WitchOfVainglory", root);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != player && !e.isAlliedTo(player))) {
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.FLASHED_BLINDNESS),
               (Integer)CONFIG.falsePerceptionDurationTicks.get(),
               (Integer)CONFIG.falsePerceptionBlindnessAmplifier.get(),
               true,
               true,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.CONFUSION),
               (Integer)CONFIG.falsePerceptionDurationTicks.get(),
               (Integer)CONFIG.falsePerceptionConfusionAmplifier.get(),
               true,
               true,
               true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE),
               (Integer)CONFIG.falsePerceptionDurationTicks.get(),
               (Integer)CONFIG.falsePerceptionMovementInterferenceAmplifier.get(),
               true,
               true,
               true
            )
         );
         CompoundTag tRoot = target.getPersistentData().getCompound("WitchOfVainglory");
         tRoot.putInt("FalsePerceptionTicks", (Integer)CONFIG.falsePerceptionDurationTicks.get());
         target.getPersistentData().put("WitchOfVainglory", tRoot);
         Vec3 pos = target.position();
         double spread = (Double)CONFIG.falsePerceptionTeleportSpread.get();
         double dx = (level.random.nextDouble() - 0.5) * spread;
         double dz = (level.random.nextDouble() - 0.5) * spread;
         target.teleportTo(pos.x + dx, pos.y, pos.z + dz);
         level.sendParticles(ParticleTypes.SMOKE, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), 12, 0.4, 0.4, 0.4, 0.04);
      }

      level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_STARE, SoundSource.PLAYERS, 1.5F, 0.5F);
      player.displayClientMessage(
         Component.translatable("tensuramoreskills.skill.witch_of_vainglory.false_perception").withStyle(ChatFormatting.DARK_PURPLE), true
      );
      instance.addMasteryPoint(player);
      instance.setCoolDown((Integer)CONFIG.falsePerceptionCooldownTicks.get(), mode);
      instance.markDirty();
   }

   private void castNeverHere(ManasSkillInstance instance, ServerPlayer caster, ServerLevel level, int mode) {
      MinecraftServer server = caster.getServer();
      if (server != null) {
         AABB box = caster.getBoundingBox().inflate(((Integer)CONFIG.neverHereRadius.get()).intValue());

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != caster && !e.isAlliedTo(caster))) {
            if (target instanceof ServerPlayer player) {
               ServerLevel destination = player.server.getLevel(player.getRespawnDimension());
               if (destination == null) {
                  destination = server.overworld();
               }

               if (player.getRespawnPosition() != null) {
                  player.teleportTo(
                     destination,
                     player.getRespawnPosition().getX() + 0.5,
                     player.getRespawnPosition().getY() + 0.1,
                     player.getRespawnPosition().getZ() + 0.5,
                     player.getYRot(),
                     player.getXRot()
                  );
               } else {
                  player.teleportTo(
                     destination,
                     destination.getSharedSpawnPos().getX() + 0.5,
                     destination.getSharedSpawnPos().getY() + 0.1,
                     destination.getSharedSpawnPos().getZ() + 0.5,
                     player.getYRot(),
                     player.getXRot()
                  );
               }

               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.witch_of_vainglory.you_were_never_here_target")
                     .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC}),
                  true
               );
            } else {
               target.teleportTo(level.getSharedSpawnPos().getX() + 0.5, level.getSharedSpawnPos().getY() + 0.1, level.getSharedSpawnPos().getZ() + 0.5);
            }
         }

         level.playSound(null, caster.blockPosition(), SoundEvents.ENDER_PEARL_THROW, SoundSource.PLAYERS, 1.2F, 0.7F);
         caster.displayClientMessage(
            Component.translatable("tensuramoreskills.skill.witch_of_vainglory.you_were_never_here")
               .withStyle(new ChatFormatting[]{ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC}),
            true
         );
         instance.addMasteryPoint(caster);
         instance.setCoolDown((Integer)CONFIG.neverHereCooldownTicks.get(), mode);
         instance.markDirty();
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity entity, DamageSource source) {
      if (!this.isInSlot(entity, instance)) {
         return true;
      } else if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return true;
      } else if (!(entity.level() instanceof ServerLevel level)) {
         return true;
      } else {
         CompoundTag var14 = instance.getOrCreateTag();
         long currentDay = level.getDayTime() / 24000L;
         long savedDay = var14.getLong("VaingloryReviveDay");
         if (savedDay != currentDay) {
            var14.putLong("VaingloryReviveDay", currentDay);
            var14.putInt("VaingloryReviveCount", 0);
         }

         int used = var14.getInt("VaingloryReviveCount");
         if (used >= (Integer)CONFIG.maxRevivesPerDay.get()) {
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.witch_of_vainglory.rewritten_exhausted").withStyle(ChatFormatting.DARK_PURPLE), false
               );
            }

            return true;
         } else {
            var14.putInt("VaingloryReviveCount", used + 1);
            entity.setHealth(
               Math.max(entity.getMaxHealth() * ((Double)CONFIG.reviveHealthRatio.get()).floatValue(), ((Double)CONFIG.reviveMinimumHealth.get()).floatValue())
            );
            entity.invulnerableTime = Math.max(entity.invulnerableTime, (Integer)CONFIG.reviveInvulnerabilityTicks.get());
            List<Holder<MobEffect>> harmfulEffects = new ArrayList<>();

            for (MobEffectInstance effect : List.copyOf(entity.getActiveEffects())) {
               if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
                  harmfulEffects.add(effect.getEffect());
               }
            }

            for (Holder<MobEffect> effect : harmfulEffects) {
               entity.removeEffect(effect);
            }

            entity.addEffect(
               new MobEffectInstance(
                  MobEffects.DAMAGE_RESISTANCE,
                  (Integer)CONFIG.reviveResistanceTicks.get(),
                  (Integer)CONFIG.reviveResistanceAmplifier.get(),
                  false,
                  false,
                  false
               )
            );
            entity.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
                  (Integer)CONFIG.reviveRegenerationTicks.get(),
                  (Integer)CONFIG.reviveRegenerationAmplifier.get(),
                  true,
                  false,
                  true
               )
            );
            entity.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
                  (Integer)CONFIG.reviveHakiCoatTicks.get(),
                  (Integer)CONFIG.reviveHakiCoatAmplifier.get(),
                  true,
                  false,
                  true
               )
            );
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            existence.setSpiritualHealth(
               Math.max(
                  existence.getSpiritualHealth(),
                  entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * (Double)CONFIG.reviveSpiritualHealthRatio.get()
               )
            );
            existence.setAura(Math.max(existence.getAura(), entity.getAttributeValue(TensuraAttributes.MAX_AURA) * (Double)CONFIG.reviveAuraRatio.get()));
            existence.setMagicule(
               Math.max(existence.getMagicule(), entity.getAttributeValue(TensuraAttributes.MAX_MAGICULE) * (Double)CONFIG.reviveMagiculeRatio.get())
            );
            existence.markDirty();
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.skill.witch_of_vainglory.rewritten")
                     .withStyle(new ChatFormatting[]{ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD}),
                  false
               );
            }

            entity.level().playSound(null, entity.blockPosition(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.3F, 1.0F);
            level.sendParticles(ParticleTypes.PORTAL, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 32, 0.7, 0.9, 0.7, 0.06);
            level.sendParticles(ParticleTypes.ENCHANT, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ(), 20, 0.5, 0.7, 0.5, 0.04);
            instance.addMasteryPoint(entity);
            instance.setCoolDown((Integer)CONFIG.rewrittenCooldownTicks.get(), 0);
            instance.setCoolDown((Integer)CONFIG.rewrittenCooldownTicks.get(), 1);
            instance.setCoolDown((Integer)CONFIG.rewrittenCooldownTicks.get(), 2);
            instance.setCoolDown((Integer)CONFIG.rewrittenCooldownTicks.get(), 3);
            instance.markDirty();
            return false;
         }
      }
   }

   @NotNull
   public List<Holder<MobEffect>> getImmuneEffects(ManasSkillInstance instance, LivingEntity entity) {
      List<Holder<MobEffect>> list = new ArrayList<>();
      CompoundTag root = instance.getOrCreateTag().getCompound("WitchOfVainglory");
      if (root.getInt("RealityCoatTicks") <= 0) {
         return list;
      }

      list.addAll(new AbnormalConditionNullification().getImmuneEffects(instance, entity));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_POISON));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE));
      list.add(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE));
      return list;
   }
}
