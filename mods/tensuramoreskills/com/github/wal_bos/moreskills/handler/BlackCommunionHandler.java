package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import com.github.wal_bos.moreskills.net.msg.BlackCommunionVfxPayload;
import com.github.wal_bos.moreskills.registry.race.MoreskillsRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent.Post;
import org.joml.Vector3f;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class BlackCommunionHandler {
   private static final String ROOT = "MoreskillsBlackCommunion";
   private static final String ACTIVE = "Active";
   private static final String POWER = "Power";
   private static final String TIME = "Time";
   private static final String PREVENT_TIME = "PreventDeathTime";
   private static final String INFECTION_ROOT = "MoreskillsBlackInfection";
   private static final String INFECTION_STACKS = "Stacks";
   private static final String INFECTION_TIME = "Time";
   private static final String INFECTION_PULSE = "Pulse";
   private static final DustParticleOptions BLACK_BLOOD = new DustParticleOptions(new Vector3f(0.02F, 0.0F, 0.015F), 1.35F);
   private static final DustParticleOptions DARK_RED_BLOOD = new DustParticleOptions(new Vector3f(0.45F, 0.0F, 0.025F), 1.15F);

   private BlackCommunionHandler() {
   }

   @SubscribeEvent
   public static void onLivingTick(Post event) {
      if (event.getEntity() instanceof LivingEntity entity) {
         if (!entity.level().isClientSide()) {
            if (BlackBloodEcologyHandler.isConvertedBloodMinion(entity)) {
               entity.getPersistentData().remove("MoreskillsBlackInfection");
               BloodfiendData.setBloodBrand(entity, 0);
            } else {
               tickInfection(entity);
            }

            if (isCommunionActive(entity)) {
               if (!isCrimsonProgenitor(entity)) {
                  clearCommunion(entity);
               } else {
                  tickCommunion(entity);
               }
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onIncomingDamage(LivingIncomingDamageEvent event) {
      LivingEntity target = event.getEntity();
      if (!target.level().isClientSide()) {
         if (BlackBloodEcologyHandler.isConvertedBloodMinion(target)
            && event.getSource().getEntity() instanceof LivingEntity attacker
            && BlackBloodEcologyHandler.isBloodFaction(attacker)) {
            event.setAmount(0.0F);
            target.getPersistentData().remove("MoreskillsBlackInfection");
            BloodfiendData.setBloodBrand(target, 0);
         } else {
            float amount = event.getAmount();
            if (!(amount <= 0.0F)) {
               if (BloodfiendHandler.isBloodfiend(target)) {
                  LivingEntity progenitor = getLinkedActiveProgenitor(target);
                  if (progenitor != null) {
                     MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
                     double reduction = Math.max(
                        0.0,
                        Math.min(
                           (Double)cfg.maxDamageReduction.get(),
                           (Double)cfg.baseDamageReduction.get() + getPower(progenitor) * (Double)cfg.damageReductionPerPower.get()
                        )
                     );
                     event.setAmount((float)Math.max(0.0, amount * (1.0 - reduction)));
                     if (event.getAmount() >= target.getHealth() && canPreventDeath(target) && preventDeath(progenitor, target)) {
                        event.setAmount(0.0F);
                        return;
                     }
                  }
               }

               if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                  if (BloodfiendHandler.isBloodfiend(attacker)) {
                     LivingEntity progenitor = getLinkedActiveProgenitor(attacker);
                     if (progenitor != null) {
                        int stacks = getInfectionStacks(target);
                        MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
                        double powerBonus = Math.min((Double)cfg.maxDamageBoost.get(), getPower(progenitor) * (Double)cfg.damageBoostPerPower.get());
                        double infectionBonus = Math.min((Double)cfg.maxInfectionDamageBoost.get(), stacks * (Double)cfg.infectionDamageBoostPerStack.get());
                        event.setAmount((float)(event.getAmount() * (1.0 + powerBonus + infectionBonus)));
                        if (stacks > 0 && event.getAmount() >= target.getHealth()) {
                           spreadInfection(target, (Double)cfg.deathSpreadRadius.get(), Math.max(1, stacks / 2));
                           feedLinkedBloodfiends(progenitor, (Integer)cfg.killBloodGain.get(), (Double)cfg.killHeal.get());
                           addPower(progenitor, (Double)cfg.killPowerGain.get());
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean start(LivingEntity caster) {
      if (caster.level() instanceof ServerLevel level) {
         if (!isCrimsonProgenitor(caster)) {
            return false;
         }

         if (isCommunionActive(caster)) {
            return true;
         }

         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         if (!(Boolean)cfg.enabled.get()) {
            return false;
         }

         int cost = (Integer)cfg.activationBloodCost.get();
         if (!BloodfiendData.consumeBlood(caster, cost)) {
            return false;
         }

         CompoundTag tag = root(caster);
         tag.putBoolean("Active", true);
         tag.putInt("Time", (Integer)cfg.duration.get());
         tag.putDouble("Power", 0.0);
         tag.putInt("PreventDeathTime", 0);
         spawnActivationParticles(caster);
         level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.2F, 0.65F);
         double radius = getRadius(caster);
         List<LivingEntity> linked = getLinkedBloodfiends(caster, radius);
         BlackCommunionVfxPayload.send(level, caster, linked, (float)radius);
         if (caster instanceof ServerPlayer serverPlayer) {
            BloodfiendHandler.syncBlood(serverPlayer);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isCrimsonProgenitor(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> instance.getRace() == MoreskillsRaces.CRIMSON_PROGENITOR.get()).orElse(false);
   }

   public static boolean isCommunionActive(LivingEntity entity) {
      CompoundTag tag = rootOptional(entity);
      return tag != null && tag.getBoolean("Active") && tag.getInt("Time") > 0;
   }

   public static void clearCommunion(LivingEntity entity) {
      CompoundTag tag = rootOptional(entity);
      if (tag != null) {
         tag.remove("Active");
         tag.remove("Power");
         tag.remove("Time");
         tag.remove("PreventDeathTime");
         if (tag.isEmpty()) {
            entity.getPersistentData().remove("MoreskillsBlackCommunion");
         }
      }
   }

   public static void collapse(LivingEntity caster) {
      if (caster.level() instanceof ServerLevel level) {
         if (isCrimsonProgenitor(caster)) {
            if (isCommunionActive(caster)) {
               MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
               double power = getPower(caster);
               double radius = getRadius(caster);
               List<LivingEntity> linked = getLinkedBloodfiends(caster, radius);
               float damage = (float)Math.min(
                  (Double)cfg.collapseMaxDamage.get(), (Double)cfg.collapseBaseDamage.get() + power * (Double)cfg.collapseDamagePerPower.get()
               );

               for (LivingEntity entity : getLivingAround(caster, radius)) {
                  if (entity.isAlive() && entity != caster) {
                     if (BloodfiendHandler.isBloodfiend(entity)) {
                        restoreLinkedEntity(
                           entity,
                           (Double)cfg.collapseHeal.get(),
                           (Double)cfg.collapseSpiritualHeal.get(),
                           (Double)cfg.collapseMagiculeRestore.get(),
                           (Integer)cfg.collapseBloodRestore.get()
                        );
                     } else if (BlackBloodEcologyHandler.isConvertedBloodMinion(entity)) {
                        entity.getPersistentData().remove("MoreskillsBlackInfection");
                        BloodfiendData.setBloodBrand(entity, 0);
                        if (entity.getHealth() < entity.getMaxHealth()) {
                           entity.heal((float)Math.min(entity.getMaxHealth(), (Double)cfg.collapseHeal.get() * 0.25));
                        }
                     } else {
                        int stacks = getInfectionStacks(entity);
                        if (stacks > 0) {
                           BloodfiendData.setBloodBrand(entity, (Integer)cfg.collapseBrandDuration.get());
                           entity.hurt(
                              TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, caster), damage * (1.0F + stacks * 0.1F)
                           );
                           if (entity instanceof Mob
                              && entity.isAlive()
                              && entity.getHealth() <= entity.getMaxHealth() * (Double)cfg.collapseMobTurnHealthThreshold.get()) {
                              BloodfiendHandler.turnKindred(caster, entity);
                           }
                        } else {
                           entity.hurt(TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, caster), damage * 0.55F);
                        }
                     }
                  }
               }

               for (LivingEntity entity : linked) {
                  restoreLinkedEntity(
                     entity,
                     (Double)cfg.collapseHeal.get(),
                     (Double)cfg.collapseSpiritualHeal.get(),
                     (Double)cfg.collapseMagiculeRestore.get(),
                     (Integer)cfg.collapseBloodRestore.get()
                  );
                  if (entity instanceof ServerPlayer serverPlayer) {
                     BloodfiendHandler.syncBlood(serverPlayer);
                  }
               }

               spawnCollapseParticles(caster, radius);
               level.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.WITHER_DEATH, SoundSource.PLAYERS, 1.7F, 0.55F);
               clearCommunion(caster);
            }
         }
      }
   }

   private static void tickCommunion(LivingEntity caster) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      if (!(Boolean)cfg.enabled.get()) {
         clearCommunion(caster);
      } else {
         CompoundTag tag = root(caster);
         int time = tag.getInt("Time");
         if (time <= 0) {
            collapse(caster);
         } else {
            tag.putInt("Time", time - 1);
            int prevent = tag.getInt("PreventDeathTime");
            if (prevent > 0) {
               tag.putInt("PreventDeathTime", prevent - 1);
            }

            if (caster.tickCount % Math.max(1, (Integer)cfg.pulseInterval.get()) != 0) {
               if (caster.tickCount % 10 == 0) {
                  spawnAmbientParticles(caster, getRadius(caster));
               }
            } else {
               double radius = getRadius(caster);
               List<LivingEntity> linked = getLinkedBloodfiends(caster, radius);
               if (!linked.isEmpty()) {
                  double gainedPower = 0.0;

                  for (LivingEntity entity : linked) {
                     gainedPower += getNodePower(entity);
                     int offering = getOfferingCost(entity);
                     if (offering > 0) {
                        BloodfiendData.consumeBlood(entity, offering);
                     }
                  }

                  addPower(caster, gainedPower);
                  shareBlood(caster, linked);
                  pulseLinkedRestoration(linked);
                  infectEnemies(caster, radius);
                  spawnNetworkParticles(caster, linked);
                  BlackBloodDomainHandler.onCommunionPulse(caster, linked, radius, getPower(caster));

                  for (LivingEntity entity : linked) {
                     if (entity instanceof ServerPlayer serverPlayer) {
                        BloodfiendHandler.syncBlood(serverPlayer);
                     }
                  }
               }
            }
         }
      }
   }

   private static void tickInfection(LivingEntity entity) {
      if (BlackBloodEcologyHandler.isConvertedBloodMinion(entity)) {
         entity.getPersistentData().remove("MoreskillsBlackInfection");
         BloodfiendData.setBloodBrand(entity, 0);
      } else {
         CompoundTag tag = infectionOptional(entity);
         if (tag != null) {
            int time = tag.getInt("Time");
            if (time <= 0) {
               entity.getPersistentData().remove("MoreskillsBlackInfection");
            } else {
               tag.putInt("Time", time - 1);
               int pulse = tag.getInt("Pulse");
               if (pulse > 0) {
                  tag.putInt("Pulse", pulse - 1);
               } else {
                  MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
                  tag.putInt("Pulse", (Integer)cfg.infectionDamageInterval.get());
                  int stacks = Math.max(1, tag.getInt("Stacks"));
                  if (stacks > 0) {
                     BloodfiendData.setBloodBrand(entity, (Integer)cfg.infectionBrandDuration.get());
                     LivingEntity progenitor = getNearestActiveProgenitor(entity, (Double)cfg.maxRadius.get());
                     if (progenitor != null) {
                        float damage = (float)Math.min((Double)cfg.infectionMaxPulseDamage.get(), (Double)cfg.infectionPulseDamage.get() * stacks);
                        entity.hurt(TensuraDamageTypes.getEntityDamageSource(entity.level(), TensuraDamageTypes.MAGIC_GENERIC, progenitor), damage);
                        feedLinkedBloodfiends(progenitor, (Integer)cfg.infectionBloodLeak.get() * stacks, (Double)cfg.infectionHealLeak.get() * stacks);
                        spawnInfectionParticles(entity);
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean preventDeath(LivingEntity progenitor, LivingEntity dying) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      double radius = getRadius(progenitor);
      List<LivingEntity> linked = getLinkedBloodfiends(progenitor, radius);
      int cost = (Integer)cfg.preventDeathBloodCost.get();
      if (getNetworkBlood(linked) < cost) {
         return false;
      }

      drainNetworkBlood(linked, cost);
      float health = Math.max(1.0F, dying.getMaxHealth() * ((Double)cfg.preventDeathHealthRatio.get()).floatValue());
      dying.setHealth(Math.min(dying.getMaxHealth(), health));
      dying.invulnerableTime = (Integer)cfg.preventDeathInvulnerabilityTicks.get();
      IExistence existence = TensuraStorages.getExistenceFrom(dying);
      existence.setSpiritualHealth(
         Math.min(
            dying.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH), existence.getSpiritualHealth() + (Double)cfg.preventDeathSpiritualRestore.get()
         )
      );
      existence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(dying), existence.getMagicule() + (Double)cfg.preventDeathMagiculeRestore.get()));
      existence.markDirty();
      root(progenitor).putInt("PreventDeathTime", (Integer)cfg.preventDeathCooldownTicks.get());
      spreadInfection(dying, (Double)cfg.preventDeathInfectionRadius.get(), (Integer)cfg.preventDeathInfectionStacks.get());
      spawnPreventDeathParticles(dying);
      if (dying instanceof ServerPlayer serverPlayer) {
         BloodfiendHandler.syncBlood(serverPlayer);
      }

      for (LivingEntity entity : linked) {
         if (entity instanceof ServerPlayer serverPlayer) {
            BloodfiendHandler.syncBlood(serverPlayer);
         }
      }

      return true;
   }

   private static boolean canPreventDeath(LivingEntity entity) {
      LivingEntity progenitor = getLinkedActiveProgenitor(entity);
      return progenitor == null ? false : root(progenitor).getInt("PreventDeathTime") <= 0;
   }

   private static void infectEnemies(LivingEntity caster, double radius) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;

      for (LivingEntity entity : getLivingAround(caster, radius)) {
         if (entity.isAlive()
            && entity != caster
            && !BlackBloodEcologyHandler.isBloodFaction(entity)
            && (!RaceUtils.isBloodless(entity) || !(Boolean)cfg.skipBloodlessInfection.get())) {
            addInfection(entity, (Integer)cfg.infectionStacksPerPulse.get());
         }
      }
   }

   private static void spreadInfection(LivingEntity source, double radius, int stacks) {
      for (LivingEntity entity : getLivingAround(source, radius)) {
         if (entity.isAlive() && entity != source && !BlackBloodEcologyHandler.isBloodFaction(entity)) {
            addInfection(entity, stacks);
         }
      }
   }

   private static void addInfection(LivingEntity entity, int stacks) {
      if (stacks > 0 && !BlackBloodEcologyHandler.isBloodFaction(entity)) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         CompoundTag tag = infection(entity);
         int old = tag.getInt("Stacks");
         tag.putInt("Stacks", Math.min((Integer)cfg.maxInfectionStacks.get(), old + stacks));
         tag.putInt("Time", (Integer)cfg.infectionDuration.get());
         BloodfiendData.setBloodBrand(entity, (Integer)cfg.infectionBrandDuration.get());
         spawnInfectionParticles(entity);
      }
   }

   private static void shareBlood(LivingEntity caster, List<LivingEntity> linked) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      int transfer = (Integer)cfg.sharedBloodTransferPerPulse.get();
      if (transfer > 0) {
         linked.sort(Comparator.comparingInt(BloodfiendData::getBlood));

         for (LivingEntity needy : linked) {
            int max = BloodfiendHandler.getMaxBlood(needy);
            int blood = BloodfiendData.getBlood(needy);
            if (!(blood >= max * (Double)cfg.sharedBloodLowRatio.get())) {
               for (int i = linked.size() - 1; i >= 0; i--) {
                  LivingEntity donor = linked.get(i);
                  if (donor != needy) {
                     int donorBlood = BloodfiendData.getBlood(donor);
                     int donorMax = BloodfiendHandler.getMaxBlood(donor);
                     if (!(donorBlood <= donorMax * (Double)cfg.sharedBloodDonorMinRatio.get())) {
                        int moved = Math.min(transfer, donorBlood - (int)(donorMax * (Double)cfg.sharedBloodDonorMinRatio.get()));
                        if (moved > 0) {
                           BloodfiendData.setBlood(donor, donorBlood - moved);
                           BloodfiendData.addBlood(needy, moved, max);
                           break;
                        }
                     }
                  }
               }
            }
         }

         BloodfiendData.addBlood(caster, (Integer)cfg.progenitorBloodGainPerPulse.get(), BloodfiendHandler.getMaxBlood(caster));
      }
   }

   private static void pulseLinkedRestoration(List<LivingEntity> linked) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;

      for (LivingEntity entity : linked) {
         restoreLinkedEntity(
            entity,
            (Double)cfg.linkedHealPerPulse.get(),
            (Double)cfg.linkedSpiritualRestorePerPulse.get(),
            (Double)cfg.linkedMagiculeRestorePerPulse.get(),
            (Integer)cfg.linkedBloodGainPerPulse.get()
         );
      }
   }

   private static void restoreLinkedEntity(LivingEntity entity, double health, double spiritual, double magicule, int blood) {
      if (health > 0.0 && entity.getHealth() < entity.getMaxHealth()) {
         entity.heal((float)health);
      }

      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      if (spiritual > 0.0) {
         existence.setSpiritualHealth(Math.min(entity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH), existence.getSpiritualHealth() + spiritual));
      }

      if (magicule > 0.0) {
         existence.setMagicule(Math.min(EnergyHelper.getMaxMagicule(entity), existence.getMagicule() + magicule));
      }

      existence.markDirty();
      if (blood > 0) {
         BloodfiendData.addBlood(entity, blood, BloodfiendHandler.getMaxBlood(entity));
      }
   }

   private static void feedLinkedBloodfiends(LivingEntity progenitor, int blood, double heal) {
      if (blood > 0 || !(heal <= 0.0)) {
         for (LivingEntity entity : getLinkedBloodfiends(progenitor, getRadius(progenitor))) {
            if (blood > 0) {
               BloodfiendData.addBlood(entity, blood, BloodfiendHandler.getMaxBlood(entity));
            }

            if (heal > 0.0 && entity.getHealth() < entity.getMaxHealth()) {
               entity.heal((float)heal);
            }

            if (entity instanceof ServerPlayer serverPlayer) {
               BloodfiendHandler.syncBlood(serverPlayer);
            }
         }
      }
   }

   private static LivingEntity getLinkedActiveProgenitor(LivingEntity entity) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      return getNearestActiveProgenitor(entity, (Double)cfg.maxRadius.get());
   }

   private static LivingEntity getNearestActiveProgenitor(LivingEntity entity, double maxRadius) {
      if (isCrimsonProgenitor(entity) && isCommunionActive(entity)) {
         return entity;
      }

      LivingEntity best = null;
      double bestDistance = Double.MAX_VALUE;

      for (LivingEntity candidate : getLivingAround(entity, maxRadius)) {
         if (isCrimsonProgenitor(candidate) && isCommunionActive(candidate)) {
            double radius = getRadius(candidate);
            double distance = candidate.distanceToSqr(entity);
            if (distance <= radius * radius && distance < bestDistance) {
               best = candidate;
               bestDistance = distance;
            }
         }
      }

      return best;
   }

   private static List<LivingEntity> getLinkedBloodfiends(LivingEntity caster, double radius) {
      List<LivingEntity> list = new ArrayList<>();

      for (LivingEntity entity : getLivingAround(caster, radius)) {
         if (entity.isAlive() && BloodfiendHandler.isBloodfiend(entity)) {
            list.add(entity);
         }
      }

      if (!list.contains(caster)) {
         list.add(caster);
      }

      return list;
   }

   private static List<LivingEntity> getLivingAround(LivingEntity center, double radius) {
      if (center.level() instanceof ServerLevel level) {
         AABB var5 = center.getBoundingBox().inflate(radius);
         return level.getEntitiesOfClass(LivingEntity.class, var5, entity -> entity.isAlive() && entity.distanceToSqr(center) <= radius * radius);
      } else {
         return List.of();
      }
   }

   private static double getRadius(LivingEntity caster) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      return Math.min((Double)cfg.maxRadius.get(), (Double)cfg.baseRadius.get() + getPower(caster) * (Double)cfg.radiusPerPower.get());
   }

   private static double getNodePower(LivingEntity entity) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      double race = getRaceWeight(entity);
      int generation = BloodfiendData.getGenerationOrDefault(entity);
      double generationMultiplier = Math.max(
         (Double)cfg.minGenerationContribution.get(), 1.0 - Math.max(0, generation - 1) * (Double)cfg.generationContributionPenalty.get()
      );
      double bloodRatio = BloodfiendHandler.getBloodRatio(entity);
      double drained = Math.min(
         (Double)cfg.maxProgressContribution.get(), BloodfiendData.getTotalBloodDrained(entity) * (Double)cfg.bloodDrainedPowerMultiplier.get()
      );
      double thralls = Math.min(
         (Double)cfg.maxProgressContribution.get(), BloodfiendData.getTotalThrallsTurned(entity) * (Double)cfg.thrallPowerMultiplier.get()
      );
      return (
            race * generationMultiplier * ((Double)cfg.emptyBloodContribution.get() + bloodRatio * (Double)cfg.fullBloodContribution.get()) + drained + thralls
         )
         * (Double)cfg.powerGainMultiplier.get();
   }

   private static int getOfferingCost(LivingEntity entity) {
      MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
      return Math.max(0, (int)Math.ceil((Double)cfg.nodeOfferingBloodCost.get() * getRaceWeight(entity)));
   }

   private static double getRaceWeight(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> {
         ManasRace race = instance.getRace();
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         if (race == MoreskillsRaces.CRIMSON_PROGENITOR.get()) {
            return (Double)cfg.crimsonProgenitorWeight.get();
         } else if (race == MoreskillsRaces.BLOOD_MONARCH.get()) {
            return (Double)cfg.bloodMonarchWeight.get();
         } else if (race == MoreskillsRaces.BLOOD_NOBLE.get()) {
            return (Double)cfg.bloodNobleWeight.get();
         } else if (race == MoreskillsRaces.ELDER_BLOODFIEND.get()) {
            return (Double)cfg.elderBloodfiendWeight.get();
         } else {
            return race == MoreskillsRaces.BLOODFIEND.get() ? (Double)cfg.bloodfiendWeight.get() : 0.0;
         }
      }).orElse(0.0);
   }

   private static int getNetworkBlood(List<LivingEntity> linked) {
      int total = 0;

      for (LivingEntity entity : linked) {
         total += BloodfiendData.getBlood(entity);
      }

      return total;
   }

   private static void drainNetworkBlood(List<LivingEntity> linked, int cost) {
      int remaining = Math.max(0, cost);
      linked.sort(Comparator.comparingInt(BloodfiendData::getBlood).reversed());

      for (LivingEntity entity : linked) {
         if (remaining <= 0) {
            return;
         }

         int blood = BloodfiendData.getBlood(entity);
         int paid = Math.min(blood, remaining);
         if (paid > 0) {
            BloodfiendData.setBlood(entity, blood - paid);
            remaining -= paid;
         }
      }
   }

   private static void addPower(LivingEntity caster, double amount) {
      if (!(amount <= 0.0)) {
         MoreskillsRaceConfig.BlackCommunion cfg = MoreskillsRaceConfig.get().BlackCommunion;
         CompoundTag tag = root(caster);
         tag.putDouble("Power", Math.min((Double)cfg.maxPower.get(), tag.getDouble("Power") + amount));
      }
   }

   private static double getPower(LivingEntity caster) {
      CompoundTag tag = rootOptional(caster);
      return tag == null ? 0.0 : Math.max(0.0, tag.getDouble("Power"));
   }

   private static int getInfectionStacks(LivingEntity entity) {
      CompoundTag tag = infectionOptional(entity);
      return tag == null ? 0 : Math.max(0, tag.getInt("Stacks"));
   }

   private static CompoundTag root(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      if (!persistent.contains("MoreskillsBlackCommunion")) {
         persistent.put("MoreskillsBlackCommunion", new CompoundTag());
      }

      return persistent.getCompound("MoreskillsBlackCommunion");
   }

   private static CompoundTag rootOptional(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      return !persistent.contains("MoreskillsBlackCommunion") ? null : persistent.getCompound("MoreskillsBlackCommunion");
   }

   private static CompoundTag infection(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      if (!persistent.contains("MoreskillsBlackInfection")) {
         persistent.put("MoreskillsBlackInfection", new CompoundTag());
      }

      return persistent.getCompound("MoreskillsBlackInfection");
   }

   private static CompoundTag infectionOptional(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      return !persistent.contains("MoreskillsBlackInfection") ? null : persistent.getCompound("MoreskillsBlackInfection");
   }

   private static void spawnActivationParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(
            BLACK_BLOOD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            80,
            entity.getBbWidth() * 1.2,
            entity.getBbHeight() * 0.6,
            entity.getBbWidth() * 1.2,
            0.05
         );
         level.sendParticles(
            DARK_RED_BLOOD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.7,
            entity.getZ(),
            55,
            entity.getBbWidth() * 0.9,
            entity.getBbHeight() * 0.5,
            entity.getBbWidth() * 0.9,
            0.03
         );
         level.sendParticles(
            ParticleTypes.SCULK_SOUL,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.85,
            entity.getZ(),
            28,
            entity.getBbWidth() * 0.7,
            entity.getBbHeight() * 0.4,
            entity.getBbWidth() * 0.7,
            0.02
         );
      }
   }

   private static void spawnAmbientParticles(LivingEntity entity, double radius) {
      if (entity.level() instanceof ServerLevel level) {
         int var16 = Math.max(12, Math.min(80, (int)(radius * 1.2)));

         for (int i = 0; i < var16; i++) {
            double angle = entity.getRandom().nextDouble() * Math.PI * 2.0;
            double dist = entity.getRandom().nextDouble() * radius;
            double x = entity.getX() + Math.cos(angle) * dist;
            double z = entity.getZ() + Math.sin(angle) * dist;
            double y = entity.getY() + 0.05 + entity.getRandom().nextDouble() * 0.4;
            level.sendParticles(i % 2 == 0 ? BLACK_BLOOD : DARK_RED_BLOOD, x, y, z, 1, 0.01, 0.02, 0.01, 0.0);
         }
      }
   }

   private static void spawnNetworkParticles(LivingEntity caster, List<LivingEntity> linked) {
      if (caster.level() instanceof ServerLevel level) {
         Vec3 var10 = caster.position().add(0.0, caster.getBbHeight() * 0.65, 0.0);

         for (LivingEntity entity : linked) {
            if (entity != caster) {
               Vec3 from = entity.position().add(0.0, entity.getBbHeight() * 0.6, 0.0);
               Vec3 step = var10.subtract(from).scale(0.1);

               for (int i = 0; i < 10; i++) {
                  Vec3 pos = from.add(step.scale(i));
                  level.sendParticles(i % 2 == 0 ? BLACK_BLOOD : DARK_RED_BLOOD, pos.x, pos.y, pos.z, 1, 0.01, 0.01, 0.01, 0.0);
               }
            }
         }
      }
   }

   private static void spawnInfectionParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(
            BLACK_BLOOD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            8,
            entity.getBbWidth() * 0.45,
            entity.getBbHeight() * 0.25,
            entity.getBbWidth() * 0.45,
            0.01
         );
         level.sendParticles(
            ParticleTypes.SCULK_SOUL,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.65,
            entity.getZ(),
            2,
            entity.getBbWidth() * 0.25,
            entity.getBbHeight() * 0.15,
            entity.getBbWidth() * 0.25,
            0.0
         );
      }
   }

   private static void spawnPreventDeathParticles(LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         level.sendParticles(
            BLACK_BLOOD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            55,
            entity.getBbWidth() * 0.9,
            entity.getBbHeight() * 0.5,
            entity.getBbWidth() * 0.9,
            0.06
         );
         level.sendParticles(
            DARK_RED_BLOOD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.65,
            entity.getZ(),
            35,
            entity.getBbWidth() * 0.7,
            entity.getBbHeight() * 0.4,
            entity.getBbWidth() * 0.7,
            0.04
         );
         level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.45F);
      }
   }

   private static void spawnCollapseParticles(LivingEntity entity, double radius) {
      if (entity.level() instanceof ServerLevel level) {
         byte var12 = 96;

         for (int i = 0; i < var12; i++) {
            double angle = i * Math.PI * 2.0 / var12;
            double x = entity.getX() + Math.cos(angle) * radius;
            double z = entity.getZ() + Math.sin(angle) * radius;
            level.sendParticles(i % 2 == 0 ? BLACK_BLOOD : DARK_RED_BLOOD, x, entity.getY() + 0.15, z, 2, 0.02, 0.04, 0.02, 0.01);
         }

         level.sendParticles(
            BLACK_BLOOD,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            160,
            radius * 0.35,
            entity.getBbHeight() * 0.8,
            radius * 0.35,
            0.08
         );
         level.sendParticles(
            ParticleTypes.SCULK_SOUL,
            entity.getX(),
            entity.getY() + entity.getBbHeight(),
            entity.getZ(),
            70,
            radius * 0.25,
            entity.getBbHeight() * 0.7,
            radius * 0.25,
            0.04
         );
      }
   }
}
