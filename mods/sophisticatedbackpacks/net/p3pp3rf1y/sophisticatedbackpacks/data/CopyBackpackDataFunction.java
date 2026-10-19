package net.p3pp3rf1y.sophisticatedbackpacks.data;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlockEntity;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class CopyBackpackDataFunction implements LootItemFunction {
   private static final CopyBackpackDataFunction INSTANCE = new CopyBackpackDataFunction();
   public static final MapCodec<CopyBackpackDataFunction> CODEC = MapCodec.unit(INSTANCE).stable();

   private CopyBackpackDataFunction() {
   }

   public ItemStack apply(ItemStack stack, LootContext lootContext) {
      BlockEntity be = (BlockEntity)lootContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
      return be instanceof BackpackBlockEntity backpackBlockEntity ? backpackBlockEntity.getBackpackWrapper().getBackpack() : stack;
   }

   public LootItemFunctionType<CopyBackpackDataFunction> getType() {
      return ModItems.COPY_BACKPACK_DATA.get();
   }

   public static CopyBackpackDataFunction.Builder builder() {
      return new CopyBackpackDataFunction.Builder();
   }

   public static class Builder implements net.minecraft.world.level.storage.loot.functions.LootItemFunction.Builder {
      public LootItemFunction build() {
         return new CopyBackpackDataFunction();
      }
   }
}
