package com.sjyueluo.tensura_skill_books.util;

import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import com.sjyueluo.tensura_skill_books.client.icon.TensuraSkillIconResolver;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class SkillItemNbt {
   private static final String TAG_RANDOM_CATEGORY = "random_category";

   private SkillItemNbt() {
   }

   public static ResourceLocation getSkillId(ItemStack stack) {
      if (stack != null && !stack.isEmpty()) {
         CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
         if (!customData.contains("skill_id")) {
            return null;
         }

         CompoundTag tag = customData.copyTag();
         return ResourceLocation.tryParse(tag.getString("skill_id"));
      } else {
         return null;
      }
   }

   public static void setSkillId(ItemStack stack, ResourceLocation skillId) {
      if (stack != null && !stack.isEmpty()) {
         if (skillId == null) {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.remove("skill_id"));
         } else {
            CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putString("skill_id", skillId.toString()));
         }
      }
   }

   public static SkillCategory getRandomCategory(ItemStack stack) {
      if (stack != null && !stack.isEmpty()) {
         CustomData customData = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
         if (!customData.contains("random_category")) {
            return null;
         }

         CompoundTag tag = customData.copyTag();

         try {
            return SkillCategory.valueOf(tag.getString("random_category"));
         } catch (IllegalArgumentException ignored) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static void setRandomCategory(ItemStack stack, SkillCategory category) {
      if (stack != null && !stack.isEmpty()) {
         CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> {
            tag.remove("skill_id");
            if (category == null) {
               tag.remove("random_category");
            } else {
               tag.putString("random_category", category.name());
            }
         });
      }
   }

   public static ResourceLocation getRenderIconId(ItemStack stack) {
      ResourceLocation skillId = getSkillId(stack);
      if (skillId != null) {
         return skillId;
      }

      SkillCategory category = getRandomCategory(stack);
      return category != null ? TensuraSkillIconResolver.iconIdForCategory(category) : null;
   }
}
