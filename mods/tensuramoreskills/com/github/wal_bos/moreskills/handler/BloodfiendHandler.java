package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.net.msg.BloodfiendDataSyncPayload;
import com.github.wal_bos.moreskills.registry.race.MoreskillsRaces;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.race.RaceHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class BloodfiendHandler {
   private static final DustParticleOptions BLOOD_DUST = new DustParticleOptions(new Vector3f(0.75F, 0.0F, 0.02F), 1.25F);
   private static final DustParticleOptions DARK_BLOOD_DUST = new DustParticleOptions(new Vector3f(0.35F, 0.0F, 0.015F), 1.0F);
   private static final ResourceLocation BLOODFIEND_RACE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "bloodfiend");
   private static final ResourceLocation ELDER_BLOODFIEND_RACE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "elder_bloodfiend");
   private static final ResourceLocation BLOOD_NOBLE_RACE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "blood_noble");
   private static final ResourceLocation BLOOD_MONARCH_RACE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "blood_monarch");
   private static final ResourceLocation CRIMSON_PROGENITOR_RACE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "crimson_progenitor");

   private BloodfiendHandler() {
   }

   @SubscribeEvent
   public static void onPlayerClone(Clone event) {
      if (event.getOriginal() instanceof ServerPlayer oldPlayer) {
         if (event.getEntity() instanceof ServerPlayer newPlayer) {
            BloodfiendData.copyTo(oldPlayer, newPlayer);
            if (isBloodfiend(newPlayer)) {
               BloodfiendData.markBloodfiend(newPlayer);
            }

            if (BloodfiendData.hasBloodfiendData(newPlayer)) {
               syncBlood(newPlayer);
               BloodfiendData.savePlayerProgress(newPlayer, true);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (BloodfiendData.hasBloodfiendData(player)) {
            syncBlood(player);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (BloodfiendData.hasBloodfiendData(player)) {
            BloodfiendData.savePlayerProgress(player, true);
         }
      }
   }

   @SubscribeEvent
   public static void onLivingTick(Post event) {
      if (event.getEntity() instanceof LivingEntity entity) {
         if (!entity.level().isClientSide()) {
            boolean hasData = BloodfiendData.hasBloodfiendData(entity);
            boolean bloodfiend = isBloodfiend(entity);
            if (hasData) {
               BloodfiendData.tickTimedData(entity);
            }

            if (!bloodfiend) {
               if (hasData && BloodfiendData.wasBloodfiend(entity)) {
                  clearBloodfiendBody(entity);
                  if (entity instanceof ServerPlayer serverPlayer) {
                     NetworkManager.sendToPlayer(serverPlayer, new BloodfiendDataSyncPayload(false, 0, 0, 0, 0, 0));
                  }
               }
            } else {
               BloodfiendData.markBloodfiend(entity);
               MoreskillsRaceConfig.Config config = MoreskillsRaceConfig.get();
               MoreskillsRaceConfig.Blood blood = config.Blood;
               int max = getMaxBlood(entity);
               if (BloodfiendData.getBlood(entity) > max) {
                  BloodfiendData.setBlood(entity, max);
               }

               if (entity instanceof Player player && (Integer)blood.hungerInterval.get() > 0 && entity.tickCount % (Integer)blood.hungerInterval.get() == 0) {
                  feedBloodfiend(player, blood);
               }

               if ((Integer)blood.passiveInterval.get() > 0 && entity.tickCount % (Integer)blood.passiveInterval.get() == 0) {
                  passiveBloodRegeneration(entity, blood);
               }

               if ((Integer)blood.masterBondInterval.get() > 0 && entity.tickCount % (Integer)blood.masterBondInterval.get() == 0) {
                  masterBondPulse(entity, blood);
               }

               if ((Integer)blood.waterCheckInterval.get() > 0 && entity.tickCount % (Integer)blood.waterCheckInterval.get() == 0) {
                  waterWeakness(entity, blood);
               }

               if (entity instanceof ServerPlayer serverPlayer && entity.tickCount % 20 == 0) {
                  syncBlood(serverPlayer);
               }
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      MoreskillsRaceConfig.BloodArts arts = MoreskillsRaceConfig.get().BloodArts;
      if (BloodfiendData.hasCrimsonGuard(target)) {
         float original = event.getAmount();
         if (original > 0.0F) {
            int blood = BloodfiendData.getBlood(target);
            if (blood <= 0) {
               BloodfiendData.setCrimsonGuard(target, 0);
               spawnGuardBreakParticles(target);
            } else {
               double bloodRatio = getBloodRatio(target);
               double power = getBloodArtPower(target);
               double baseReduction = Math.max(0.0, Math.min(0.9, 1.0 - (Double)arts.crimsonGuardDamageTakenMultiplier.get()));
               double reduction = baseReduction * (0.2 + bloodRatio * 0.8) * Math.min(1.35, power);
               reduction = Math.max(0.0, Math.min(0.85, reduction));
               float prevented = (float)(original * reduction);
               int wantedCost = getBloodShieldCost(target, prevented, bloodRatio, power);
               int paid = consumeBloodUpTo(target, wantedCost);
               float paidRatio = wantedCost <= 0 ? 1.0F : Math.min(1.0F, (float)paid / wantedCost);
               float realPrevented = prevented * paidRatio;
               event.setAmount(Math.max(0.0F, original - realPrevented));
               if (paid > 0 && paid >= wantedCost) {
                  spawnGuardParticles(target);
               } else {
                  BloodfiendData.setCrimsonGuard(target, 0);
                  spawnGuardBreakParticles(target);
               }

               if (target instanceof ServerPlayer serverPlayer) {
                  syncBlood(serverPlayer);
               }
            }
         }
      }

      if (event.getSource().getEntity() instanceof LivingEntity attacker) {
         if (isBloodfiend(attacker)) {
            if (SkillUtils.isSkillToggled(attacker, (ManasSkill)AllSkills.BLOOD_ARTS.get())) {
               float original = event.getAmount();
               if (!(original <= 0.0F)) {
                  int blood = BloodfiendData.getBlood(attacker);
                  if (blood > 0) {
                     double bloodRatio = getBloodRatio(attacker);
                     double power = getBloodArtPower(attacker);
                     double bonus = (Double)arts.bloodSurgeDamageMultiplier.get() * power * (0.15 + bloodRatio * 0.85);
                     bonus = Math.max(0.0, Math.min(2.5, bonus));
                     float bonusDamage = (float)(original * bonus);
                     int wantedCost = getBloodSurgeCost(attacker, bonusDamage, bloodRatio, power);
                     int paid = consumeBloodUpTo(attacker, wantedCost);
                     float paidRatio = wantedCost <= 0 ? 1.0F : Math.min(1.0F, (float)paid / wantedCost);
                     float realBoost = bonusDamage * paidRatio;
                     event.setAmount(original + realBoost);
                     if (paid > 0) {
                        spawnSurgeParticles(attacker);
                     }

                     if (attacker instanceof ServerPlayer serverPlayer) {
                        syncBlood(serverPlayer);
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean isBloodfiend(LivingEntity entity) {
      ResourceLocation race = getRaceId(entity);
      return BLOODFIEND_RACE.equals(race)
         || ELDER_BLOODFIEND_RACE.equals(race)
         || BLOOD_NOBLE_RACE.equals(race)
         || BLOOD_MONARCH_RACE.equals(race)
         || CRIMSON_PROGENITOR_RACE.equals(race);
   }

   private static ResourceLocation getRaceId(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> {
         ManasRace race = instance.getRace();
         return race == null ? null : race.getRegistryName();
      }).orElse(null);
   }

   public static int getMaxBlood(LivingEntity entity) {
      MoreskillsRaceConfig.Config config = MoreskillsRaceConfig.get();
      ResourceLocation race = getRaceId(entity);
      int base;
      if (CRIMSON_PROGENITOR_RACE.equals(race)) {
         base = (Integer)config.CrimsonProgenitor.maxBlood.get();
      } else if (BLOOD_MONARCH_RACE.equals(race)) {
         base = (Integer)config.BloodMonarch.maxBlood.get();
      } else if (BLOOD_NOBLE_RACE.equals(race)) {
         base = (Integer)config.BloodNoble.maxBlood.get();
      } else if (ELDER_BLOODFIEND_RACE.equals(race)) {
         base = (Integer)config.ElderBloodfiend.maxBlood.get();
      } else {
         base = (Integer)config.Bloodfiend.maxBlood.get();
      }

      int generation = BloodfiendData.getGenerationOrDefault(entity);
      double penalty = Math.max(0.0, generation - 1) * (Double)config.Blood.bloodCapacityPenaltyPerGeneration.get();
      double multiplier = Math.max((Double)config.Blood.minimumPowerMultiplier.get(), 1.0 - penalty);
      if (hasLivingBloodMaster(entity)) {
         multiplier *= config.Blood.masterBondBloodCapacityMultiplier.get();
      }

      return Math.max(20, (int)(base * multiplier));
   }

   public static double getBloodArtPower(LivingEntity entity) {
      MoreskillsRaceConfig.Blood config = MoreskillsRaceConfig.get().Blood;
      int generation = BloodfiendData.getGenerationOrDefault(entity);
      double penalty = Math.max(0.0, generation - 1) * (Double)config.bloodArtPenaltyPerGeneration.get();
      double power = Math.max((Double)config.minimumPowerMultiplier.get(), 1.0 - penalty);
      if (hasLivingBloodMaster(entity)) {
         power *= config.masterBondBloodArtMultiplier.get();
      }

      return power;
   }

   public static double getBloodRatio(LivingEntity entity) {
      int max = Math.max(1, getMaxBlood(entity));
      int blood = Math.max(0, BloodfiendData.getBlood(entity));
      return Math.max(0.0, Math.min(1.0, (double)blood / max));
   }

   public static int applyKindredCostReduction(LivingEntity entity, int cost) {
      if (cost <= 0) {
         return 0;
      }

      if (!hasLivingBloodMaster(entity)) {
         return cost;
      }

      double multiplier = (Double)MoreskillsRaceConfig.get().Blood.masterBondBloodCostMultiplier.get();
      return Math.max(1, (int)Math.ceil(cost * multiplier));
   }

   private static int getBloodShieldCost(LivingEntity entity, float preventedDamage, double bloodRatio, double power) {
      if (preventedDamage <= 0.0F) {
         return 0;
      }

      double maxBlood = Math.max(1.0, getMaxBlood(entity));
      double damagePerBlood = Math.max(8.0, maxBlood * (0.45 + bloodRatio * 0.35) * Math.max(0.25, power));
      int cost = (int)Math.ceil(preventedDamage / damagePerBlood);
      return Math.max(1, applyKindredCostReduction(entity, cost));
   }

   private static int getBloodSurgeCost(LivingEntity entity, float bonusDamage, double bloodRatio, double power) {
      if (bonusDamage <= 0.0F) {
         return 0;
      }

      double maxBlood = Math.max(1.0, getMaxBlood(entity));
      double damagePerBlood = Math.max(6.0, maxBlood * (0.25 + bloodRatio * 0.25) * Math.max(0.25, power));
      int cost = (int)Math.ceil(bonusDamage / damagePerBlood);
      return Math.max(1, applyKindredCostReduction(entity, cost));
   }

   private static int consumeBloodUpTo(LivingEntity entity, int wanted) {
      if (wanted <= 0) {
         return 0;
      }

      int blood = BloodfiendData.getBlood(entity);
      int paid = Math.max(0, Math.min(blood, wanted));
      if (paid > 0) {
         BloodfiendData.setBlood(entity, blood - paid);
      }

      return paid;
   }

   public static void syncBlood(ServerPlayer player) {
      NetworkManager.sendToPlayer(
         player,
         new BloodfiendDataSyncPayload(
            true,
            BloodfiendData.getBlood(player),
            getMaxBlood(player),
            BloodfiendData.getGenerationOrDefault(player),
            BloodfiendData.getTotalBloodDrained(player),
            BloodfiendData.getTotalThrallsTurned(player)
         )
      );
   }

   private static void feedBloodfiend(Player player, MoreskillsRaceConfig.Blood blood) {
      FoodData food = player.getFoodData();
      int stored = BloodfiendData.getBlood(player);
      if (stored > 0) {
         int minimum = (Integer)blood.minimumFoodLevelFromBlood.get();
         if (food.getFoodLevel() < minimum) {
            food.setFoodLevel(minimum);
         }

         if (food.getFoodLevel() < 20) {
            int cost = applyKindredCostReduction(player, (Integer)blood.hungerBloodCost.get());
            if (BloodfiendData.consumeBlood(player, cost)) {
               food.eat((Integer)blood.hungerFoodRestore.get(), ((Double)blood.hungerSaturationRestore.get()).floatValue());
               if (player instanceof ServerPlayer serverPlayer) {
                  syncBlood(serverPlayer);
               }
            }
         }
      }
   }

   private static void passiveBloodRegeneration(LivingEntity entity, MoreskillsRaceConfig.Blood blood) {
      if (BloodfiendData.getBlood(entity) >= (Integer)blood.passiveBloodCost.get()) {
         int threshold = getRegenThreshold(entity);
         if (BloodfiendData.getBlood(entity) >= threshold) {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            boolean needsHealth = entity.getHealth() < entity.getMaxHealth();
            boolean needsSpiritual = existence.getSpiritualHealth() < entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
            boolean needsMagicule = existence.getMagicule() < EnergyHelper.getMaxMagicule(entity);
            if (needsHealth || needsSpiritual || needsMagicule) {
               int cost = applyKindredCostReduction(entity, (Integer)blood.passiveBloodCost.get());
               if (BloodfiendData.consumeBlood(entity, cost)) {
                  double power = getBloodArtPower(entity);
                  if (needsHealth) {
                     entity.heal((float)((Double)blood.passiveHeal.get() * power));
                  }

                  if (needsSpiritual) {
                     existence.setSpiritualHealth(
                        Math.min(
                           entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH),
                           existence.getSpiritualHealth() + (Double)blood.passiveSpiritualHeal.get() * power
                        )
                     );
                  }

                  if (needsMagicule) {
                     existence.setMagicule(
                        Math.min(EnergyHelper.getMaxMagicule(entity), existence.getMagicule() + (Double)blood.passiveMagiculeRestore.get() * power)
                     );
                  }

                  existence.markDirty();
                  spawnRegenParticles(entity);
                  if (entity instanceof ServerPlayer serverPlayer) {
                     syncBlood(serverPlayer);
                  }
               }
            }
         }
      }
   }

   private static void masterBondPulse(LivingEntity entity, MoreskillsRaceConfig.Blood blood) {
      LivingEntity master = getLivingBloodMaster(entity).orElse(null);
      if (master != null) {
         if (!(entity.distanceTo(master) > (Double)blood.masterBondRange.get())) {
            if (BloodfiendData.getBlood(entity) < getMaxBlood(entity)) {
               BloodfiendData.addBlood(entity, (Integer)blood.masterBondBloodGain.get(), getMaxBlood(entity));
               spawnMasterBondParticles(master, entity);
               if (entity instanceof ServerPlayer serverPlayer) {
                  syncBlood(serverPlayer);
               }
            }
         }
      }
   }

   private static int getRegenThreshold(LivingEntity entity) {
      MoreskillsRaceConfig.Config config = MoreskillsRaceConfig.get();
      ResourceLocation race = getRaceId(entity);
      if (CRIMSON_PROGENITOR_RACE.equals(race)) {
         return (Integer)config.CrimsonProgenitor.bloodRegenThreshold.get();
      } else if (BLOOD_MONARCH_RACE.equals(race)) {
         return (Integer)config.BloodMonarch.bloodRegenThreshold.get();
      } else if (BLOOD_NOBLE_RACE.equals(race)) {
         return (Integer)config.BloodNoble.bloodRegenThreshold.get();
      } else {
         return ELDER_BLOODFIEND_RACE.equals(race)
            ? (Integer)config.ElderBloodfiend.bloodRegenThreshold.get()
            : (Integer)config.Bloodfiend.bloodRegenThreshold.get();
      }
   }

   private static void waterWeakness(LivingEntity entity, MoreskillsRaceConfig.Blood blood) {
      boolean water = entity.isInWaterOrBubble();
      boolean rain = entity.level() instanceof ServerLevel level && level.isRainingAt(entity.blockPosition());
      if (water || rain) {
         int generation = BloodfiendData.getGenerationOrDefault(entity);
         double multiplier = 1.0 + Math.max(0, generation - 1) * (Double)blood.waterWeaknessPerGeneration.get();
         if (hasLivingBloodMaster(entity)) {
            multiplier *= blood.masterBondWaterWeaknessMultiplier.get();
         }

         if (water) {
            BloodfiendData.consumeBlood(entity, Math.max(1, (int)(((Integer)blood.waterBloodDrain.get()).intValue() * multiplier)));
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            existence.setMagicule(Math.max(0.0, existence.getMagicule() - (Double)blood.waterMagiculeDrain.get() * multiplier));
            existence.markDirty();
            entity.hurt(entity.damageSources().source(DamageTypes.DROWN), (float)((Double)blood.waterDamage.get() * multiplier));
         } else {
            int rainDrain = Math.max(0, (int)(((Integer)blood.rainBloodDrain.get()).intValue() * multiplier));
            BloodfiendData.consumeBlood(entity, rainDrain);
            entity.hurt(entity.damageSources().source(DamageTypes.DROWN), (float)((Double)blood.rainDamage.get() * multiplier));
         }

         spawnWaterWeaknessParticles(entity);
         if (entity instanceof ServerPlayer serverPlayer) {
            syncBlood(serverPlayer);
         }
      }
   }

   public static boolean turnKindred(LivingEntity owner, LivingEntity target) {
      if (!isBloodfiend(owner)) {
         return false;
      } else {
         return target instanceof Player player ? turnPlayer(owner, player) : turnMob(owner, target);
      }
   }

   public static boolean turnMob(LivingEntity owner, LivingEntity target) {
      if (target instanceof Mob mob) {
         MoreskillsRaceConfig.BloodArts config = MoreskillsRaceConfig.get().BloodArts;
         if (!(Boolean)config.allowMobTurning.get()) {
            return false;
         }

         if (!canTurnByHealthOrEp(owner, target, (Double)config.mobTurnHealthThreshold.get())) {
            return false;
         }

         IExistence existence = TensuraStorages.getExistenceFrom(target);
         existence.setTemporaryOwner(owner.getUUID());
         existence.markDirty();
         mob.setTarget(null);
         BloodfiendData.addThrallTurn(owner);
         BloodfiendData.setGeneration(
            target, Math.min((Integer)MoreskillsRaceConfig.get().Blood.maxGeneration.get(), BloodfiendData.getGenerationOrDefault(owner) + 1)
         );
         BloodfiendData.setMaster(target, owner.getUUID());
         BloodfiendData.addBlood(owner, (Integer)config.kindredRiteSuccessBloodGain.get(), getMaxBlood(owner));
         if (owner instanceof ServerPlayer serverPlayer) {
            syncBlood(serverPlayer);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean turnPlayer(LivingEntity owner, Player target) {
      MoreskillsRaceConfig.BloodArts config = MoreskillsRaceConfig.get().BloodArts;
      if (!(Boolean)config.allowPlayerTurning.get()) {
         if (owner instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensuramoreskills.message.bloodfiend.player_turning_locked").withStyle(ChatFormatting.RED), true
            );
         }

         return false;
      } else {
         if (isBloodfiend(target)) {
            return false;
         }

         if (!canTurnByHealthOrEp(owner, target, (Double)config.playerTurnHealthThreshold.get())) {
            return false;
         }

         if (target.isShiftKeyDown() && BloodfiendData.hasTurningConsent(target, owner)) {
            boolean changed = RaceHelper.evolveRace(target, (ManasRace)MoreskillsRaces.BLOODFIEND.get(), true, true);
            if (!changed && !isBloodfiend(target)) {
               RaceAPI.getRaceFrom(target).setRace(((ManasRace)MoreskillsRaces.BLOODFIEND.get()).createDefaultInstance(), false, false, null);
               RaceAPI.getRaceFrom(target).markDirty();
            }

            BloodfiendData.markBloodfiend(target);
            BloodfiendData.setGeneration(
               target, Math.min((Integer)MoreskillsRaceConfig.get().Blood.maxGeneration.get(), BloodfiendData.getGenerationOrDefault(owner) + 1)
            );
            BloodfiendData.setMaster(target, owner.getUUID());
            BloodfiendData.addBlood(target, Math.max(1, getMaxBlood(target) / 2), getMaxBlood(target));
            BloodfiendData.addThrallTurn(owner);
            BloodfiendData.addBlood(owner, (Integer)config.kindredRiteSuccessBloodGain.get(), getMaxBlood(owner));
            IExistence existence = TensuraStorages.getExistenceFrom(target);
            existence.setPermanentOwner(owner.getUUID());
            if (existence.getAlignment().equals(Alignment.DEFAULT)) {
               existence.setAlignment(Alignment.MAJIN);
            }

            if (existence.getOriginalAlignment().equals(Alignment.DEFAULT)) {
               existence.setOriginalAlignment(Alignment.MAJIN);
            }

            existence.markDirty();
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 0.7F, 1.35F);
            spawnKindredParticles(owner, target);
            if (target instanceof ServerPlayer serverPlayer) {
               syncBlood(serverPlayer);
            }

            if (owner instanceof ServerPlayer serverPlayer) {
               syncBlood(serverPlayer);
            }

            return isBloodfiend(target);
         } else {
            BloodfiendData.requestTurningConsent(target, owner, (Integer)config.playerTurnConsentTicks.get());
            if (owner instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensuramoreskills.message.bloodfiend.player_turning_requires_consent").withStyle(ChatFormatting.RED), true
               );
            }

            target.displayClientMessage(
               Component.translatable("tensuramoreskills.message.bloodfiend.player_turning_consent_hint").withStyle(ChatFormatting.RED), true
            );
            return false;
         }
      }
   }

   private static boolean canTurnByHealthOrEp(LivingEntity owner, LivingEntity target, double healthThreshold) {
      MoreskillsRaceConfig.BloodArts config = MoreskillsRaceConfig.get().BloodArts;
      if (target.getHealth() <= target.getMaxHealth() * healthThreshold) {
         return true;
      }

      double ownerEp = EnergyHelper.getMaxEP(owner);
      double targetEp = EnergyHelper.getMaxEP(target);
      return ownerEp > 0.0 && targetEp <= ownerEp * (Double)config.instantTurnEpRatio.get();
   }

   private static boolean hasLivingBloodMaster(LivingEntity entity) {
      return getLivingBloodMaster(entity).isPresent();
   }

   private static Optional<LivingEntity> getLivingBloodMaster(LivingEntity entity) {
      Optional<UUID> uuid = BloodfiendData.getMaster(entity);
      if (uuid.isEmpty()) {
         return Optional.empty();
      }

      if (entity.level() instanceof ServerLevel level) {
         if (level.getEntity(uuid.get()) instanceof LivingEntity master) {
            return master.isAlive() && isBloodfiend(master) ? Optional.of(master) : Optional.empty();
         } else {
            return Optional.empty();
         }
      } else {
         return Optional.empty();
      }
   }

   private static void clearBloodfiendBody(LivingEntity entity) {
      Optional<UUID> master = BloodfiendData.getMaster(entity);
      if (master.isPresent()) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         if (master.get().equals(existence.getPermanentOwner())) {
            existence.setPermanentOwner(null);
            existence.markDirty();
         }
      }

      BloodfiendData.clearRaceDataAfterRaceLoss(entity);
   }

   public static void spawnDrainParticles(LivingEntity owner, LivingEntity target) {
      if (owner.level() instanceof ServerLevel level) {
         Vec3 var10 = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
         Vec3 to = owner.position().add(0.0, owner.getBbHeight() * 0.55, 0.0);
         Vec3 step = to.subtract(var10).scale(0.071);

         for (int i = 0; i < 15; i++) {
            Vec3 pos = var10.add(step.scale(i));
            double wave = Math.sin((owner.tickCount + i) * 0.55) * 0.1;
            level.sendParticles(BLOOD_DUST, pos.x + wave, pos.y, pos.z - wave, 2, 0.025, 0.025, 0.025, 0.0);
            if (i % 2 == 0) {
               level.sendParticles(DARK_BLOOD_DUST, pos.x - wave, pos.y + 0.03, pos.z + wave, 1, 0.015, 0.015, 0.015, 0.0);
            }

            if (i % 3 == 0) {
               level.sendParticles(ParticleTypes.CRIMSON_SPORE, pos.x, pos.y, pos.z, 2, 0.025, 0.025, 0.025, 0.01);
            }
         }

         level.sendParticles(
            BLOOD_DUST,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.55,
            target.getZ(),
            14,
            target.getBbWidth() * 0.35,
            target.getBbHeight() * 0.3,
            target.getBbWidth() * 0.35,
            0.02
         );
         level.sendParticles(
            ParticleTypes.DAMAGE_INDICATOR,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.75,
            target.getZ(),
            3,
            target.getBbWidth() * 0.25,
            target.getBbHeight() * 0.15,
            target.getBbWidth() * 0.25,
            0.01
         );
      }
   }

   public static void spawnGuardParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         double var16 = getBloodRatio(entity);
         int rings = 22 + (int)(var16 * 18.0);

         for (int i = 0; i < rings; i++) {
            double angle = i * Math.PI * 2.0 / rings;
            double radius = entity.getBbWidth() * (0.9 + var16 * 0.45);
            double x = entity.getX() + Math.cos(angle) * radius;
            double z = entity.getZ() + Math.sin(angle) * radius;
            double y = entity.getY() + 0.1 + entity.getRandom().nextDouble() * entity.getBbHeight();
            level.sendParticles(BLOOD_DUST, x, y, z, 1, 0.02, 0.08, 0.02, 0.0);
         }

         level.sendParticles(
            ParticleTypes.CRIMSON_SPORE,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            18,
            entity.getBbWidth() * 0.55,
            entity.getBbHeight() * 0.35,
            entity.getBbWidth() * 0.55,
            0.025
         );
         level.sendParticles(
            ParticleTypes.DAMAGE_INDICATOR,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.75,
            entity.getZ(),
            4,
            entity.getBbWidth() * 0.35,
            entity.getBbHeight() * 0.2,
            entity.getBbWidth() * 0.35,
            0.01
         );
      }
   }

   public static void spawnBrandParticles(LivingEntity owner, LivingEntity target) {
      if (target.level() instanceof ServerLevel level) {
         for (int var15 = 0; var15 < 2; var15++) {
            double y = target.getY() + target.getBbHeight() * (0.45 + var15 * 0.2);
            double radius = target.getBbWidth() * (0.75 + var15 * 0.25);

            for (int i = 0; i < 32; i++) {
               double angle = i * Math.PI * 2.0 / 32.0 + var15 * 0.25;
               double x = target.getX() + Math.cos(angle) * radius;
               double z = target.getZ() + Math.sin(angle) * radius;
               level.sendParticles(BLOOD_DUST, x, y, z, 1, 0.01, 0.03, 0.01, 0.0);
            }
         }

         level.sendParticles(
            DARK_BLOOD_DUST,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.65,
            target.getZ(),
            18,
            target.getBbWidth() * 0.35,
            target.getBbHeight() * 0.2,
            target.getBbWidth() * 0.35,
            0.0
         );
         level.sendParticles(
            ParticleTypes.CRIMSON_SPORE,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.65,
            target.getZ(),
            16,
            target.getBbWidth() * 0.45,
            target.getBbHeight() * 0.25,
            target.getBbWidth() * 0.45,
            0.025
         );
      }
   }

   public static void spawnKindredParticles(LivingEntity owner, LivingEntity target) {
      if (target.level() instanceof ServerLevel level) {
         for (int var15 = 0; var15 < 3; var15++) {
            double y = target.getY() + target.getBbHeight() * (0.25 + var15 * 0.25);
            double radius = target.getBbWidth() * (0.65 + var15 * 0.25);

            for (int i = 0; i < 24; i++) {
               double angle = i * Math.PI * 2.0 / 24.0 + var15 * 0.35;
               double x = target.getX() + Math.cos(angle) * radius;
               double z = target.getZ() + Math.sin(angle) * radius;
               level.sendParticles(BLOOD_DUST, x, y, z, 1, 0.01, 0.04, 0.01, 0.0);
            }
         }

         Vec3 from = owner.position().add(0.0, owner.getBbHeight() * 0.6, 0.0);
         Vec3 to = target.position().add(0.0, target.getBbHeight() * 0.6, 0.0);
         Vec3 step = to.subtract(from).scale(0.1);

         for (int i = 0; i < 10; i++) {
            Vec3 pos = from.add(step.scale(i));
            level.sendParticles(BLOOD_DUST, pos.x, pos.y, pos.z, 2, 0.015, 0.015, 0.015, 0.0);
         }

         level.sendParticles(
            ParticleTypes.DAMAGE_INDICATOR,
            target.getX(),
            target.getY() + target.getBbHeight(),
            target.getZ(),
            4,
            target.getBbWidth() * 0.25,
            target.getBbHeight() * 0.15,
            target.getBbWidth() * 0.25,
            0.01
         );
      }
   }

   private static void spawnSurgeParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         double var5 = getBloodRatio(entity);
         int count = 10 + (int)(var5 * 16.0);
         level.sendParticles(
            DARK_BLOOD_DUST,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            count,
            entity.getBbWidth() * 0.55,
            entity.getBbHeight() * 0.4,
            entity.getBbWidth() * 0.55,
            0.0
         );
         level.sendParticles(
            BLOOD_DUST,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.65,
            entity.getZ(),
            Math.max(4, count / 2),
            entity.getBbWidth() * 0.45,
            entity.getBbHeight() * 0.3,
            entity.getBbWidth() * 0.45,
            0.015
         );
         level.sendParticles(
            ParticleTypes.CRIMSON_SPORE,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ(),
            Math.max(4, count / 3),
            entity.getBbWidth() * 0.4,
            entity.getBbHeight() * 0.25,
            entity.getBbWidth() * 0.4,
            0.025
         );
      }
   }

   private static void spawnGuardBreakParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(
            DARK_BLOOD_DUST,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.5,
            entity.getZ(),
            28,
            entity.getBbWidth() * 0.75,
            entity.getBbHeight() * 0.45,
            entity.getBbWidth() * 0.75,
            0.03
         );
         level.sendParticles(
            ParticleTypes.CRIMSON_SPORE,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            20,
            entity.getBbWidth() * 0.65,
            entity.getBbHeight() * 0.35,
            entity.getBbWidth() * 0.65,
            0.04
         );
      }
   }

   private static void spawnRegenParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         for (int var13 = 0; var13 < 8; var13++) {
            double angle = entity.getRandom().nextDouble() * Math.PI * 2.0;
            double radius = entity.getBbWidth() * (0.3 + entity.getRandom().nextDouble() * 0.5);
            double x = entity.getX() + Math.cos(angle) * radius;
            double z = entity.getZ() + Math.sin(angle) * radius;
            double y = entity.getY() + entity.getRandom().nextDouble() * entity.getBbHeight();
            level.sendParticles(BLOOD_DUST, x, y, z, 1, 0.015, 0.04, 0.015, 0.0);
         }
      }
   }

   private static void spawnMasterBondParticles(LivingEntity master, LivingEntity kindred) {
      if (kindred.level() instanceof ServerLevel level) {
         Vec3 var8 = master.position().add(0.0, master.getBbHeight() * 0.65, 0.0);
         Vec3 to = kindred.position().add(0.0, kindred.getBbHeight() * 0.65, 0.0);
         Vec3 step = to.subtract(var8).scale(0.1);

         for (int i = 0; i < 10; i++) {
            Vec3 pos = var8.add(step.scale(i));
            level.sendParticles(DARK_BLOOD_DUST, pos.x, pos.y, pos.z, 1, 0.015, 0.015, 0.015, 0.0);
         }
      }
   }

   private static void spawnWaterWeaknessParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         for (int var9 = 0; var9 < 6; var9++) {
            double x = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();
            double y = entity.getY() + entity.getRandom().nextDouble() * entity.getBbHeight();
            double z = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();
            level.sendParticles(DARK_BLOOD_DUST, x, y, z, 1, 0.01, 0.015, 0.01, 0.0);
         }
      }
   }
}
