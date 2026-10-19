package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.fungus.FungusInfectionProvider;
import com.github.wal_bos.moreskills.handler.AbhothPlagueHandler;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.monster.MetalSlimeEntity;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class AbhothLordOfPestilenceSkill extends Skill {
   private static final MoreSkillsConfigs.AbhothLordOfPestilenceConfig CONFIG = MoreSkillsConfigs.GRAND.abhothLordOfPestilence;
   public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "abhoth_lord_of_pestilence");
   public static final String TAG_ROOT = "tms_fungus_infection";
   private static final String TAG_EFFECTS = "effects";
   private static final String TAG_WORKSHOP = "workshop";
   private static final String TAG_LAST_AURA = "abhoth_last_ooze_aura";
   private static final String TAG_LAST_VISUAL = "abhoth_last_visual";
   private static final int SLOT_COUNT = 10;
   private static volatile Map<String, Holder<MobEffect>> EFFECT_BY_DESC = null;

   public AbhothLordOfPestilenceSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/wip.png");
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

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.epidemicSequencingSweepCost.get();
         case 1 -> CONFIG.mutationVaultCost.get();
         case 2 -> entity.isShiftKeyDown() ? (Double)CONFIG.fungalCatalystAscendedCost.get() : (Double)CONFIG.fungalCatalystCost.get();
         case 3 -> CONFIG.fleshyMiscreationsCost.get();
         default -> 0.0;
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? 3 : mode - 1;
      } else {
         return mode >= 3 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "abhoth_lord_of_pestilence.epidemic_sequencing_sweep";
         case 1 -> "abhoth_lord_of_pestilence.mutation_vault";
         case 2 -> "abhoth_lord_of_pestilence.fungal_catalyst";
         case 3 -> "abhoth_lord_of_pestilence.fleshy_miscreations";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            CompoundTag var10 = instance.getOrCreateTag();
            long now = level.getGameTime();
            if (now - var10.getLong("abhoth_last_visual") >= ((Integer)CONFIG.passiveVisualIntervalTicks.get()).intValue()) {
               var10.putLong("abhoth_last_visual", now);
               instance.markDirty();
               level.sendParticles(
                  ParticleTypes.ASH,
                  player.getX(),
                  player.getY() + 1.0,
                  player.getZ(),
                  (Integer)CONFIG.passiveAshParticles.get(),
                  (Double)CONFIG.passiveParticleXZ.get(),
                  (Double)CONFIG.passiveParticleY.get(),
                  (Double)CONFIG.passiveParticleXZ.get(),
                  (Double)CONFIG.passiveParticleSpeed.get()
               );
               level.sendParticles(
                  ParticleTypes.MYCELIUM,
                  player.getX(),
                  player.getY() + 0.35,
                  player.getZ(),
                  (Integer)CONFIG.passiveMyceliumParticles.get(),
                  (Double)CONFIG.passiveMyceliumXZ.get(),
                  (Double)CONFIG.passiveMyceliumY.get(),
                  (Double)CONFIG.passiveMyceliumXZ.get(),
                  (Double)CONFIG.passiveParticleSpeed.get()
               );
            }

            if (instance.isToggled()) {
               if (isInCombat(player)) {
                  if (now - var10.getLong("abhoth_last_ooze_aura") >= ((Integer)CONFIG.oozeFilmIntervalTicks.get()).intValue()) {
                     var10.putLong("abhoth_last_ooze_aura", now);
                     instance.markDirty();

                     for (int dx = -1; dx <= 1; dx++) {
                        for (int dz = -1; dz <= 1; dz++) {
                           AbhothPlagueHandler.placeFilm(
                              player, player.blockPosition().offset(dx, -1, dz), now + ((Integer)CONFIG.oozeFilmDurationTicks.get()).intValue()
                           );
                        }
                     }

                     level.sendParticles(
                        ParticleTypes.ASH,
                        player.getX(),
                        player.getY() + 0.05,
                        player.getZ(),
                        (Integer)CONFIG.oozeFilmAshParticles.get(),
                        (Double)CONFIG.oozeFilmParticleXZ.get(),
                        (Double)CONFIG.oozeFilmParticleY.get(),
                        (Double)CONFIG.oozeFilmParticleXZ.get(),
                        (Double)CONFIG.oozeFilmParticleSpeed.get()
                     );
                     level.playSound(
                        null,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        SoundEvents.SLIME_SQUISH_SMALL,
                        SoundSource.PLAYERS,
                        ((Double)CONFIG.oozeFilmSoundVolume.get()).floatValue(),
                        ((Double)CONFIG.oozeFilmSoundPitch.get()).floatValue()
                     );
                  }
               }
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            if (instance.onCoolDown(mode)) {
               player.displayClientMessage(Component.translatable("tensuramoreskills.skill.abhoth_lord_of_pestilence.cooldown"), true);
            } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               switch (mode) {
                  case 0:
                     boolean ok = epidemicSequencingSweep(player);
                     if (ok) {
                        this.addMasteryPoint(instance, entity);
                     }

                     instance.setCoolDown((Integer)CONFIG.epidemicSequencingSweepCooldownTicks.get(), mode);
                     break;
                  case 1:
                     FungusInfectionProvider.open(player);
                     level.sendParticles(ParticleTypes.MYCELIUM, player.getX(), player.getY() + 1.0, player.getZ(), 28, 0.8, 0.8, 0.8, 0.02);
                     instance.setCoolDown((Integer)CONFIG.mutationVaultCooldownTicks.get(), mode);
                     break;
                  case 2:
                     boolean crouch = player.isShiftKeyDown();
                     boolean ok = fungalCatalyst(player, crouch);
                     if (ok) {
                        this.addMasteryPoint(instance, entity);
                     }

                     instance.setCoolDown(
                        crouch ? (Integer)CONFIG.fungalCatalystAscendedCooldownTicks.get() : (Integer)CONFIG.fungalCatalystCooldownTicks.get(), mode
                     );
                     break;
                  case 3:
                     boolean ok = fleshyMiscreations(player);
                     if (ok) {
                        this.addMasteryPoint(instance, entity);
                     }

                     instance.setCoolDown((Integer)CONFIG.fleshyMiscreationsCooldownTicks.get(), mode);
               }
            }
         }
      }
   }

   private static boolean epidemicSequencingSweep(ServerPlayer player) {
      List<LivingEntity> targets;
      if (player.isShiftKeyDown()) {
         targets = List.of(player);
      } else {
         AABB box = player.getBoundingBox().inflate((Double)CONFIG.epidemicSequencingSweepRadius.get());
         targets = player.level().getEntitiesOfClass(LivingEntity.class, box, target -> target != player && target.isAlive());
      }

      if (targets.isEmpty()) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.abhoth_lord_of_pestilence.no_targets"));
         return false;
      }

      int gained = storeEffectsFromTargets(player, targets);
      if (gained <= 0) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.abhoth_lord_of_pestilence.no_effects"));
         return false;
      }

      if (player.level() instanceof ServerLevel level) {
         TensuraParticleHelper.addServerParticlesAroundPos(
            player.getRandom(),
            level,
            player.position().add(0.0, 1.0, 0.0),
            TensuraParticleUtils.getPoisonEffect((Integer)CONFIG.epidemicSequencingPoisonParticles.get()),
            (Double)CONFIG.epidemicSequencingParticleRadius.get()
         );
         level.sendParticles(
            ParticleTypes.ASH,
            player.getX(),
            player.getY() + 1.0,
            player.getZ(),
            (Integer)CONFIG.epidemicSequencingAshParticles.get(),
            (Double)CONFIG.epidemicSequencingAshRadius.get(),
            (Double)CONFIG.epidemicSequencingAshY.get(),
            (Double)CONFIG.epidemicSequencingAshRadius.get(),
            (Double)CONFIG.epidemicSequencingAshSpeed.get()
         );
      }

      player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.abhoth_lord_of_pestilence.sweep_success", new Object[]{gained}));
      return true;
   }

   private static int storeEffectsFromTargets(ServerPlayer player, List<LivingEntity> targets) {
      int gained = 0;
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag map = root.getCompound("effects");

      for (LivingEntity target : targets) {
         for (MobEffectInstance effect : target.getActiveEffects()) {
            String key = ((MobEffect)effect.getEffect().value()).getDescriptionId();
            map.putInt(key, map.getInt(key) + (Integer)CONFIG.epidemicSequencingPointsPerEffect.get());
            gained += CONFIG.epidemicSequencingPointsPerEffect.get();
         }
      }

      root.put("effects", map);
      player.getPersistentData().put("tms_fungus_infection", root);
      return gained;
   }

   private static boolean fungalCatalyst(ServerPlayer player, boolean crouch) {
      List<String> chosen = getWorkshopEffects(player);
      if (chosen.isEmpty()) {
         chosen = autoFillWorkshopFromStored(player);
      }

      if (chosen.isEmpty()) {
         player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.fungus_infection.no_workshop"));
         return false;
      }

      double radius = crouch ? (Double)CONFIG.fungalCatalystAscendedRadius.get() : (Double)CONFIG.fungalCatalystRadius.get();
      AABB box = player.getBoundingBox().inflate(radius);
      List<LivingEntity> targets = player.level().getEntitiesOfClass(LivingEntity.class, box, targetx -> targetx != player && targetx.isAlive());
      int applied = 0;
      if (crouch) {
         for (String key : chosen) {
            if (applyStoredEffect(
               player, player, key, (Integer)CONFIG.fungalCatalystAscendedEffectDurationTicks.get(), (Integer)CONFIG.fungalCatalystAscendedBonusAmplifier.get()
            )) {
               applied++;
            }
         }

         boostSelfEffects(player);
      }

      for (LivingEntity target : targets) {
         for (String key : chosen) {
            if (applyStoredEffect(
               player,
               target,
               key,
               crouch ? (Integer)CONFIG.fungalCatalystAscendedEffectDurationTicks.get() : (Integer)CONFIG.fungalCatalystEffectDurationTicks.get(),
               crouch ? (Integer)CONFIG.fungalCatalystAscendedBonusAmplifier.get() : (Integer)CONFIG.fungalCatalystBonusAmplifier.get()
            )) {
               applied++;
            }
         }

         AbhothPlagueHandler.infect(
            player,
            target,
            crouch ? (Integer)CONFIG.fungalCatalystAscendedInfectionDurationTicks.get() : (Integer)CONFIG.fungalCatalystInfectionDurationTicks.get(),
            true
         );
      }

      if (player.level() instanceof ServerLevel level) {
         level.sendParticles(
            ParticleTypes.ASH,
            player.getX(),
            player.getY() + 0.7,
            player.getZ(),
            crouch ? (Integer)CONFIG.fungalCatalystAscendedAshParticles.get() : (Integer)CONFIG.fungalCatalystAshParticles.get(),
            radius * (Double)CONFIG.fungalCatalystAshRadiusMultiplier.get(),
            (Double)CONFIG.fungalCatalystAshY.get(),
            radius * (Double)CONFIG.fungalCatalystAshRadiusMultiplier.get(),
            (Double)CONFIG.fungalCatalystAshSpeed.get()
         );
         level.sendParticles(
            ParticleTypes.MYCELIUM,
            player.getX(),
            player.getY() + 0.25,
            player.getZ(),
            crouch ? (Integer)CONFIG.fungalCatalystAscendedMyceliumParticles.get() : (Integer)CONFIG.fungalCatalystMyceliumParticles.get(),
            radius * (Double)CONFIG.fungalCatalystMyceliumRadiusMultiplier.get(),
            (Double)CONFIG.fungalCatalystMyceliumY.get(),
            radius * (Double)CONFIG.fungalCatalystMyceliumRadiusMultiplier.get(),
            (Double)CONFIG.fungalCatalystMyceliumSpeed.get()
         );
         level.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(),
            SoundSource.PLAYERS,
            crouch ? ((Double)CONFIG.fungalCatalystAscendedSoundVolume.get()).floatValue() : ((Double)CONFIG.fungalCatalystSoundVolume.get()).floatValue(),
            ((Double)CONFIG.fungalCatalystSoundPitch.get()).floatValue()
         );
      }

      player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.abhoth_lord_of_pestilence.catalyst_success", new Object[]{applied}));
      return applied > 0 || crouch;
   }

   private static boolean fleshyMiscreations(ServerPlayer player) {
      if (player.level() instanceof ServerLevel level) {
         if (player.getHealth() <= ((Double)CONFIG.fleshyMiscreationsMinimumHealth.get()).floatValue()) {
            player.sendSystemMessage(Component.translatable("tensuramoreskills.skill.abhoth_lord_of_pestilence.too_weak"));
            return false;
         }

         float sacrifice = Math.max(
            ((Double)CONFIG.fleshyMiscreationsMinimumSacrifice.get()).floatValue(),
            player.getHealth() * ((Double)CONFIG.fleshyMiscreationsHealthSacrificeRatio.get()).floatValue()
         );
         player.setHealth(Math.max(1.0F, player.getHealth() - sacrifice));

         for (int i = 0; i < CONFIG.fleshyMiscreationsSpawnCount.get(); i++) {
            MetalSlimeEntity slime = new MetalSlimeEntity((EntityType)MonsterEntityTypes.METAL_SLIME.get(), level);
            slime.moveTo(
               player.getX() + (i % 2 == 0 ? (Double)CONFIG.fleshyMiscreationsSpawnSpread.get() : -(Double)CONFIG.fleshyMiscreationsSpawnSpread.get()),
               player.getY(),
               player.getZ(),
               player.getYRot(),
               0.0F
            );
            slime.setSize((Integer)CONFIG.fleshyMiscreationsSlimeSize.get(), true);
            slime.setPersistenceRequired();
            slime.setCustomName(Component.translatable("tensuramoreskills.entity.abhoth_miscreation"));
            slime.getPersistentData().putUUID("tms_abhoth_owner", player.getUUID());
            slime.getPersistentData().putLong("tms_abhoth_expire", level.getGameTime() + ((Integer)CONFIG.fleshyMiscreationsLifetimeTicks.get()).intValue());
            slime.getPersistentData().putBoolean("tms_abhoth_miscreation", true);
            level.addFreshEntity(slime);
         }

         level.sendParticles(
            ParticleTypes.ITEM_SLIME,
            player.getX(),
            player.getY() + 0.7,
            player.getZ(),
            (Integer)CONFIG.fleshyMiscreationsParticles.get(),
            (Double)CONFIG.fleshyMiscreationsParticleXZ.get(),
            (Double)CONFIG.fleshyMiscreationsParticleY.get(),
            (Double)CONFIG.fleshyMiscreationsParticleXZ.get(),
            (Double)CONFIG.fleshyMiscreationsParticleSpeed.get()
         );
         level.playSound(
            null,
            player.getX(),
            player.getY(),
            player.getZ(),
            SoundEvents.SLIME_BLOCK_BREAK,
            SoundSource.PLAYERS,
            ((Double)CONFIG.fleshyMiscreationsSoundVolume.get()).floatValue(),
            ((Double)CONFIG.fleshyMiscreationsSoundPitch.get()).floatValue()
         );
         return true;
      } else {
         return false;
      }
   }

   public static boolean applyStoredEffect(ServerPlayer owner, LivingEntity target, String key, int baseDuration, int bonusAmplifier) {
      Holder<MobEffect> holder = resolveEffectByDescId(key);
      if (holder == null) {
         return false;
      }

      int durationLevel = getUpgradeDur(owner, key);
      int amplifierLevel = getUpgradeAmp(owner, key);
      int duration = Math.max(
         (Integer)CONFIG.storedEffectMinimumDurationTicks.get(),
         (int)(baseDuration * (1.0 + (Double)CONFIG.storedEffectDurationMultiplierPerUpgrade.get() * durationLevel))
      );
      int amplifier = Math.max(0, amplifierLevel + bonusAmplifier);
      target.addEffect(new MobEffectInstance(holder, duration, amplifier, false, false, true));
      return true;
   }

   public static List<String> getWorkshopEffects(ServerPlayer player) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag workshop = root.getCompound("workshop");
      List<String> out = new ArrayList<>();

      for (int i = 0; i < 10; i++) {
         String key = workshop.getString("e" + i);
         if (!key.isEmpty()) {
            out.add(key);
         }
      }

      return out;
   }

   private static List<String> autoFillWorkshopFromStored(ServerPlayer player) {
      CompoundTag root = getOrCreateRoot(player);
      CompoundTag effects = root.getCompound("effects");
      if (effects.isEmpty()) {
         return List.of();
      }

      List<String> keys = new ArrayList<>(effects.getAllKeys());
      keys.sort(String::compareToIgnoreCase);
      CompoundTag workshop = root.getCompound("workshop");
      List<String> out = new ArrayList<>();

      for (String key : keys) {
         if (out.size() >= 10) {
            break;
         }

         if (resolveEffectByDescId(key) != null) {
            workshop.putString("e" + out.size(), key);
            out.add(key);
         }
      }

      workshop.putInt("size", out.size());
      root.put("workshop", workshop);
      player.getPersistentData().put("tms_fungus_infection", root);
      return out;
   }

   private static void boostSelfEffects(ServerPlayer player) {
      for (MobEffectInstance effect : new ArrayList(player.getActiveEffects())) {
         if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.BENEFICIAL) {
            int duration = Math.max(
               effect.getDuration() + (Integer)CONFIG.ascendedSelfEffectExtensionTicks.get(), (Integer)CONFIG.ascendedSelfEffectMinimumDurationTicks.get()
            );
            int amplifier = Math.min(255, effect.getAmplifier() + (Integer)CONFIG.ascendedSelfEffectAmplifierBonus.get());
            player.addEffect(new MobEffectInstance(effect.getEffect(), duration, amplifier, effect.isAmbient(), effect.isVisible(), effect.showIcon()));
         }
      }

      player.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
            (Integer)CONFIG.ascendedSelfRegenerationDurationTicks.get(),
            (Integer)CONFIG.ascendedSelfRegenerationAmplifier.get(),
            false,
            false,
            true
         )
      );
      player.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.MAGICULE_REGENERATION),
            (Integer)CONFIG.ascendedMagiculeRegenerationDurationTicks.get(),
            (Integer)CONFIG.ascendedMagiculeRegenerationAmplifier.get(),
            false,
            false,
            true
         )
      );
      EnergyHelper.gainMagicule(player, (Double)CONFIG.ascendedMagiculeGain.get(), GainType.NORMAL);
   }

   private static boolean isInCombat(ServerPlayer player) {
      if (player.getLastHurtByMob() != null
         && player.getLastHurtByMob().isAlive()
         && player.distanceToSqr(player.getLastHurtByMob()) < (Double)CONFIG.combatRecentTargetMaxDistanceSqr.get()) {
         return true;
      }

      if (player.getLastHurtMob() != null
         && player.getLastHurtMob().isAlive()
         && player.distanceToSqr(player.getLastHurtMob()) < (Double)CONFIG.combatRecentTargetMaxDistanceSqr.get()) {
         return true;
      }

      AABB box = player.getBoundingBox().inflate((Double)CONFIG.combatNearbyRadius.get());
      return !player.level()
         .getEntitiesOfClass(LivingEntity.class, box, target -> target != player && target.isAlive() && !target.isAlliedTo(player))
         .isEmpty();
   }

   private static CompoundTag getOrCreateRoot(ServerPlayer player) {
      CompoundTag root = player.getPersistentData().getCompound("tms_fungus_infection");
      if (!root.contains("effects")) {
         root.put("effects", new CompoundTag());
      }

      if (!root.contains("workshop")) {
         root.put("workshop", new CompoundTag());
      }

      if (!root.contains("upgrades")) {
         root.put("upgrades", new CompoundTag());
      }

      if (!root.contains("spent")) {
         root.put("spent", new CompoundTag());
      }

      return root;
   }

   private static int getUpgradeDur(ServerPlayer player, String key) {
      CompoundTag root = getOrCreateRoot(player);
      return root.getCompound("upgrades").getCompound(key).getInt("dur");
   }

   private static int getUpgradeAmp(ServerPlayer player, String key) {
      CompoundTag root = getOrCreateRoot(player);
      return root.getCompound("upgrades").getCompound(key).getInt("amp");
   }

   private static Holder<MobEffect> resolveEffectByDescId(String descId) {
      if (descId != null && !descId.isEmpty()) {
         Map<String, Holder<MobEffect>> map = EFFECT_BY_DESC;
         if (map == null) {
            HashMap<String, Holder<MobEffect>> built = new HashMap<>();

            for (MobEffect effect : BuiltInRegistries.MOB_EFFECT) {
               Holder<MobEffect> holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
               built.put(effect.getDescriptionId(), holder);
               ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey(effect);
               if (id != null) {
                  built.put(id.toString(), holder);
               }
            }

            EFFECT_BY_DESC = built;
            map = built;
         }

         return map.get(descId);
      } else {
         return null;
      }
   }
}
