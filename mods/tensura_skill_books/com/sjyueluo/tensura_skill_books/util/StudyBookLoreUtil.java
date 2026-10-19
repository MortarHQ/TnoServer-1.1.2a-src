package com.sjyueluo.tensura_skill_books.util;

import com.sjyueluo.tensura_skill_books.catalog.SkillCatalog;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import java.util.List;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;

public final class StudyBookLoreUtil {
   private static final String LEGACY_MARKER_1 = "study skill id";
   private static final String LEGACY_MARKER_2 = "skill id:";

   private StudyBookLoreUtil() {
   }

   public static void ensureLore(ItemStack stack, ResourceLocation skillId) {
      if (stack != null && !stack.isEmpty() && skillId != null) {
         ItemLore existingLore = (ItemLore)stack.getOrDefault(DataComponents.LORE, ItemLore.EMPTY);
         if (!existingLore.lines().isEmpty()) {
            String first = ((Component)existingLore.lines().get(0)).getString();
            if (first.contains(skillId.toString()) && !containsLegacyMarkers(first)) {
               return;
            }
         }

         Object skill = SkillCatalog.get(skillId);
         Component name = (Component)(skill != null ? SkillCompat.getSkillName(skill) : Component.literal(SkillNameUtil.prettyTitleFromPath(skillId.getPath())));
         Component line = Component.empty()
            .append(name)
            .append(Component.literal(" (" + skillId + ")"))
            .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true));
         stack.set(DataComponents.LORE, new ItemLore(List.of(line)));
      }
   }

   public static void ensureRandomLore(ItemStack stack, SkillCategory category) {
      if (stack != null && !stack.isEmpty() && category != null) {
         ItemLore existingLore = (ItemLore)stack.getOrDefault(DataComponents.LORE, ItemLore.EMPTY);
         Component name = StudyBookStackFactory.randomCategoryName(category);
         if (existingLore.lines().isEmpty() || !((Component)existingLore.lines().get(0)).getString().equals(name.getString())) {
            Component line = Component.empty().append(name).setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY).withItalic(true));
            stack.set(DataComponents.LORE, new ItemLore(List.of(line)));
         }
      }
   }

   private static boolean containsLegacyMarkers(String json) {
      if (json == null) {
         return false;
      }

      String lower = json.toLowerCase(Locale.ROOT);
      return lower.contains("lord") || lower.contains("study skill id") || lower.contains("skill id:");
   }
}
