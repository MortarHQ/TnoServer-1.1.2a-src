package noobanidus.mods.lootr.common.impl.adapter;

import com.google.auto.service.AutoService;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import org.jetbrains.annotations.Nullable;

@AutoService(ILootrDataAdapter.class)
public class RandomizableContainerBlockEntityAdapter implements ILootrDataAdapter<RandomizableContainerBlockEntity> {
   @Override
   public Class<RandomizableContainerBlockEntity> getAssignableClass() {
      return RandomizableContainerBlockEntity.class;
   }

   public ResourceKey<LootTable> getLootTable(RandomizableContainerBlockEntity entity) {
      return entity.getLootTable();
   }

   public long getLootSeed(RandomizableContainerBlockEntity entity) {
      return entity.getLootTableSeed();
   }

   public void setLootTable(RandomizableContainerBlockEntity entity, @Nullable ResourceKey<LootTable> table, long seed) {
      entity.setLootTable(table, seed);
   }

   @Nullable
   public NonNullList<ItemStack> getInventoryCopy(RandomizableContainerBlockEntity entity) {
      if (this.getLootTable(entity) != null) {
         return null;
      }

      NonNullList<ItemStack> result = NonNullList.withSize(entity.getContainerSize(), ItemStack.EMPTY);

      for (int i = 0; i < entity.getContainerSize(); i++) {
         result.set(i, entity.getItem(i).copy());
      }

      return result;
   }

   @Override
   public int priority() {
      return -100;
   }
}
