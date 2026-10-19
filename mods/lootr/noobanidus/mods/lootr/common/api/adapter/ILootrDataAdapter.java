package noobanidus.mods.lootr.common.api.adapter;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

public non-sealed interface ILootrDataAdapter<T> extends ILootrAdapter<T> {
   @Nullable
   ResourceKey<LootTable> getLootTable(T var1);

   long getLootSeed(T var1);

   void setLootTable(T var1, @Nullable ResourceKey<LootTable> var2, long var3);

   default boolean hasCopyableComponentsViaItem(T entity) {
      return false;
   }

   @Nullable
   default NonNullList<ItemStack> getInventoryCopy(T entity) {
      return null;
   }

   default void clear(T entity) {
      Clearable.tryClear(entity);
      this.setLootTable(entity, null, 0L);
   }
}
