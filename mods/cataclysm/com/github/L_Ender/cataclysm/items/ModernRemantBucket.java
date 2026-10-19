package com.github.L_Ender.cataclysm.items;

import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;

public class ModernRemantBucket extends MobBucketItem {
   public ModernRemantBucket(EntityType<?> fishTypeIn, Fluid fluid, Properties builder) {
      super(fishTypeIn, fluid, SoundEvents.BUCKET_EMPTY_FISH, builder.stacksTo(1));
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
      ItemStack itemStack = player.getItemInHand(interactionHand);
      BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, net.minecraft.world.level.ClipContext.Fluid.NONE);
      if (blockHitResult.getType() == Type.MISS) {
         return InteractionResultHolder.pass(itemStack);
      }

      if (blockHitResult.getType() != Type.BLOCK) {
         return InteractionResultHolder.pass(itemStack);
      }

      BlockPos blockPos = blockHitResult.getBlockPos();
      if (level.mayInteract(player, blockPos)) {
         this.checkExtraContent(player, level, itemStack, blockPos);
         if (player instanceof ServerPlayer) {
            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
         }

         player.awardStat(Stats.ITEM_USED.get(this));
         return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemStack, player), level.isClientSide());
      } else {
         return InteractionResultHolder.fail(itemStack);
      }
   }

   public boolean emptyContents(@Nullable Player player, Level level, BlockPos blockPos, @Nullable BlockHitResult blockHitResult) {
      BlockState blockstate = level.getBlockState(blockPos);
      if (!blockstate.isAir() && !blockstate.canBeReplaced(Fluids.EMPTY)) {
         return false;
      }

      this.playEmptySound(player, level, blockPos);
      return true;
   }
}
