package com.sjyueluo.tensura_skill_books.registry;

import com.sjyueluo.tensura_skill_books.catalog.SkillCatalog;
import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import com.sjyueluo.tensura_skill_books.catalog.SkillEntry;
import com.sjyueluo.tensura_skill_books.client.icon.TensuraSkillIconResolver;
import com.sjyueluo.tensura_skill_books.util.SkillItemNbt;
import com.sjyueluo.tensura_skill_books.util.StudyBookStackFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {
   private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "tensura_skill_books");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COMBAT = registerTab(
      "combat", TensuraSkillIconResolver.TAB_ICON_COMBAT, SkillCategory.COMBAT
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> UNIQUE = registerTab(
      "unique", TensuraSkillIconResolver.TAB_ICON_UNIQUE, SkillCategory.UNIQUE
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ULTIMATE = registerTab(
      "ultimate", TensuraSkillIconResolver.TAB_ICON_ULTIMATE, SkillCategory.ULTIMATE
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXTRA = registerTab(
      "extra", TensuraSkillIconResolver.TAB_ICON_EXTRA, SkillCategory.EXTRA
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COMMON = registerTab(
      "common", TensuraSkillIconResolver.TAB_ICON_COMMON, SkillCategory.COMMON
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> RESISTANCE = registerTab(
      "resistance", TensuraSkillIconResolver.TAB_ICON_RESISTANCE, SkillCategory.RESISTANCE
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> INTRINSIC = registerTab(
      "intrinsic", TensuraSkillIconResolver.TAB_ICON_INTRINSIC, SkillCategory.INTRINSIC
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAGIC_ASPECTUAL = registerTab(
      "magic_aspectual", TensuraSkillIconResolver.TAB_ICON_MAGIC_ASPECTUAL, SkillCategory.MAGIC_ASPECTUAL
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAGIC_SPIRITUAL = registerTab(
      "magic_spiritual", TensuraSkillIconResolver.TAB_ICON_MAGIC_SPIRITUAL, SkillCategory.MAGIC_SPIRITUAL
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAGIC_SUMMONING = registerTab(
      "magic_summoning", TensuraSkillIconResolver.TAB_ICON_MAGIC_SUMMONING, SkillCategory.MAGIC_SUMMONING
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAGIC_MISC = registerTab(
      "magic_misc", TensuraSkillIconResolver.TAB_ICON_MAGIC_MISC, SkillCategory.MAGIC_MISC
   );
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MISC = TABS.register(
      "misc",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.tensura_skill_books.misc"))
         .icon(() -> tabIcon(TensuraSkillIconResolver.TAB_ICON_ALL))
         .displayItems((parameters, output) -> addBooks(output, SkillCategory.MISC))
         .build()
   );

   private ModCreativeTabs() {
   }

   public static void register(IEventBus modBus) {
      TABS.register(modBus);
   }

   public static CreativeModeTab tabFor(SkillCategory category) {
      return switch (category) {
         case COMBAT -> (CreativeModeTab)COMBAT.get();
         case UNIQUE -> (CreativeModeTab)UNIQUE.get();
         case ULTIMATE -> (CreativeModeTab)ULTIMATE.get();
         case EXTRA -> (CreativeModeTab)EXTRA.get();
         case COMMON -> (CreativeModeTab)COMMON.get();
         case RESISTANCE -> (CreativeModeTab)RESISTANCE.get();
         case INTRINSIC -> (CreativeModeTab)INTRINSIC.get();
         case MAGIC_ASPECTUAL -> (CreativeModeTab)MAGIC_ASPECTUAL.get();
         case MAGIC_SPIRITUAL -> (CreativeModeTab)MAGIC_SPIRITUAL.get();
         case MAGIC_SUMMONING -> (CreativeModeTab)MAGIC_SUMMONING.get();
         case MAGIC_MISC -> (CreativeModeTab)MAGIC_MISC.get();
         default -> (CreativeModeTab)MISC.get();
      };
   }

   private static DeferredHolder<CreativeModeTab, CreativeModeTab> registerTab(String name, ResourceLocation pseudoId, SkillCategory category) {
      return TABS.register(
         name,
         () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tensura_skill_books." + name))
            .icon(() -> tabIcon(pseudoId))
            .displayItems((parameters, output) -> addBooks(output, category))
            .build()
      );
   }

   private static void addBooks(Output output, SkillCategory category) {
      for (SkillEntry entry : SkillCatalog.all()) {
         if (category == SkillCategory.MISC ? entry.category() != SkillCategory.ALL && tabFor(entry.category()) == MISC.get() : entry.category() == category) {
            output.accept(StudyBookStackFactory.skillBook(entry), tabVisibility(category));
         }
      }

      if (category == SkillCategory.MISC) {
         for (ItemStack stack : StudyBookStackFactory.randomBooks()) {
            output.accept(stack);
         }

         for (SkillEntry entry : SkillCatalog.all()) {
            if (entry.category() != SkillCategory.ALL && tabFor(entry.category()) != MISC.get()) {
               output.accept(StudyBookStackFactory.skillBook(entry), TabVisibility.SEARCH_TAB_ONLY);
            }
         }
      }
   }

   private static TabVisibility tabVisibility(SkillCategory category) {
      return category == SkillCategory.MISC ? TabVisibility.PARENT_AND_SEARCH_TABS : TabVisibility.PARENT_TAB_ONLY;
   }

   private static ItemStack tabIcon(ResourceLocation pseudoId) {
      ItemStack stack = new ItemStack((ItemLike)ModItems.SKILL_STUDY_BOOK.get());
      SkillItemNbt.setSkillId(stack, pseudoId);
      return stack;
   }
}
