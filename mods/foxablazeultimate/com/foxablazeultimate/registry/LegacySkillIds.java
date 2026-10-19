package com.foxablazeultimate.registry;

import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;

public final class LegacySkillIds {
   public static final ResourceLocation BEELZEBUB = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "beelzebub");
   public static final ResourceLocation BEELZEBUTH = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "beelzebuth");

   private LegacySkillIds() {
   }

   public static void migrateUniqueOwnership(ITrulyUnique unique) {
      UUID previousOwner = unique.getOwner(BEELZEBUB);
      if (previousOwner != null) {
         UUID currentOwner = unique.getOwner(BEELZEBUTH);
         if (currentOwner == null || currentOwner.equals(previousOwner)) {
            if (currentOwner == null) {
               unique.addSkill(BEELZEBUTH, previousOwner);
            }

            unique.removeSkill(BEELZEBUB);
            unique.markDirty();
         }
      }
   }

   public static boolean hasConflictingLegacyOwner(ITrulyUnique unique, ResourceLocation skillId, UUID player) {
      return BEELZEBUTH.equals(skillId) && unique.getOwner(BEELZEBUB) != null && !player.equals(unique.getOwner(BEELZEBUB));
   }

   public static void migrateCrystalState(CompoundTag sub) {
      String oldId = BEELZEBUB.toString();
      String newId = BEELZEBUTH.toString();
      CompoundTag eaten = sub.getCompound("crystal_eaten");
      if (eaten.contains(oldId)) {
         eaten.putInt(newId, Math.max(eaten.getInt(oldId), eaten.getInt(newId)));
         eaten.remove(oldId);
         sub.put("crystal_eaten", eaten);
      }

      ListTag locked = sub.getList("wisdom_crystal_locked", 8);
      boolean hadOldLock = false;
      boolean hasNewLock = false;

      for (int index = locked.size() - 1; index >= 0; index--) {
         if (oldId.equals(locked.getString(index))) {
            locked.remove(index);
            hadOldLock = true;
         } else if (newId.equals(locked.getString(index))) {
            hasNewLock = true;
         }
      }

      if (hadOldLock) {
         if (!hasNewLock) {
            locked.add(StringTag.valueOf(newId));
         }

         sub.put("wisdom_crystal_locked", locked);
      }
   }
}
