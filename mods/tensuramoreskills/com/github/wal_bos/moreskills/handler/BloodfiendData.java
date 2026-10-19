package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreskillsRaceConfig;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public final class BloodfiendData {
   private static final String ROOT = "MoreskillsBloodfiend";
   private static final String WAS_BLOODFIEND = "WasBloodfiend";
   private static final String BLOOD = "Blood";
   private static final String TOTAL_BLOOD = "TotalBloodDrained";
   private static final String TOTAL_THRALLS = "TotalThrallsTurned";
   private static final String GENERATION = "Generation";
   private static final String GUARD = "CrimsonGuard";
   private static final String GUARD_TIME = "CrimsonGuardTime";
   private static final String BRAND = "BloodBrand";
   private static final String BRAND_TIME = "BloodBrandTime";
   private static final String MASTER = "Master";
   private static final String CONSENT_OWNER = "ConsentOwner";
   private static final String CONSENT_TIME = "ConsentTime";
   private static final String SURGE_COST_TIME = "SurgeCostTime";
   private static final String LAST_PROGRESS_SAVE = "LastProgressSave";

   private BloodfiendData() {
   }

   public static boolean hasBloodfiendData(LivingEntity entity) {
      return entity.getPersistentData().contains("MoreskillsBloodfiend");
   }

   private static CompoundTag root(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      if (!persistent.contains("MoreskillsBloodfiend")) {
         persistent.put("MoreskillsBloodfiend", new CompoundTag());
      }

      return persistent.getCompound("MoreskillsBloodfiend");
   }

   private static Optional<CompoundTag> rootOptional(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      return !persistent.contains("MoreskillsBloodfiend") ? Optional.empty() : Optional.of(persistent.getCompound("MoreskillsBloodfiend"));
   }

   public static boolean wasBloodfiend(LivingEntity entity) {
      return rootOptional(entity).map(tag -> tag.getBoolean("WasBloodfiend")).orElse(false);
   }

   public static void markBloodfiend(LivingEntity entity) {
      root(entity).putBoolean("WasBloodfiend", true);
   }

   public static void clearRaceDataAfterRaceLoss(LivingEntity entity) {
      CompoundTag persistent = entity.getPersistentData();
      if (persistent.contains("MoreskillsBloodfiend")) {
         persistent.remove("MoreskillsBloodfiend");
         savePlayerProgress(entity, true);
      }
   }

   public static int getBlood(LivingEntity entity) {
      return rootOptional(entity).map(tag -> tag.getInt("Blood")).orElse(0);
   }

   public static void setBlood(LivingEntity entity, int value) {
      root(entity).putInt("Blood", Math.max(0, value));
   }

   public static void addBlood(LivingEntity entity, int value, int max) {
      if (value > 0) {
         CompoundTag tag = root(entity);
         int old = tag.getInt("Blood");
         int next = Math.min(max, old + value);
         tag.putInt("Blood", Math.max(0, next));
      }
   }

   public static boolean consumeBlood(LivingEntity entity, int value) {
      if (value <= 0) {
         return true;
      }

      CompoundTag tag = rootOptional(entity).orElse(null);
      if (tag == null) {
         return false;
      }

      int blood = tag.getInt("Blood");
      if (blood < value) {
         return false;
      }

      tag.putInt("Blood", blood - value);
      return true;
   }

   public static void addBloodDrainProgress(LivingEntity entity, int amount) {
      if (amount > 0) {
         CompoundTag tag = root(entity);
         tag.putInt("TotalBloodDrained", tag.getInt("TotalBloodDrained") + amount);
         savePlayerProgress(entity, false);
      }
   }

   public static int getTotalBloodDrained(LivingEntity entity) {
      return rootOptional(entity).map(tag -> tag.getInt("TotalBloodDrained")).orElse(0);
   }

   public static void addThrallTurn(LivingEntity entity) {
      CompoundTag tag = root(entity);
      tag.putInt("TotalThrallsTurned", tag.getInt("TotalThrallsTurned") + 1);
      savePlayerProgress(entity, true);
   }

   public static int getTotalThrallsTurned(LivingEntity entity) {
      return rootOptional(entity).map(tag -> tag.getInt("TotalThrallsTurned")).orElse(0);
   }

   public static void addMobTurn(LivingEntity entity) {
      addThrallTurn(entity);
   }

   public static int getTotalMobsTurned(LivingEntity entity) {
      return getTotalThrallsTurned(entity);
   }

   public static int getGeneration(LivingEntity entity) {
      CompoundTag tag = root(entity);
      int generation = tag.getInt("Generation");
      if (generation <= 0) {
         generation = (Integer)MoreskillsRaceConfig.get().Blood.defaultNaturalGeneration.get();
         tag.putInt("Generation", generation);
      }

      return generation;
   }

   public static int getGenerationOrDefault(LivingEntity entity) {
      CompoundTag tag = rootOptional(entity).orElse(null);
      if (tag == null) {
         return (Integer)MoreskillsRaceConfig.get().Blood.defaultNaturalGeneration.get();
      }

      int generation = tag.getInt("Generation");
      return generation <= 0 ? (Integer)MoreskillsRaceConfig.get().Blood.defaultNaturalGeneration.get() : generation;
   }

   public static void setGeneration(LivingEntity entity, int generation) {
      int max = (Integer)MoreskillsRaceConfig.get().Blood.maxGeneration.get();
      root(entity).putInt("Generation", Math.max(1, Math.min(max, generation)));
   }

   public static void setCrimsonGuard(LivingEntity entity, int ticks) {
      CompoundTag tag = root(entity);
      tag.putBoolean("CrimsonGuard", ticks > 0);
      tag.putInt("CrimsonGuardTime", Math.max(0, ticks));
   }

   public static boolean hasCrimsonGuard(LivingEntity entity) {
      CompoundTag tag = rootOptional(entity).orElse(null);
      return tag != null && tag.getBoolean("CrimsonGuard") && tag.getInt("CrimsonGuardTime") > 0;
   }

   public static void setBloodBrand(LivingEntity entity, int ticks) {
      CompoundTag tag = root(entity);
      tag.putBoolean("BloodBrand", ticks > 0);
      tag.putInt("BloodBrandTime", Math.max(0, ticks));
   }

   public static boolean hasBloodBrand(LivingEntity entity) {
      CompoundTag tag = rootOptional(entity).orElse(null);
      return tag != null && tag.getBoolean("BloodBrand") && tag.getInt("BloodBrandTime") > 0;
   }

   public static Optional<UUID> getMaster(LivingEntity entity) {
      CompoundTag tag = rootOptional(entity).orElse(null);
      return tag != null && tag.hasUUID("Master") ? Optional.of(tag.getUUID("Master")) : Optional.empty();
   }

   public static void setMaster(LivingEntity entity, UUID uuid) {
      root(entity).putUUID("Master", uuid);
   }

   public static void clearMaster(LivingEntity entity) {
      rootOptional(entity).ifPresent(tag -> tag.remove("Master"));
   }

   public static void requestTurningConsent(LivingEntity target, LivingEntity owner, int ticks) {
      CompoundTag tag = root(target);
      tag.putUUID("ConsentOwner", owner.getUUID());
      tag.putInt("ConsentTime", Math.max(1, ticks));
   }

   public static boolean hasTurningConsent(LivingEntity target, LivingEntity owner) {
      CompoundTag tag = rootOptional(target).orElse(null);
      return tag != null && tag.getInt("ConsentTime") > 0 && tag.hasUUID("ConsentOwner") && tag.getUUID("ConsentOwner").equals(owner.getUUID());
   }

   public static boolean canPaySurgeCost(LivingEntity entity, int interval) {
      return rootOptional(entity).map(tag -> tag.getInt("SurgeCostTime") <= 0).orElse(true);
   }

   public static void setSurgeCostTime(LivingEntity entity, int ticks) {
      root(entity).putInt("SurgeCostTime", Math.max(0, ticks));
   }

   public static void tickTimedData(LivingEntity entity) {
      CompoundTag tag = rootOptional(entity).orElse(null);
      if (tag != null) {
         int guard = tag.getInt("CrimsonGuardTime");
         if (guard > 0) {
            int next = guard - 1;
            tag.putInt("CrimsonGuardTime", next);
            if (next <= 0) {
               tag.putBoolean("CrimsonGuard", false);
            }
         }

         int brand = tag.getInt("BloodBrandTime");
         if (brand > 0) {
            int next = brand - 1;
            tag.putInt("BloodBrandTime", next);
            if (next <= 0) {
               tag.putBoolean("BloodBrand", false);
            }
         }

         int consent = tag.getInt("ConsentTime");
         if (consent > 0) {
            int next = consent - 1;
            tag.putInt("ConsentTime", next);
            if (next <= 0) {
               tag.remove("ConsentOwner");
            }
         }

         int surge = tag.getInt("SurgeCostTime");
         if (surge > 0) {
            tag.putInt("SurgeCostTime", surge - 1);
         }
      }
   }

   public static void copyTo(LivingEntity oldEntity, LivingEntity newEntity) {
      CompoundTag oldPersistent = oldEntity.getPersistentData();
      CompoundTag newPersistent = newEntity.getPersistentData();
      if (!oldPersistent.contains("MoreskillsBloodfiend")) {
         newPersistent.remove("MoreskillsBloodfiend");
      } else {
         newPersistent.put("MoreskillsBloodfiend", oldPersistent.getCompound("MoreskillsBloodfiend").copy());
      }
   }

   public static void savePlayerProgress(LivingEntity entity, boolean immediate) {
      CompoundTag tag = rootOptional(entity).orElse(null);
      if (tag != null) {
         int now = entity.tickCount;
         int last = tag.getInt("LastProgressSave");
         if (immediate || now - last >= 100) {
            tag.putInt("LastProgressSave", now);
         }
      }
   }

   public static void setClientSyncedProgress(LivingEntity entity, int totalBloodDrained, int totalThrallsTurned) {
      CompoundTag tag = root(entity);
      tag.putInt("TotalBloodDrained", Math.max(0, totalBloodDrained));
      tag.putInt("TotalThrallsTurned", Math.max(0, totalThrallsTurned));
   }

   public static void setClientSyncedGeneration(LivingEntity entity, int generation) {
      if (generation > 0) {
         root(entity).putInt("Generation", generation);
      }
   }

   public static void setClientSyncedBlood(LivingEntity entity, int blood) {
      root(entity).putInt("Blood", Math.max(0, blood));
   }
}
