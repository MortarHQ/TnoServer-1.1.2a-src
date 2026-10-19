package net.darkhax.bookshelf.common.api.loot;

import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.registry.register.RegisterLootDescription;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.bookshelf.common.impl.data.loot.entries.LootItemStack;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorCompositeEntryBase;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorLootItem;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorLootPool;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorLootTable;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorNestedLootTable;
import net.darkhax.bookshelf.common.mixin.access.loot.AccessorTagEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import org.jetbrains.annotations.NotNull;

public class LootPoolEntryDescriptions {
   private static final CachedSupplier<ItemStack> UNKNOWN_ITEM_DISPLAY = CachedSupplier.cache(
      () -> {
         ItemStack stack = new ItemStack(Items.STRUCTURE_VOID);
         stack.set(DataComponents.ITEM_NAME, Component.translatable("tooltips.bookshelf.loot.unknown"));
         stack.set(
            DataComponents.LORE,
            new ItemLore(List.of(), List.of(Component.translatable("tooltips.bookshelf.loot.unknown.desc").withStyle(ChatFormatting.GRAY)))
         );
         return stack;
      }
   );
   private static final CachedSupplier<ItemStack> EMPTY_ITEM_DISPLAY = CachedSupplier.cache(
      () -> {
         ItemStack stack = new ItemStack(Items.BARRIER);
         stack.set(DataComponents.ITEM_NAME, Component.translatable("tooltips.bookshelf.loot.empty"));
         stack.set(
            DataComponents.LORE, new ItemLore(List.of(), List.of(Component.translatable("tooltips.bookshelf.loot.empty.desc").withStyle(ChatFormatting.GRAY)))
         );
         return stack;
      }
   );
   private static final CachedSupplier<ItemStack> DYNAMIC_DISPLAY = CachedSupplier.cache(
      () -> {
         ItemStack stack = new ItemStack(Items.JIGSAW);
         stack.set(DataComponents.ITEM_NAME, Component.translatable("tooltips.bookshelf.loot.dynamic"));
         stack.set(
            DataComponents.LORE,
            new ItemLore(List.of(), List.of(Component.translatable("tooltips.bookshelf.loot.dynamic.desc").withStyle(ChatFormatting.GRAY)))
         );
         return stack;
      }
   );
   private static final Map<LootPoolEntryType, LootPoolEntryDescriber> DESCRIBERS = new HashMap<>();
   private static boolean hasInitialized = false;
   public static final LootPoolEntryDescriber EMPTY = (server, entry, collector) -> {
      if (entry instanceof EmptyLootItem) {
         collector.accept(EMPTY_ITEM_DISPLAY.get());
      }
   };
   public static final LootPoolEntryDescriber ITEM = (server, entry, collector) -> {
      if (entry instanceof AccessorLootItem accessor) {
         collector.accept(new ItemStack(accessor.bookshelf$item()));
      }
   };
   public static final LootPoolEntryDescriber LOOT_TABLE = (server, entry, collector) -> {
      if (entry instanceof AccessorNestedLootTable accessor) {
         getPotentialItems(server, accessor.bookshelf$contents(), collector);
      }
   };
   public static final LootPoolEntryDescriber DYNAMIC = (server, entry, collector) -> {
      if (entry instanceof DynamicLoot) {
         collector.accept(DYNAMIC_DISPLAY.get());
      }
   };
   public static final LootPoolEntryDescriber TAG = (server, entry, collector) -> {
      if (entry instanceof AccessorTagEntry tagEntry) {
         getTagItems(tagEntry.bookshelf$tag(), collector);
      }
   };
   public static final LootPoolEntryDescriber COMPOSITE = (server, entry, collector) -> {
      if (entry instanceof AccessorCompositeEntryBase accessor) {
         getPotentialItems(server, accessor.bookshelf$children(), collector);
      }
   };
   public static final LootPoolEntryDescriber ITEM_STACK = (server, entry, collector) -> {
      if (entry instanceof LootItemStack loot) {
         collector.accept(loot.getBaseStack());
      }
   };

   private static void bootstrap() {
      if (!hasInitialized) {
         RegisterLootDescription register = new RegisterLootDescription(DESCRIBERS::put);
         Services.CONTENT_PROVIDERS.get().forEach(provider -> provider.registerLootDescriptions(register));
         hasInitialized = true;
      }
   }

   public static List<ItemStack> getUniqueItems(@NotNull RegistryAccess registries, LootTable table) {
      List<ItemStack> items = new ArrayList<>();
      getPotentialItems(registries, table, stack -> addStacking(items, stack));
      return items;
   }

   public static void getPotentialItems(@NotNull RegistryAccess registries, Either<ResourceKey<LootTable>, LootTable> table, Consumer<ItemStack> consumer) {
      LootTable resolved = (LootTable)table.map(rl -> (LootTable)registries.registryOrThrow(Registries.LOOT_TABLE).get(rl), Function.identity());
      if (resolved != null) {
         getPotentialItems(registries, resolved, consumer);
      }
   }

   public static void getPotentialItems(@NotNull RegistryAccess registries, LootTable table, Consumer<ItemStack> consumer) {
      if (table instanceof AccessorLootTable tableAccess) {
         for (LootPool pool : tableAccess.bookshelf$pools()) {
            if (pool instanceof AccessorLootPool poolAccess) {
               getPotentialItems(registries, poolAccess.bookshelf$entries(), consumer);
            }
         }
      }
   }

   public static void getPotentialItems(@NotNull RegistryAccess registries, List<LootPoolEntryContainer> entries, Consumer<ItemStack> collector) {
      for (LootPoolEntryContainer entry : entries) {
         getPotentialItems(registries, entry, collector);
      }
   }

   public static void getPotentialItems(@NotNull RegistryAccess registries, LootPoolEntryContainer entry, Consumer<ItemStack> collector) {
      bootstrap();
      LootPoolEntryDescriber describer = DESCRIBERS.get(entry.getType());
      if (describer != null) {
         describer.getPotentialDrops(registries, entry, collector);
      } else {
         collector.accept(UNKNOWN_ITEM_DISPLAY.get());
      }
   }

   private static void addStacking(List<ItemStack> items, ItemStack toAdd) {
      for (ItemStack existing : items) {
         if (Objects.equals(existing, toAdd) || ItemStack.isSameItemSameComponents(existing, toAdd)) {
            return;
         }
      }

      items.add(toAdd);
   }

   private static void getTagItems(TagKey<Item> tag, Consumer<ItemStack> collector) {
      for (Holder<Item> item : BuiltInRegistries.ITEM.getTagOrEmpty(tag)) {
         collector.accept(new ItemStack(item));
      }
   }
}
