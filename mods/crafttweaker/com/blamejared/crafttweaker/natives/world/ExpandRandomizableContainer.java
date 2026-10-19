package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.storage.loot.LootTable;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/RandomizableContainer")
@NativeTypeRegistration(value = RandomizableContainer.class, zenCodeName = "crafttweaker.api.world.RandomizableContainer")
public class ExpandRandomizableContainer {
   @ZenCodeType.Method
   @ZenCodeType.Getter("lootTable")
   public static @ZenCodeType.Nullable ResourceKey<LootTable> getLootTable(RandomizableContainer internal) {
      return internal.getLootTable();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("lootTable")
   public static void setLootTable(RandomizableContainer internal, ResourceKey<LootTable> lootTable) {
      internal.setLootTable(lootTable);
   }

   @ZenCodeType.Method
   public static void setLootTable(RandomizableContainer internal, ResourceKey<LootTable> lootTable, long seed) {
      internal.setLootTable(lootTable, seed);
   }

   @ZenCodeType.Method
   public static void setLootTable(RandomizableContainer internal, ResourceLocation lootTable) {
      internal.setLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTable));
   }

   @ZenCodeType.Method
   public static void setLootTable(RandomizableContainer internal, ResourceLocation lootTable, long seed) {
      internal.setLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTable), seed);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("lootSeed")
   public static long getLootTableSeed(RandomizableContainer internal) {
      return internal.getLootTableSeed();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("lootSeed")
   public static void setLootTableSeed(RandomizableContainer internal, long seed) {
      internal.setLootTableSeed(seed);
   }
}
