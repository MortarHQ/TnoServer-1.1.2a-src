package com.foxablazeultimate.item;

import com.foxablazeultimate.config.FoxAblazeUltimateConfig;
import com.foxablazeultimate.registry.LegacySkillIds;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public final class CrystalLockState {
   private static final String ROOT_TAG = "foxablazeultimate";
   private static final String EATEN_BY_SKILL = "crystal_eaten";
   private static final String LOCKED_KEY = "wisdom_crystal_locked";
   private static final String LEGACY_WISDOM_EATEN = "wisdom_eaten";
   private static final String WISDOM_EASTER_EGG_EATEN = "wisdom_easter_egg_eaten";
   private static final String WISDOM_EASTER_EGG_SHOWN = "wisdom_easter_egg_shown";
   private static final ResourceLocation LEGACY_RAPHAEL_ID = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "raphael");
   public static final int CRYSTAL_LOCK_THRESHOLD = 10;
   public static final int WISDOM_EASTER_EGG_THRESHOLD = 22;

   private CrystalLockState() {
   }

   public static int incrementEaten(Player player, ResourceLocation skillId) {
      if (FoxAblazeUltimateConfig.disableCrystalSkillLocking(skillId)) {
         return getEaten(player, skillId);
      }

      CompoundTag sub = subTag(player);
      migrateLegacyIfNeeded(sub);
      CompoundTag eatenMap = sub.getCompound("crystal_eaten");
      int next = eatenMap.getInt(skillId.toString()) + 1;
      eatenMap.putInt(skillId.toString(), next);
      sub.put("crystal_eaten", eatenMap);
      return next;
   }

   public static int getEaten(Player player, ResourceLocation skillId) {
      CompoundTag sub = subTag(player);
      migrateLegacyIfNeeded(sub);
      return sub.getCompound("crystal_eaten").getInt(skillId.toString());
   }

   public static boolean recordWisdomCrystalForEasterEgg(Player player) {
      CompoundTag sub = subTag(player);
      if (sub.getBoolean("wisdom_easter_egg_shown")) {
         return false;
      }

      int eaten = Math.max(0, sub.getInt("wisdom_easter_egg_eaten"));
      int next = Math.min(22, eaten + 1);
      sub.putInt("wisdom_easter_egg_eaten", next);
      if (next < 22) {
         return false;
      }

      sub.putBoolean("wisdom_easter_egg_shown", true);
      return true;
   }

   public static boolean addCrystalLock(Player player, ResourceLocation skillId) {
      if (FoxAblazeUltimateConfig.disableCrystalSkillLocking(skillId)) {
         return false;
      }

      CompoundTag sub = subTag(player);
      ListTag list = sub.getList("wisdom_crystal_locked", 8);
      String s = skillId.toString();

      for (int i = 0; i < list.size(); i++) {
         if (s.equals(list.getString(i))) {
            return false;
         }
      }

      list.add(StringTag.valueOf(s));
      sub.put("wisdom_crystal_locked", list);
      return true;
   }

   public static boolean isCrystalLocked(ServerPlayer player, ResourceLocation skillId) {
      CompoundTag sub = subTag(player);
      if (!sub.contains("wisdom_crystal_locked")) {
         return false;
      }

      ListTag list = sub.getList("wisdom_crystal_locked", 8);
      String s = skillId.toString();

      for (int i = 0; i < list.size(); i++) {
         if (s.equals(list.getString(i))) {
            return true;
         }
      }

      return false;
   }

   public static boolean removeCrystalLock(Player player, ResourceLocation skillId) {
      CompoundTag sub = subTag(player);
      String skillKey = skillId.toString();
      boolean changed = false;
      ListTag lockedSkills = sub.getList("wisdom_crystal_locked", 8);

      for (int index = lockedSkills.size() - 1; index >= 0; index--) {
         if (skillKey.equals(lockedSkills.getString(index))) {
            lockedSkills.remove(index);
            changed = true;
         }
      }

      if (changed) {
         sub.put("wisdom_crystal_locked", lockedSkills);
      }

      CompoundTag eatenMap = sub.getCompound("crystal_eaten");
      if (eatenMap.contains(skillKey)) {
         eatenMap.remove(skillKey);
         sub.put("crystal_eaten", eatenMap);
         changed = true;
      }

      return changed;
   }

   public static boolean migrateSkillState(Player player, ResourceLocation from, ResourceLocation to) {
      if (from != null && to != null && !from.equals(to)) {
         CompoundTag sub = subTag(player);
         migrateLegacyIfNeeded(sub);
         String fromKey = from.toString();
         String toKey = to.toString();
         boolean changed = false;
         CompoundTag eatenMap = sub.getCompound("crystal_eaten");
         int migratedCount = Math.max(0, eatenMap.getInt(toKey));
         if (eatenMap.contains(fromKey)) {
            long combined = migratedCount + Math.max(0L, eatenMap.getInt(fromKey));
            migratedCount = (int)Math.min(10L, combined);
            eatenMap.putInt(toKey, migratedCount);
            eatenMap.remove(fromKey);
            sub.put("crystal_eaten", eatenMap);
            changed = true;
         }

         ListTag lockedSkills = sub.getList("wisdom_crystal_locked", 8);
         boolean fromLocked = false;
         boolean toLocked = false;

         for (int index = lockedSkills.size() - 1; index >= 0; index--) {
            String value = lockedSkills.getString(index);
            if (fromKey.equals(value)) {
               lockedSkills.remove(index);
               fromLocked = true;
               changed = true;
            } else if (toKey.equals(value)) {
               toLocked = true;
            }
         }

         boolean reachedThreshold = migratedCount >= 10 && !FoxAblazeUltimateConfig.disableCrystalSkillLocking(to);
         if ((fromLocked || reachedThreshold) && !toLocked) {
            lockedSkills.add(StringTag.valueOf(toKey));
            changed = true;
         }

         if (changed) {
            sub.put("wisdom_crystal_locked", lockedSkills);
         }

         return changed;
      } else {
         return false;
      }
   }

   public static void copyToClone(Player original, Player clone) {
      CompoundTag originalRoot = original.getPersistentData();
      if (originalRoot.contains("foxablazeultimate", 10)) {
         CompoundTag originalSub = originalRoot.getCompound("foxablazeultimate");
         CompoundTag cloneSub = subTag(clone);
         if (originalSub.contains("crystal_eaten", 10)) {
            cloneSub.put("crystal_eaten", originalSub.getCompound("crystal_eaten").copy());
         }

         if (originalSub.contains("wisdom_crystal_locked", 9)) {
            cloneSub.put("wisdom_crystal_locked", originalSub.getList("wisdom_crystal_locked", 8).copy());
         }

         if (originalSub.contains("wisdom_eaten", 3)) {
            cloneSub.putInt("wisdom_eaten", originalSub.getInt("wisdom_eaten"));
         }

         if (originalSub.contains("wisdom_easter_egg_eaten", 3)) {
            cloneSub.putInt("wisdom_easter_egg_eaten", originalSub.getInt("wisdom_easter_egg_eaten"));
         }

         if (originalSub.contains("wisdom_easter_egg_shown", 1)) {
            cloneSub.putBoolean("wisdom_easter_egg_shown", originalSub.getBoolean("wisdom_easter_egg_shown"));
         }
      }
   }

   public static List<ResourceLocation> getLockedSkills(ServerPlayer player) {
      CompoundTag sub = subTag(player);
      if (!sub.contains("wisdom_crystal_locked")) {
         return List.of();
      }

      ListTag list = sub.getList("wisdom_crystal_locked", 8);
      List<ResourceLocation> out = new ArrayList<>(list.size());

      for (int i = 0; i < list.size(); i++) {
         ResourceLocation rl = ResourceLocation.tryParse(list.getString(i));
         if (rl != null) {
            out.add(rl);
         }
      }

      return out;
   }

   private static void migrateLegacyIfNeeded(CompoundTag sub) {
      if (sub.contains("wisdom_eaten")) {
         int legacy = sub.getInt("wisdom_eaten");
         sub.remove("wisdom_eaten");
         if (legacy > 0) {
            CompoundTag eatenMap = sub.getCompound("crystal_eaten");
            String key = LEGACY_RAPHAEL_ID.toString();
            int existing = eatenMap.getInt(key);
            eatenMap.putInt(key, Math.max(existing, legacy));
            sub.put("crystal_eaten", eatenMap);
         }
      }
   }

   private static CompoundTag subTag(Player player) {
      CompoundTag root = player.getPersistentData();
      CompoundTag sub;
      if (root.contains("foxablazeultimate")) {
         sub = root.getCompound("foxablazeultimate");
      } else {
         sub = new CompoundTag();
         root.put("foxablazeultimate", sub);
      }

      LegacySkillIds.migrateCrystalState(sub);
      return sub;
   }
}
