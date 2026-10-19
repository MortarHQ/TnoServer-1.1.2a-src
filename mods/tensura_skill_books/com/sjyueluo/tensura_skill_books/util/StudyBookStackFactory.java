package com.sjyueluo.tensura_skill_books.util;

import com.sjyueluo.tensura_skill_books.TensuraSkillBooksConfig;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import com.sjyueluo.tensura_skill_books.catalog.SkillEntry;
import com.sjyueluo.tensura_skill_books.registry.ModItems;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class StudyBookStackFactory {
   private StudyBookStackFactory() {
   }

   public static ItemStack skillBook(SkillEntry entry) {
      ItemStack stack = base();
      SkillItemNbt.setSkillId(stack, entry.id());
      stack.set(
         DataComponents.CUSTOM_NAME,
         Component.translatable("item.tensura_skill_books.skill_study_book").append(": ").append(SkillCompat.getSkillName(entry.skill()))
      );
      StudyBookLoreUtil.ensureLore(stack, entry.id());
      return stack;
   }

   public static ItemStack randomBook(SkillCategory category) {
      ItemStack stack = base();
      SkillItemNbt.setRandomCategory(stack, category);
      stack.set(
         DataComponents.CUSTOM_NAME, Component.translatable("item.tensura_skill_books.skill_study_book").append(": ").append(randomCategoryName(category))
      );
      StudyBookLoreUtil.ensureRandomLore(stack, category);
      return stack;
   }

   public static List<ItemStack> randomBooks() {
      List<ItemStack> stacks = new ArrayList<>();

      for (SkillCategory category : SkillCategory.values()) {
         if (category != SkillCategory.MISC && TensuraSkillBooksConfig.randomBookEnabled(category)) {
            stacks.add(randomBook(category));
         }
      }

      return stacks;
   }

   public static Component randomCategoryName(SkillCategory category) {
      return Component.translatable("book.tensura_skill_books.random." + category.name().toLowerCase(Locale.ROOT));
   }

   private static ItemStack base() {
      return ((Item)ModItems.SKILL_STUDY_BOOK.get()).getDefaultInstance();
   }
}
