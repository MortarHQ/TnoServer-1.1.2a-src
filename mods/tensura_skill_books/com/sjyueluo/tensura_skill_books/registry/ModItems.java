package com.sjyueluo.tensura_skill_books.registry;

import com.sjyueluo.tensura_skill_books.item.SkillStudyBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public final class ModItems {
   private static final Items ITEMS = DeferredRegister.createItems("tensura_skill_books");
   public static final DeferredItem<Item> SKILL_STUDY_BOOK = ITEMS.registerItem(
      "skill_study_book", SkillStudyBookItem::new, SkillStudyBookItem.defaultProperties(Rarity.RARE)
   );

   private ModItems() {
   }

   public static void register(IEventBus modBus) {
      ITEMS.register(modBus);
   }
}
