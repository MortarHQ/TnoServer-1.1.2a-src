package com.github.ysbbbbbb.kaleidoscopecookery.mixin;

import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.SteamerBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen.SteamerBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {
   @Inject(
      method = "tick",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"
      ),
      cancellable = true
   )
   private void onSpawnAtLocation(CallbackInfo ci) {
      FallingBlockEntity self = (FallingBlockEntity)this;
      BlockState blockState = self.getBlockState();
      if (blockState.is((Block)ModBlocks.STEAMER.get())) {
         ci.cancel();

         for (ItemStack drop : this.dropAsItem(blockState, self.blockData, self.level())) {
            self.spawnAtLocation(drop);
         }
      }
   }

   public List<ItemStack> dropAsItem(BlockState blockState, @Nullable CompoundTag steamerTag, Level level) {
      NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
      int[] cookingProgress = new int[8];
      int[] cookingTime = new int[8];
      if (steamerTag != null) {
         if (steamerTag.contains("Items", 9)) {
            ContainerHelper.loadAllItems(steamerTag, items, level.registryAccess());
         }

         if (steamerTag.contains("CookingProgress", 11)) {
            cookingProgress = steamerTag.getIntArray("CookingProgress");
         }

         if (steamerTag.contains("CookingTime", 11)) {
            cookingTime = steamerTag.getIntArray("CookingTime");
         }
      }

      List<ItemStack> drops = Lists.newArrayList();
      boolean half = (Boolean)blockState.getValue(SteamerBlock.HALF);
      ItemStack first = ((Item)ModItems.STEAMER.get()).getDefaultInstance();
      if (items.stream().allMatch(ItemStack::isEmpty)) {
         drops.add(first);
         if (!half) {
            drops.add(((Item)ModItems.STEAMER.get()).getDefaultInstance());
         }

         return drops;
      } else {
         CompoundTag tag1 = new CompoundTag();
         CompoundTag tag2 = new CompoundTag();
         SteamerBlockEntity.saveSplit(tag1, tag2, level, items, cookingProgress, cookingTime);
         BlockItem.setBlockEntityData(first, ModBlocks.STEAMER_BE.get(), tag1);
         drops.add(first);
         if (!half) {
            ItemStack second = ((Item)ModItems.STEAMER.get()).getDefaultInstance();
            BlockItem.setBlockEntityData(second, ModBlocks.STEAMER_BE.get(), tag2);
            drops.add(second);
         }

         return drops;
      }
   }
}
