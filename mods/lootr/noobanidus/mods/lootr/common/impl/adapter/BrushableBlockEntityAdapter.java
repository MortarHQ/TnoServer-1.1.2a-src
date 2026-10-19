package noobanidus.mods.lootr.common.impl.adapter;

import com.google.auto.service.AutoService;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import noobanidus.mods.lootr.common.mixin.accessor.AccessorMixinBrushableBlockEntity;
import org.jetbrains.annotations.Nullable;

@AutoService(ILootrDataAdapter.class)
public class BrushableBlockEntityAdapter implements ILootrDataAdapter<BrushableBlockEntity> {
   @Override
   public Class<BrushableBlockEntity> getAssignableClass() {
      return BrushableBlockEntity.class;
   }

   public ResourceKey<LootTable> getLootTable(BrushableBlockEntity entity) {
      return ((AccessorMixinBrushableBlockEntity)entity).lootr$getLootTable();
   }

   public long getLootSeed(BrushableBlockEntity entity) {
      return ((AccessorMixinBrushableBlockEntity)entity).lootr$getLootTableSeed();
   }

   public void setLootTable(BrushableBlockEntity entity, ResourceKey<LootTable> table, long seed) {
      entity.setLootTable(table, seed);
   }

   @Nullable
   public NonNullList<ItemStack> getInventoryCopy(BrushableBlockEntity entity) {
      if (this.getLootTable(entity) == null) {
         NonNullList<ItemStack> result = NonNullList.withSize(1, ItemStack.EMPTY);
         result.set(0, entity.getItem().copy());
         return result;
      } else {
         return null;
      }
   }
}
