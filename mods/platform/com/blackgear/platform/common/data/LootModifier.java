package com.blackgear.platform.common.data;

import com.blackgear.platform.common.data.neoforge.LootModifierImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public class LootModifier {
   @ExpectPlatform
   @Transformed
   public static void modify(LootModifier.LootTableModifier modifier) {
      LootModifierImpl.modify(modifier);
   }

   public interface LootTableContext {
      void addPool(Builder var1);

      boolean addToPool(int var1, ArrayList<LootPoolEntryContainer> var2);

      default boolean addToPool(ArrayList<LootPoolEntryContainer> content) {
         return this.addToPool(0, content);
      }

      default boolean addToPool(int index, LootPoolEntryContainer... content) {
         ArrayList<LootPoolEntryContainer> entries = new ArrayList<>();
         Collections.addAll(entries, content);
         return this.addToPool(index, entries);
      }

      default boolean addToPool(LootPoolEntryContainer... content) {
         return this.addToPool(0, content);
      }
   }

   public interface LootTableModifier {
      void modify(ResourceKey<LootTable> var1, LootModifier.LootTableContext var2, boolean var3);
   }
}
