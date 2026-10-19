package com.playernbtftb.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class NBTUtils {
   public static boolean compoundEquals(CompoundTag requiredNbt, CompoundTag entityNbt) {
      for (String key : requiredNbt.getAllKeys()) {
         Tag requiredTag = requiredNbt.get(key);
         if (requiredTag.getId() == 10) {
            if (!entityNbt.contains(key, 10)) {
               return false;
            }

            CompoundTag entityCompound = entityNbt.getCompound(key);
            if (!compoundEquals((CompoundTag)requiredTag, entityCompound)) {
               return false;
            }
         } else if (requiredTag.getId() == 9) {
            if (!entityNbt.contains(key, 9)) {
               return false;
            }

            byte listType = ((ListTag)requiredTag).getElementType();
            ListTag entityList = entityNbt.getList(key, listType);
            if (!listEquals((ListTag)requiredTag, entityList)) {
               return false;
            }
         } else if (!requiredTag.equals(entityNbt.get(key))) {
            return false;
         }
      }

      return true;
   }

   private static boolean listEquals(ListTag requiredList, ListTag entityList) {
      if (entityList.size() < requiredList.size()) {
         return false;
      }

      for (int i = 0; i < requiredList.size(); i++) {
         Tag requiredTag = requiredList.get(i);
         boolean found = false;

         for (int j = 0; j < entityList.size() && !found; j++) {
            Tag entityTag = entityList.get(j);
            if (requiredTag.getId() == 10 && entityTag.getId() == 10) {
               if (compoundEquals((CompoundTag)requiredTag, (CompoundTag)entityTag)) {
                  found = true;
               }
            } else if (requiredTag.getId() == 9 && entityTag.getId() == 9) {
               if (listEquals((ListTag)requiredTag, (ListTag)entityTag)) {
                  found = true;
               }
            } else if (requiredTag.equals(entityTag)) {
               found = true;
            }
         }

         if (!found) {
            return false;
         }
      }

      return true;
   }
}
