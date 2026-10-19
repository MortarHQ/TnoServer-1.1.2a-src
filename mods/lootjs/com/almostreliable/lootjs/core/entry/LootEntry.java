package com.almostreliable.lootjs.core.entry;

import com.almostreliable.lootjs.loot.LootConditionList;
import com.almostreliable.lootjs.loot.LootConditionsContainer;
import com.almostreliable.lootjs.loot.LootEntryList;
import com.almostreliable.lootjs.util.DebugInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.CompositeEntryBase;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntries;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.SequentialEntry;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public interface LootEntry extends LootConditionsContainer<LootEntry> {
   List<LootItemCondition> EMPTY_CONDITIONS = List.of();
   List<LootItemFunction> EMPTY_FUNCTIONS = List.of();

   static ItemLootEntry ofItem(Item item) {
      return new ItemLootEntry(new ItemStack(item));
   }

   static ItemLootEntry of(ItemStack itemStack) {
      return new ItemLootEntry(itemStack);
   }

   static ItemLootEntry of(Item item, NumberProvider count) {
      return new ItemLootEntry(item, count);
   }

   static EmptyLootEntry empty() {
      return new EmptyLootEntry();
   }

   static TableReferenceLootEntry reference(ResourceLocation location) {
      return new TableReferenceLootEntry(location);
   }

   static TagLootEntry tag(String tag) {
      return tag(tag, false);
   }

   static TagLootEntry tag(String tag, boolean expand) {
      if (tag.startsWith("#")) {
         tag = tag.substring(1);
      }

      return new TagLootEntry(TagKey.create(Registries.ITEM, ResourceLocation.parse(tag)), expand);
   }

   static CompositeLootEntry alternative(LootEntry... entries) {
      LootEntryList el = new LootEntryList(entries);
      LootConditionList cl = new LootConditionList();
      return new CompositeLootEntry(new AlternativesEntry(el.getElements(), cl.getElements()), el, cl);
   }

   static CompositeLootEntry sequence(LootEntry... entries) {
      LootEntryList el = new LootEntryList(entries);
      LootConditionList cl = new LootConditionList();
      return new CompositeLootEntry(new SequentialEntry(el.getElements(), cl.getElements()), el, cl);
   }

   static CompositeLootEntry group(LootEntry... entries) {
      LootEntryList el = new LootEntryList(entries);
      LootConditionList cl = new LootConditionList();
      return new CompositeLootEntry(new EntryGroup(el.getElements(), cl.getElements()), el, cl);
   }

   static ItemLootEntry testItem(String name) {
      return (ItemLootEntry)ofItem(Items.PAPER).setName(Component.literal(name));
   }

   static EntityPredicate ep(EntityPredicate ep) {
      return ep;
   }

   static CompositeLootEntry ofIngredient(Ingredient ingredient) {
      if (ingredient.isEmpty()) {
         throw new IllegalArgumentException(
            "[LootEntry.ofIngredient()] Invalid ingredient, returning empty group. Consider using `LootEntry.empty()` if you want to create an empty loot entry."
         );
      }

      CompositeLootEntry group = group();
      LootEntryList entries = group.getEntries();

      for (ItemStack item : ingredient.getItems()) {
         entries.add(new ItemLootEntry(item));
      }

      return group;
   }

   static LootEntry ofVanilla(LootPoolEntryContainer vanillaEntry) {
      if (vanillaEntry instanceof LootItem e) {
         return new ItemLootEntry(e);
      } else if (vanillaEntry instanceof TagEntry e) {
         return new TagLootEntry(e);
      } else if (vanillaEntry instanceof EmptyLootItem e) {
         return new EmptyLootEntry(e);
      } else if (vanillaEntry instanceof NestedLootTable e) {
         return new TableReferenceLootEntry(e);
      } else if (vanillaEntry instanceof DynamicLoot e) {
         return new DynamicLootEntry(e);
      } else {
         return vanillaEntry instanceof CompositeEntryBase c ? new CompositeLootEntry(c) : new LootEntry.Unknown(vanillaEntry);
      }
   }

   LootPoolEntryType getVanillaType();

   LootPoolEntryContainer getVanillaEntry();

   LootEntry when(Consumer<LootConditionList> var1);

   LootConditionList getConditions();

   default ResourceLocation getType() {
      ResourceLocation key = BuiltInRegistries.LOOT_POOL_ENTRY_TYPE.getKey(this.getVanillaType());
      if (key == null) {
         throw new IllegalStateException("Could not find key for loot pool entry type " + this.getVanillaType());
      } else {
         return key;
      }
   }

   default void collectDebugInfo(DebugInfo info) {
   }

   default boolean isItem() {
      return this.getVanillaType() == LootPoolEntries.ITEM;
   }

   default boolean isTag() {
      return this.getVanillaType() == LootPoolEntries.TAG;
   }

   default boolean isEmpty() {
      return this.getVanillaType() == LootPoolEntries.EMPTY;
   }

   default boolean isDynamic() {
      return this.getVanillaType() == LootPoolEntries.DYNAMIC;
   }

   default boolean isReference() {
      return this.getVanillaType() == LootPoolEntries.LOOT_TABLE;
   }

   default boolean isSimple() {
      return !this.isComposite();
   }

   default boolean isAlternative() {
      return this.getVanillaType() == LootPoolEntries.ALTERNATIVES;
   }

   default boolean isSequence() {
      return this.getVanillaType() == LootPoolEntries.SEQUENCE;
   }

   default boolean isGroup() {
      return this.getVanillaType() == LootPoolEntries.ALTERNATIVES;
   }

   default boolean isComposite() {
      return this.isAlternative() || this.isSequence() || this.isGroup();
   }

   default ItemStack createRandomItem(LootContext context) {
      ArrayList<LootPoolEntry> entries = new ArrayList<>();
      this.getVanillaEntry().expand(context, entries::add);
      LootPoolEntry randomEntry = entries.isEmpty() ? null : entries.get(context.getRandom().nextInt(entries.size()));
      if (randomEntry == null) {
         return ItemStack.EMPTY;
      }

      ArrayList<ItemStack> loot = new ArrayList<>();
      randomEntry.createItemStack(loot::add, context);
      return loot.isEmpty() ? ItemStack.EMPTY : loot.get(context.getRandom().nextInt(loot.size()));
   }

   record Unknown(LootPoolEntryContainer getVanillaEntry) implements LootEntry {
      @Override
      public LootPoolEntryType getVanillaType() {
         return this.getVanillaEntry().getType();
      }

      @Override
      public LootEntry when(Consumer<LootConditionList> callback) {
         return this;
      }

      @Override
      public LootConditionList getConditions() {
         return new LootConditionList();
      }

      @Override
      public void collectDebugInfo(DebugInfo info) {
         info.add("Unknown entry type: " + this.getType());
      }

      @Override
      public ItemStack createRandomItem(LootContext context) {
         return ItemStack.EMPTY;
      }

      public LootEntry.Unknown addCondition(LootItemCondition condition) {
         return this;
      }
   }
}
